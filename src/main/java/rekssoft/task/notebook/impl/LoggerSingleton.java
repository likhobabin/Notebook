package rekssoft.task.notebook.impl;

import java.io.FileOutputStream;
import java.io.IOException;
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
    
    public static final String FILE_NAME = ".log";

    protected static class TxtLogger implements Closable {

        public TxtLogger() {
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
            return open(false, Level.INFO);
        }

        public Logger open(boolean anIsFileAppend, Level aLogLevel) {
            try {
                if (null != sysLogger && !openedState) {
                    initialize(anIsFileAppend, aLogLevel);
                    openedState = true;
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return sysLogger;
        }

        private void initialize(boolean anIsFileAppend,
                                Level aLogLevel) throws IOException {
            sysLogger.setLevel(aLogLevel);
            sysLogger.addHandler(createHandler(anIsFileAppend));

        }

        private Handler createHandler(boolean anIsFileAppend)
                throws IOException {

            String undecodeLoggMsgPath =
                    rekssoft.task.notebook.impl.LoggerSingleton.class
                    .getProtectionDomain().getCodeSource()
                    .getLocation().getPath()
                    + FILE_NAME;

            String decodeLoggMsgPath = java.net.URLDecoder
                    .decode(undecodeLoggMsgPath, "UTF8");

            FileOutputStream fos = new FileOutputStream(decodeLoggMsgPath,
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
            }
        }
        private static final Logger sysLogger =
                Logger.getLogger(LoggerSingleton.class.getName());
        
        private boolean openedState = false;
    }

    public Logger getSystemLogger() {
        return getLogger().getSystemLogger();
    }

    public boolean isLoggerOpened() {
        return getLogger().isOpened();
    }

    public boolean openLogger() {
        if (null == getLogger().getSystemLogger()
                && null == getLogger().defaultOpen()) {

            System.err.println("Can't open "
                    + "rekssoft.task.notebook.impl.getInternalLogger()");

            return false;
        }
        return true;
    }

    public boolean openLogger(boolean isAppend, Level aLogLevel) {
        if (null == getLogger().getSystemLogger()
                && null == getLogger().open(isAppend, aLogLevel)) {

            System.err.println("Can't open "
                    + "rekssoft.task.notebook.impl.getInternalLogger()");

            return false;
        }
        return true;
    }

    public void closeLogger() {
        getLogger().close();
    }

    private TxtLogger getLogger() {
        return null == txtLogger
                ? txtLogger = new TxtLogger()
                : txtLogger;
    }
    private TxtLogger txtLogger;
}