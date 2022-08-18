package ru.job4j.accident.repository.bymemory;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Component
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(3);

    private AccidentMem() {
        accidents.put(1, new Accident(1, "Name1", "Text1", "Address1"));
        accidents.put(2, new Accident(2, "Name2", "Text2", "Address2"));
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }

    public Accident add(Accident accident) {
        accident.setId(id.incrementAndGet());
        return accidents.put(accident.getId(), accident);
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }

    public Accident update(Accident accident, int id) {
        return accidents.replace(id, accident);
    }
}
