package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Maintenance;
import tn.esprit.autolock.dto.MaintenanceDTO;
import tn.esprit.autolock.mapper.MaintenanceMapper;
import tn.esprit.autolock.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Maintenances", description = "Gestion des maintenances des véhicules")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IMaintenanceService maintenanceService;
    private final MaintenanceMapper maintenanceMapper;

    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<MaintenanceDTO> findAll() {
        return maintenanceMapper.toDtoList(maintenanceService.findAll());
    }

    @Operation(summary = "Consulter une maintenance")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public MaintenanceDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return maintenanceMapper.toDto(maintenanceService.findById(id));
    }

    @Operation(summary = "Créer une maintenance")
    @ApiResponse(responseCode = "201", description = "Créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDTO create(@RequestBody Maintenance maintenance) {
        return maintenanceMapper.toDto(maintenanceService.create(maintenance));
    }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponse(responseCode = "200", description = "Modifié")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public MaintenanceDTO update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
                         @RequestBody Maintenance maintenance) {
        return maintenanceMapper.toDto(maintenanceService.update(id, maintenance));
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponse(responseCode = "204", description = "Supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        maintenanceService.deleteById(id);
    }
}
