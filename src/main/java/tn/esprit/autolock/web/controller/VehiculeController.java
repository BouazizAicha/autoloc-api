package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.dto.VehiculeDTO;
import tn.esprit.autolock.mapper.VehiculeMapper;
import tn.esprit.autolock.service.IVehiculeService;

import java.util.List;

@Tag(name = "Véhicules", description = "Gestion de la flotte de véhicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IVehiculeService vehiculeService;
    private final VehiculeMapper vehiculeMapper;

    @Operation(summary = "Lister les vehicules")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<VehiculeDTO> findAll() {
        return vehiculeMapper.toDtoList(vehiculeService.findAll());
    }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public VehiculeDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return vehiculeMapper.toDto(vehiculeService.findById(id));
    }

    @Operation(summary = "Créer un véhicule")
    @ApiResponse(responseCode = "201", description = "Créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO create(@RequestBody Vehicule vehicule) {
        return vehiculeMapper.toDto(vehiculeService.create(vehicule));
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Modifié")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public VehiculeDTO update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
                         @RequestBody Vehicule vehicule) {
        return vehiculeMapper.toDto(vehiculeService.update(id, vehicule));
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        vehiculeService.deleteById(id);
    }
}
