package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentHibernate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentHibernate accidentsRepository;
    private final RuleService ruleService;
    private final TypeService typeService;

    public Set<Accident> findAll() {
        return accidentsRepository.findAll();
    }

    public boolean create(Accident accident, int typeId, String[] ids) {
        Optional<Accident> accidentOpt = loadTypeAndRules(accident, typeId, ids);
        if (accidentOpt.isEmpty()) {
            return false;
        }
        accidentsRepository.create(accidentOpt.get());
        return true;
    }

    public boolean update(Accident accident, int typeId, String[] ids) {
        Optional<Accident> accidentOpt = loadTypeAndRules(accident, typeId, ids);
        if (accidentOpt.isEmpty()) {
            return false;
        }
        accidentsRepository.update(accidentOpt.get());
        return true;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidentsRepository.findById(id));
    }

    private Optional<Accident> loadTypeAndRules(Accident accident, int typeId, String[] ids) {
        Set<Rule> rules = new HashSet<>();
        Optional<AccidentType> typeOpt = typeService.findById(typeId);
        if (typeOpt.isEmpty()) {
            return Optional.empty();
        }
        for (String id : ids) {
            Optional<Rule> optRule = ruleService.findById(Integer.parseInt(id));
            if (optRule.isEmpty()) {
                return Optional.empty();
            }
            rules.add(optRule.get());
        }
        accident.setType(typeOpt.get());
        accident.setRules(rules);
        return Optional.of(accident);
    }
}
