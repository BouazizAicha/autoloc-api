package tn.esprit.autolock.service;

import tn.esprit.autolock.domain.Client;

import java.util.List;

public interface IServiceClient {
    List<Client> retrieveAllClients();
    Client addClient(Client client);
    Client updateClient(Client client);
    Client retrieveClient(Long idClient);
    void removeClient(Long idClient);
    List<Client> addClients(List<Client> clients);
}
