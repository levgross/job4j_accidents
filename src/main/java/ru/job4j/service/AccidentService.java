package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.AccidentMem;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem store;

    public List<Accident> findAll() {
        return store.findAll();
    }
}
