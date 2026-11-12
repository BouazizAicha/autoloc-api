package tn.esprit.autolock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratDTO {

    private Long idContrat;
    private String dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;
    private Long idReservation;
}
