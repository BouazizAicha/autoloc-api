package tn.esprit.autolock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autolock.domain.Client;

import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findFirstByNomAndPrenom(String nom, String prenom);
}
