package rekssoft.task.notebook;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
/**
 *
 * @author ilya
 */
class UserDAOImpl implements UserDAO {

    public UserDAOImpl() {
        userResrcFork = SingleResourceFork.RESRC_FORK;
    }

    public void initialize(String aUserForkName) {
        userResrcFork.setName(aUserForkName);
        userResrcFork.initialize();
    }

    public boolean isInitialized() {
        return userResrcFork.isInitialized();
    }

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
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (IllegalStateException ex) {
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch (RollbackException ex) {
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
            if (!getResrcForkConnection().isOpen()) {
                System.err.println("Debug UserDAOImpl.findAll "
                        + "the fork connection is closed");

                return null;
            }
            result = (List<User>) getResrcForkConnection().createNamedQuery(User.FIND_ALL_QUERY)
                    .getResultList();

        }
        catch (IllegalArgumentException ex) {
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
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch(NoResultException ex){
            System.err.println("Debug UserDAOImpl.removeByMail "
                        + "could not find an entity with the mail");
            return false;
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
        return null == rmUser ? false : true;
    }

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
            ex.printStackTrace();
            isThrowing = true;
            throw ex;
        }
        catch(NoResultException ex){
            System.err.println("Debug UserDAOImpl.removeByPhonenumber "
                        + "could not find an entity with the phone number");
            return false;
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
        return null == rmUser ? false : true;
    }

    public void close() throws RuntimeException {
        userResrcFork.close();
    }

    private EntityManager getResrcForkConnection() {
        return userResrcFork.open();
    }
    private final ResourceFork userResrcFork;
}
