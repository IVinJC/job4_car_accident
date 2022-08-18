package ru.job4j.accident.repository.byspringjdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.model.Type;

import java.util.List;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Accident add(Accident accident) {
        jdbc.update("insert into accident (name, text, address, rule_id, type_id) values (?, ?, ?, ?, ?)",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getRule().getId(),
                accident.getType().getId());
        return accident;
    }

    public List<Accident> findAll() {
        return jdbc.query("select * from accident",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(new Type(rs.getInt("type_id")));
                    accident.setRule(new Rule(rs.getInt("rule_id")));
                    return accident;
                });
    }

    public Accident findById(int id) {
        return jdbc.queryForObject("select * from accident where id = ?", new Object[]{id},
                (rs, row) -> {
                    Accident accident = new Accident(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("text"),
                            rs.getString("address")
                    );
                    accident.setType(new Type(rs.getInt("type_id")));
                    accident.setRule(new Rule(rs.getInt("rule_id")));
                    return accident;
                });
    }

    public Accident update(Accident accident, int id) {
        jdbc.update("update accident set name = ?, text = ?, address = ?, rule_id = ?, type_id = ? where id = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, accident.getName());
                    preparedStatement.setString(2, accident.getText());
                    preparedStatement.setString(3, accident.getAddress());
                    preparedStatement.setInt(4, accident.getRule().getId());
                    preparedStatement.setInt(5, accident.getType().getId());
                    preparedStatement.setInt(6, accident.getId());
                });
        accident.setId(id);
        return accident;
    }
}

