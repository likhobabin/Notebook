package rekssoft.task.notebook.impl;

import java.util.logging.Level;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author ilya
 */
public class TestLoggerSingleton {

    @BeforeClass
    public static void open() {
        if (!logSingleton.openLogger(false, Level.ALL)) {
            throw new IllegalStateException("Can't open "
                    + "rekssoft.task.notebook.impl.SingletonLogger");

        }
    }

    @AfterClass
    public static void close() {
        logSingleton.closeLogger();
    }

    @Test
    public void testMsgLogging() {
        if (logSingleton.isLoggerOpened()) {
            logSingleton.getSystemLogger().info("Info messsage");
        }
    }

    @Test
    public void testSMsgLogging() {
        try {
            throw new RuntimeException("Test: TestTxtLogger.testMsgLogging");
        }
        catch (RuntimeException ex) {
            if (logSingleton.isLoggerOpened()) {
                logSingleton.getSystemLogger().logp(Level.SEVERE, getClass().getName(),
                                              "SEVERE Logging",
                                              ex.getMessage(), ex);
                
                logSingleton.getSystemLogger().logp(Level.WARNING, getClass().getName(),
                                              "WARNING Logging",
                                              ex.getMessage(), ex);
                                
                logSingleton.getSystemLogger().logp(Level.INFO, getClass().getName(),
                                              "INFO Logging",
                                              ex.getMessage(), ex);
                
                logSingleton.getSystemLogger().logp(Level.CONFIG, getClass().getName(),
                                              "CONFIG Logging",
                                              ex.getMessage(), ex);
                
                logSingleton.getSystemLogger().logp(Level.FINE, getClass().getName(),
                                                    "FINE Logging",
                                                    ex.getMessage(), ex);
                
                logSingleton.getSystemLogger().logp(Level.FINEST, getClass().getName(),
                                                    "FINEST Logging",
                                                    ex.getMessage(), ex);
            }
        }
    }
    private final static LoggerSingleton logSingleton = LoggerSingleton.OPENING_LOGGER;
}
