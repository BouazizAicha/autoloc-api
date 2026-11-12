package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDTO {

    private Long idMaintenance;
    private String dateDebut;
    private String dateFin;
    private String description;
    private Long idVehicule;
}
