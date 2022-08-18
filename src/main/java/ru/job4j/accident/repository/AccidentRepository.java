package ru.job4j.accident.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
@Repository
public interface AccidentRepository extends CrudRepository<Accident, Integer> {
}