package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.List;

@Repository
@AllArgsConstructor
public class TypeHibernate {
    private final SessionFactory sf;

    public AccidentType create(AccidentType type) {
        try (Session session = sf.openSession()) {
            session.save(type);
            return type;
        }
    }

    public List<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType order by id", AccidentType.class)
                    .list();
        }
    }

    public AccidentType findById(int id) {
        try (Session session = sf.openSession()) {
            return (AccidentType) session
                    .createQuery("from AccidentType where id = :fId")
                    .setParameter("fId", id).
                    getSingleResult();
        }
    }
}
