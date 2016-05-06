package com.thedogdidit.madlib;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test WordsReader
 */
public class WordsReaderTest extends TestCase {
    static WordsReader wordsRedr;

    @Before
    public void setUp() throws Exception {
        wordsRedr = new WordsReader("src/test/resources/words_short.json");
        try {
            wordsRedr.process();
            assertTrue("WordsReader should now be valid.", wordsRedr.isValid());
        }
        catch (FileNotFoundException e) {
            // Shouldn't be here.
            System.err.println("WordsReaderTest caught process() file error.");
            e.printStackTrace();
        }
    }

    @Test
    public void testWordsReaderTest() throws Exception {
        assertThat("WordsReaderTest is correct type", wordsRedr, instanceOf(WordsReader.class));
    }

    @Test
    public void testRandomWordNoun() throws Exception {
        assertEquals("Random word noun", "watermelon", wordsRedr.randomWord("noun"));
    }

    @Test
    public void testRandomWordAdjective() throws Exception {
        assertEquals("Random word adjective", "smelly", wordsRedr.randomWord("adjective"));
    }

    @Test
    public void testRandomWordNone() throws Exception {
        assertEquals("Random word none", "none", wordsRedr.randomWord("none"));
    }

    @Test
    public void words() throws Exception {
        assertFalse("words should be populated, not empty", wordsRedr.words().isEmpty());
    }


    @Test
    public void testProcessBadFile() throws Exception {
        WordsReader badWordsRedr = new WordsReader("foo.json");
        try {
            badWordsRedr.process();
        }
        catch (IOException e) {
            assertTrue("File path is bad, couldn't process", badWordsRedr.words().isEmpty());
        }
    }

    @Test
    public void testProcess() throws Exception {
        assertFalse("WordsReader should now be valid.", wordsRedr.words().isEmpty());
    }

    @Test
    public void isValid() throws Exception {
        assertTrue("isValid should be true.", wordsRedr.isValid());
    }
}