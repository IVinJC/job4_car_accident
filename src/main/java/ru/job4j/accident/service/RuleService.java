package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.RuleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RuleService {
    private final RuleRepository ruleRepository;

    @Autowired
    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public Collection<Rule> findAll() {
        List<Rule> res = new ArrayList<>();
        ruleRepository.findAll().forEach(res::add);
        return res;
    }

    public Rule add(Rule rule) {
        return ruleRepository.save(rule);
    }

    public Rule findById(int id) {
        return ruleRepository.findById(id).orElse(null);
    }

    public Rule update(Rule rule, int id) {
        rule.setId(findById(id).getId());
        return ruleRepository.save(rule);
    }
}
