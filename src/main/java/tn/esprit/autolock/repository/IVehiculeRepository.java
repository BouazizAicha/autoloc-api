package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Vehicule;

import java.util.Optional;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    Optional<Vehicule> findByImmatriculation(String immatriculation);
}
