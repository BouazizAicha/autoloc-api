package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Maintenance;
import tn.esprit.autolock.exception.ResourceNotFoundException;
import tn.esprit.autolock.repository.IMaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    // Injection par constructeur (généré par Lombok) d'une dépendance typée par interface
    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance e) {
        if (e.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Un nouveau maintenance ne doit pas avoir d'identifiant");
        }
        valider(e);
        return maintenanceRepository.save(e);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance e) {
        valider(e);
        // Charger l'existant puis recopier : on ne touche ni à l'identifiant ni aux collections liées
        Maintenance existant = findById(id);
        existant.setDateDebut(e.getDateDebut());
        existant.setDateFin(e.getDateFin());
        existant.setDescription(e.getDescription());
        if (e.getVehicule() != null) {
            existant.setVehicule(e.getVehicule());
        }
        return maintenanceRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private static void valider(Maintenance e) {
        if (e.getDateDebut() != null && e.getDateFin() != null && e.getDateFin().isBefore(e.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
