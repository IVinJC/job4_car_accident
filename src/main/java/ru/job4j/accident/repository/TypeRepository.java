package ru.job4j.accident.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Type;
@Repository
public interface TypeRepository extends CrudRepository<Type, Integer> {
}