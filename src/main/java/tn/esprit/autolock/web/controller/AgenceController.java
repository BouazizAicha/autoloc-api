package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Agence;
import tn.esprit.autolock.dto.AgenceDTO;
import tn.esprit.autolock.mapper.AgenceMapper;
import tn.esprit.autolock.service.IAgenceService;

import java.util.List;

@Tag(name = "Agences", description = "Gestion des agences de location")
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IAgenceService agenceService;
    private final AgenceMapper agenceMapper;

    @Operation(summary = "Lister les agences")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<AgenceDTO> findAll() {
        return agenceMapper.toDtoList(agenceService.findAll());
    }

    @Operation(summary = "Consulter une agence")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public AgenceDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return agenceMapper.toDto(agenceService.findById(id));
    }

    @Operation(summary = "Créer une agence")
    @ApiResponse(responseCode = "201", description = "Créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDTO create(@RequestBody Agence agence) {
        return agenceMapper.toDto(agenceService.create(agence));
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponse(responseCode = "200", description = "Modifié")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public AgenceDTO update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
                         @RequestBody Agence agence) {
        return agenceMapper.toDto(agenceService.update(id, agence));
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponse(responseCode = "204", description = "Supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        agenceService.deleteById(id);
    }
}
