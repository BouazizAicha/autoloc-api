package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Client;
import tn.esprit.autolock.dto.ClientDTO;
import tn.esprit.autolock.mapper.ClientMapper;
import tn.esprit.autolock.service.IClientService;

import java.util.List;

@Tag(name = "Gestion des clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    // Le contrôleur dépend de l'interface du service (jamais de son implémentation)
    private final IClientService clientService;
    private final ClientMapper clientMapper;

    @Operation(description = "Récupérer la liste complète (clients)")
    @GetMapping
    public List<ClientDTO> findAll() {
        return clientMapper.toDtoList(clientService.findAll());
    }

    @Operation(description = "Récupérer un élément par son id")
    @GetMapping("/{id}")
    public ClientDTO findById(@PathVariable("id") Long id) {
        return clientMapper.toDto(clientService.findById(id));
    }

    @Operation(description = "Créer un élément (sans identifiant)")
    @PostMapping
    public ClientDTO create(@RequestBody Client client) {
        return clientMapper.toDto(clientService.create(client));
    }

    @Operation(description = "Modifier un élément existant")
    @PutMapping("/{id}")
    public ClientDTO update(@PathVariable("id") Long id, @RequestBody Client client) {
        return clientMapper.toDto(clientService.update(id, client));
    }

    @Operation(description = "Supprimer un élément par son id")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        clientService.deleteById(id);
    }
}
