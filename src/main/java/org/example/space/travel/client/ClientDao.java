package org.example.space.travel.client;

import java.util.List;
import org.example.space.travel.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ClientDao {
    private final SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

    public void save(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(client);
            tx.commit();
        }
    }

    public Client findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Client.class, id);
        }
    }

    public List<Client> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Client> query = session.createQuery("FROM Client", Client.class);
            query.setFirstResult(0);
            query.setMaxResults(50);
            transaction.commit();
            return query.list();
        }
    }

    public void update(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(client);
            transaction.commit();
        }
    }

    public void delete(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(client);
            transaction.commit();
        }
    }

}
