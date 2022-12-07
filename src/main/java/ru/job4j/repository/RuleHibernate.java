package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleHibernate {
    private final SessionFactory sf;

    public Rule create(Rule rule) {
        try (Session session = sf.openSession()) {
            session.save(rule);
            return rule;
        }
    }

    public List<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule order by id", Rule.class)
                    .list();
        }
    }

    public Rule findById(int id) {
        try (Session session = sf.openSession()) {
            return (Rule) session
                    .createQuery("from Rule where id = :fId")
                    .setParameter("fId", id).
                    getSingleResult();
        }
    }

    public Set<Rule> findRulesByAccidentId(int accidentId) {
        Set<Rule> rules = new HashSet<>();
        try (Session session = sf.openSession()) {
            List<Integer> ids = session
                    .createSQLQuery("select ar.rule_id from accident_rule ar where ar.accident_id = :fId")
                    .setParameter("fId", accidentId)
                    .list();
            for (Integer id : ids) {
                rules.add(this.findById(id));
            }
        }
        return rules;
    }
}
