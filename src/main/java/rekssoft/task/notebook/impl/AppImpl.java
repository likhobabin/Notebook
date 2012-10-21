package rekssoft.task.notebook.impl;

import java.io.Console;
import java.util.List;
import javax.persistence.PersistenceException;
import rekssoft.task.notebook.interfaces.App;
import rekssoft.task.notebook.interfaces.CommandParser;
import static rekssoft.task.notebook.interfaces.CommandParser.QUIT_COMMAND;
import rekssoft.task.notebook.interfaces.Creator;
import rekssoft.task.notebook.interfaces.UserDAO;

/**
 * Class <tt>AppImpl</tt> implements the interface {@link App} and provides the
 * life-cycle of the application. <p> A typical application life-cycle is
 * presentation of user information, and waiting for user commands. To create an
 * AppImpl instance you should use a {@link Creator} class.
 *
 * @see Creator
 * @see App
 * @author ilya
 */
public class AppImpl implements App {

    /**
     * Runs the application
     *
     * @param args
     */
    public static void main(String[] args) {
        App app = null;
        try {
            /*
             * creates with Creator class
             */
            app = Creator.createApp();
            /*
             * presents a help information and waits for user commands
             */
            app.startDialog();
        }
        /*
         * to be sure the App instance is closed
         */
        finally {
            app.close();
        }
    }

    public void startDialog() {
        /*
         * presents help
         */
        helpDialog();
        waitCommand();
    }

    /**
     * Help presentation
     */
    public void helpDialog() {
        System.out.println("Usage: notebook <command> ");
        System.out.println("Commands: ");
        System.out.println("--help\n"
                + "--insert <full-name> <e-mail> <phone-number>\n"
                + "--print [Show an users table]\n"
                + "--find <first-name>\n"
                + "--remove <e-mail> [Remove an user by e-mail]\n"
                + "--quit [Quit the program]\n"
                + "[ Remark:"
                + "\nRegular expressions:"
                + "\n<full-name> : <firstname> <surname> (/^[A-Z]([A-Za-z]){1,20} ^[A-Z]([A-Za-z]){1,20}/)"
                + "\n\t iVan eGorov "
                + "\n\t Alkjhhkhkhkhkhkhkhkjhk kjhkhhkhkhkhkhkhkj (not more than 21 chars) "
                + "are INCORRECT"
                + "\n\t Ivan Pupckin is CORRECT"
                + "\n<e-mail> : "
                + "\n\t__iv++an...iv--anov@g__m++a--i?l.c"
                + "\n\t.ivanov@gmail.com "
                + "\n\t_iv--a-n--o++v.@gmail.c"
                + "\n\tivan..ov@gmail.com or ivanov@gma..il.com are INCORRECT"
                + "\n\ti.v+n-0_v.iv+an@ya.ru"
                + "\n\tivan.ivanov@gmail.com is CORRECT"
                + "\n<phone-number> /^[0-9]\\(([0-9]){3}\\)([0-9]){7}/"
                + "\n\t 8(950)1234567 is CORRECT"
                + "\n\t 8789a12132434343 is INCORRECT ]");
    }

    /**
     * Waits an user command and passes it to a CommandParser object. Ends up
     * the application life-cycle.
     *
     * @throws NullPointerException if the console can be done
     */
    public void waitCommand() throws NullPointerException {
        Console console = System.console();
        if (null == console) {
            throw new NullPointerException();
        }
        String command = null;

        System.out.println("Waiting for a command...");
        while (!getCommandParcer().isQuit()) {
            command = console.readLine("%s", "Input a command ");
            getCommandParcer().setCommand(command);
            showDialog();
        }
    }

    /**
     * Presents a simple user table.
     */
    public void printDialog() {
        List<User> allUsers = null;
        try {
            if (null == (allUsers = getUserDAO().findAll())) {
                System.err.println("Info: App.findAll"
                        + " Could not find users");

            } else {
                if (0 != allUsers.size()) {
                    final String[] tableHead = {
                        "Firstname",
                        "Surname",
                        "E-mail",
                        "Phone-number"
                    };
                    final int addnlAsteriskCount = 13;
                    final int maxFirstnameLength =
                            getMaxFirstnameLength(allUsers, tableHead[0].length());

                    final int maxSurnameLength =
                            getMaxSurnameLength(allUsers, tableHead[1].length());

                    final int maxMailLengh =
                            getMaxMailLengh(allUsers, tableHead[2].length());

                    final int maxPhonenumberLengh =
                            getMaxPhonenumberLengh(allUsers, tableHead[3].length());

                    final int asteriskCount = maxFirstnameLength + maxSurnameLength
                            + maxMailLengh + maxPhonenumberLengh + addnlAsteriskCount;

                    printTableHead(tableHead, maxFirstnameLength, maxSurnameLength,
                                   maxMailLengh, maxPhonenumberLengh, addnlAsteriskCount);
                    for (User currUser : allUsers) {
                        printUserRow(currUser, maxFirstnameLength, maxSurnameLength,
                                     maxMailLengh, maxPhonenumberLengh);
                    }

                    for (int i = 0; asteriskCount > i; i++) {
                        System.out.print("*");
                    }
                    System.out.println();
                } else {
                    System.err.println("Info: The Users Database is empty");
                }
            }
        }
        catch (PersistenceException ex) {
            ex.printStackTrace();
            System.err.println("Info: App.findAll"
                    + " Could not find users,"
                    + " database error");

            getCommandParcer().setCommand(QUIT_COMMAND);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
            System.err.println("Info: App.findAll"
                    + " Could not find users,"
                    + "runtime error");

            getCommandParcer().setCommand(QUIT_COMMAND);
        }
    }

    /*    
     * All the following static methods (printTableHead, printUserRow, 
     * getMaxFirstnameLength and so on ) are used to implement left alignment of
     * an user table.
     */
    private static void printTableHead(String[] aTableHead,
                                       int aMaxFirstnameLength,
                                       int aMaxSurnameLength,
                                       int aMaxMailLength,
                                       int aMaxPhonenumberLength,
                                       int anAddnlAsteriskCount) {

        final int asteriskCount = aMaxFirstnameLength + aMaxSurnameLength
                + aMaxMailLength + aMaxPhonenumberLength + anAddnlAsteriskCount;
        for (int i = 0; asteriskCount > i; i++) {
            System.out.print("*");
        }
        System.out.println();
        int addnlWhiteSpace = aMaxFirstnameLength
                - aTableHead[0].length();

        System.out.format("| %s", aTableHead[0]);
        for (int i = 0; addnlWhiteSpace > i; i++) {
            System.out.print(" ");
        }
        System.out.print(" ");

        addnlWhiteSpace = aMaxSurnameLength
                - aTableHead[1].length();

        System.out.format("| %s", aTableHead[1]);
        for (int i = 0; addnlWhiteSpace > i; i++) {
            System.out.print(" ");
        }
        System.out.print(" ");

        addnlWhiteSpace = aMaxMailLength
                - aTableHead[2].length();

        System.out.format("| %s", aTableHead[2]);
        for (int i = 0; addnlWhiteSpace > i; i++) {
            System.out.print(" ");
        }
        System.out.print(" ");

        addnlWhiteSpace = aMaxPhonenumberLength
                - aTableHead[3].length();

        System.out.format("| %s", aTableHead[3]);
        for (int i = 0; addnlWhiteSpace > i; i++) {
            System.out.print(" ");
        }
        System.out.println(" |");
        for (int i = 0; asteriskCount > i; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    private static void printUserRow(User anUser,
                                     int aMaxFirstnameLength,
                                     int aMaxSurnameLength,
                                     int aMaxMailLength,
                                     int aMaxPhonenumber) {

        int addnlWhiteSpace = aMaxFirstnameLength
                - anUser.getFirstname().length();

        System.out.format("| %s", anUser.getFirstname());
        for (int i = 0; addnlWhiteSpace > i; i++) {
            System.out.print(" ");
        }
        System.out.print(" |");

        addnlWhiteSpace = aMaxSurnameLength
                - anUser.getSurname().length();

        System.out.format(" %s", anUser.getSurname());
        for (int i = 0; addnlWhiteSpace > i; i++) {
            System.out.print(" ");
        }
        System.out.print(" |");

        addnlWhiteSpace = aMaxMailLength
                - anUser.getMail().length();

        System.out.format(" %s", anUser.getMail());
        for (int i = 0; addnlWhiteSpace > i; i++) {
            System.out.print(" ");
        }
        System.out.print(" |");

        addnlWhiteSpace = aMaxPhonenumber
                - anUser.getPhoneNumber().length();

        System.out.format(" %s", anUser.getPhoneNumber());
        for (int i = 0; addnlWhiteSpace > i; i++) {
            System.out.print(" ");
        }
        System.out.println(" |");
    }

    private static int getMaxFirstnameLength(final List<User> allUsers,
                                             int maxLength) {
        for (User currUser : allUsers) {
            if (maxLength < currUser.getFirstname().length()) {
                maxLength = currUser.getFirstname().length();
            }
        }
        return maxLength;
    }

    private static int getMaxSurnameLength(final List<User> allUsers,
                                           int maxLength) {

        for (User currUser : allUsers) {
            if (maxLength < currUser.getSurname().length()) {
                maxLength = currUser.getSurname().length();
            }
        }
        return maxLength;
    }

    private static int getMaxMailLengh(final List<User> allUsers,
                                       int maxLength) {

        for (User currUser : allUsers) {
            if (maxLength < currUser.getMail().length()) {
                maxLength = currUser.getMail().length();
            }
        }
        return maxLength;
    }

    private static int getMaxPhonenumberLengh(final List<User> allUsers,
                                              int maxLength) {

        for (User currUser : allUsers) {
            if (maxLength < currUser.getPhoneNumber().length()) {
                maxLength = currUser.getPhoneNumber().length();
            }
        }
        return maxLength;
    }

    /**
     * Presents an user table that is a result of an user name search.
     */
    public void findByNameDialog() {
        String findName = getCommandParcer().parseFindByNameCommand();
        if (null == findName) {
            System.err.println("Info: App.findByNameDialog"
                    + " Could not parse the input command");

        } else {
            List<User> findByNameUsers = null;
            try {
                if (null == (findByNameUsers = getUserDAO().findByName(findName))) {
                    System.err.println("Info: App.findAll"
                            + " Could not find users");

                } else {
                    if (0 != findByNameUsers.size()) {
                        final String[] tableHead = {
                            "Firstname",
                            "Surname",
                            "E-mail",
                            "Phone-number"
                        };
                        final int addnlAsteriskCount = 13;
                        final int maxFirstnameLength =
                                getMaxFirstnameLength(findByNameUsers,
                                                      tableHead[0].length());

                        final int maxSurnameLength =
                                getMaxSurnameLength(findByNameUsers,
                                                    tableHead[1].length());

                        final int maxMailLengh =
                                getMaxMailLengh(findByNameUsers,
                                                tableHead[2].length());

                        final int maxPhonenumberLengh =
                                getMaxPhonenumberLengh(findByNameUsers,
                                                       tableHead[3].length());

                        final int asteriskCount =
                                maxFirstnameLength + maxSurnameLength
                                + maxMailLengh + maxPhonenumberLengh
                                + addnlAsteriskCount;

                        printTableHead(tableHead, maxFirstnameLength,
                                       maxSurnameLength, maxMailLengh,
                                       maxPhonenumberLengh, addnlAsteriskCount);

                        for (User currUser : findByNameUsers) {
                            printUserRow(currUser, maxFirstnameLength,
                                         maxSurnameLength, maxMailLengh,
                                         maxPhonenumberLengh);
                        }

                        for (int i = 0; asteriskCount > i; i++) {
                            System.out.print("*");
                        }
                        System.out.println();
                    } else {
                        System.err.println("Info: Can't find an user by the name");
                    }
                }
            }
            catch (PersistenceException ex) {
                ex.printStackTrace();
                System.err.println("Info: App.findByNameDialog"
                        + " Could not find users,"
                        + " database error");

                getCommandParcer().setCommand(QUIT_COMMAND);
            }
            catch (RuntimeException ex) {
                ex.printStackTrace();
                System.err.println("Info: App.findByNameDialog"
                        + " Could not find users,"
                        + "runtime error");

                getCommandParcer().setCommand(QUIT_COMMAND);
            }
        }
    }

    /**
     * Receives the insert type commands and processes them with use of an
     * {@link UserDAO} instance. So it inserts an {@link User} object if the
     * object is not null and no unpredictable exceptions are raised. If an
     * exception is raised,the method passes the quit type command.
     *
     * @see UserDAO
     * @see User
     */
    public void insertDialog() {
        User insertUser = getCommandParcer().parseInsertCommand();
        if (null == insertUser) {
            System.err.println("Info: App.insertDialog"
                    + " Could not parse the input command");

        } else {
            try {
                if (!getUserDAO().insert(insertUser)) {
                    System.out.println("Info: App.insertDialog"
                            + " Could not insert the input user");

                }
            }
            catch (PersistenceException ex) {
                ex.printStackTrace();
                System.err.println("Info: App.insertDialog"
                        + " Could not insert the input user,"
                        + " database error");

                getCommandParcer().setCommand(QUIT_COMMAND);
            }
            catch (RuntimeException ex) {
                ex.printStackTrace();
                System.err.println("Info: App.insertDialog"
                        + " Could not insert the input user,"
                        + "runtime error");

                getCommandParcer().setCommand(QUIT_COMMAND);
            }
        }
    }

    /**
     * Removes an user from the database with use of his e-mail. So it removes
     * an {@link User} object if the object is not null and no unpredictable
     * exceptions are raised. If an exception is raised,the method passes the
     * quit type command.
     *
     * @see User
     */
    public void removeDialog() {
        String rmMail = getCommandParcer().parseRemoveCommand();
        if (null == rmMail) {
            System.err.println("Info: App.removeDialog"
                    + " Could not parse the input command");
        } else {
            try {
                if (!getUserDAO().removeByMail(rmMail)) {
                    System.out.println("Info: App.removeDialog"
                            + " Could not remove an user by the mail");

                }
            }
            catch (PersistenceException ex) {
                ex.printStackTrace();
                System.err.println("Info: App.removeDialog"
                        + " Could not remove an user by the mail,"
                        + " database error");

                getCommandParcer().setCommand(QUIT_COMMAND);
            }
            catch (RuntimeException ex) {
                ex.printStackTrace();
                System.err.println("Info: App.insertDialog"
                        + " Could not remove an user by the mail,"
                        + " runtime error");

                getCommandParcer().setCommand(QUIT_COMMAND);
            }
        }

    }

    /**
     * The end of the application causes the completion of an {@link UserDAO}
     * object. The database connection is closed as a result of that.
     *
     * @see UserDAO
     */
    public void close() {
        getUserDAO().close();
    }

    protected void showDialog() {
        if (getCommandParcer().isHelped()) {
            helpDialog();
        } else if (getCommandParcer().isInserted()) {
            insertDialog();
        } else if (getCommandParcer().isPrinted()) {
            printDialog();
        } else if (getCommandParcer().isRemoved()) {
            removeDialog();
        } else if (getCommandParcer().isFoundByName()) {
            findByNameDialog();
        } else if (getCommandParcer().isQuit()) {
            System.out.println("Good bye!!!");
        } else {
            System.out.println("An unsupported command");
        }
    }

    protected CommandParser getCommandParcer() {
        return (null == commandParser)
                ? commandParser = Creator.createCommandParcer()
                : commandParser;
    }

    /**
     * Creates and initializes an {@link UserDAO} object. The database
     * connection is opened as a result of that. The 'NotebookUsers' is a
     * persistence unit name and it is passed to the <tt>UserDAO</tt> object.
     *
     * @return <tt>UserDAO</tt> initialized UserDAO object
     */
    protected UserDAO getUserDAO() {
        if (null == userDAO) {
            userDAO = Creator.createUserDAO();
            userDAO.initialize("NotebookUsers");
        }
        return userDAO;
    }
    private CommandParser commandParser;
    private UserDAO userDAO;
}
