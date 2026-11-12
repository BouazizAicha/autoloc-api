package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Equipement;

public interface EquipementRepository extends JpaRepository<Equipement, Long> {
}
