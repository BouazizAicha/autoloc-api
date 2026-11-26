package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Maintenance;

import java.util.Optional;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Optional<Maintenance> findFirstByDescription(String description);
}
