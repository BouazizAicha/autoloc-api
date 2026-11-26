package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Contrat;
import tn.esprit.autolock.dto.ContratDTO;
import tn.esprit.autolock.mapper.ContratMapper;
import tn.esprit.autolock.service.IContratService;

import java.util.List;

@Tag(name = "Gestion des contrats")
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IContratService contratService;
    private final ContratMapper contratMapper;

    @Operation(description = "Récupérer la liste complète (contrats)")
    @GetMapping
    public List<ContratDTO> findAll() {
        return contratMapper.toDtoList(contratService.findAll());
    }

    @Operation(description = "Récupérer un élément par son id")
    @GetMapping("/{id}")
    public ContratDTO findById(@PathVariable("id") Long id) {
        return contratMapper.toDto(contratService.findById(id));
    }

    @Operation(description = "Créer un élément (sans identifiant)")
    @PostMapping
    public ContratDTO create(@RequestBody Contrat contrat) {
        return contratMapper.toDto(contratService.create(contrat));
    }

    @Operation(description = "Modifier un élément existant")
    @PutMapping("/{id}")
    public ContratDTO update(@PathVariable("id") Long id, @RequestBody Contrat contrat) {
        return contratMapper.toDto(contratService.update(id, contrat));
    }

    @Operation(description = "Supprimer un élément par son id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        contratService.deleteById(id);
    }
}
