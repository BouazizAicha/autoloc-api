package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
