package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.bymemory.AccidentMem;

import java.util.Collection;

@Service
public class AccidentService {
    private final AccidentMem accidentMem;
    @Autowired
    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }


    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Accident add(Accident accident) {
        return accidentMem.add(accident);
    }

    public Accident findById(int id) {
        return accidentMem.findById(id);
    }

    public Accident update(Accident accident, int id) {
        return accidentMem.update(accident, id);
    }
}
