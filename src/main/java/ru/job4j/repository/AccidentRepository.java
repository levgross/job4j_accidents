package ru.job4j.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Accident;

import java.util.List;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {
    @EntityGraph(value = "accident-entity-graph")
    List<Accident> findAll();
}
