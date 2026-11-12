package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Maintenance;
import tn.esprit.autolock.dto.MaintenanceDTO;
import tn.esprit.autolock.mapper.MaintenanceMapper;
import tn.esprit.autolock.service.IServiceMaintenance;

import java.util.List;

@Tag(name = "Gestion des maintenances")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServiceMaintenance
    private final IServiceMaintenance serviceMaintenance;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final MaintenanceMapper maintenanceMapper;

    @Operation(description = "Récupérer tous les maintenances")
    @GetMapping
    public List<MaintenanceDTO> retrieveAllMaintenances() {
        return maintenanceMapper.toDtoList(serviceMaintenance.retrieveAllMaintenances());
    }

    @Operation(description = "Récupérer un maintenance par son id")
    @GetMapping("/{idMaintenance}")
    public MaintenanceDTO retrieveMaintenance(@PathVariable Long idMaintenance) {
        return maintenanceMapper.toDto(serviceMaintenance.retrieveMaintenance(idMaintenance));
    }

    @Operation(description = "Ajouter un maintenance")
    @PostMapping
    public MaintenanceDTO addMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceMapper.toDto(serviceMaintenance.addMaintenance(maintenance));
    }

    @Operation(description = "Ajouter plusieurs maintenances")
    @PostMapping("/all")
    public List<MaintenanceDTO> addMaintenances(@RequestBody List<Maintenance> maintenances) {
        return maintenanceMapper.toDtoList(serviceMaintenance.addMaintenances(maintenances));
    }

    @Operation(description = "Modifier un maintenance")
    @PutMapping
    public MaintenanceDTO updateMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceMapper.toDto(serviceMaintenance.updateMaintenance(maintenance));
    }

    @Operation(description = "Supprimer un maintenance par son id")
    @DeleteMapping("/{idMaintenance}")
    public void removeMaintenance(@PathVariable Long idMaintenance) {
        serviceMaintenance.removeMaintenance(idMaintenance);
    }
}
