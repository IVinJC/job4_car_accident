package ru.job4j.accident.repository.byspringjdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.job4j.accident.model.Type;
import java.util.List;
@Repository
public class TypeJdbcTemplate {
    private final JdbcTemplate jdbc;

    public TypeJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Type add(Type type) {
        jdbc.update("insert into type (name) values (?)",
                type.getName());
        return type;
    }

    public List<Type> findAll() {
        return jdbc.query("select * from type",
                (rs, row) -> {
                    Type type = new Type();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public Type findById(int id) {
        return jdbc.queryForObject("select * from type where id = ?", new Object[]{id},
                (rs, row) -> {
                    Type type = new Type();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }

    public Type update(Type type, int id) {
        jdbc.update("update type set name = ? where id = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, type.getName());
                    preparedStatement.setInt(2, type.getId());
                });
        type.setId(id);
        return type;
    }
}
