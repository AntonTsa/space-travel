package org.example.space.travel.planet;

import java.util.List;

public interface PlanetService {
    void savePlanet(Planet planet);

    Planet getPlanetById(String id);

    List<Planet> getAllPlanets();

    void updatePlanet(Planet planet);

    void deletePlanet(Planet planet);
}
