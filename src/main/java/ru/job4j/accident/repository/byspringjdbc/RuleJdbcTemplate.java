package ru.job4j.accident.repository.byspringjdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RuleJdbcTemplate {
    private final JdbcTemplate jdbc;
    private final AccidentJdbcTemplate accidentJdbcTemplate;

    public RuleJdbcTemplate(JdbcTemplate jdbc, AccidentJdbcTemplate accidentJdbcTemplate) {
        this.jdbc = jdbc;
        this.accidentJdbcTemplate = accidentJdbcTemplate;
    }

    public Rule add(Rule rule) {
        jdbc.update("insert into rule (name) values (?)",
                rule.getName());
        String sql = "INSERT INTO accident_rule (accident_id, rule_id) "
                + "SELECT a.id, r.id FROM accident a, rule r WHERE a.name = '?' AND r.name = '?'";
        Set<Accident> rules = rule.getAccidents();
        rules.forEach(accident -> jdbc.update(sql, accident.getName(), rule.getName()));
        return rule;
    }

    public List<Rule> findAll() {
        return jdbc.query("select * from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    rule.setAccidents(new HashSet<>(accidentJdbcTemplate.findAccidentsByRuleId(rule.getId())));
                    return rule;
                });
    }

    public Rule findById(int id) {
        return jdbc.queryForObject("select * from rule where id = ?", new Object[]{id},
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    rule.setAccidents(new HashSet<>(accidentJdbcTemplate.findAccidentsByRuleId(rule.getId())));
                    return rule;
                });
    }

    public Rule update(Rule rule, int id) {
        jdbc.update("update rule set name = ? where id = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, rule.getName());
                    preparedStatement.setInt(2, rule.getId());
                });
        rule.setId(id);
        jdbc.update("delete from accident_rule where rule_id = ?",
                rule.getId());
        for (Accident accident : rule.getAccidents()) {
            jdbc.update("insert into accident_rule(accident_id, rule_id) values(?, ?)",
                    accident.getId(),
                    rule.getId());
        }
        return rule;
    }

    public List<Rule> findRulesByAccident(int id) {
        return jdbc.query("select r.id, r.name from accident a join accident_rule ar on a.id = ar.accident_id join rule r"
                        + " on ar.rule_id = r.id where a.id = id;",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }
}
