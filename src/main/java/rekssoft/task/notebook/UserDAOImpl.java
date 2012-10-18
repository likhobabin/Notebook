package rekssoft.task.notebook;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

/**
 *
 * @author ilya
 */
class UserDAOImpl implements UserDAO {

    public UserDAOImpl() {
        userFork = SingleFork.FORK;
    }

    public void initialize(String aUserForkName) {
        userFork.setName(aUserForkName);
        userFork.initialize();
    }

    public boolean isInitialized() {
        return userFork.isInitialized();
    }

    public boolean insert(User anUser) throws IllegalArgumentException,
                                              IllegalStateException,
                                              PersistenceException {

        boolean isThrowing = false;
        if (!checkInsertingUser(anUser)) {
            return false;
        }
        try {
            if (!getConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.insert "
                        + "the fork connection is closed");
                return false;
            }
            getConnection().getTransaction().begin();
            getConnection().persist(anUser);
            getConnection().getTransaction().commit();
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (IllegalStateException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (EntityExistsException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (TransactionRequiredException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (RollbackException ex) {
            ex.printStackTrace();
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

    public List<User> findAll() throws IllegalArgumentException,
                                       PersistenceException {

        List<User> result = null;
        boolean isThrowing = false;
        try {
            if (!getConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.findAll "
                        + "the fork connection is closed");

                return null;
            }
            result = (List<User>) getConnection().createNamedQuery(User.FIND_ALL_QUERY)
                    .getResultList();

        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (QueryTimeoutException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (PessimisticLockException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (LockTimeoutException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (PersistenceException ex) {
            ex.printStackTrace();
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

    public boolean removeByMail(String aMailaddress)
            throws IllegalArgumentException, PersistenceException {

        User rmUser = null;
        boolean isThrowing = false;
        try {
            if (!getConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.removeByMail "
                        + "the fork connection is closed");

                return false;
            }
            rmUser = (User) getConnection().createNamedQuery(User.FIND_BY_MAIL_QUERY)
                    .setParameter("mail", aMailaddress)
                    .getSingleResult();

            getConnection().getTransaction().begin();
            /*
             * The data is removed from the database
             * but the object is still accessible
             * */
            getConnection().remove(rmUser);
            getConnection().getTransaction().commit();
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (QueryTimeoutException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (PessimisticLockException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (LockTimeoutException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (EntityExistsException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (TransactionRequiredException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (RollbackException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (NonUniqueResultException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (PersistenceException ex) {
            ex.printStackTrace();
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
        return true;
    }

    public boolean removeByPhonenumber(String aPhonenumber)
            throws IllegalArgumentException, PersistenceException {

        User rmUser = null;
        boolean isThrowing = false;
        try {
            if (!getConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.removeByMail "
                        + "the fork connection is closed");

                return false;
            }
            rmUser = (User) getConnection().createNamedQuery(User.FIND_BY_PHONENUMBER_QUERY)
                    .setParameter("phoneNumber", aPhonenumber)
                    .getSingleResult();

            getConnection().getTransaction().begin();
            /*
             * The data is removed from the database
             * but the object is still accessible
             * */
            getConnection().remove(rmUser);
            getConnection().getTransaction().commit();
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (QueryTimeoutException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (PessimisticLockException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (LockTimeoutException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (EntityExistsException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (TransactionRequiredException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (RollbackException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (NonUniqueResultException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (PersistenceException ex) {
            ex.printStackTrace();
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
        return true;
    }

    public void close() throws RuntimeException {
        userFork.close();
    }

    private EntityManager getConnection() {
        return userFork.open();
    }
    private final Fork userFork;
}
