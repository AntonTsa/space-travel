package org.example.space.travel.planet;

import java.util.List;

public interface PlanetDao {
    void save(Planet planet);

    Planet findById(String id);

    List<Planet> findAll();

    void update(Planet planet);

    void delete(Planet planet);
}
