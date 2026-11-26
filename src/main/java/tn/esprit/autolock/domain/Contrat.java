package tn.esprit.autolock.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    @Column(nullable = false)
    private LocalDate dateSignature;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Column(nullable = false)
    private boolean valide;

    // Un contrat correspond à une seule réservation (côté propriétaire : colonne id_reservation, unique)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation", unique = true)
    private Reservation reservation;

    // Un contrat peut être réglé en plusieurs paiements (la clé étrangère est dans paiement)
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Paiement> paiements = new ArrayList<>();
}
