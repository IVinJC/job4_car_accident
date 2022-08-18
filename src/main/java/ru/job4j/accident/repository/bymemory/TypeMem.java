package ru.job4j.accident.repository.bymemory;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Type;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TypeMem {
    private final Map<Integer, Type> types = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(4);

    public TypeMem() {
        types.put(1, Type.of(1, "Две машины"));
        types.put(2, Type.of(2, "Машина и человек"));
        types.put(3, Type.of(3, "Машина и велосипед"));
    }

    public Collection<Type> findAll() {
        return types.values();
    }

    public boolean add(Type type) {
        type.setId(id.incrementAndGet());
        return types.put(type.getId(), type) == null;
    }

    public Type findById(int id) {
        return types.get(id);
    }

    public Type update(Type type) {
        return types.replace(type.getId(), type);
    }
}
