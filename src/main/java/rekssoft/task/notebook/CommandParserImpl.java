package rekssoft.task.notebook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
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

    public boolean isInserted() {
        return commandExpression.contains(INSERT_COMMAND);
    }

    public User parseInserting() {
        Pattern splitPattern = Pattern.compile("[ ]+");
        String[] args = splitPattern.split(getCommandExpression());        
        if (5 != args.length) {
            System.err.println("Debug CommandParserImpl.parseInserting Incorrect "
                    + "number of arguments");
            return null;
        }
        String firstname = null;
        if (!Pattern.matches(NAME_PATTERN, args[1])) {
            System.err.println("Debug CommandParserImpl.parseInserting Incorrect "
                    + "a firstname");
            return null;
        }
        firstname = args[1];

        String surname = null;
        if (!Pattern.matches(NAME_PATTERN, args[2])) {
            System.err.println("Debug CommandParserImpl.parseInserting Incorrect "
                    + "a surname");
            return null;
        }
        surname = args[2];

        String mail = null;
        if (!Pattern.matches(MAIL_PATTERN, args[3])) {
            System.err.println("Debug CommandParserImpl.parseInserting Incorrect "
                    + "an e-mail");
            return null;
        }
        mail = args[3];

        String phoneNumber = null;
        if (!Pattern.matches(PHONE_NUMBER_PATTERN, args[4])) {
            System.err.println("Debug CommandParserImpl.parseInserting Incorrect"
                    + "a phone-number");
            return null;
        }
        phoneNumber = args[4];

        return new User(firstname, surname, mail, phoneNumber);
    }

    public boolean isRemoved() {
        return commandExpression.contains(REMOVE_COMMAND);
    }

    public String parseRemoving() {
        Pattern splitPattern = Pattern.compile("[ ]+");
        String[] args = splitPattern.split(getCommandExpression());
        if (2 != args.length) {
            System.err.println("Debug CommandParserImpl.parseRemoving Incorrect "
                    + "number of arguments");
            return null;
        }
        if (!Pattern.matches(MAIL_PATTERN, args[1])) {
            System.err.println("Debug CommandParserImpl.parseRemoving Incorrect "
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

    protected Matcher getCommandMatcher() {
        return commandMatcher;
    }
    
    private String commandExpression;
    private Matcher commandMatcher;
}
