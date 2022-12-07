package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Rule;
import ru.job4j.repository.RuleHibernate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleHibernate store;

    public List<Rule> findAll() {
        return store.findAll();
    }

    public void create(Rule rule) {
        store.create(rule);
    }

    public Optional<Rule> findById(int id) {
        Rule rule = store.findById(id);
        if (rule == null) {
            return Optional.empty();
        }

        return Optional.of(rule);
    }

    public Set<Rule> findRulesByAccidentId(int accidentId) {
        return store.findRulesByAccidentId(accidentId);
    }
}
