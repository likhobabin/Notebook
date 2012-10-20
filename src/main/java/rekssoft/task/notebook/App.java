package rekssoft.task.notebook;

/**
 * interface <tt>App</tt> is a central application interface
 * that represents the life-cycle of the notebook application. It is a part 
 * of the presentation layout of the application.
 * <p><tt>App</tt> interface is implemented by a {@link AppImpl} class. 
 * 
 * @see AppImpl
 * @author ilya
 */
public interface App extends Closable {
    
    /**
     * Provides a base of the application life-cycle.
     */
    public void startDialog();

    /**
     * Controls the application life-cycle continuance
     * and takes in client commands.
     * @throws NullPointerException if the console can be done 
     */
    public void waitCommand() throws NullPointerException;
    
    /**
     * Prints help information. 
     */
    public void helpDialog();

    /**
     * Presents an user table.
     */
    public void printDialog();
    
    /**
     * Provides dialog with an {@link UserDAO} object 
     * to insert new users.
     * @see UserDAO 
     */
    public void insertDialog();
    
    /**
     * Provides dialog with an {@link UserDAO} object 
     * to remove users.
     * @see UserDAO 
    */
    public void removeDialog();
    
}
