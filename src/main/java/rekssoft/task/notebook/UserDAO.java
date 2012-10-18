package rekssoft.task.notebook;

import java.util.List;
import javax.persistence.PersistenceException;
/**
 *
 * @author ilya
 */
public interface UserDAO extends Closable {
    
    public boolean isInitialized();
    
    public void initialize(String aPersistenceUnitName);

    public boolean insert(User anUser) throws PersistenceException;
    
    public List<User> findAll() throws PersistenceException;

    public boolean removeByMail(String aMail)
            throws PersistenceException;

    public boolean removeByPhonenumber(String aPhonenumber)
            throws PersistenceException;
}
