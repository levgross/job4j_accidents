package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Rule;

public interface RuleRepository extends CrudRepository<Rule, Integer> {

}
