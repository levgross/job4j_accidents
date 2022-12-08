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

    public Rule create(Rule rule) {
        return store.create(rule);
    }

    public void update(Rule rule) {
        store.update(rule);
    }

    public void delete(int ruleId) {
        store.delete(ruleId);
    }

    public List<Rule> findAll() {
        return store.findAll();
    }

    public Optional<Rule> findById(int id) {
        return store.findById(id);
    }

    public Set<Rule> findRulesByAccidentId(int accidentId) {
        return store.findRulesByAccidentId(accidentId);
    }
}
