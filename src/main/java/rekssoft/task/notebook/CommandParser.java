package rekssoft.task.notebook;

/**
 *
 * @author ilya
 */
interface CommandParser {
    public static final String HELP_COMMAND = "--help";    
    public static final String PRINT_COMMAND = "--print";  
    public static final String INSERT_COMMAND = "--insert";    
    public static final String REMOVE_COMMAND = "--remove";
    public static final String QUIT_COMMAND = "--quit";  
    
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
    
    public void setCommand(String anCommandArgument);
    public boolean isHelped();
    public boolean isPrinted();
    public boolean isInserted();
    public User parseInserting();
    public boolean isRemoved();
    public String parseRemoving();
    public boolean isQuit();
}
