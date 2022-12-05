package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TypeMem {
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(3);

    public TypeMem() {
        types.put(1, new AccidentType(1, "Two vehicles"));
        types.put(2, new AccidentType(2, "Vehicle and human"));
        types.put(3, new AccidentType(3, "Vehicle and bicycle"));
    }

    public Set<AccidentType> findAll() {
        return new HashSet<>(types.values());
    }

    public void create(AccidentType type) {
        type.setId(ids.incrementAndGet());
        types.put(type.getId(), type);
    }

    public void update(AccidentType type) {
        types.replace(type.getId(), type);
    }

    public Optional<AccidentType> findById(int id) {
        AccidentType type = types.get(id);
        if (type == null) {
            return Optional.empty();
        }
        return Optional.of(type);
    }
}
