package net.dgrigore.mem.analyzer.doodle;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DoodleDao {

    @PersistenceContext
    private EntityManager em;

    public long count() {
        return this.em.createQuery("SELECT count(d) FROM Doodle d", Long.class).getSingleResult();
    }

    public List<Doodle> find(int firstResult, int maxResults) {
        return this.em.createQuery("SELECT d FROM Doodle d", Doodle.class).setFirstResult(firstResult)
                .setMaxResults(maxResults).getResultList();
    }

    public void save(List<Doodle> doodleList) {
        for (Doodle doodle : doodleList) {
            this.em.persist(doodle);
        }
        this.em.flush();
    }

    public void detach(List<Doodle> doodleList) {
        for (Doodle doodle : doodleList) {
            this.em.detach(doodle);
        }
    }
}
