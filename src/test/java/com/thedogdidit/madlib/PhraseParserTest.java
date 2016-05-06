package com.thedogdidit.madlib;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Test PhraseParser
 */
public class PhraseParserTest extends TestCase {
    private static PhraseParser phrsParser;

    @Before
    public void setUp() throws Exception {
        phrsParser = new PhraseParser();
    }

    @Test
    public void testPhraseParserType() throws Exception {
        assertThat("PhraseParser is correct type", phrsParser, instanceOf(PhraseParser.class));
    }

    @Test
    public void testParsePhrases() throws Exception {
        List<String> aryLst = new ArrayList<String>();
        WordsReader wrdRedr = new WordsReader("src/test/resources/words.json");
        PhrasesReader phrsRedr = new PhrasesReader("src/test/resources/phrases.txt");
        List<String> phrases = phrsRedr.phrases();
        List<String> parsed = phrsParser.parsePhrases(phrases, wrdRedr);

        assertEquals("PhraseParser returns correct type.\n", aryLst, parsed);
    }

    @Test
    public void testParsePhrasesInvalideWordsReader() throws Exception {
        WordsReader wrdRedr = new WordsReader("");
        PhrasesReader phrsRedr = new PhrasesReader("src/test/resources/phrases.txt");
        List<String> phrases = phrsRedr.phrases();
        List<String> parsed = phrsParser.parsePhrases(phrases, wrdRedr);

        assertEquals("Invalid WordsReader object", phrases, parsed);
    }
}