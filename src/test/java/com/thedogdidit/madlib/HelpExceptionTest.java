package com.thedogdidit.madlib;

import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Test custom HelpException
 */
public class HelpExceptionTest extends TestCase {

    @Test
    public void testHelpExceptionType() throws Exception {
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