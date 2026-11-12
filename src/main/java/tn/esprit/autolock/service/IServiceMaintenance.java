package tn.esprit.autolock.service;

import tn.esprit.autolock.domain.Maintenance;

import java.util.List;

public interface IServiceMaintenance {
    List<Maintenance> retrieveAllMaintenances();

    Maintenance addMaintenance(Maintenance maintenance);

    Maintenance updateMaintenance(Maintenance maintenance);

    Maintenance retrieveMaintenance(Long idMaintenance);

    void removeMaintenance(Long idMaintenance);

    List<Maintenance> addMaintenances(List<Maintenance> maintenances);
}
