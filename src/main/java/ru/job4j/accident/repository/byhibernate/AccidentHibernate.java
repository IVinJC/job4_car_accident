package ru.job4j.accident.repository.byhibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.List;
import java.util.function.Function;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Accident add(Accident accident) {
        tx(session -> session.save(accident));
        return accident;
    }

    public List<Accident> findAll() {
        return tx(session -> session.createQuery(""
                + "select distinct c from Accident c join fetch c.rules", Accident.class).list());
    }

    public Accident findById(int id) {
        return (Accident) tx(session -> session.createQuery(
                "select distinct c from Accident c join fetch c.rules where c.id = :fId")
                .setParameter("fId", id)
                .uniqueResult());
    }

    public Accident update(Accident accident, int id) {

        Accident accidentWasUpdated = tx(session -> session.createQuery(
                "select distinct a from Accident a join fetch a.rules where a.id = :fId",
                Accident.class)
                .setParameter("fId", id)
                .uniqueResult());
        accidentWasUpdated.setName(accident.getName());
        accidentWasUpdated.setText(accident.getText());
        accidentWasUpdated.setRules(accident.getRules());
        accidentWasUpdated.setType(accident.getType());
        return (Accident) tx(session -> session.save(accidentWasUpdated));
    }
}