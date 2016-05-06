package com.thedogdidit.madlib;

import junit.framework.*;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Test CmdLineParser
 */
public class CmdLineParserTest extends TestCase {
    private static final String[] args = {"-j", "words.json", "-p", "plain.txt", "-o", "outfile.txt"};
    private static CmdLineParser clp;

    @Before
    public void setUp() {
        clp = new CmdLineParser(args);
    }


    @Test
    public void testOptionEmpty() throws Exception {
        assertEquals("Expecting <empty string> arg", "", clp.option("j"));
    }

    @Test
    public void testCmdLineParserType() throws Exception {
        assertThat("CmdLineParser is correct type", clp, instanceOf(CmdLineParser.class));
    }

    @Test
    public void testOption() throws Exception {
        clp.parse();
        String foo = "";
        assertEquals("Expecting string", foo.getClass(), clp.option("j").getClass());
    }

    @Test
    public void testIsValid() throws Exception {
        clp.parse();
        assertTrue("testIsValid TRUE", clp.isValid());
    }

    @Test
    public void testIsValidFalse() throws Exception {
        assertFalse("testIsValid FALSE:", clp.isValid());
    }

    @Test
    public void testOptionsClass() throws Exception {
        HashMap<String, String> testObj = new HashMap<String, String>();
        assertEquals("testObj is HashMap", testObj.getClass(), clp.options().getClass());
    }

    @Test
    public void testOptionsEmpty() throws Exception {
        assertTrue("testObj is empty", clp.options().isEmpty());
    }

    @Test
    public void testOptionsLoaded() throws Exception {
        clp.parse();
        assertFalse("testObj is not empty", clp.options().isEmpty());
    }

    @Test
    public void testOptionH() throws Exception {
        CmdLineParser clp = new CmdLineParser(new String[]{"-h"});
        try {
            clp.parse();
        }
        catch (HelpException e) {
            assertEquals("Got HelpException", e.getClass(), HelpException.class);
        }
    }
}