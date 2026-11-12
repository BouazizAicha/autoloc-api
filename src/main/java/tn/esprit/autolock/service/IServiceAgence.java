package tn.esprit.autolock.service;

import tn.esprit.autolock.domain.Agence;

import java.util.List;

public interface IServiceAgence {
    List<Agence> retrieveAllAgences();

    Agence addAgence(Agence agence);

    Agence updateAgence(Agence agence);

    Agence retrieveAgence(Long idAgence);

    void removeAgence(Long idAgence);

    List<Agence> addAgences(List<Agence> agences);
}
