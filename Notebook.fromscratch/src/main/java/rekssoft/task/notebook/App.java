package rekssoft.task.notebook;

/**
 *
 * @author ilya
 */
public interface App extends Closable {

    public void startDialog();

    public void helpDialog();

    public void printDialog();

    public void insertDialog();

    public void removeDialog();

    public void waitCommand() throws NullPointerException;
}
