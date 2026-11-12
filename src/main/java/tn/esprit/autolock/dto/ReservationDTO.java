package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autolock.domain.StatutReservation;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Long idReservation;
    private String dateDebut;
    private String dateFin;
    private StatutReservation statut;
    private Long idClient;
    private Long idVehicule;
}
