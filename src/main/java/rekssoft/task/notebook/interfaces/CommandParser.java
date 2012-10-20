package rekssoft.task.notebook.interfaces;

import rekssoft.task.notebook.User;

/**
 * Interface <tt>CommandParser</tt> allows to a client 
 * to parse input commands.
 * <p> A <tt>CommandParser</tt> represents the following operations:
 * <p> - determination of the type of an input command;
 * <p> - determination of correctness of an input command;
 * <p> - extracting data from a command;
 * <p> Client of the <tt>CommandParser</tt> is {@link AppImpl} that implements the 
 * {@link App} interface. 
 * <tt>CommandParser</tt> is implemented by {@link CommandParserImpl}
 * 
 * @see App
 * @see AppImpl
 * @see CommandParserImpl
 * 
 * @author ilya
 */
public interface CommandParser {
    /*
     * All these are types of command. They speak for themselves.
     */
    public static final String HELP_COMMAND = "--help";    
    public static final String PRINT_COMMAND = "--print";  
    public static final String INSERT_COMMAND = "--insert";    
    public static final String REMOVE_COMMAND = "--remove";
    public static final String QUIT_COMMAND = "--quit";  
    
    /*
     * These are regular expression patterns. They are used to parse particulars
     * of an input command.
     */
    public static final String NAME_PATTERN = "^[A-Z]([A-Za-z]){1,20}";    
    public static final String MAIL_PATTERN = "^[0-9A-Za-z]+((\\.[0-9A-Za-z]+)*"
            + "(_[0-9A-Za-z]+)*(-[0-9A-Za-z]+)*(\\+[0-9A-Za-z]+)*)*"
            + "@" + "[0-9A-Za-z]+"
            + "((\\.[0-9A-Za-z]+)*(_[0-9A-Za-z]+)*(-[0-9A-Za-z]+)*(\\+[0-9A-Za-z]+)*)*"
            + "\\.([A-za-z0-9]{2,})$";    
    
//    public static final String MAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"+
//      "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public static final String PHONE_NUMBER_PATTERN = 
            "^[0-9]\\(([0-9]){3}\\)([0-9]){7}";
    /**
     * Stores a command expression. 
     * @param anCommandExpression a command expression 
     */
    public void setCommand(String anCommandExpression);
    
    /**
     * Returns <tt>true</tt> if the input command is the help type.
     * @return <tt>true</tt> if the input command is the help type
     */
    public boolean isHelped();
    
    /**
     * Returns <tt>true</tt> if the input command is the print type.
     * @return <tt>true</tt> if the input command is the print type
    */
    public boolean isPrinted();
    
    /**
     * Returns <tt>true</tt> if the input command is the insert type.
     * @return <tt>true</tt> if the input command is the insert type
    */
    public boolean isInserted();
    
    /**
     * Returns an {@link User} object if the input command is the insert type 
     * and if it is correctness otherwise it returns {@code null}. 
     * @return extracted User object
     * @see User
     */
    public User parseInserting();
    
    /**
     * Returns <tt>true</tt> if the input command is the remove type.
     * @return <tt>true</tt> if the input command is the remove type
    */
    public boolean isRemoved();
    
    /**
     * Returns a <tt>String</tt> object if the input command is the remove type 
     * and if it is correctness otherwise it returns <tt>false</tt>.
     * The <tt>String</tt> object is a mail address that will be used to remove 
     * its owner.
     * @return an extracted mail address
     */
    public String parseRemoving();
    
    /**
     * Returns <tt>true</tt> if the input command is the quit type.
     * @return <tt>true</tt> if the input command is the quit type
    */
    public boolean isQuit();
}
