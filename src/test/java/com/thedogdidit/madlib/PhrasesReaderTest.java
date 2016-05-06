package com.thedogdidit.madlib;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Test PhrasesReader
 */
public class PhrasesReaderTest {
    private static PhrasesReader phrsRdr;

    @Before
    public void setUp() {
        phrsRdr = new PhrasesReader("src/test/resources/phrases.txt");
    }

    @Test
    public void testPhrasesReaderType() {
        assertThat("PhrasesReader is correct type", phrsRdr, instanceOf(PhrasesReader.class));
    }

    @Test
    public void testPhrasesType() {
        List<String> phr = phrsRdr.phrases();
        List<String> foo = new ArrayList<String>();
        assertEquals(foo, phr);
    }

    @Test
    public void testPhrasesEmpty() {
        assertTrue("phrsRdr.phrases() should be empty.", phrsRdr.phrases().isEmpty());
    }

    @Test
    public void testPhrases() throws Exception {
        phrsRdr.process();
        assertFalse("phrsRdr.phrases() should not be empty.", phrsRdr.phrases().isEmpty());
    }

    @Test
    public void testIsValidBad() {
        phrsRdr = new PhrasesReader("badname.txt");
        try {
            phrsRdr.process();
        }
        catch (IOException e) {
            assertFalse("File path is bad, should be invalid", phrsRdr.isValid());
        }
    }

    @Test
    public void testIsValid() throws Exception {
        phrsRdr.process();
        assertTrue("isValid should be true", phrsRdr.isValid());
    }

}