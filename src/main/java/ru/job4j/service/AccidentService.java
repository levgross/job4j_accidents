package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentJdbcTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentJdbcTemplate accidentsRepostiory;
    private final RuleService ruleService;
    private final TypeService typeService;

    public List<Accident> findAll() {
        return accidentsRepostiory.findAll();
    }

    public boolean create(Accident accident, int typeId, String[] ids) {
        Set<Rule> rules = new HashSet<>();
        Optional<AccidentType> typeOpt = typeService.findById(typeId);
        if (typeOpt.isEmpty()) {
            return false;
        }
        accident.setType(typeOpt.get());
        for (String id : ids) {
            Optional<Rule> optRule = ruleService.findById(Integer.parseInt(id));
            if (optRule.isEmpty()) {
                return false;
            }
            rules.add(optRule.get());
        }
        accident.setRules(rules);
        accidentsRepostiory.create(accident);
        return true;
    }

    public void update(Accident accident) {
        accidentsRepostiory.update(accident);
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidentsRepostiory.findById(id));
    }
}
