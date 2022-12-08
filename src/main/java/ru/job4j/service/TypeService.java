package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeService {
    private final AccidentTypeRepository store;

    public AccidentType create(AccidentType type) {
        return store.save(type);
    }

    public void update(AccidentType type) {
        store.save(type);
    }

    public void delete(AccidentType type) {
        store.delete(type);
    }

    public List<AccidentType> findAll() {
        return (List<AccidentType>) store.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return store.findById(id);
    }
}
