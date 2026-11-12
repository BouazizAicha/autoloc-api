package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolock.domain.CategorieVehicule;
import tn.esprit.autolock.domain.StatutVehicule;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeDTO {

    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;
    private CategorieVehicule categorie;
    private BigDecimal tarifJournalier;
    private StatutVehicule statut;
    private Long idAgence;
    private List<String> equipements;
}
