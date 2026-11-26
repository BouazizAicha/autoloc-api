package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Equipement;
import tn.esprit.autolock.dto.EquipementDTO;
import tn.esprit.autolock.mapper.EquipementMapper;
import tn.esprit.autolock.service.IEquipementService;

import java.util.List;

@Tag(name = "Gestion des equipements")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IEquipementService equipementService;
    private final EquipementMapper equipementMapper;

    @Operation(description = "Récupérer la liste complète (equipements)")
    @GetMapping
    public List<EquipementDTO> findAll() {
        return equipementMapper.toDtoList(equipementService.findAll());
    }

    @Operation(description = "Récupérer un élément par son id")
    @GetMapping("/{id}")
    public EquipementDTO findById(@PathVariable("id") Long id) {
        return equipementMapper.toDto(equipementService.findById(id));
    }

    @Operation(description = "Créer un élément (sans identifiant)")
    @PostMapping
    public EquipementDTO create(@RequestBody Equipement equipement) {
        return equipementMapper.toDto(equipementService.create(equipement));
    }

    @Operation(description = "Modifier un élément existant")
    @PutMapping("/{id}")
    public EquipementDTO update(@PathVariable("id") Long id, @RequestBody Equipement equipement) {
        return equipementMapper.toDto(equipementService.update(id, equipement));
    }

    @Operation(description = "Supprimer un élément par son id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        equipementService.deleteById(id);
    }
}
