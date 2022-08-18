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
                + "select distinct c from Accident c join fetch c.rule join fetch c.type", Accident.class).list());
    }

    public Accident findById(int id) {
        return (Accident) tx(session -> session.createQuery("from Accident r where r.id = :fId")
                .setParameter("fId", id)
                .uniqueResult());
    }

    public Accident update(Accident accident, int id) {
        return tx(session -> session.createQuery("update Accident r set "
                        + "r.name = :fName,"
                        + "r.text = :fText,"
                        + "r.address = :fAddress,"
                        + "r.rule = :fRule,"
                        + "r.type = :fType"
                        + " where r.id = :fId", Accident.class)
                .setParameter("fName", accident.getName())
                .setParameter("fText", accident.getText())
                .setParameter("fAddress", accident.getAddress())
                .setParameter("fRule", accident.getRule())
                .setParameter("fType", accident.getType())
                .setParameter("fId", id)
                .uniqueResult());
    }
}