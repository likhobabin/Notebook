package rekssoft.task.notebook;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author ilya
 */
public interface Fork extends Closable {
    public void setName(String aPersistenceUnitName);

    public void initialize() throws PersistenceException ;

    public boolean isInitialized();

    public EntityManager open() throws PersistenceException;

    public void close();    
}
