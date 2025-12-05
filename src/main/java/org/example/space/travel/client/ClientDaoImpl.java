package org.example.space.travel.client;

import java.util.List;
import org.example.space.travel.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientDaoImpl implements ClientDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);
    private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

    @Override
    public void save(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(client);
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
    public Client findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Client.class, id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query<Client> query = session.createQuery("FROM Client", Client.class);
                query.setFirstResult(0);
                query.setMaxResults(50);
                transaction.commit();
                return query.list();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
            }
            return List.of();
        }
    }

    @Override
    public void update(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(client);
                transaction.commit();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(client);
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
