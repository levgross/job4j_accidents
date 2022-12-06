package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.service.TypeService;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;
    private final TypeService typeService;

    private final static String CREATE = "insert into accident (name, text, address, type_id) "
            + "values (?, ?, ?, ?)";
    private final static String UPDATE = "update accident set name = ?, text = ?, address = ?, type_id = ? "
            + "where id = ?";
    private final static String FIND_ALL = "select * from accident order by id";
    private final static String FIND_BY_ID = "select name, text, address, type_id from accident where id = ?";

    public Accident create(Accident accident) {
        jdbc.update(CREATE,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId()
        );
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

    }

    public List<Accident> findAll() {
        return jdbc.query(FIND_ALL,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(typeService.findById(rs.getInt("type_id")).get());
                    return accident;
                });
    }

    public Accident findById(int id) {
        return jdbc.queryForObject(FIND_BY_ID,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(id);
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(typeService.findById(rs.getInt("type_id")).get());
                    return accident;
                }, id);
    }
}
