package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import tn.esprit.autolock.domain.ModePaiement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaiementDTO {

    private Long idPaiement;
    private BigDecimal montant;
    private String datePaiement;
    private ModePaiement modePaiement;
    private Long idContrat;
}
