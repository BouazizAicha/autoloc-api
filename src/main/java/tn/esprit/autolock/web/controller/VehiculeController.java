package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.dto.VehiculeDTO;
import tn.esprit.autolock.mapper.VehiculeMapper;
import tn.esprit.autolock.service.IVehiculeService;

import java.util.List;

@Tag(name = "Gestion des vehicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IVehiculeService vehiculeService;
    private final VehiculeMapper vehiculeMapper;

    @Operation(description = "Récupérer la liste complète (vehicules)")
    @GetMapping
    public List<VehiculeDTO> findAll() {
        return vehiculeMapper.toDtoList(vehiculeService.findAll());
    }

    @Operation(description = "Récupérer un élément par son id")
    @GetMapping("/{id}")
    public VehiculeDTO findById(@PathVariable("id") Long id) {
        return vehiculeMapper.toDto(vehiculeService.findById(id));
    }

    @Operation(description = "Créer un élément (sans identifiant)")
    @PostMapping
    public VehiculeDTO create(@RequestBody Vehicule vehicule) {
        return vehiculeMapper.toDto(vehiculeService.create(vehicule));
    }

    @Operation(description = "Modifier un élément existant")
    @PutMapping("/{id}")
    public VehiculeDTO update(@PathVariable("id") Long id, @RequestBody Vehicule vehicule) {
        return vehiculeMapper.toDto(vehiculeService.update(id, vehicule));
    }

    @Operation(description = "Supprimer un élément par son id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        vehiculeService.deleteById(id);
    }
}
