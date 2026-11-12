package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Contrat;
import tn.esprit.autolock.dto.ContratDTO;
import tn.esprit.autolock.mapper.ContratMapper;
import tn.esprit.autolock.service.IServiceContrat;

import java.util.List;

@Tag(name = "Gestion des contrats")
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServiceContrat
    private final IServiceContrat serviceContrat;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final ContratMapper contratMapper;

    @Operation(description = "Récupérer tous les contrats")
    @GetMapping
    public List<ContratDTO> retrieveAllContrats() {
        return contratMapper.toDtoList(serviceContrat.retrieveAllContrats());
    }

    @Operation(description = "Récupérer un contrat par son id")
    @GetMapping("/{idContrat}")
    public ContratDTO retrieveContrat(@PathVariable Long idContrat) {
        return contratMapper.toDto(serviceContrat.retrieveContrat(idContrat));
    }

    @Operation(description = "Ajouter un contrat")
    @PostMapping
    public ContratDTO addContrat(@RequestBody Contrat contrat) {
        return contratMapper.toDto(serviceContrat.addContrat(contrat));
    }

    @Operation(description = "Ajouter plusieurs contrats")
    @PostMapping("/all")
    public List<ContratDTO> addContrats(@RequestBody List<Contrat> contrats) {
        return contratMapper.toDtoList(serviceContrat.addContrats(contrats));
    }

    @Operation(description = "Modifier un contrat")
    @PutMapping
    public ContratDTO updateContrat(@RequestBody Contrat contrat) {
        return contratMapper.toDto(serviceContrat.updateContrat(contrat));
    }

    @Operation(description = "Supprimer un contrat par son id")
    @DeleteMapping("/{idContrat}")
    public void removeContrat(@PathVariable Long idContrat) {
        serviceContrat.removeContrat(idContrat);
    }
}
