package rekssoft.task.notebook;

import java.io.Console;
import java.util.List;
import javax.persistence.PersistenceException;
import static rekssoft.task.notebook.CommandParser.QUIT_COMMAND;

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
                + "--print [Show an users table]\n"
                + "--remove <e-mail> [Remove an user by e-mail]\n"
                + "--quit [Quit the program]\n"
                + "[Remark:"
                + "\nRegular expressions:"
                + "\n<full-name> : ^[A-Z]([A-Za-z]){1,20}"
                + "\n<e-mail> : \n\t^[0-9A-Za-z]+((\\.[0-9A-Za-z]+)*"
            + "\n\t(_[0-9A-Za-z]+)*(-[0-9A-Za-z]+)*(\\+[0-9A-Za-z]+)*)*"
            + "\n\t@" + "[0-9A-Za-z]+"
            + "\n\t((\\.[0-9A-Za-z]+)*(_[0-9A-Za-z]+)*(-[0-9A-Za-z]+)*(\\+[0-9A-Za-z]+)*)*"
            + "\n\t\\.([A-za-z0-9]{2,})$"
                + "\n\tSo e-mails look like __iv++an...iv--anov@g__m++a--i?l.c are incorrect"
                + "\n\t i.v+n-0_v.iv+an@ya.ru is correct"
                + "\n<phone-number> ^[0-9]\\(([0-9]){3}\\)([0-9]){7} ]");   
    }

    public void printDialog() {
        List<User> allUsers = getUserDAO().findAll();
        printUsersTable(allUsers);
    }

    private static void printUsersTable(List<User> anAllUsers) {        
        if (0 != anAllUsers.size()) {
            final String[] tableHead = {
                "Firstname",
                "Surname",
                "E-mail",
                "Phone-number"
            };
            final int addnlAsteriskCount = 13;
            final int maxFirstnameLength =
                    getMaxFirstnameLength(anAllUsers, tableHead[0].length());

            final int maxSurnameLength =
                    getMaxSurnameLength(anAllUsers, tableHead[1].length());

            final int maxMailLengh =
                    getMaxMailLengh(anAllUsers, tableHead[2].length());

            final int maxPhonenumberLengh =
                    getMaxPhonenumberLengh(anAllUsers, tableHead[3].length());

            final int asteriskCount = maxFirstnameLength + maxSurnameLength
                    + maxMailLengh + maxPhonenumberLengh + addnlAsteriskCount;
            
            printTableHead(tableHead, maxFirstnameLength, maxSurnameLength,
                           maxMailLengh, maxPhonenumberLengh, addnlAsteriskCount);
            for (User currUser : anAllUsers) {
                printUserRow(currUser, maxFirstnameLength, maxSurnameLength,
                             maxMailLengh, maxPhonenumberLengh);
            }
            
            for (int i = 0; asteriskCount > i; i++) {
                System.out.print("*");
            }
            System.out.println();
        }
        else {
            System.out.println("Info:: The Users Database is empty");
        }
    }
    
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
        for(int i = 0; addnlWhiteSpace > i; i++){
            System.out.print(" ");
        }
        System.out.print(" ");
        
        addnlWhiteSpace = aMaxSurnameLength 
                - aTableHead[1].length();
        
        System.out.format("| %s", aTableHead[1]);
        for(int i = 0; addnlWhiteSpace > i; i++){
            System.out.print(" ");
        }
        System.out.print(" ");        
        
        addnlWhiteSpace = aMaxMailLength 
                - aTableHead[2].length();
        
        System.out.format("| %s", aTableHead[2]);
        for(int i = 0; addnlWhiteSpace > i; i++){
            System.out.print(" ");
        }
        System.out.print(" ");    
        
        addnlWhiteSpace = aMaxPhonenumberLength 
                - aTableHead[3].length();
        
        System.out.format("| %s", aTableHead[3]);
        for(int i = 0; addnlWhiteSpace > i; i++){
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
        for(int i = 0; addnlWhiteSpace > i; i++){
            System.out.print(" ");
        }
        System.out.print(" |");
        
        addnlWhiteSpace = aMaxSurnameLength 
                - anUser.getSurname().length();
        
        System.out.format(" %s", anUser.getSurname());
        for(int i = 0; addnlWhiteSpace > i; i++){
            System.out.print(" ");
        }
        System.out.print(" |");        
        
        addnlWhiteSpace = aMaxMailLength 
                - anUser.getMail().length();
        
        System.out.format(" %s", anUser.getMail());
        for(int i = 0; addnlWhiteSpace > i; i++){
            System.out.print(" ");
        }
        System.out.print(" |");    
        
        addnlWhiteSpace = aMaxPhonenumber 
                - anUser.getPhoneNumber().length();
        
        System.out.format(" %s", anUser.getPhoneNumber());
        for(int i = 0; addnlWhiteSpace > i; i++){
            System.out.print(" ");
        }
        System.out.println(" |");           
    }
    
    private static int getMaxFirstnameLength(final List<User> allUsers,
                                          int maxLength){
        for(User currUser : allUsers) {
            if(maxLength < currUser.getFirstname().length()){
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
                                    int maxLength ) {
        
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
    
    public void insertDialog() {
        User insertUser = getCommandParcer().parseInserting();
        if(null == insertUser){
            System.err.println("Info: App.insertDialog"
                    + " Could not parse the input command");
            
        }
        else {
            try {
                if(!getUserDAO().insert(insertUser)) {
                    System.out.println("Info: App.insertDialog"
                        + " Could not insert the input user");
                    
                }
            }
            catch (PersistenceException ex) {
                System.err.println("Info: App.insertDialog"
                        + " Could not insert the input user,"
                        + " database error");
                
                getCommandParcer().setCommand(QUIT_COMMAND);
//                ex.printStackTrace();
            }
            catch(RuntimeException ex) {
                System.err.println("Info: App.insertDialog"
                        + " Could not insert the input user,"
                        + "runtime error");  
                
                getCommandParcer().setCommand(QUIT_COMMAND);
//                ex.printStackTrace();
            }
        }
    }

    /*
     * Removal by e-mail 
     */
    public void removeDialog() {
        String rmMail = getCommandParcer().parseRemoving();
        if (null == rmMail) {
            System.err.println("Info: App.removeDialog"
                    + " Could not parse the input command");
        } else {
            try {
                if(!getUserDAO().removeByMail(rmMail)) {
                    System.out.println("Info: App.removeDialog"
                        + " Could not remove an user by the mail");
                    
                }
            }
            catch (PersistenceException ex) {
                System.err.println("Info: App.removeDialog"
                        + " Could not remove an user by the mail,"
                        + " database error");
                
                getCommandParcer().setCommand(QUIT_COMMAND);                
//                ex.printStackTrace();
            }
            catch(RuntimeException ex) {
                System.err.println("Info: App.insertDialog"
                        + " Could not remove an user by the mail,"
                        + " runtime error");
                
                getCommandParcer().setCommand(QUIT_COMMAND);
//                ex.printStackTrace();
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
