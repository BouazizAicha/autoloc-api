package tn.esprit.autolock.repository;

import org.springframework.data.repository.Repository;
import tn.esprit.autolock.domain.Client;

interface ClientRepository extends Repository<Client, Long> {
}
