package rekssoft.task.notebook.interfaces;

import rekssoft.task.notebook.impl.User;

/**
 * Interface <tt>CommandParser</tt> allows to a client to parse input commands.
 * <p> A <tt>CommandParser</tt> represents the following operations: <p> -
 * determination of the type of an input command; <p> - determination of
 * correctness of an input command; <p> - extracting data from a command; <p>
 * Client of the <tt>CommandParser</tt> is {@link AppImpl} that implements the
 * {@link App} interface. <tt>CommandParser</tt> is implemented by
 * {@link CommandParserImpl}
 *
 * @see App
 * @see rekssoft.task.notebook.impl.AppImpl
 * @see rekssoft.task.notebook.impl.CommandParserImpl
 *
 * @author ilya
 */
public interface CommandParser {
    /*
     * All these are types of command. They speak for themselves.
     */

    public static final String HELP_COMMAND = "--help";
    public static final String PRINT_COMMAND = "--print";
    public static final String FIND_BY_NAME_COMMAND = "--find";
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
    public static final String PHONE_NUMBER_PATTERN =
            "^[0-9]\\(([0-9]){3}\\)([0-9]){7}";

    /**
     * Stores a command expression.
     *
     * @param anCommandExpression a command expression
     */
    public void setCommand(String anCommandExpression);

    /**
     * Returns <tt>true</tt> if the input command is the help type.
     *
     * @return <tt>true</tt> if the input command is the help type
     */
    public boolean isHelped();

    /**
     * Returns <tt>true</tt> if the input command is the print type.
     *
     * @return <tt>true</tt> if the input command is the print type
     */
    public boolean isPrinted();

    /**
     * Returns <tt>true</tt> if the input command is the find by name type.
     *
     * @return <tt>true</tt> if the input command is the find by name type
     */
    public boolean isFoundByName();

    /**
     * Returns a <tt>String</tt> object if the input command is the find by name
     * type and if it is correctness otherwise it returns <tt>null</tt>. The
     * <tt>String</tt> object is an user first name that will be used to find
     * its owners.
     *
     * @return extracted an user first name
     */
    public String parseFindByNameCommand();

    /**
     * Returns <tt>true</tt> if the input command is the insert type.
     *
     * @return <tt>true</tt> if the input command is the insert type
     */
    public boolean isInserted();

    /**
     * Returns an {@link User} object if the input command is the insert type
     * and if it is correctness otherwise it returns {@code null}.
     *
     * @return extracted an User object
     * @see rekssoft.task.notebook.impl.User
     */
    public User parseInsertCommand();

    /**
     * Returns <tt>true</tt> if the input command is the remove type.
     *
     * @return <tt>true</tt> if the input command is the remove type
     */
    public boolean isRemoved();

    /**
     * Returns a <tt>String</tt> object if the input command is the remove type
     * and if it is correctness otherwise it returns <tt>null</tt>. The
     * <tt>String</tt> object is a mail address that will be used to remove its
     * owner.
     *
     * @return extracted a mail address
     */
    public String parseRemoveCommand();

    /**
     * Returns <tt>true</tt> if the input command is the quit type.
     *
     * @return <tt>true</tt> if the input command is the quit type
     */
    public boolean isQuit();
}
