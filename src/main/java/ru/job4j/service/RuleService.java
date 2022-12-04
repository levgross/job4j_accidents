package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Rule;
import ru.job4j.repository.RuleMem;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleMem store;

    public Set<Rule> findAll() {
        return store.findAll();
    }

    public void create(Rule rule) {
        store.create(rule);
    }

    public void update(Rule rule) {
        store.update(rule);
    }

    public Optional<Rule> findById(int id) {
        return store.findById(id);
    }
}
