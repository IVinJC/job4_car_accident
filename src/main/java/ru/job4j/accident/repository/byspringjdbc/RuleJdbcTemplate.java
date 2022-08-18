package ru.job4j.accident.repository.byspringjdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;

import java.util.List;
@Repository
public class RuleJdbcTemplate {
    private final JdbcTemplate jdbc;

    public RuleJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Rule add(Rule rule) {
        jdbc.update("insert into rule (name) values (?)",
                rule.getName());
        return rule;
    }

    public List<Rule> findAll() {
        return jdbc.query("select * from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Rule findById(int id) {
        return jdbc.queryForObject("select * from rule where id = ?", new Object[]{id},
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Rule update(Rule rule, int id) {
        jdbc.update("update rule set name = ? where id = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, rule.getName());
                    preparedStatement.setInt(4, rule.getId());
                });
        rule.setId(id);
        return rule;
    }
}
