package org.example.space.travel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;
import org.example.space.travel.client.Client;
import org.example.space.travel.client.ClientService;
import org.example.space.travel.client.ClientServiceImpl;
import org.example.space.travel.config.HibernateUtil;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, typeOfSrc, ctx)
                            -> new JsonPrimitive(src.toString()))
            .setPrettyPrinting()
            .create();
    private static final Flyway FLYWAY = Flyway.configure()
            .locations("classpath:db/migration/")
            .dataSource("jdbc:h2:file:~/space", "sa", "")
            .load();

    public static void main(String[] args) {
        FLYWAY.migrate();
        ClientService clientService = new ClientServiceImpl();

        LOGGER.info("All Clients: \n{}",
                clientService.getAllClients().stream()
                        .map(GSON::toJson)
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("No clients found."));

        clientService.saveClient(Client.builder().name("Nastya").build());

        Long id = 13L;

        LOGGER.info("Client with id {}: \n{}", id,
                GSON.toJson(clientService.getClientById(id)));

        clientService.updateClient(Client.builder()
                .id(id)
                .name("Alisa").build());

        LOGGER.info("Client with id {}: \n{}", id,
                GSON.toJson(clientService.getClientById(id)));

        clientService.deleteClient(clientService.getClientById(id));

        LOGGER.info("All Clients: \n{}",
                clientService.getAllClients().stream()
                        .map(GSON::toJson)
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("No clients found."));

        HibernateUtil.getInstance().close();
    }
}
