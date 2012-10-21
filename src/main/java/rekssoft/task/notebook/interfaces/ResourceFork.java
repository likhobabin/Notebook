package rekssoft.task.notebook.interfaces;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Interface <tt>ResourceFork</tt> presents a contract that is used by clients
 * to initialize and to open a database connection with use of the provider API
 * (based on JPA 2.0). In that particular case a provider is Hibernate and a
 * database is PostgreSQL. <tt>ResourceFork</tt> is implemented by
 * {@link SingleesourceFork} To sum up, <tt>ResourceFork</tt> is the contract of
 * the resource layout of the application.
 *
 * @see rekssoft.task.notebook.impl.SingleResourceFork
 * @author ilya
 */
public interface ResourceFork extends Closable {

    /**
     * Sets the name of a persistence unit.
     *
     * @param aPersistenceUnitName the name of a persistence unit
     */
    public void setName(String aPersistenceUnitName);

    /**
     * Initializes the database connection.
     *
     * @throws PersistenceException if something goes wrong
     */
    public void initialize() throws PersistenceException;

    /**
     * Returns <tt>true</tt> if the database connection is done.
     *
     * @return <tt>true</tt> if the database connection is done.
     */
    public boolean isInitialized();

    /**
     * @return <tt>EntityManager</tt> the object provides the control over a
     * database.
     * @throws PersistenceException if some database errors occur
     */
    public EntityManager open() throws PersistenceException;
}
