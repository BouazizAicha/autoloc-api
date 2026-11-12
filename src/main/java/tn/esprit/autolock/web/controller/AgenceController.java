package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Agence;
import tn.esprit.autolock.dto.AgenceDTO;
import tn.esprit.autolock.mapper.AgenceMapper;
import tn.esprit.autolock.service.IServiceAgence;

import java.util.List;

@Tag(name = "Gestion des agences")
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServiceAgence
    private final IServiceAgence serviceAgence;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final AgenceMapper agenceMapper;

    @Operation(description = "Récupérer tous les agences")
    @GetMapping
    public List<AgenceDTO> retrieveAllAgences() {
        return agenceMapper.toDtoList(serviceAgence.retrieveAllAgences());
    }

    @Operation(description = "Récupérer un agence par son id")
    @GetMapping("/{idAgence}")
    public AgenceDTO retrieveAgence(@PathVariable Long idAgence) {
        return agenceMapper.toDto(serviceAgence.retrieveAgence(idAgence));
    }

    @Operation(description = "Ajouter un agence")
    @PostMapping
    public AgenceDTO addAgence(@RequestBody Agence agence) {
        return agenceMapper.toDto(serviceAgence.addAgence(agence));
    }

    @Operation(description = "Ajouter plusieurs agences")
    @PostMapping("/all")
    public List<AgenceDTO> addAgences(@RequestBody List<Agence> agences) {
        return agenceMapper.toDtoList(serviceAgence.addAgences(agences));
    }

    @Operation(description = "Modifier un agence")
    @PutMapping
    public AgenceDTO updateAgence(@RequestBody Agence agence) {
        return agenceMapper.toDto(serviceAgence.updateAgence(agence));
    }

    @Operation(description = "Supprimer un agence par son id")
    @DeleteMapping("/{idAgence}")
    public void removeAgence(@PathVariable Long idAgence) {
        serviceAgence.removeAgence(idAgence);
    }
}
