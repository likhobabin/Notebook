package rekssoft.task.notebook.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import rekssoft.task.notebook.interfaces.ResourceFork;

/**
 * Class <tt>SingleResourceFork</tt> provides a database access with a typical
 * life-cycle: initialization, opening and closing. <p>This gets a persistence
 * unit name to initialize a database connection with use of his constructor or
 * his <tt>setName</tt> method. So it is possible to use a
 * <tt>SingleResourceFork</tt> again with various database connections. It
 * implements the {@link ResourceFork} interface and provides the Singleton
 * pattern. It deals with a {@link EntityManagerFactory} and a
 * {@link EntityManager} classes, they provide all need capabilities to interact
 * with a database and are the part of JPA API. All need settings is presented
 * in the <tt>META-INF/persistence.xml</tt>. A first-hand client of the
 * SingleResourceFork is the {@link UserDAOImpl} class. To summarize, the
 * <tt>SingleResourceFork</tt> is the resource layout class.
 *
 * @see EntityManagerFactory
 * @see EntityManager
 * @see UserDAOImpl
 * @see ResourceFork
 * @author ilya
 */
public enum ResourceForkSingleton implements ResourceFork {

    RESRC_FORK;

    ResourceForkSingleton() {
    }

    /**
     * The passing persistence unit name must be presented in the
     * persistence.xml.
     *
     * @param aPersistenceUnitName a passing persistence unit
     */
    ResourceForkSingleton(String aPersistenceUnitName) {
        persistenceUnitName = aPersistenceUnitName;
    }

    /**
     * The storing persistence unit name must be presented in the
     * persistence.xml.
     *
     * @param aPersistenceUnitName a passing persistence unit
     */
    public void setName(String aPersistenceUnitName) {
        close();
        persistenceUnitName = aPersistenceUnitName;
    }

    /**
     * Initializes an {@link EntityManagerFactory} with the storing persistence
     * unit name if it has not yet been initialized.
     *
     * @see EntityManagerFactory
     * @throws PersistenceException if some database errors occur
     */
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

    /**
     * Creates an {@link EntityManager} with the initialized
     * {@link EntityManagerFactory} object. If the {@link EntityManagerFactory}
     * instance has not yet been initialized it returned null.
     *
     * @see EntityManager
     * @see EntityManagerFactory
     * @throws PersistenceException if some database errors occur
     */
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
