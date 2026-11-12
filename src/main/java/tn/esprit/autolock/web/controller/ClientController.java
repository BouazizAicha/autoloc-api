package tn.esprit.autolock.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autolock.domain.Client;
import tn.esprit.autolock.dto.ClientDTO;
import tn.esprit.autolock.mapper.ClientMapper;
import tn.esprit.autolock.service.IServiceClient;

import java.util.List;

@Tag(name = "Gestion des clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    // Le contrôleur dépend de l'interface du service (et non de son implémentation) : Spring injecte ServiceClient
    private final IServiceClient serviceClient;
    // Le mapper convertit l'entité en DTO avant de répondre au client
    private final ClientMapper clientMapper;

    @Operation(description = "Récupérer tous les clients")
    @GetMapping
    public List<ClientDTO> retrieveAllClients() {
        return clientMapper.toDtoList(serviceClient.retrieveAllClients());
    }

    @Operation(description = "Récupérer un client par son id")
    @GetMapping("/{idClient}")
    public ClientDTO retrieveClient(@PathVariable Long idClient) {
        return clientMapper.toDto(serviceClient.retrieveClient(idClient));
    }

    @Operation(description = "Ajouter un client")
    @PostMapping
    public ClientDTO addClient(@RequestBody Client client) {
        return clientMapper.toDto(serviceClient.addClient(client));
    }

    @Operation(description = "Ajouter plusieurs clients")
    @PostMapping("/all")
    public List<ClientDTO> addClients(@RequestBody List<Client> clients) {
        return clientMapper.toDtoList(serviceClient.addClients(clients));
    }

    @Operation(description = "Modifier un client")
    @PutMapping
    public ClientDTO updateClient(@RequestBody Client client) {
        return clientMapper.toDto(serviceClient.updateClient(client));
    }

    @Operation(description = "Supprimer un client par son id")
    @DeleteMapping("/{idClient}")
    public void removeClient(@PathVariable Long idClient) {
        serviceClient.removeClient(idClient);
    }
}
