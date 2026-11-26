package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolock.domain.Reservation;
import tn.esprit.autolock.domain.Client;
import tn.esprit.autolock.domain.Contrat;
import tn.esprit.autolock.domain.StatutReservation;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.dto.ReservationResponseDTO;
import tn.esprit.autolock.dto.ReservationWithContratRequestDTO;
import tn.esprit.autolock.mapper.ReservationMapper;
import tn.esprit.autolock.repository.IClientRepository;
import tn.esprit.autolock.repository.IVehiculeRepository;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public Reservation create(Reservation e) {
        if (e.getIdReservation() != null) {
            throw new IllegalArgumentException("Un nouveau reservation ne doit pas avoir d'identifiant");
        }
        valider(e);
        return reservationRepository.save(e);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation e) {
        valider(e);
        // Charger l'existant puis recopier : on ne touche ni à l'identifiant ni aux collections liées
        Reservation existant = findById(id);
        existant.setDateDebut(e.getDateDebut());
        existant.setDateFin(e.getDateFin());
        existant.setStatut(e.getStatut());
        if (e.getClient() != null) {
            existant.setClient(e.getClient());
        }
        if (e.getVehicule() != null) {
            existant.setVehicule(e.getVehicule());
        }
        return reservationRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        Reservation reservation = findById(idReservation);
        Client client = clientRepository.findFirstByNomAndPrenom(nom, prenom)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + nom + " " + prenom));
        reservation.setClient(client);
        return reservationMapper.toResponseDto(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        if (requestDTO.getIdClient() == null || requestDTO.getIdVehicule() == null
                || requestDTO.getDateSignature() == null) {
            throw new IllegalArgumentException("Le client, le véhicule et la date de signature sont obligatoires");
        }
        Client client = clientRepository.findById(requestDTO.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client", requestDTO.getIdClient()));
        Vehicule vehicule = vehiculeRepository.findById(requestDTO.getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", requestDTO.getIdVehicule()));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.getDateDebut());
        reservation.setDateFin(requestDTO.getDateFin());
        reservation.setStatut(requestDTO.getStatut() != null ? requestDTO.getStatut() : StatutReservation.CONFIRMEE);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        valider(reservation);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.getDateSignature());
        contrat.setMontantTotal(requestDTO.getMontantTotal() != null
                ? requestDTO.getMontantTotal()
                : MontantCalculator.calculer(vehicule.getTarifJournalier(), requestDTO.getDateDebut(), requestDTO.getDateFin()));
        contrat.setValide(requestDTO.getValide() == null || requestDTO.getValide());
        contrat.setReservation(reservation);
        reservation.setContrat(contrat);

        // Un seul save : le contrat est enregistré avec la réservation (cascade ALL)
        return reservationMapper.toResponseDto(reservationRepository.save(reservation));
    }

    private static void valider(Reservation e) {
        if (e.getDateDebut() != null && e.getDateFin() != null && e.getDateFin().isBefore(e.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
