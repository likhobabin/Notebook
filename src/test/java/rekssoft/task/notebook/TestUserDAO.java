package rekssoft.task.notebook;

import java.util.List;
import javax.persistence.RollbackException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author ilya
 */
public class TestUserDAO {
    
    @Before
    public void initialize(){
        userDAO.initialize("NotebookUsers");
    }
    @After
    public void close() {
        userDAO.close();
    }
    
    @Test
    public void testInitializtion() {
        assertEquals("Initialization is incorrect", 
                      userDAO.isInitialized(), true);
        
    }
    
    @Test
    public void testInsertion(){
        User insertUser = new User();
        insertUser.setFirstname("Vasya");
        insertUser.setSurname("Pupkin");
        insertUser.setMail("vasya.pupkin@gmail.com");
        insertUser.setPhoneNumber("8(777)6669990");
        userDAO.insert(insertUser);
    }
    
    @Test
    public void testUniqueMail() {
        try {
            User insertMailOrigUser = new User();
            insertMailOrigUser.setFirstname("Vova");
            insertMailOrigUser.setSurname("Puchkov");
            insertMailOrigUser.setMail("vasya.pupkin@gmail.com");
            insertMailOrigUser.setPhoneNumber("8(777)6769990");
            insertMailOrigUser.setFirstname("Tony");

            User insertMailCloneUser = new User();
            insertMailCloneUser.setFirstname("Vitya");
            insertMailCloneUser.setSurname("Puchkov");
            insertMailCloneUser.setMail("vasya.pupkin@gmail.com");
            insertMailCloneUser.setPhoneNumber("8(797)6769990");

            assertEquals("Could not insert an user successfully", 
                         userDAO.insert(insertMailOrigUser), true);
            assertEquals("Failed check of unique mails",
                         userDAO.insert(insertMailCloneUser), false);
        }
        /*
         * the predictable exception
         */
        catch (RollbackException ex) {
            System.err.println("****Debug TestUserDAO.testUniqueMail"
                    + " the predictable exception****");
        }        
    }
    
    @Test
    public void testUniquePhonenumber() {
        try {
            User insertMailOrigUser = new User();
            insertMailOrigUser.setFirstname("Vova");
            insertMailOrigUser.setSurname("Puchkov");
            insertMailOrigUser.setMail("vasya.pupkin@gmail.com");
            insertMailOrigUser.setPhoneNumber("8(777)6769990");
            insertMailOrigUser.setFirstname("Tony");

            User insertMailCloneUser = new User();
            insertMailCloneUser.setFirstname("Vitya");
            insertMailCloneUser.setSurname("Puchkov");
            insertMailCloneUser.setMail("Vitya.Puchkov@gmail.com");
            insertMailOrigUser.setPhoneNumber("8(777)6769990");

            assertEquals("Could not insert an user successfully", 
                         userDAO.insert(insertMailOrigUser), true);
            
            assertEquals("Failed check of unique phonenumber",
                         userDAO.insert(insertMailCloneUser), false);
            
        }
        /*
         * the predictable exception
         */
        catch (RollbackException ex) {
        }        
    }
    
    @Test
    public void testInsertOfUnsettingUser() {        
        User insertUnsettingUser = new User();
        assertEquals("Failed check of an unsetting user",
                     userDAO.insert(insertUnsettingUser), false); 
        
    }
    
    @Test
    public void testFindAllQuery() {     
        createBulkOfUsers();
        
        List<User> allUsers = null;
        allUsers = userDAO.findAll();
        assertEquals("Failed search of all users",
                     (null != allUsers), true); 
        
    }
    
    @Test
    public void testFindAllInEmptyDataBase() {     
        List<User> allUsers = null;
        allUsers = userDAO.findAll();
        assertEquals("Failed search of all users",
                     (0 == allUsers.size()), true); 
        
    }
    
    @Test
    public void testRemoveByMail() {     
        final String rmMail = "venya.pupkin@gmail.com";
        createBulkOfUsers();
        
        assertEquals("Failed removing an user by the mail",
                     userDAO.removeByMail(rmMail), true); 
        
        List<User> allUsers = null;
        allUsers = userDAO.findAll();
        assertEquals("Failed search of all users",
                     (null != allUsers), true); 
        
        for(User currUser : allUsers){
            if(rmMail.equals(currUser.getMail())){
                throw new IllegalStateException();
            }
        }        
    }
    
    @Test
    public void testRemoveByMailFromEmptyDataBase() {     
        final String rmMail = "venya.pupkin@gmail.com";        
        assertEquals("Failed removing an user by the mail"
                + " in the empty database",
                userDAO.removeByMail(rmMail), false); 
        
    }
    
    @Test
    public void testRemoveByPhoneNumber() {     
        final String rmPhoneNumbet = "8(877)6669990";
        createBulkOfUsers();
        
        assertEquals("Failed removing an user by the mail",
                     userDAO.removeByPhonenumber(rmPhoneNumbet), true); 
        
        List<User> allUsers = null;
        allUsers = userDAO.findAll();
        assertEquals("Failed search of all users",
                     (null != allUsers), true); 
        
        for(User currUser : allUsers){
            if(rmPhoneNumbet.equals(currUser.getMail())){
                throw new IllegalStateException();
            }
        }        
    }
    
    @Test
    public void testRemoveByPhoneNumberFromEmptyDataBase() {     
        final String rmPhoneNumbet = "8(877)6669990";        
        assertEquals("Failed removing an user by the phone number"
                + " in the empty database",
                userDAO.removeByPhonenumber(rmPhoneNumbet), false); 
                
    }
    
    private void createBulkOfUsers(){
        
        User insertUser0 = new User();
        insertUser0.setFirstname("Venya");
        insertUser0.setSurname("Pupkin");
        insertUser0.setMail("venya.pupkin@gmail.com");
        insertUser0.setPhoneNumber("8(777)7779990");
        userDAO.insert(insertUser0);        
        
        User insertUser1 = new User();
        insertUser1.setFirstname("Mitya");
        insertUser1.setSurname("Pupkin");
        insertUser1.setMail("vitya.pupkin@gmail.com");
        insertUser1.setPhoneNumber("8(877)6669990");
        userDAO.insert(insertUser1);
        
        User insertUser2 = new User();
        insertUser2.setFirstname("Mitya");
        insertUser2.setSurname("Pupkin");
        insertUser2.setMail("mitya.pupkin@gmail.com");
        insertUser2.setPhoneNumber("8(887)6669990");
        userDAO.insert(insertUser2);
        
        User insertUser4 = new User();
        insertUser4.setFirstname("Slava");
        insertUser4.setSurname("Pupkin");
        insertUser4.setMail("slava.pupkin@gmail.com");
        insertUser4.setPhoneNumber("8(888)6669990");
        userDAO.insert(insertUser4);
    }
    
    private UserDAO userDAO = Creator.createUserDAO();
}
