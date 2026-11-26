package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Agence;
import tn.esprit.autolock.dto.AgenceDTO;
import tn.esprit.autolock.mapper.AgenceMapper;
import tn.esprit.autolock.service.IAgenceService;

import java.util.List;

@Tag(name = "Gestion des agences")
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IAgenceService agenceService;
    private final AgenceMapper agenceMapper;

    @Operation(description = "Récupérer la liste complète (agences)")
    @GetMapping
    public List<AgenceDTO> findAll() {
        return agenceMapper.toDtoList(agenceService.findAll());
    }

    @Operation(description = "Récupérer un élément par son id")
    @GetMapping("/{id}")
    public AgenceDTO findById(@PathVariable("id") Long id) {
        return agenceMapper.toDto(agenceService.findById(id));
    }

    @Operation(description = "Créer un élément (sans identifiant)")
    @PostMapping
    public AgenceDTO create(@RequestBody Agence agence) {
        return agenceMapper.toDto(agenceService.create(agence));
    }

    @Operation(description = "Modifier un élément existant")
    @PutMapping("/{id}")
    public AgenceDTO update(@PathVariable("id") Long id, @RequestBody Agence agence) {
        return agenceMapper.toDto(agenceService.update(id, agence));
    }

    @Operation(description = "Supprimer un élément par son id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        agenceService.deleteById(id);
    }
}
