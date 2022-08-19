package ru.job4j.accident.repository.byspringjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.model.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;
    private final TypeJdbcTemplate typeJdbcTemplate;
    private final RuleJdbcTemplate ruleJdbcTemplate;

    @Autowired
    public AccidentJdbcTemplate(JdbcTemplate jdbc, TypeJdbcTemplate typeJdbcTemplate, RuleJdbcTemplate ruleJdbcTemplate) {
        this.jdbc = jdbc;
        this.typeJdbcTemplate = typeJdbcTemplate;
        this.ruleJdbcTemplate = ruleJdbcTemplate;
    }

    public Accident add(Accident accident) {
        jdbc.update("insert into accident (name, text, address, type_id) values (?, ?, ?, ?)",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId());

        String sql = "INSERT INTO accident_rule (accident_id, rule_id) "
                + "SELECT a.id, r.id FROM accident a, rule r WHERE a.name = '?' AND r.name = '?'";
        Set<Rule> rules = accident.getRules();
        rules.forEach(rule -> jdbc.update(sql, accident.getName(), rule.getName()));
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
                    accident.setType(typeJdbcTemplate.findById(rs.getInt("type_id")));
                    accident.setRules(new HashSet<>(ruleJdbcTemplate.findRulesByAccident(accident.getId())));
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
                    accident.setType(typeJdbcTemplate.findById(rs.getInt("type_id")));
                    accident.setRules(new HashSet<>(ruleJdbcTemplate.findRulesByAccident(id)));
                    return accident;
                });
    }

    public Accident update(Accident accident, int id) {
        jdbc.update("update accident set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, accident.getName());
                    preparedStatement.setString(2, accident.getText());
                    preparedStatement.setString(3, accident.getAddress());
                    preparedStatement.setInt(4, accident.getType().getId());
                    preparedStatement.setInt(5, accident.getId());
                });
        accident.setId(id);
        jdbc.update("delete from accident_rule where accident_id = ?",
                accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule(accident_id, rule_id) values(?, ?)",
                    accident.getId(),
                    rule.getId());
        }
        return accident;
    }

    public List<Accident> findAccidentsByRuleId(int id) {
        return jdbc.query("select a.* from rule r join accident_rule ar on r.id = ar.rule_id join accident a"
                        + " on ar.accident_id = a.id where r.id = ?;", new Object[]{id},
                (rs, row) -> {
                    Accident accident = new Accident(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("text"),
                            rs.getString("address")
                    );
                    accident.setType(typeJdbcTemplate.findById(rs.getInt("type_id")));
                    accident.setRules(new HashSet<>(ruleJdbcTemplate.findRulesByAccident(accident.getId())));
                    return accident;
                });
    }
}

