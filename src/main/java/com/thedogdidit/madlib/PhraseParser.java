package com.thedogdidit.madlib;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Phrase parser
 *
 * Parses a passed list of strings for embedded tokens and replaces them with words of the type indicated by the token
 * from the passed, existing, WordsReader object.
 *
 * Example string with tokens: "There once was a [noun] from a [adjective] place who wanted to [verb]."
 */
class PhraseParser {

    /**
     * Constructor
     */
    public PhraseParser() {
        // Nothing to see here...move along.
    }

    /**
     * Parse a passed list of strings. Replace tokens with random values from an existing WordsReader object.
     *
     * @param phrases List<String> of phrases to parse.
     * @param wrdRdr Existing WordsReader object.
     * @return Parsed List<String> of phrases.
     */
    public List<String> parsePhrases(List<String> phrases, WordsReader wrdRdr) {
        List<String> parsedPhrases = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\[(.+?)\\]");

        if (! wrdRdr.isValid()) {
            //System.err.println("WordsReader object not valid in parsePhrases.");
            return phrases; //TODO: Probably should raise some error and handle in caller...
        }

        for (String line : phrases) {
            Matcher matcher = pattern.matcher(line);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                matcher.appendReplacement(sb, wrdRdr.randomWord(matcher.group(1)));
            }

            matcher.appendTail(sb);
            parsedPhrases.add(sb.toString());
        }

        return parsedPhrases;
    }
}
