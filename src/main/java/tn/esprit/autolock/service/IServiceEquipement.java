package tn.esprit.autolock.service;

import tn.esprit.autolock.domain.Equipement;

import java.util.List;

public interface IServiceEquipement {
    List<Equipement> retrieveAllEquipements();

    Equipement addEquipement(Equipement equipement);

    Equipement updateEquipement(Equipement equipement);

    Equipement retrieveEquipement(Long idEquipement);

    void removeEquipement(Long idEquipement);

    List<Equipement> addEquipements(List<Equipement> equipements);
}
