package tn.esprit.autolock.service;

import java.util.List;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.dto.VehiculeDTO;

public interface IVehiculeService {

    Vehicule create(Vehicule vehicule);

    Vehicule findById(Long id);

    List<Vehicule> findAll();

    Vehicule update(Long id, Vehicule vehicule);

    void deleteById(Long id);

    VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement);

    VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation);
}
