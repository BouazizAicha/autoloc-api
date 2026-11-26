package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Equipement;
import tn.esprit.autolock.dto.EquipementDTO;
import tn.esprit.autolock.mapper.EquipementMapper;
import tn.esprit.autolock.service.IEquipementService;

import java.util.List;

@Tag(name = "Équipements", description = "Gestion des équipements des véhicules")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IEquipementService equipementService;
    private final EquipementMapper equipementMapper;

    @Operation(summary = "Lister les equipements")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<EquipementDTO> findAll() {
        return equipementMapper.toDtoList(equipementService.findAll());
    }

    @Operation(summary = "Consulter un équipement")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public EquipementDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return equipementMapper.toDto(equipementService.findById(id));
    }

    @Operation(summary = "Créer un équipement")
    @ApiResponse(responseCode = "201", description = "Créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDTO create(@RequestBody Equipement equipement) {
        return equipementMapper.toDto(equipementService.create(equipement));
    }

    @Operation(summary = "Modifier un équipement")
    @ApiResponse(responseCode = "200", description = "Modifié")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public EquipementDTO update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
                         @RequestBody Equipement equipement) {
        return equipementMapper.toDto(equipementService.update(id, equipement));
    }

    @Operation(summary = "Supprimer un équipement")
    @ApiResponse(responseCode = "204", description = "Supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        equipementService.deleteById(id);
    }
}
