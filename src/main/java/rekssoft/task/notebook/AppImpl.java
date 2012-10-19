package rekssoft.task.notebook;

import java.io.Console;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

class AppImpl implements App {

    public static void main(String[] args) {
        Creator.createApp().startDialog();
    }

    public void startDialog() {
        helpDialog();
        waitCommand();
        close();
    }

    public void helpDialog() {
        System.out.println("Usage: notebook <command> ");
        System.out.println("Commands: ");
        System.out.println("--help\n"
                + "--insert <full-name> <e-mail> <phone-number>\n"
                + "--print\n"
                + "--remove <e-mail> ");

    }

    public void printDialog() {
        List<User> allUsers = getUserDAO().findAll();
        printUsersTable(allUsers);
    }

    private static void printUsersTable(List<User> anAllUsers) {
        System.out.println("***********************************************");
        System.out.println("| Firstname | Surname | E-mail | Phone-number |");
        System.out.println("***********************************************");
        
        if (0 != anAllUsers.size()) {
            //calculate a max length of the users table
            final int maxPhoneNumberLength = 21;
            final int addnlAsteriskCount = 8;
            final int asteriskCount =
                    maxFirstnameLength(anAllUsers) + maxSurnameLength(anAllUsers)
                    + maxMailLengh(anAllUsers)
                    + maxPhoneNumberLength + addnlAsteriskCount;

            for (int i = 0; asteriskCount > i; i++) {
                System.out.print("*");
            }
            System.out.println();
            for (User currUser : anAllUsers) {
                System.out.println(currUser);
            }
            for (int i = 0; asteriskCount > i; i++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    private static int maxFirstnameLength(List<User> allUsers){
        int maxLength=-1;
        for(User currUser : allUsers) {
            if(maxLength < currUser.getFirstname().length()){
                maxLength = currUser.getFirstname().length();
            }
        }
        return maxLength;
    }
    
    private static int maxSurnameLength(List<User> allUsers) {
        int maxLength = -1;
        for (User currUser : allUsers) {
            if (maxLength < currUser.getSurname().length()) {
                maxLength = currUser.getSurname().length();
            }
        }
        return maxLength;
    }
    
    private static int maxMailLengh(List<User> allUsers) {
        int maxLength = -1;
        for (User currUser : allUsers) {
            if (maxLength < currUser.getMail().length()) {
                maxLength = currUser.getMail().length();
            }
        }
        return maxLength;
    }
    
    public void insertDialog() {
        User insertUser = getCommandParcer().parseInserting();
        if(null == insertUser){
            System.err.println("Debug AppImpl.insertDialog"
                    + " Could not parse the input command");
            
        }
        else {
            try {
                if(!getUserDAO().insert(insertUser)) {
                    System.out.println("Debug AppImpl.insertDialog"
                        + " Could not insert the input user");
                    
                }
            }
            catch (RollbackException ex) {
                System.err.println("Debug AppImpl.insertDialog"
                        + " Could not insert the input user, it is a clone entity");
                
                ex.printStackTrace();
            }
            catch (PersistenceException ex) {
                System.err.println("Debug AppImpl.insertDialog"
                        + " Could not insert the input user,"
                        + " database error");
                
                ex.printStackTrace();
            }
            catch(RuntimeException ex) {
                System.err.println("Debug AppImpl.insertDialog"
                        + " Could not insert the input user");       
                
                ex.printStackTrace();
            }
        }
    }

    /*
     * Removal by e-mail 
     */
    public void removeDialog() {
        String rmMail = getCommandParcer().parseRemoving();
        if (null == rmMail) {
            System.err.println("Debug AppImpl.removeDialog"
                    + " Could not parse the input command");
        } else {
            try {
                if(!getUserDAO().removeByMail(rmMail)) {
                    System.out.println("Debug AppImpl.removeDialog"
                        + " Could not remove an user by the mail");
                    
                }
            }
            catch (PersistenceException ex) {
                System.err.println("Debug AppImpl.removeDialog"
                        + " Could not remove an user by the mail,"
                        + " database error");
                
                ex.printStackTrace();
            }
            catch(RuntimeException ex) {
                System.err.println("Debug AppImpl.insertDialog"
                        + " Could not remove an user by the mail,"
                        + " runtime error");                
                
                ex.printStackTrace();
            }            
        }
        
    }

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
    
    public void close(){ 
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
        } else if (getCommandParcer().isQuit()) {
            System.out.println("Good bye!!!");
        } else {
            System.out.println("An unsupported command");
        }
    }

    private CommandParser getCommandParcer() {
        return (null == commandParser)
                ? commandParser = Creator.createCommandParcer()
                : commandParser;
    }
    
    private UserDAO getUserDAO() {        
        if(null == userDAO) {
            userDAO = Creator.createUserDAO();
            userDAO.initialize("NotebookUsers");
        }
        return userDAO;
    }
    
    private CommandParser commandParser;
    private UserDAO userDAO;
}
