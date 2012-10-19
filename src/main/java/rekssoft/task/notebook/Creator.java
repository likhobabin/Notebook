package rekssoft.task.notebook;

/**
 * Class <tt>Creator</tt> is used to create objects without knowledge about
 * their types of implementations. 
 * <p>So if implementation will be changed the code that uses the {@code Creator} 
 * class won't be changed. 
 * @author ilya
 */
public class Creator {
    /**
     * It might not be constructed. {@code Creator}'s static methods only use.
     */
    private Creator(){        
    }
    
    /**
     * Constructs an <tt>App</tt> object. 
     * @return a new <tt>App</tt> object
    */
    public static App createApp() {
        return new AppImpl();
    }
    
    /**
     * Constructs a <tt>CommandParser</tt> object. 
     * @return a new <tt>CommandParser</tt> object
    */
    public static CommandParser createCommandParcer() {
        return new CommandParserImpl();
    }
    
    /**
     * Constructs a <tt>UserDAO</tt> object. 
     * @return a new <tt>UserDAO</tt> object
     */
    public static UserDAO createUserDAO() {
        return new UserDAOImpl();
    }
}
