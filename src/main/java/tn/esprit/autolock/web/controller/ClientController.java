package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Client;
import tn.esprit.autolock.dto.ClientDTO;
import tn.esprit.autolock.mapper.ClientMapper;
import tn.esprit.autolock.service.IClientService;

import java.util.List;

@Tag(name = "Clients", description = "Gestion des clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    // Le contrôleur dépend de l'interface du service (jamais du repository ni de l'implémentation)
    private final IClientService clientService;
    private final ClientMapper clientMapper;

    @Operation(summary = "Lister les clients")
    @ApiResponse(responseCode = "200", description = "Liste retournée (éventuellement vide)")
    @GetMapping
    public List<ClientDTO> findAll() {
        return clientMapper.toDtoList(clientService.findAll());
    }

    @Operation(summary = "Consulter un client")
    @ApiResponse(responseCode = "200", description = "Trouvé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public ClientDTO findById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        return clientMapper.toDto(clientService.findById(id));
    }

    @Operation(summary = "Créer un client")
    @ApiResponse(responseCode = "201", description = "Créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO create(@RequestBody Client client) {
        return clientMapper.toDto(clientService.create(client));
    }

    @Operation(summary = "Modifier un client")
    @ApiResponse(responseCode = "200", description = "Modifié")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PutMapping("/{id}")
    public ClientDTO update(@Parameter(description = "Identifiant") @PathVariable("id") Long id,
                         @RequestBody Client client) {
        return clientMapper.toDto(clientService.update(id, client));
    }

    @Operation(summary = "Supprimer un client")
    @ApiResponse(responseCode = "204", description = "Supprimé")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable("id") Long id) {
        clientService.deleteById(id);
    }
}
