package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}
