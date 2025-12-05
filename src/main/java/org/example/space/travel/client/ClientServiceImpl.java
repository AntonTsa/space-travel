package org.example.space.travel.client;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientDao clientDao = new ClientDaoImpl();

    @Override
    public void saveClient(Client client) {
        clientDao.save(client);
    }

    @Override
    public Client getClientById(Long id) {
        return clientDao.findById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientDao.findAll();
    }

    @Override
    public void updateClient(Client client) {
        clientDao.update(client);
    }

    @Override
    public void deleteClient(Client client) {
        clientDao.delete(client);
    }
}
