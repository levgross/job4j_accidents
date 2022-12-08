package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class RuleHibernate {
    private final CrudRepository crudRepository;

    public Rule create(Rule rule) {
        crudRepository.run(session ->
                session.save(rule));
        return rule;
    }

    public void update(Rule rule) {
        crudRepository.run(session ->
                session.update(rule));
    }

    public void delete(int ruleId) {
        crudRepository.run("delete from Rule where id = :fId",
                Map.of("fId", ruleId));
    }

    public List<Rule> findAll() {
        return crudRepository
                .query("from Rule order by id", Rule.class);
    }

    public Optional<Rule> findById(int id) {
       return crudRepository
                .optional("from Rule where id = :fId",
                        Rule.class,
                        Map.of("fId", id));
    }

    public Set<Rule> findRulesByAccidentId(int accidentId) {
        return new HashSet<>(crudRepository
                .sqlQuery("select ar.rule_id from accident_rule ar where ar.accident_id = :fId",
                        Map.of("fId", accidentId)));
    }
}
