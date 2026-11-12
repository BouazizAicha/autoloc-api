package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long idClient;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String dateInscription;
}
