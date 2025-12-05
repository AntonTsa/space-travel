package org.example.space.travel.planet;

import java.util.List;
import org.example.space.travel.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanetDaoImpl implements PlanetDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetDaoImpl.class);
    private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

    @Override
    public void save(Planet planet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(planet);
                transaction.commit();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public Planet findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Planet.class, id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Planet> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query<Planet> query = session.createQuery("FROM Planet", Planet.class);
                query.setFirstResult(0);
                query.setMaxResults(50);
                transaction.commit();
                return query.list();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                return List.of();
            }
        }
    }

    @Override
    public void update(Planet planet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(planet);
                transaction.commit();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void delete(Planet planet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(planet);
                transaction.commit();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
