package rekssoft.task.notebook.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import rekssoft.task.notebook.interfaces.ResourceFork;
import rekssoft.task.notebook.interfaces.UserDAO;

/**
 * Class <tt>UserDAOImpl</tt> implements a {@link UserDAO} interface and so it
 * is a part of the business layout. It is the first-hand client of the resource
 * layout {@link ResourceFork} interface and it provides a set of capabilities
 * to find, to insert and to remove {@link User} objects.
 *
 * @see UserDAO
 * @see User
 * @see ResourceFork
 * @author ilya
 */
public class UserDAOImpl implements UserDAO {

    public UserDAOImpl() {
        userResrcFork = ResourceForkSingleton.RESRC_FORK;
    }

    /**
     * Initializes a {@link ResourceFork} instance.
     *
     * @param aUserForkName is used to initialize a {@link ResourceFork}
     * instance
     */
    public void initialize(String aUserForkName) {
        userResrcFork.setName(aUserForkName);
        userResrcFork.initialize();
    }

    public boolean isInitialized() {
        return userResrcFork.isInitialized();
    }

    /**
     * Inserts an {@link User} object into the opened database. Returns
     * <tt>false</tt> if the object is not set, if the database connection is
     * not opened. The database connection would be closed if any database
     * exception was raised and the exception would be passed to the {@link App}
     * object.
     *
     * @param anUser
     * @return <tt>false</tt> if the object is not set, if the database
     * connection is not opened otherwise it returns <tt>true</tt>
     * @throws IllegalArgumentException is the consequence of some database
     * errors
     * @throws IllegalStateException is the consequence of some database errors
     * @throws PersistenceException is the consequence of some database errors
     */
    public boolean insert(User anUser) throws IllegalArgumentException,
                                              IllegalStateException,
                                              PersistenceException {

        boolean isThrowing = false;
        if (!checkInsertingUser(anUser)) {
            return false;
        }
        try {
            if (!getResrcForkConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.insert "
                        + "the fork connection is closed");
                return false;
            }
            getResrcForkConnection().getTransaction().begin();
            getResrcForkConnection().persist(anUser);
            getResrcForkConnection().getTransaction().commit();
        }
        catch (IllegalArgumentException ex) {
            isThrowing = true;
            throw ex;
        }
        catch (IllegalStateException ex) {
            isThrowing = true;
            throw ex;
        }
        catch (RollbackException ex) {
            System.err.println("Debug UserDAOImpl.insert "
                    + "Couldn't insert an (PhoneNumber or E-mail) clone "
                    + "entity");

            return false;
        }
        catch (PersistenceException ex) {
            isThrowing = true;
            throw ex;
        }
        finally {
            if (isThrowing) {
                System.err.print("Debug UserDAOImpl.insert is failed, "
                        + "close the connection");
                close();
            }
        }
        return true;
    }

    private boolean checkInsertingUser(User insertUser) {
        return null != insertUser.getFirstname()
                && null != insertUser.getSurname()
                && null != insertUser.getMail()
                && null != insertUser.getPhoneNumber();
    }

    /**
     * Finds all {@link User} objects into the opened database. Returns
     * <tt>null</tt>, if the database connection is not opened. The database
     * connection would be closed if any database exception was raised and then
     * the exception will be passed to the {@link App} object.
     *
     * @return Returns <tt>null</tt>, if the database connection is not opened,
     * otherwise it returns <tt>List<User></tt> object
     * @throws IllegalArgumentException is the consequence of some database
     * errors
     * @throws PersistenceException is the consequence of some database errors
     */
    public List<User> findAll() throws IllegalArgumentException,
                                       PersistenceException {

        List<User> result = null;
        boolean isThrowing = false;
        try {
            if (!getResrcForkConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.findAll "
                        + "the fork connection is closed");

                return null;
            }
            result = (List<User>) getResrcForkConnection().createNamedQuery(User.FIND_ALL_QUERY)
                    .getResultList();

        }
        catch (IllegalArgumentException ex) {
            isThrowing = true;
            throw ex;
        }
        catch (PersistenceException ex) {
            isThrowing = true;
            throw ex;
        }
        finally {
            if (isThrowing) {
                System.err.print("Debug UserDAOImpl.findAll is failed, "
                        + "close the connection");
                close();
            }
        }
        return result;
    }

    public List<User> findByName(String aFirstName)
            throws IllegalArgumentException,
                   PersistenceException {

        List<User> result = null;
        boolean isThrowing = false;
        try {
            if (!getResrcForkConnection().isOpen()) {
                System.err.println("Debug UserDAO.findByFullName "
                        + "the fork connection is closed");

                return null;
            }
            result = (List<User>) getResrcForkConnection()
                    .createNamedQuery(User.FIND_BY_NAME_QUERY)
                    .setParameter("firstname", aFirstName)
                    .getResultList();

        }
        catch (IllegalArgumentException ex) {
            isThrowing = true;
            throw ex;
        }
        catch (PersistenceException ex) {
            isThrowing = true;
            throw ex;
        }
        finally {
            if (isThrowing) {
                System.err.print("Debug UserDAOImpl.findByFullName is failed, "
                        + "close the connection");
                close();
            }
        }
        return result;
    }

    /**
     * Removes {@link User} object from the opened database with use of his
     * e-mail. Returns <tt>false</tt>, if the database connection is not opened,
     * or if a user is not found. The database connection would be closed if any
     * database exception was raised and then the exception will be passed to
     * the {@link App} object.
     *
     * @return Returns <tt>false</tt>, if the database connection is not opened,
     * or if a user is not found, otherwise it returns <tt>true</tt>
     * @throws IllegalArgumentException is the consequence of some database
     * errors
     * @throws PersistenceException is the consequence of some database errors
     */
    public boolean removeByMail(String aMailaddress)
            throws IllegalArgumentException, PersistenceException {

        User rmUser = null;
        boolean isThrowing = false;
        try {
            if (!getResrcForkConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.removeByMail "
                        + "the fork connection is closed");

                return false;
            }
            rmUser = (User) getResrcForkConnection().createNamedQuery(User.FIND_BY_MAIL_QUERY)
                    .setParameter("mail", aMailaddress)
                    .getSingleResult();

            getResrcForkConnection().getTransaction().begin();
            /*
             * The data is removed from the database
             * but the object is still accessible
             * */
            getResrcForkConnection().remove(rmUser);
            getResrcForkConnection().getTransaction().commit();
        }
        catch (IllegalArgumentException ex) {
            isThrowing = true;
            throw ex;
        }
        catch (NoResultException ex) {
            System.err.println("Debug UserDAOImpl.removeByMail "
                    + "could not find an entity with the mail");
            return false;
        }
        catch (PersistenceException ex) {
            isThrowing = true;
            throw ex;
        }
        finally {
            if (isThrowing) {
                System.err.print("Debug UserDAOImpl.removeByMail is failed, "
                        + "close the connection");
                close();
            }
        }
        return null == rmUser ? false : true;
    }

    /**
     * Removes {@link User} object from the opened database with use of his
     * phone-number. Returns <tt>false</tt>, if the database connection is not
     * opened, or if a user is not found. The database connection would be
     * closed if any database exception was raised and then the exception will
     * be passed to the {@link App} object.
     *
     * @return Returns <tt>false</tt>, if the database connection is not opened,
     * or if a user is not found, otherwise it returns <tt>true</tt>
     * @throws IllegalArgumentException is the consequence of some database
     * errors
     * @throws PersistenceException is the consequence of some database errors
     */
    public boolean removeByPhonenumber(String aPhonenumber)
            throws IllegalArgumentException, PersistenceException {

        User rmUser = null;
        boolean isThrowing = false;
        try {
            if (!getResrcForkConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.removeByMail "
                        + "the fork connection is closed");

                return false;
            }
            rmUser = (User) getResrcForkConnection().createNamedQuery(User.FIND_BY_PHONENUMBER_QUERY)
                    .setParameter("phoneNumber", aPhonenumber)
                    .getSingleResult();

            getResrcForkConnection().getTransaction().begin();
            /*
             * The data is removed from the database
             * but the object is still accessible
             * */
            getResrcForkConnection().remove(rmUser);
            getResrcForkConnection().getTransaction().commit();
        }
        catch (IllegalArgumentException ex) {
            isThrowing = true;
            throw ex;
        }
        catch (NoResultException ex) {
            System.err.println("Debug UserDAOImpl.removeByPhonenumber "
                    + "could not find an entity with the phone number");
            return false;
        }
        catch (PersistenceException ex) {
            isThrowing = true;
            throw ex;
        }
        finally {
            if (isThrowing) {
                System.err.print("Debug UserDAOImpl.removeByPhonenumber is failed, "
                        + "close the connection");
                close();
            }
        }
        return null == rmUser ? false : true;
    }

    public void close() throws RuntimeException {
        userResrcFork.close();
    }

    /**
     * @return a {@link ResourceFork} object that is responsible for the
     * resource layout
     */
    protected EntityManager getResrcForkConnection() {
        return userResrcFork.open();
    }
    private final ResourceFork userResrcFork;
}
