package rekssoft.task.notebook;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author ilya
 */
public interface ResourceFork extends Closable {
    public void setName(String aPersistenceUnitName);

    public void initialize() throws PersistenceException ;

    public boolean isInitialized();

    public EntityManager open() throws PersistenceException; 
}
