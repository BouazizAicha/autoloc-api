package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Equipement;
import tn.esprit.autolock.dto.EquipementDTO;
import tn.esprit.autolock.mapper.EquipementMapper;
import tn.esprit.autolock.service.IServiceEquipement;

import java.util.List;

@Tag(name = "Gestion des equipements")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServiceEquipement
    private final IServiceEquipement serviceEquipement;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final EquipementMapper equipementMapper;

    @Operation(description = "Récupérer tous les equipements")
    @GetMapping
    public List<EquipementDTO> retrieveAllEquipements() {
        return equipementMapper.toDtoList(serviceEquipement.retrieveAllEquipements());
    }

    @Operation(description = "Récupérer un équipement par son id")
    @GetMapping("/{idEquipement}")
    public EquipementDTO retrieveEquipement(@PathVariable Long idEquipement) {
        return equipementMapper.toDto(serviceEquipement.retrieveEquipement(idEquipement));
    }

    @Operation(description = "Ajouter un équipement")
    @PostMapping
    public EquipementDTO addEquipement(@RequestBody Equipement equipement) {
        return equipementMapper.toDto(serviceEquipement.addEquipement(equipement));
    }

    @Operation(description = "Ajouter plusieurs equipements")
    @PostMapping("/all")
    public List<EquipementDTO> addEquipements(@RequestBody List<Equipement> equipements) {
        return equipementMapper.toDtoList(serviceEquipement.addEquipements(equipements));
    }

    @Operation(description = "Modifier un équipement")
    @PutMapping
    public EquipementDTO updateEquipement(@RequestBody Equipement equipement) {
        return equipementMapper.toDto(serviceEquipement.updateEquipement(equipement));
    }

    @Operation(description = "Supprimer un équipement par son id")
    @DeleteMapping("/{idEquipement}")
    public void removeEquipement(@PathVariable Long idEquipement) {
        serviceEquipement.removeEquipement(idEquipement);
    }
}
