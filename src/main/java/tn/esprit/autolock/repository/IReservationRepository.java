package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
