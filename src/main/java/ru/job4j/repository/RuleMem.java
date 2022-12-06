package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RuleMem {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(3);

    public RuleMem() {
        rules.put(1, new Rule(1, "Rule. 1"));
        rules.put(2, new Rule(2, "Rule. 2"));
        rules.put(3, new Rule(3, "Rule. 3"));
    }

    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    public void create(Rule rule) {
        rule.setId(ids.incrementAndGet());
        rules.put(rule.getId(), rule);
    }

    public void update(Rule rule) {
        rules.replace(rule.getId(), rule);
    }

    public Optional<Rule> findById(int id) {
        Rule rule = rules.get(id);
        if (rule == null) {
            return Optional.empty();
        }
        return Optional.of(rule);
    }
}
