package tn.esprit.autolock.service;

import java.util.List;
import tn.esprit.autolock.domain.Paiement;

public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}
