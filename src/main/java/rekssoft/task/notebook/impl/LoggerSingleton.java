package rekssoft.task.notebook.impl;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class <tt><LoggerSingleton/tt> is a logging class the objective of which is a
 * recording of all occurred exceptions. <p> The logger is based on the
 * singleton pattern. A text file of the logger is placed into the
 * ${PROJ_DIR}/target/classes/.log by default.
 *
 * @author ilya
 */
enum LoggerSingleton {

    OPENING_LOGGER;

    public Logger getSystemLogger() {
        return getLogger().getSystemLogger();
    }

    public boolean isLoggerOpened() {
        return getLogger().isOpened();
    }

    public boolean openLogger()
            throws NullPointerException {

        if (null == getLogger().getSystemLogger()
                && null == getLogger().defaultOpen()) {

            System.err.println("Can't open "
                    + "rekssoft.task.notebook.impl.LoggerSingleton.oepnLogger");

            return false;
        }
        return true;
    }

    public boolean openLogger(boolean isAppend, Level aLogLevel)
            throws NullPointerException {

        if (null == getLogger().getSystemLogger()
                && null == getLogger().open(isAppend, aLogLevel)) {

            System.err.println("Can't open "
                    + "rekssoft.task.notebook.impl.LoggerSingleton.oepnLogger");

            return false;
        }
        return true;
    }

    public void closeLogger() {
        getLogger().close();
    }

    private TxtLogger getLogger() {
        try {
            if (null == txtLogger) {
                txtLogger = new TxtLogger();
            }
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
        return txtLogger;
    }
    private TxtLogger txtLogger;
}