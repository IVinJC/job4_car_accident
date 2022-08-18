package ru.job4j.accident.repository.bymemory;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RuleMem {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(4);

    public RuleMem() {
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
    }

    public Collection<Rule> findAll() {
        return rules.values();
    }

    public boolean add(Rule rule) {
        rule.setId(id.incrementAndGet());
        return rules.put(rule.getId(), rule) == null;
    }

    public Rule findById(int id) {
        return rules.get(id);
    }

    public Rule update(Rule rule) {
        return rules.replace(rule.getId(), rule);
    }
}
