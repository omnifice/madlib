package com.thedogdidit.madlib;

import junit.framework.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PhrasesReaderTest {
    static PhrasesReader phrsRdr;
    static PhrasesReader phrsRdrBadFile;

    @BeforeClass
    public static void setUpClass() {
        phrsRdrBadFile = new PhrasesReader("badFileName");
    }

    @Before
    public void setUp() {
        phrsRdr = new PhrasesReader("src/main/resources/phrases.txt");
    }

    @Test
    public void phrasesType() throws Exception {
        List<String> phr = phrsRdr.phrases();
        List<String> foo = new ArrayList<String>();
        assertEquals(foo, phr);
    }

    @Test
    public void phrasesEmpty() throws Exception {
        assertTrue("phrsRdr.phrases() should be empty.", phrsRdr.phrases().isEmpty());
    }

    @Test
    public void phrases() throws Exception {
        phrsRdr.process();
        assertFalse("phrsRdr.phrases() should not be empty.", phrsRdr.phrases().isEmpty());
    }

    @Test
    public void isValidBad() throws Exception {
        try {
            phrsRdrBadFile.process();
        }
        catch (FileNotFoundException e) {
            assertFalse("File path is bad, should be invalid", phrsRdrBadFile.isValid());
        }
    }

    @Test
    public void isValid() throws Exception {
        phrsRdr.process();
        assertTrue("isValid should be true", phrsRdr.isValid());
    }

}