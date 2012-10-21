package rekssoft.task.notebook;

import rekssoft.task.notebook.interfaces.App;
import rekssoft.task.notebook.interfaces.CommandParser;
import rekssoft.task.notebook.interfaces.UserDAO;

/**
 * Class <tt>Creator</tt> is used to create objects without knowledge about
 * their types of implementation. <p>So if implementation was changed the code
 * that uses the {@code Creator} class would not be changed. <p>This creates the
 * following types: <p> - {@link App}; <p> - {@link CommandParser}; <p> -
 * {@link UserDAO};
 *
 * @see App
 * @see CommandParser
 * @see UserDAO
 * @author ilya
 */
public class Creator {

    /**
     * It might not be constructed. {@code Creator}'s static methods only use.
     */
    private Creator() {
    }

    /**
     * Constructs an {@link App} object.
     *
     * @return a new <tt>App</tt> object
     * @see App
     */
    public static App createApp() {
        return new AppImpl();
    }

    /**
     * Constructs a {@link CommandParser} object.
     *
     * @return a new <tt>CommandParser</tt> object
     * @see CommandParser
     */
    public static CommandParser createCommandParcer() {
        return new CommandParserImpl();
    }

    /**
     * Constructs a {@link UserDAO} object.
     *
     * @return a new <tt>UserDAO</tt> object
     * @see UserDAO
     */
    public static UserDAO createUserDAO() {
        return new UserDAOImpl();
    }
}
