package rekssoft.task.notebook.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import rekssoft.task.notebook.interfaces.Closable;

/**
 *
 * @author ilya
 */
public enum LoggerSingleton {

    OPENING_LOGGER;

    protected static class TxtLogger implements Closable {

        public TxtLogger() throws UnsupportedEncodingException {
            String undecodeLoggMsgPath =
                    rekssoft.task.notebook.impl.LoggerSingleton.class
                    .getProtectionDomain().getCodeSource()
                    .getLocation().getPath()
                    + ".log";

            logFilePath = java.net.URLDecoder
                    .decode(undecodeLoggMsgPath, "UTF8");

        }

        public TxtLogger(String aFilePath) {
            logFilePath = aFilePath;
        }

        public boolean isOpened() {
            return openedState;
        }

        public Logger getSystemLogger() {
            if (!openedState) {
                return null;
            }
            return sysLogger;
        }

        public Logger defaultOpen() {
            return open(false, Level.WARNING);
        }

        public Logger open(boolean anIsFileAppend, Level aLogLevel) {
            try {
                if (null != sysLogger && !openedState) {
                    sysLogger.setLevel(aLogLevel);
                    sysLogger.addHandler(createHandler(anIsFileAppend));
                    openedState=true;
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return sysLogger;
        }

        private Handler createHandler(boolean anIsFileAppend)
                throws IOException {

            FileOutputStream fos = new FileOutputStream(logFilePath,
                                                        anIsFileAppend);

            StreamHandler streamHandler =
                    new StreamHandler(fos, new SimpleFormatter());

            streamHandler.setLevel(sysLogger.getLevel());
            return streamHandler;
        }

        public void close() throws SecurityException {
            if (openedState) {
                for (Handler currHandler : sysLogger.getHandlers()) {
                    currHandler.flush();
                    currHandler.close();
                }
                openedState=false;
            }
        }
        private static final Logger sysLogger =
                Logger.getLogger(LoggerSingleton.class.getName());
        private boolean openedState = false;
        private final String logFilePath;
    }

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