package org.example.space.travel.client;

import java.util.List;

public interface ClientService {
    void saveClient(Client client);

    Client getClientById(Long id);

    List<Client> getAllClients();

    void updateClient(Client client);

    void deleteClient(Client client);
}
