package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
