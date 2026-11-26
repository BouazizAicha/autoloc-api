package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autolock.domain.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationWithContratRequestDTO {

    // Réservation
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
    private Long idClient;
    private Long idVehicule;

    // Contrat associé
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private Boolean valide;
}
