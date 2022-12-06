package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.List;

@Repository
@AllArgsConstructor
public class TypeJdbcTemplate {
    private final JdbcTemplate jdbc;

    private final static String CREATE = "insert into type (name) values (?)";
    private final static String FIND_ALL = "select * from type order by id";
    private final static String FIND_BY_ID = "select name from type where id = ?";

    public AccidentType create(AccidentType type) {
        jdbc.update(CREATE,
            type.getName()
        );
        return type;
    }

    public List<AccidentType> findAll() {
        return jdbc.query(FIND_ALL,
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public AccidentType findById(int id) {
        return jdbc.queryForObject(FIND_BY_ID,
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(id);
                    type.setName(rs.getString("name"));
                    return type;
                }, id);
    }
}
