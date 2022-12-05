package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.TypeMem;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class TypeService {
    private final TypeMem store;

    public Set<AccidentType> findAll() {
        return store.findAll();
    }

    public void create(AccidentType type) {
        store.create(type);
    }

    public void update(AccidentType type) {
        store.update(type);
    }

    public Optional<AccidentType> findById(int id) {
        return store.findById(id);
    }
}
