package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Type;
import ru.job4j.accident.repository.TypeRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TypeService {
    private final TypeRepository typeRepository;
    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public Collection<Type> findAll() {
        List<Type> res = new ArrayList<>();
        typeRepository.findAll().forEach(res::add);
        return res;
    }

    public Type add(Type rule) {
        return typeRepository.save(rule);
    }

    public Type findById(int id) {
        return typeRepository.findById(id).get();
    }

    public Type update(Type rule, int id) {
        rule.setId(findById(id).getId());
        return typeRepository.save(rule);
    }
}
