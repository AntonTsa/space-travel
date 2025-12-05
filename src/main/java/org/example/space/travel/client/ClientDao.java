package org.example.space.travel.client;

import java.util.List;

public interface ClientDao {
    void save(Client client);

    Client findById(Long id);

    List<Client> findAll();

    void update(Client client);

    void delete(Client client);
}
