package rekssoft.task.notebook.impl;

import java.util.regex.Pattern;
import rekssoft.task.notebook.interfaces.CommandParser;

/**
 * Class <tt>CommandParserImpl</tt> presents a parser of input user commands of
 * several types. This implements the {@link CommandParser} interface and
 * provides the following capabilities: <p> - storing an input command
 * expression; <p> - evaluating of a command type with use of <tt>isHelped</tt>,
 * <tt>isPrinted</tt>, <tt>isInserted</tt> and <tt>isRemoved</tt> methods; <p> -
 * extracting data with use of <tt>parseInsertCommand</tt>,
 * <tt>parseRemoveCommand</tt> and <tt>parseFoundByNameCommand</tt>;
 *
 * @see CommandParser
 * @author ilya
 */
public class CommandParserImpl implements CommandParser {

    public void setCommand(String aCommandExpression)
            throws NullPointerException {

        if (null == aCommandExpression) {
            throw new NullPointerException();
        }

        commandExpression = aCommandExpression;
    }

    public boolean isHelped() {
        return commandExpression.equals(HELP_COMMAND);
    }

    public boolean isPrinted() {
        return commandExpression.equals(PRINT_COMMAND);
    }

    public boolean isFoundByName() {
        return commandExpression.contains(FIND_BY_NAME_COMMAND);
    }

    /**
     * Breaks the stored command expression up an arguments array. Arguments are
     * used for extracting an user first name. If the command expression is
     * correct, it returns an user first name otherwise it returns null.
     *
     * @return an user first name if the command expression is correct otherwise
     * it returns null
     */
    public String parseFindByNameCommand() {
        Pattern splitPattern = Pattern.compile("[ ]+");
        String[] args = splitPattern.split(getCommandExpression());
        if (2 != args.length) {
            System.err.println("Debug CommandParserImpl.parseFindByNameCommand Incorrect "
                    + "number of arguments");
            return null;
        }
        String firstname = null;
        if (!Pattern.matches(NAME_PATTERN, args[1])) {
            System.err.println("Debug CommandParserImpl.parseFindByNameCommand Incorrect "
                    + "a firstname");
            return null;
        }
        firstname = args[1];
        return firstname;
    }

    public boolean isInserted() {
        return commandExpression.contains(INSERT_COMMAND);
    }

    /**
     * Breaks the stored command expression up an arguments array. Arguments are
     * used for extracting an {@link User} object. If the command expression is
     * correct, it returns an <tt>User</tt> object otherwise it returns null.
     *
     * @see User
     * @return an <tt>User</tt> object if the command expression is correct
     * otherwise it returns null
     */
    public User parseInsertCommand() {
        Pattern splitPattern = Pattern.compile("[ ]+");
        String[] args = splitPattern.split(getCommandExpression());
        if (5 != args.length) {
            System.err.println("Debug CommandParserImpl.parseInsertCommand Incorrect "
                    + "number of arguments");
            return null;
        }
        String firstname = null;
        if (!Pattern.matches(NAME_PATTERN, args[1])) {
            System.err.println("Debug CommandParserImpl.parseInsertCommand Incorrect "
                    + "a firstname");
            return null;
        }
        firstname = args[1];

        String surname = null;
        if (!Pattern.matches(NAME_PATTERN, args[2])) {
            System.err.println("Debug CommandParserImpl.parseInsertCommand Incorrect "
                    + "a surname");
            return null;
        }
        surname = args[2];

        String mail = null;
        if (!Pattern.matches(MAIL_PATTERN, args[3])) {
            System.err.println("Debug CommandParserImpl.parseInsertCommand Incorrect "
                    + "an e-mail");
            return null;
        }
        mail = args[3];

        String phoneNumber = null;
        if (!Pattern.matches(PHONE_NUMBER_PATTERN, args[4])) {
            System.err.println("Debug CommandParserImpl.parseInsertCommand Incorrect"
                    + "a phone-number");
            return null;
        }
        phoneNumber = args[4];

        return new User(firstname, surname, mail, phoneNumber);
    }

    public boolean isRemoved() {
        return commandExpression.contains(REMOVE_COMMAND);
    }

    /**
     * Breaks the stored command expression up an arguments array. Arguments are
     * used for extracting an e-mail address. If the command expression is
     * correct, it returns an e-mail address otherwise it returns null.
     *
     * @return an e-mail address if the command expression is correct otherwise
     * it returns null
     */
    public String parseRemoveCommand() {
        Pattern splitPattern = Pattern.compile("[ ]+");
        String[] args = splitPattern.split(getCommandExpression());
        if (2 != args.length) {
            System.err.println("Debug CommandParserImpl.parseRemoveCommand Incorrect "
                    + "number of arguments");
            return null;
        }
        if (!Pattern.matches(MAIL_PATTERN, args[1])) {
            System.err.println("Debug CommandParserImpl.parseRemoveCommand Incorrect "
                    + "an e-mail");
            return null;
        }
        return args[1];
    }

    public boolean isQuit() {
        if (null == commandExpression) {
            return false;
        }
        return commandExpression.equals(QUIT_COMMAND);
    }

    protected String getCommandExpression() {
        return commandExpression;
    }
    private String commandExpression;
}
