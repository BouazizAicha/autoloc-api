package tn.esprit.autolock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolock.domain.Maintenance;
import tn.esprit.autolock.repository.MaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceMaintenance implements IServiceMaintenance {

    // Injection de dépendance par constructeur (généré par Lombok grâce à @RequiredArgsConstructor)
    private final MaintenanceRepository maintenanceRepository;

    @Override
    public List<Maintenance> retrieveAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance addMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance updateMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance retrieveMaintenance(Long idMaintenance) {
        return maintenanceRepository.findById(idMaintenance).orElse(null);
    }

    @Override
    public void removeMaintenance(Long idMaintenance) {
        maintenanceRepository.deleteById(idMaintenance);
    }

    @Override
    public List<Maintenance> addMaintenances(List<Maintenance> maintenances) {
        return maintenanceRepository.saveAll(maintenances);
    }
}
