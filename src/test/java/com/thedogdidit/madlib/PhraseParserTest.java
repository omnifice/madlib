package com.thedogdidit.madlib;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test PhraseParser
 */
public class PhraseParserTest {
    private static PhraseParser phrsParser;

    @Before
    public void setUp() {
        phrsParser = new PhraseParser();
    }

    @Test
    public void testPhraseParserType() {
        assertThat("PhraseParser is correct type", phrsParser, instanceOf(PhraseParser.class));
    }

    @Test
    public void testParsePhrases() {
        List<String> aryLst = new ArrayList<>();
        WordsReader wrdRedr = new WordsReader("src/test/resources/words.json");
        PhrasesReader phrsRedr = new PhrasesReader("src/test/resources/phrases.txt");
        List<String> phrases = phrsRedr.phrases();
        List<String> parsed = phrsParser.parsePhrases(phrases, wrdRedr);

        assertEquals("PhraseParser returns correct type.\n", aryLst, parsed);
    }

    @Test
    public void testParsePhrasesInvalideWordsReader() {
        WordsReader wrdRedr = new WordsReader("");
        PhrasesReader phrsRedr = new PhrasesReader("src/test/resources/phrases.txt");
        List<String> phrases = phrsRedr.phrases();
        List<String> parsed = phrsParser.parsePhrases(phrases, wrdRedr);

        assertEquals("Invalid WordsReader object", phrases, parsed);
    }
}