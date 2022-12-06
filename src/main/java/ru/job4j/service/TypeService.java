package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.TypeJdbcTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeService {
    private final TypeJdbcTemplate store;

    public List<AccidentType> findAll() {
        return store.findAll();
    }

    public void create(AccidentType type) {
        store.create(type);
    }

    public Optional<AccidentType> findById(int id) {
        AccidentType type = store.findById(id);
        if (type == null) {
            return Optional.empty();
        }
        return Optional.of(type);
    }
}
