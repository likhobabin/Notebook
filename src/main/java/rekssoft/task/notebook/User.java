package rekssoft.task.notebook;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Class <tt>User</tt> implements a mapping of user entities to a relational
 * database.
 *
 * <p>This maps user entities into a database table called notebook_users_t. All
 * named queries are stored into the set of final static variables for
 * simplification. <p> So user entity has the following attributes: <p> - an id
 * attribute (primary key); <p> - a first name (not more than 21 characters);
 * <p> - a surname (not more than 21 characters); <p> - an e-mail (an unique
 * value); <p> - a phone number (an unique value).
 *
 * @author ilya
 */
@Entity
@Table(name = "notebook_users_t")
@Access(AccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u "
    + "ORDER BY u.firstname ASC"),
    @NamedQuery(name = "User.findByMail", query = "SELECT u FROM User u "
    + "WHERE u.mail = :mail"),
    @NamedQuery(name = "User.findByPhonenumber", query = "SELECT u FROM User u "
    + "WHERE u.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "User.deleteByMail", query = "DELETE FROM User u "
    + "WHERE u.mail = :mail"),
    @NamedQuery(name = "User.deleteByPhonenumber", query = "DELETE FROM User u "
    + "WHERE u.phoneNumber = :phoneNumber")
})
public class User implements Serializable {

    public static final String FIND_ALL_QUERY = "User.findAll";
    public static final String FIND_BY_MAIL_QUERY = "User.findByMail";
    public static final String FIND_BY_PHONENUMBER_QUERY =
            "User.findByPhonenumber";
    public static final String DELETE_BY_MAIL_QUERY = "User.deleteByMail";
    public static final String DELETE_BY_PHONENUMBER_QUERY =
            "User.deleteByPhonenumber";

    public User() {
    }

    public User(String aFisrtName, String aSurname,
                String aMail, String aPhoneNumber) {
        firstname = aFisrtName;
        surname = aSurname;
        mail = aMail;
        phoneNumber = aPhoneNumber;
    }

    public void setId(long anId) {
        id = anId;
    }

    public long getId() {
        return id;
    }

    public void setFirstname(String aFirstname) {
        firstname = aFirstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setSurname(String aSurname) {
        surname = aSurname;
    }

    public String getSurname() {
        return surname;
    }

    public void setMail(String aMail) {
        mail = aMail;
    }

    public String getMail() {
        return mail;
    }

    public void setPhoneNumber(String aPhoneNumber) {
        phoneNumber = aPhoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, length = 21)
    private String firstname;
    @Column(nullable = false, length = 21)
    private String surname;
    @Column(nullable = false, unique = true)
    private String mail;
    @Column(nullable = false, unique = true, length = 13)
    private String phoneNumber;
}
