package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final CrudRepository crudRepository;

    public Accident create(Accident accident) {
        crudRepository.run(session ->
                session.save(accident));
        return accident;
    }

    public void update(Accident accident) {
        crudRepository.run(session ->
                session.update(accident));
    }

    public void delete(int accidentId) {
        crudRepository.run("delete from Accident where id = :tId",
                Map.of("tId", accidentId));
    }

    public Set<Accident> findAll() {
        return new HashSet<>(crudRepository
                .query("from Accident a join fetch a.rules order by a.id", Accident.class));
    }

    public Optional<Accident> findById(int id) {
            return crudRepository
                    .optional("from Accident a join fetch a.type join fetch a.rules where a.id = :fId",
                            Accident.class,
                            Map.of("fId", id));
    }
}
