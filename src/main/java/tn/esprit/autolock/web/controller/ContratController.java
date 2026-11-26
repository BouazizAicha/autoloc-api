package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Contrat;
import tn.esprit.autolock.dto.ContratDTO;
import tn.esprit.autolock.mapper.ContratMapper;
import tn.esprit.autolock.service.IContratService;

import java.util.List;

@Tag(name = "Contrats", description = "Gestion des contrats de location")
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IContratService contratService;
    private final ContratMapper contratMapper;

    @Operation(summary = "Lister les contrats")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<ContratDTO> findAll() {
        return contratMapper.toDtoList(contratService.findAll());
    }

    @Operation(summary = "Consulter un contrat")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public ContratDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return contratMapper.toDto(contratService.findById(id));
    }

    @Operation(summary = "Créer un contrat")
    @ApiResponse(responseCode = "201", description = "Créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDTO create(@RequestBody Contrat contrat) {
        return contratMapper.toDto(contratService.create(contrat));
    }

    @Operation(summary = "Modifier un contrat")
    @ApiResponse(responseCode = "200", description = "Modifié")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public ContratDTO update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
                         @RequestBody Contrat contrat) {
        return contratMapper.toDto(contratService.update(id, contrat));
    }

    @Operation(summary = "Supprimer un contrat")
    @ApiResponse(responseCode = "204", description = "Supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        contratService.deleteById(id);
    }
}
