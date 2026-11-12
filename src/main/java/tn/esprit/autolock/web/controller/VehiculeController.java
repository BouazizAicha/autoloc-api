package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Vehicule;
import tn.esprit.autolock.dto.VehiculeDTO;
import tn.esprit.autolock.mapper.VehiculeMapper;
import tn.esprit.autolock.service.IServiceVehicule;

import java.util.List;

@Tag(name = "Gestion des vehicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServiceVehicule
    private final IServiceVehicule serviceVehicule;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final VehiculeMapper vehiculeMapper;

    @Operation(description = "Récupérer tous les vehicules")
    @GetMapping
    public List<VehiculeDTO> retrieveAllVehicules() {
        return vehiculeMapper.toDtoList(serviceVehicule.retrieveAllVehicules());
    }

    @Operation(description = "Récupérer un véhicule par son id")
    @GetMapping("/{idVehicule}")
    public VehiculeDTO retrieveVehicule(@PathVariable Long idVehicule) {
        return vehiculeMapper.toDto(serviceVehicule.retrieveVehicule(idVehicule));
    }

    @Operation(description = "Ajouter un véhicule")
    @PostMapping
    public VehiculeDTO addVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeMapper.toDto(serviceVehicule.addVehicule(vehicule));
    }

    @Operation(description = "Ajouter plusieurs vehicules")
    @PostMapping("/all")
    public List<VehiculeDTO> addVehicules(@RequestBody List<Vehicule> vehicules) {
        return vehiculeMapper.toDtoList(serviceVehicule.addVehicules(vehicules));
    }

    @Operation(description = "Modifier un véhicule")
    @PutMapping
    public VehiculeDTO updateVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeMapper.toDto(serviceVehicule.updateVehicule(vehicule));
    }

    @Operation(description = "Supprimer un véhicule par son id")
    @DeleteMapping("/{idVehicule}")
    public void removeVehicule(@PathVariable Long idVehicule) {
        serviceVehicule.removeVehicule(idVehicule);
    }
}
