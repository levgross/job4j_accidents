package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Rule;
import ru.job4j.repository.RuleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleRepository store;

    public Rule create(Rule rule) {
        return store.save(rule);
    }

    public void update(Rule rule) {
        store.save(rule);
    }

    public void delete(Rule rule) {
        store.delete(rule);
    }

    public List<Rule> findAll() {
        return (List<Rule>) store.findAll();
    }

    public Optional<Rule> findById(int id) {
        return store.findById(id);
    }
}
