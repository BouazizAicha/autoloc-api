package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}
