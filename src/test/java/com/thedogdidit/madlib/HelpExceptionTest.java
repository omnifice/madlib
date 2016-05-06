package com.thedogdidit.madlib;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Test custom HelpException
 */
public class HelpExceptionTest {

    @Test
    public void testHelpExceptionType() {
        HelpException exception = new HelpException("exception test");
        assertThat("HelpException is correct type", exception, instanceOf(HelpException.class));
    }


    @Test
    public void testHelpException() {
        try {
            causeHelpException();
        }
        catch (HelpException e) {
            assertEquals("Got test HelpException", "test HelpException", e.getMessage());
        }
    }

    private void causeHelpException() throws HelpException {
        throw new HelpException("test HelpException");
    }
}