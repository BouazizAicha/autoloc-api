package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Reservation;

import java.time.LocalDate;
import java.util.Optional;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findFirstByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin);
}
