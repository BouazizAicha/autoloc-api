package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Paiement;
import tn.esprit.autolock.dto.PaiementDTO;
import tn.esprit.autolock.mapper.PaiementMapper;
import tn.esprit.autolock.service.IServicePaiement;

import java.util.List;

@Tag(name = "Gestion des paiements")
@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServicePaiement
    private final IServicePaiement servicePaiement;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final PaiementMapper paiementMapper;

    @Operation(description = "Récupérer tous les paiements")
    @GetMapping
    public List<PaiementDTO> retrieveAllPaiements() {
        return paiementMapper.toDtoList(servicePaiement.retrieveAllPaiements());
    }

    @Operation(description = "Récupérer un paiement par son id")
    @GetMapping("/{idPaiement}")
    public PaiementDTO retrievePaiement(@PathVariable Long idPaiement) {
        return paiementMapper.toDto(servicePaiement.retrievePaiement(idPaiement));
    }

    @Operation(description = "Ajouter un paiement")
    @PostMapping
    public PaiementDTO addPaiement(@RequestBody Paiement paiement) {
        return paiementMapper.toDto(servicePaiement.addPaiement(paiement));
    }

    @Operation(description = "Ajouter plusieurs paiements")
    @PostMapping("/all")
    public List<PaiementDTO> addPaiements(@RequestBody List<Paiement> paiements) {
        return paiementMapper.toDtoList(servicePaiement.addPaiements(paiements));
    }

    @Operation(description = "Modifier un paiement")
    @PutMapping
    public PaiementDTO updatePaiement(@RequestBody Paiement paiement) {
        return paiementMapper.toDto(servicePaiement.updatePaiement(paiement));
    }

    @Operation(description = "Supprimer un paiement par son id")
    @DeleteMapping("/{idPaiement}")
    public void removePaiement(@PathVariable Long idPaiement) {
        servicePaiement.removePaiement(idPaiement);
    }
}
