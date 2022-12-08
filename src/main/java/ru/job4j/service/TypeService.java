package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.TypeHibernate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeService {
    private final TypeHibernate store;

    public AccidentType create(AccidentType type) {
        return store.create(type);
    }

    public void update(AccidentType type) {
        store.update(type);
    }

    public void delete(int typeId) {
        store.delete(typeId);
    }

    public List<AccidentType> findAll() {
        return store.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return store.findById(id);
    }
}
