package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Reservation;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IReservationRepository reservationRepository;

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

    private static void valider(Reservation e) {
        if (e.getDateDebut() != null && e.getDateFin() != null && e.getDateFin().isBefore(e.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
