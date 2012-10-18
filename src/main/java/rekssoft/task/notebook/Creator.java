package rekssoft.task.notebook;

/**
 *
 * @author ilya
 */
public class Creator {
    private Creator(){
        
    }
    
    public static UserDAO createUserDAO() {
        return new UserDAOImpl();
    }
    
    public static App createApp() {
        return new AppImpl();
    }
}
