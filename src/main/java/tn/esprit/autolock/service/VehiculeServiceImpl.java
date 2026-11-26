package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.domain.Equipement;
import tn.esprit.autolock.domain.Maintenance;
import tn.esprit.autolock.dto.VehiculeDTO;
import tn.esprit.autolock.mapper.VehiculeMapper;
import tn.esprit.autolock.repository.IEquipementRepository;
import tn.esprit.autolock.repository.IMaintenanceRepository;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public Vehicule create(Vehicule e) {
        if (e.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        valider(e);
        return vehiculeRepository.save(e);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule e) {
        valider(e);
        // Charger l'existant puis recopier : on ne touche ni à l'identifiant ni aux collections liées
        Vehicule existant = findById(id);
        existant.setImmatriculation(e.getImmatriculation());
        existant.setMarque(e.getMarque());
        existant.setModele(e.getModele());
        existant.setCategorie(e.getCategorie());
        existant.setTarifJournalier(e.getTarifJournalier());
        existant.setStatut(e.getStatut());
        if (e.getAgence() != null) {
            existant.setAgence(e.getAgence());
        }
        if (e.getEquipements() != null && !e.getEquipements().isEmpty()) {
            existant.setEquipements(e.getEquipements());
        }
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        // Vehicule est le côté propriétaire de l'association : on ajoute l'équipement à son Set
        vehicule.getEquipements().add(equipement);
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        Maintenance maintenance = maintenanceRepository.findFirstByDescription(description)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance introuvable : " + description));
        Vehicule vehicule = vehiculeRepository.findByImmatriculation(immatriculation)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule introuvable : " + immatriculation));
        // Maintenance est le côté propriétaire (colonne id_vehicule)
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);
        return vehiculeMapper.toDto(vehicule);
    }

    private static void valider(Vehicule e) {
        if (e.getTarifJournalier() != null && e.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }
}
