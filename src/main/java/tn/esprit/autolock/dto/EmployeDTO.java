package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autolock.domain.RoleEmploye;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeDTO {

    private Long idEmploye;
    private String nom;
    private String prenom;
    private RoleEmploye role;
    private Long idAgence;
    private String nomAgence;
}
