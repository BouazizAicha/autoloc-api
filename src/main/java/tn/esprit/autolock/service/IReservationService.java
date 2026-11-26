package tn.esprit.autolock.service;

import java.util.List;
import tn.esprit.autolock.domain.Reservation;
import tn.esprit.autolock.dto.ReservationResponseDTO;
import tn.esprit.autolock.dto.ReservationWithContratRequestDTO;

public interface IReservationService {

    Reservation create(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Long id, Reservation reservation);

    void deleteById(Long id);

    ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom);

    ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO);
}
