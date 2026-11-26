package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolock.domain.Contrat;
import tn.esprit.autolock.domain.Reservation;
import tn.esprit.autolock.dto.ContratDTO;
import tn.esprit.autolock.mapper.ContratMapper;
import tn.esprit.autolock.repository.IReservationRepository;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IContratRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    public Contrat create(Contrat e) {
        if (e.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        valider(e);
        return contratRepository.save(e);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat e) {
        valider(e);
        // Charger l'existant puis recopier : on ne touche ni à l'identifiant ni aux collections liées
        Contrat existant = findById(id);
        existant.setDateSignature(e.getDateSignature());
        existant.setMontantTotal(e.getMontantTotal());
        existant.setValide(e.isValide());
        if (e.getReservation() != null) {
            existant.setReservation(e.getReservation());
        }
        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        if (dateSignature == null) {
            throw new IllegalArgumentException("La date de signature est obligatoire");
        }
        Reservation reservation = reservationRepository.findFirstByDateDebutAndDateFin(dateDebut, dateFin)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Réservation introuvable pour la période " + dateDebut + " - " + dateFin));
        if (reservation.getContrat() != null) {
            throw new IllegalArgumentException("Cette réservation a déjà un contrat");
        }
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        BigDecimal tarif = reservation.getVehicule() != null ? reservation.getVehicule().getTarifJournalier() : null;
        contrat.setMontantTotal(MontantCalculator.calculer(tarif, dateDebut, dateFin));
        contrat.setValide(true);
        contrat.setReservation(reservation);
        return contratMapper.toDto(contratRepository.save(contrat));
    }

    private static void valider(Contrat e) {
        if (e.getMontantTotal() != null && e.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas être négatif");
        }
    }
}
