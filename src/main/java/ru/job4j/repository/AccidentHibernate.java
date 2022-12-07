package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.service.RuleService;

import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final SessionFactory sf;

    public Accident create(Accident accident) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(accident);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
        return accident;
    }

    public void update(Accident accident) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(accident);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }

    public Set<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return new HashSet<>(session
                    .createQuery("from Accident a join fetch a.rules order by a.id", Accident.class)
                    .list());
        }
    }

    public Accident findById(int id) {
        try (Session session = sf.openSession()) {
            return (Accident) session
                    .createQuery("from Accident a join fetch a.type join fetch a.rules where a.id = :fId")
                    .setParameter("fId", id).
                    getSingleResult();
        }
    }
}
