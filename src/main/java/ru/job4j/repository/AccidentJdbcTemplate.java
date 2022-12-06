package ru.job4j.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.Rule;
import ru.job4j.service.RuleService;
import ru.job4j.service.TypeService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;
    private final TypeService typeService;
    private final RuleService ruleService;

    private final static String CREATE = "insert into accident (name, text, address, type_id) "
            + "values (?, ?, ?, ?)";
    private final static String ADD_RULES = "insert into accident_rule (accident_id, rule_id) "
            + "values (?, ?)";
    private final static String DELETE_RULES = "delete from accident_rule where accident_id = ?";
    private final static String UPDATE = "update accident set name = ?, text = ?, address = ?, type_id = ? "
            + "where id = ?";
    private final static String FIND_ALL = "select * from accident order by id";
    private final static String FIND_BY_ID = "select * from accident where id = ?";

    public AccidentJdbcTemplate(JdbcTemplate jdbc, TypeService typeService, RuleService ruleService) {
        this.jdbc = jdbc;
        this.typeService = typeService;
        this.ruleService = ruleService;
    }

    public Accident create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(cn -> {
            PreparedStatement ps = cn.prepareStatement(CREATE, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.setId((Integer) keyHolder.getKey());
        addRulesToAccident(accident);
        return accident;
    }

    public void update(Accident accident) {
        jdbc.update(UPDATE,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );
        jdbc.update(DELETE_RULES,
                accident.getId()
        );
        addRulesToAccident(accident);
    }

    public List<Accident> findAll() {
        return jdbc.query(FIND_ALL, new AccidentMapper());
    }

    public Accident findById(int id) {
        return jdbc.queryForObject(FIND_BY_ID,
                new AccidentMapper(), id);
    }

    private void addRulesToAccident(Accident accident) {
        for (Rule rule : accident.getRules()) {
            jdbc.update(ADD_RULES,
                    accident.getId(),
                    rule.getId()
            );
        }
    }

    private class AccidentMapper implements RowMapper<Accident> {
        @Override
        public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
            Accident accident = new Accident();
            accident.setId(rs.getInt("id"));
            accident.setName(rs.getString("name"));
            accident.setText(rs.getString("text"));
            accident.setAddress(rs.getString("address"));
            accident.setType(typeService.findById(rs.getInt("type_id")).get());
            accident.setRules(ruleService.findRulesByAccidentId(accident.getId()));
            return accident;
        }
    }
}
