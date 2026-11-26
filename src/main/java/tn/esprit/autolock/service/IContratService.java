package tn.esprit.autolock.service;

import java.time.LocalDate;
import java.util.List;
import tn.esprit.autolock.domain.Contrat;
import tn.esprit.autolock.dto.ContratDTO;

public interface IContratService {

    Contrat create(Contrat contrat);

    Contrat findById(Long id);

    List<Contrat> findAll();

    Contrat update(Long id, Contrat contrat);

    void deleteById(Long id);

    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
