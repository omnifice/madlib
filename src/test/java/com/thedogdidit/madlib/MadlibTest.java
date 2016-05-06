package com.thedogdidit.madlib;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests for App
 */
public class MadlibTest {

    // args for most tests.
    static Madlib madlib;
    private static final String[] args = {
            "-p", "target/test-classes/phrases.txt",
            "-j", "target/test-classes/words.json",
            "-o", "target/test-classes/outfile.txt"
    };


    /**
     * Run before each test.
     */
    @Before
    public void setUp() {
        madlib = new Madlib();
    }

    /**
     * Test class type
     */
    @Test
    public void testAppType() {
        assertThat("App is correct type", madlib, instanceOf(Madlib.class));
    }


    /**
     * NOTE: Command line is tested in another test class.
     */


    @Test
    public void testGetWords() {
        HashMap<String, String> opts;
        opts = madlib.parseCommandLine(args);

        // Get word list from JSON file.
        WordsReader wrdReader = madlib.getWords(opts.get("j"));

        assertFalse("WordReader should contain words.", wrdReader.words().isEmpty());
    }


    @Test
    public void testGetPhrases() {
        HashMap<String, String> opts;
        opts = madlib.parseCommandLine(args);

        // Get word list from JSON file.
        PhrasesReader phraseRdr = madlib.getPhrases(opts.get("p"));

        assertFalse("PhrasesReader should contain phrases.", phraseRdr.phrases().isEmpty());
    }


    @Test
    public void testParsePhrases() {
        HashMap<String, String> opts;
        opts = madlib.parseCommandLine(args);

        // Get word list from JSON file.
        WordsReader wrdReader = madlib.getWords(opts.get("j"));
        PhrasesReader phraseRdr = madlib.getPhrases(opts.get("p"));
        List<String> parsedPhrases = madlib.parsePhrases(phraseRdr, wrdReader);

        assertFalse("parsedPhrases should contain a list of strings.", parsedPhrases.isEmpty());
    }


    @Test
    public void testWriteMadlib() {
        HashMap<String, String> opts;
        opts = madlib.parseCommandLine(args);

        // Get word list from JSON file.
        WordsReader wrdReader = madlib.getWords(opts.get("j"));
        PhrasesReader phraseRdr = madlib.getPhrases(opts.get("p"));
        List<String> parsedPhrases = madlib.parsePhrases(phraseRdr, wrdReader);
        madlib.writeMadlib(opts.get("o"), parsedPhrases);
        Path filePath = Paths.get(opts.get("o"));
        Boolean goodPath = (Files.exists(filePath) && Files.isRegularFile(filePath) && Files.isReadable(filePath));

        assertTrue("Output file should exits, be a regular file, and be readable.", goodPath);
    }


    @Test
    public void testCelebrate() {
        String nl = System.getProperty("line.separator");
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        madlib.celebrate();
        assertEquals("Celebrate string should match \nEnjoy your madlib! :)\n", "\nEnjoy your madlib! :)\n", output.toString());
    }
}
