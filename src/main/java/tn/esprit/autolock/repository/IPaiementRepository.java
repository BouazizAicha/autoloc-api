package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
