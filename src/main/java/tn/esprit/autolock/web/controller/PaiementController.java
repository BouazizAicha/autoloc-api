package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autolock.dto.PaiementDTO;
import tn.esprit.autolock.mapper.PaiementMapper;
import tn.esprit.autolock.service.IPaiementService;

import java.util.List;

@Tag(name = "Paiements", description = "Consultation des paiements (lecture seule : ils passent par le contrat)")
@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IPaiementService paiementService;
    private final PaiementMapper paiementMapper;

    @Operation(summary = "Lister les paiements")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<PaiementDTO> findAll() {
        return paiementMapper.toDtoList(paiementService.findAll());
    }

    @Operation(summary = "Consulter un paiement")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public PaiementDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return paiementMapper.toDto(paiementService.findById(id));
    }
}
