package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
}
