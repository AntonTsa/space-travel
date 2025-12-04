package org.example.space.travel.planet;

import java.util.List;

public class PlanetServiceImpl implements PlanetService {
    private final PlanetDao planetDao = new PlanetDao();

    @Override
    public void savePlanet(Planet planet) {
        planetDao.save(planet);
    }

    @Override
    public Planet getPlanetById(String id) {
        return planetDao.findById(id);
    }

    @Override
    public List<Planet> getAllPlanets() {
        return planetDao.findAll();
    }

    @Override
    public void updatePlanet(Planet planet) {
        planetDao.update(planet);
    }

    @Override
    public void deletePlanet(Planet planet) {
        planetDao.delete(planet);
    }
}
