package rekssoft.task.notebook;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 *
 * @author ilya
 */
enum SingleFork implements Fork {

    FORK;

    SingleFork() {
    }

    SingleFork(String aPersistenceUnitName) {
        persistenceUnitName = aPersistenceUnitName;
    }

    public void setName(String aPersistenceUnitName) {
        close();
        persistenceUnitName = aPersistenceUnitName;
    }

    public void initialize()
            throws PersistenceException {
        if (!isInitialized()) {
            emf =
                Persistence.createEntityManagerFactory(persistenceUnitName);
        }
    }

    public boolean isInitialized() {
        if (null == emf) {
            return false;
        }
        return emf.isOpen();
    }

    public EntityManager open() throws PersistenceException {
        if (!isInitialized()) {
            return null;
        }
        if (!isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    private boolean isOpen() {
        if (null == em) {
            return false;
        }
        return em.isOpen();
    }

    public void close() {
        if (isOpen()) {
            em.close();
        }
        if (isInitialized()) {
            emf.close();
        }
    }
    
    private String persistenceUnitName;
    private EntityManagerFactory emf;
    private EntityManager em;
}
