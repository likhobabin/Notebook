package rekssoft.task.notebook;

import java.io.Console;

class AppImpl implements App {

    static void main(String[] args) {
        Creator.createApp().startDialog();
    }

    public void startDialog() {
        helpDialog();
        waitCommand();
    }

    public void helpDialog() {
        System.out.println("Usage: notebook <command> ");
        System.out.println("Commands: ");
        System.out.println("--help\n"
                + "--insert <full-name> <e-mail> <phone-number>\n"
                + "--print\n"
                + "--remove <e-mail>");

    }

    public void printDialog() {
        //calculate a max length of the following lines
        System.out.println("****************************************************");
        System.out.println("| Full-name            | E-mail      | Phone-number |");
        //display a format output of all users
        System.out.println("***************************************************");
        //
    }

    public void insertDialog() {
    }

    public void removeDialog() {
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
                ? commandParser = new CommandParserImpl()
                : commandParser;
    }
    
    private CommandParser commandParser;
}
