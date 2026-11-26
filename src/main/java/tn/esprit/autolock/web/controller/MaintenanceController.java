package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Maintenance;
import tn.esprit.autolock.dto.MaintenanceDTO;
import tn.esprit.autolock.mapper.MaintenanceMapper;
import tn.esprit.autolock.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Gestion des maintenances")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IMaintenanceService maintenanceService;
    private final MaintenanceMapper maintenanceMapper;

    @Operation(description = "Récupérer la liste complète (maintenances)")
    @GetMapping
    public List<MaintenanceDTO> findAll() {
        return maintenanceMapper.toDtoList(maintenanceService.findAll());
    }

    @Operation(description = "Récupérer un élément par son id")
    @GetMapping("/{id}")
    public MaintenanceDTO findById(@PathVariable("id") Long id) {
        return maintenanceMapper.toDto(maintenanceService.findById(id));
    }

    @Operation(description = "Créer un élément (sans identifiant)")
    @PostMapping
    public MaintenanceDTO create(@RequestBody Maintenance maintenance) {
        return maintenanceMapper.toDto(maintenanceService.create(maintenance));
    }

    @Operation(description = "Modifier un élément existant")
    @PutMapping("/{id}")
    public MaintenanceDTO update(@PathVariable("id") Long id, @RequestBody Maintenance maintenance) {
        return maintenanceMapper.toDto(maintenanceService.update(id, maintenance));
    }

    @Operation(description = "Supprimer un élément par son id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        maintenanceService.deleteById(id);
    }
}
