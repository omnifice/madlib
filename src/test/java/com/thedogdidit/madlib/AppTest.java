package com.thedogdidit.madlib;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;


/**
 * Tests for App
 */
public class AppTest extends TestCase {

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class, HelpExceptionTest.class, CmdLineParserTest.class, WordsReaderTest.class, PhrasesReaderTest.class, PhraseParserTest.class );
    }


    // args for most tests.
    static String[] args = {
            "-p", "target/test-classes/phrases.txt",
            "-j", "target/test-classes/words.json",
            "-o", "target/test-classes/outfile.txt"
    };


    /**
     * Test App
     */
    @org.junit.Test
    public void testAppType() throws Exception {
        App app = new App();
        assertThat("App is correct type", app, instanceOf(App.class));
    }

    /**
     * Command line -h is tested in another test class.
     */

    @org.junit.Test
    public void testParseCommandLine() throws Exception {
        App ml = new App();
        HashMap<String, String> opts;
        opts = ml.parseCommandLine(args);

        assertEquals("Should be HashMap with 3 keys.", 3, opts.size());
    }


    @org.junit.Test
    public void testParseCommandLineLong() throws Exception {
        String[] args = {
                "--plaintext", "target/test-classes/phrases.txt",
                "--json", "target/test-classes/words.json",
                "--outfile", "target/test-classes/outfile.txt"
        };

        App ml = new App();
        HashMap<String, String> opts;
        opts = ml.parseCommandLine(args);

        assertEquals("Should be HashMap with 3 long keys.", 3, opts.size());
    }


    @org.junit.Test
    public void testGetWords() throws Exception {
        App ml = new App();
        HashMap<String, String> opts;
        opts = ml.parseCommandLine(args);

        // Get word list from JSON file.
        WordsReader wrdReader = ml.getWords(opts.get("j"));

        assertFalse("WordReader should contain words.", wrdReader.words().isEmpty());
    }


    @org.junit.Test
    public void testGetPhrases() throws Exception {
        App ml = new App();
        HashMap<String, String> opts;
        opts = ml.parseCommandLine(args);

        // Get word list from JSON file.
        WordsReader wrdReader = ml.getWords(opts.get("j"));
        PhrasesReader phraseRdr = ml.getPhrases(opts.get("p"));

        assertFalse("PhrasesReader should contain phrases.", phraseRdr.phrases().isEmpty());
    }


    @org.junit.Test
    public void testParsePhrases() throws Exception {
        App ml = new App();
        HashMap<String, String> opts;
        opts = ml.parseCommandLine(args);

        // Get word list from JSON file.
        WordsReader wrdReader = ml.getWords(opts.get("j"));
        PhrasesReader phraseRdr = ml.getPhrases(opts.get("p"));
        List<String> parsedPhrases = ml.parsePhrases(phraseRdr, wrdReader);

        assertFalse("parsedPhrases should contain a list of strings.", parsedPhrases.isEmpty());
    }


    @org.junit.Test
    public void testWriteMadlib() throws Exception {
        App ml = new App();
        HashMap<String, String> opts;
        opts = ml.parseCommandLine(args);

        // Get word list from JSON file.
        WordsReader wrdReader = ml.getWords(opts.get("j"));
        PhrasesReader phraseRdr = ml.getPhrases(opts.get("p"));
        List<String> parsedPhrases = ml.parsePhrases(phraseRdr, wrdReader);
        ml.writeMadlib(opts.get("o"), parsedPhrases);
        Path filePath = Paths.get(opts.get("o"));
        Boolean goodPath = (Files.exists(filePath) && Files.isRegularFile(filePath) && Files.isReadable(filePath));

        assertTrue("Output file should exits, be a regular file, and be readable.", goodPath);
    }
}
