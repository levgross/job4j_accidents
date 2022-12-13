package ru.job4j.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {
    @EntityGraph(value = "accident-entity-graph")
    List<Accident> findAll();

    @EntityGraph(value = "accident-entity-graph")
    Optional<Accident> findById(int id);
}
