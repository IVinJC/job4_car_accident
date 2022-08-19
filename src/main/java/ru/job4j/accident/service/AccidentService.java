package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentRepository;

import java.util.Collection;
import java.util.stream.StreamSupport;

@Service
public class AccidentService {
    private final AccidentRepository accidentRepository;
    @Autowired
    public AccidentService(AccidentRepository accidentRepository) {
        this.accidentRepository = accidentRepository;
    }

    public Collection<Accident> findAll() {
        return StreamSupport
                .stream(accidentRepository.findAll()
                        .spliterator(), false)
                .toList();
    }

    public Accident add(Accident accident) {
        return accidentRepository.save(accident);
    }

    public Accident findById(int id) {
        return accidentRepository.findById(id).orElse(null);
    }

    public Accident update(Accident accident, int id) {
        accident.setId(id);
        return accidentRepository.save(accident);
    }
}
