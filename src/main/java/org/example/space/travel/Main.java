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
import org.example.space.travel.planet.Planet;
import org.example.space.travel.planet.PlanetService;
import org.example.space.travel.planet.PlanetServiceImpl;
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

        Long clientId = 14L;

        LOGGER.info("Client with id {}: \n{}", clientId,
                GSON.toJson(clientService.getClientById(clientId)));

        clientService.updateClient(Client.builder()
                .id(clientId)
                .name("Alisa").build());

        LOGGER.info("Client with id {}: \n{}", clientId,
                GSON.toJson(clientService.getClientById(clientId)));

        clientService.deleteClient(clientService.getClientById(clientId));

        LOGGER.info("All Clients: \n{}",
                clientService.getAllClients().stream()
                        .map(GSON::toJson)
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("No clients found."));

        PlanetService planetService = new PlanetServiceImpl();

        LOGGER.info("All Planets: \n{}",
                planetService.getAllPlanets().stream()
                        .map(GSON::toJson)
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("No planets found."));

        planetService.savePlanet(Planet.builder().id("MARS").name("Mars").build());

        String planetId = "MARS";

        LOGGER.info("Planet with id {}: \n{}", planetId,
                GSON.toJson(planetService.getPlanetById(planetId)));

        planetService.updatePlanet(Planet.builder()
                .id(planetId)
                .name("Mars Updated").build());

        LOGGER.info("Planet with id {}: \n{}", planetId,
                GSON.toJson(planetService.getPlanetById(planetId)));

        planetService.deletePlanet(planetService.getPlanetById(planetId));

        LOGGER.info("All Planets: \n{}",
                planetService.getAllPlanets().stream()
                        .map(GSON::toJson)
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("No planets found."));

        HibernateUtil.getInstance().close();
    }
}
