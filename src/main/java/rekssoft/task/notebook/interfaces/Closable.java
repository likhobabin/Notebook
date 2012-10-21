package rekssoft.task.notebook.interfaces;

/**
 * Interface Closable indicates that a class implementing him must be closed
 * after use.
 *
 * @author ilya
 */
public interface Closable {

    void close() throws RuntimeException;
}
