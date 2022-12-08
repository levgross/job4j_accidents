package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TypeHibernate {
    private final CrudRepository crudRepository;

    public AccidentType create(AccidentType type) {
        crudRepository.run(session ->
                session.save(type));
        return type;
    }

    public void update(AccidentType type) {
        crudRepository.run(session ->
                session.update(type));
    }

    public void delete(int typeId) {
        crudRepository.run("delete from AccidentType where id = :tId",
                Map.of("tId", typeId));
    }

    public List<AccidentType> findAll() {
        return crudRepository
                .query("from AccidentType order by id", AccidentType.class);
    }

    public Optional<AccidentType> findById(int id) {
        return crudRepository
                .optional("from AccidentType where id = :fId",
                        AccidentType.class,
                        Map.of("fId", id));
    }
}
