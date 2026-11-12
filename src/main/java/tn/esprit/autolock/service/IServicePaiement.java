package tn.esprit.autolock.service;

import tn.esprit.autolock.domain.Paiement;

import java.util.List;

public interface IServicePaiement {
    List<Paiement> retrieveAllPaiements();

    Paiement addPaiement(Paiement paiement);

    Paiement updatePaiement(Paiement paiement);

    Paiement retrievePaiement(Long idPaiement);

    void removePaiement(Long idPaiement);

    List<Paiement> addPaiements(List<Paiement> paiements);
}
