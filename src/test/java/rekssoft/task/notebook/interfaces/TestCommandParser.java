package rekssoft.task.notebook.interfaces;

import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import rekssoft.task.notebook.impl.User;
import static rekssoft.task.notebook.interfaces.CommandParser.MAIL_PATTERN;
import static rekssoft.task.notebook.interfaces.CommandParser.NAME_PATTERN;
import static rekssoft.task.notebook.interfaces.CommandParser.PHONE_NUMBER_PATTERN;
//
//import org.junit.Ignore;

/**
 *
 * @author ilya
 */
public class TestCommandParser {

    @Before
    public void createParcer() {
        commandParser = Creator.createCommandParcer();
    }

    @Test
    public void testNamePattern() {
        assertEquals("Incorrect NAME matching",
                     Pattern.matches(NAME_PATTERN, "Ivan"), true);

        assertEquals("Incorrect NAME matching",
                     Pattern.matches(NAME_PATTERN, "ivan"), false);

        assertEquals("Incorrect NAME matching",
                     Pattern.matches(NAME_PATTERN, "I_van"), false);

        assertEquals("Incorrect NAME matching",
                     Pattern.matches(NAME_PATTERN,
                                     "Ivsanjhjgjgjgjgjgjghfgfghfghfhfhfhfhf"),
                     false);

    }

    @Test
    public void testPhoneNumber() {
        assertEquals("Incorrect PHONE-NUMBER matching",
                     Pattern.matches(PHONE_NUMBER_PATTERN,
                                     "8(960)0000000"), true);

        assertEquals("Incorrect PHONE-NUMBER matching",
                     Pattern.matches(PHONE_NUMBER_PATTERN,
                                     "a(787)0000000"), false);

        assertEquals("Incorrect PHONE-NUMBER matching",
                     Pattern.matches(PHONE_NUMBER_PATTERN,
                                     "(787)0000000"), false);

        assertEquals("Incorrect PHONE-NUMBER matching",
                     Pattern.matches(PHONE_NUMBER_PATTERN,
                                     "8(1711187)0000000"), false);

        assertEquals("Incorrect PHONE-NUMBER matching",
                     Pattern.matches(PHONE_NUMBER_PATTERN,
                                     "8(787)11110000000"), false);

        assertEquals("Incorrect PHONE-NUMBER matching",
                     Pattern.matches(PHONE_NUMBER_PATTERN,
                                     "8(787)000"), false);

        assertEquals("Incorrect PHONE-NUMBER matching",
                     Pattern.matches(PHONE_NUMBER_PATTERN,
                                     "8(787)aaaaaaa000"), false);

        assertEquals("Incorrect PHONE-NUMBER matching",
                     Pattern.matches(PHONE_NUMBER_PATTERN,
                                     "8(960]0000000"), false);
    }

    @Test
    public void testMailPattern() {
        assertEquals("Incorrect MAIL matching",
                     Pattern.matches(MAIL_PATTERN,
                                     "ivan.ivanov@gmail.com"), true);
        
        assertEquals("Incorrect MAIL matching",
                     Pattern.matches(MAIL_PATTERN,
                                     "di-v+a_n_i+v+a.n.o.v@g+m+a-i-l+l_k-p_j.com9"),
                     true);

        assertEquals("Incorrect MAIL matching",
                     Pattern.matches(MAIL_PATTERN,
                                     ".ivanov@gmail.com"), false);

        assertEquals("Incorrect MAIL matching",
                     Pattern.matches(MAIL_PATTERN,
                                     "_iv--a-n--o++v.@gmail.c"), false);

        assertEquals("Incorrect MAIL matching",
                     Pattern.matches(MAIL_PATTERN,
                                     "ivan..ov@gmail.com"), false);

        assertEquals("Incorrect MAIL matching",
                     Pattern.matches(MAIL_PATTERN,
                                     "ivanov@g..mail.com"), false);
    }

    //@Ignore
    @Test
    public void testInsertCommand() {
        commandParser.setCommand("--insert    Ivan Ivanov "
                + "ivan.ivanov@gmail.com 8(960)0000000");
        
        User insertUser = commandParser.parseInsertCommand();
        assertEquals("Incorrect the insert command", (null != insertUser),
                     true);

        commandParser.setCommand("--insert ivan Ivanov "
                + "__iv++an...iv--anov@g__m++a--i?l.c 8(960)8800088");
        
        insertUser = commandParser.parseInsertCommand();
        assertEquals("Incorrect the insert command", (null == insertUser),
                     true);
        
    }

    @Test
    public void testRemoveCommand() {
        commandParser.setCommand("--remove ivan.ivanov@gmail.com");
        String removingContact = commandParser.parseRemoveCommand();
        assertEquals("Incorrect the remove command", (null != removingContact),
                     true);

        commandParser.setCommand("--remove i--v++an..i--vanov@--gmail.c");
        removingContact = commandParser.parseRemoveCommand();
        assertEquals("Incorrect the remove command", (null == removingContact),
                     true);
        
    }
    
    @Test 
    public void testFindByNameCommand(){
        commandParser.setCommand("--find Olya");
        String findName = commandParser.parseFindByNameCommand();
        assertEquals("Incorrect find by name command", (null != findName),
                     true);
    }
    
    private CommandParser commandParser;
}
