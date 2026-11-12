package tn.esprit.autolock.service;

import tn.esprit.autolock.domain.Vehicule;

import java.util.List;

public interface IServiceVehicule {
    List<Vehicule> retrieveAllVehicules();
    Vehicule addVehicule(Vehicule vehicule);
    Vehicule updateVehicule(Vehicule vehicule);
    Vehicule retrieveVehicule(Long idVehicule);
    void removeVehicule(Long idVehicule);
    List<Vehicule> addVehicules(List<Vehicule> vehicules);
}
