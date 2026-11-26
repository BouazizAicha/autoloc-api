package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Gestion des paiements")
@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IPaiementService paiementService;
    private final PaiementMapper paiementMapper;

    @Operation(description = "Récupérer tous les paiements")
    @GetMapping
    public List<PaiementDTO> findAll() {
        return paiementMapper.toDtoList(paiementService.findAll());
    }

    @Operation(description = "Récupérer un paiement par son id")
    @GetMapping("/{id}")
    public PaiementDTO findById(@PathVariable("id") Long id) {
        return paiementMapper.toDto(paiementService.findById(id));
    }
}
