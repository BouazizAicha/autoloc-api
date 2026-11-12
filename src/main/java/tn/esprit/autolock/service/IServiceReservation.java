package tn.esprit.autolock.service;

import tn.esprit.autolock.domain.Reservation;

import java.util.List;

public interface IServiceReservation {
    List<Reservation> retrieveAllReservations();

    Reservation addReservation(Reservation reservation);

    Reservation updateReservation(Reservation reservation);

    Reservation retrieveReservation(Long idReservation);

    void removeReservation(Long idReservation);

    List<Reservation> addReservations(List<Reservation> reservations);
}
