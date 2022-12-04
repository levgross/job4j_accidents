package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        accidents.put(0, new Accident(0, "John Smith", "Description 1", "Address 1"));
        accidents.put(1, new Accident(1, "Sam Brown", "Description 2", "Address 2"));
        accidents.put(2, new Accident(2, "Paul Pierce", "Description 3", "Address 3"));
    }

    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

}
