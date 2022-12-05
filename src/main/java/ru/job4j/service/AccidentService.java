package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentMem;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem store;
    private final RuleService ruleService;

    public List<Accident> findAll() {
        return store.findAll();
    }

    public boolean create(Accident accident, String[] ids) {
        Set<Rule> rules = new HashSet<>();
        for (String id : ids) {
            Optional<Rule> optRule = ruleService.findById(Integer.parseInt(id));
            if (optRule.isEmpty()) {
                return false;
            }
            rules.add(optRule.get());
        }
        accident.setRules(rules);
        return store.create(accident);
    }

    public void update(Accident accident) {
        store.update(accident);
    }

    public Optional<Accident> findById(int id) {
        return store.findById(id);
    }
}
