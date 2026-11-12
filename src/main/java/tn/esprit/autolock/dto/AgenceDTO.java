package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgenceDTO {

    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
}
