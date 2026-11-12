package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Agence;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
}
