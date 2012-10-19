package rekssoft.task.notebook;

import java.util.List;
import javax.persistence.PersistenceException;

/**
 * Interface <tt>UserDAO</tt> presents operation set that is used by a client to
 * control over user entities. <p> These are the following operations: <p> -
 * insert an user; <p> - find all users storing in a database; <p> - remove an
 * user with use of his e-mail; <p> - remove an user with use of his phone
 * number; <p> Class {@link User} presents user entities.
 * <tt>UserDAO</tt> is implemented by {@link UserDAOImpl}.
 * <p> REMARK: DAO - Data Access Object.
 *
 * @see User
 * @see UserDAOImpl
 * @author ilya
 */
public interface UserDAO extends Closable {

    /**
     * Returns <tt>true</tt> if the database connection is done.
     *
     * @return <tt>true</tt> if the database connection is done
     */
    public boolean isInitialized();

    /**
     * Makes a new database connection called by <tt>aPersistenceUnitName</tt>.
     *
     * @param aPersistenceUnitName the name is used to obtain a database
     * connection
     */
    public void initialize(String aPersistenceUnitName);

    /**
     * Inserts an {@link User} object into the database.
     *
     * @param anUser inserting user object
     * @return <tt>true</tt> if everything is right
     * @throws PersistenceException if some database errors occur
     */
    public boolean insert(User anUser) throws PersistenceException;

    /**
     * Finds all {@link User} objects in the database.
     *
     * @return <tt>List<User></tt> list of users
     * @throws PersistenceException if some database errors occur
     */
    public List<User> findAll() throws PersistenceException;

    /**
     * Removes an {@link User} object from the database by his e-mail.
     *
     * @param aMail e-mail value
     * @return <tt>true</tt> if everything is right
     * @throws PersistenceException if some database errors occur
     */
    public boolean removeByMail(String aMail)
            throws PersistenceException;

    /**
     * Removes an {@link User} object from the database by his phone number.
     *
     * @param aPhonenumber phone number value
     * @return <tt>true</tt> if everything is right
     * @throws PersistenceException if some database errors occur
     */
    public boolean removeByPhonenumber(String aPhonenumber)
            throws PersistenceException;
}
