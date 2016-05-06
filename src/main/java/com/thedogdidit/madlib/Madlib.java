package com.thedogdidit.madlib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Madlib
 *
 * Accepts three command line parameters which indicate a JSON formatted word list, a plain text file of strings (the
 * story) which contains tokens indicating word types corresponding to those in the JSON file, and an output file name
 * for which to write the complete madlib to.
 *
 * Command line options:
 *      -j,--json        wordslist.json
 *                          - JSON array in the format: [{"word": "watermelon", "type": "noun"}]
 *                          - There is a file in src/main/resources/words.json for using with command line options in build/run
 *      -p.--plaintext   plaintext_phrases.txt
 *                          - Phrases containing tokens matching typ in JSON words, e.g: [name] wants a new [noun].
 *                          - There is a file in src/main/resources/phrases.txt for using with command line options in build/run
 *      -o,--output      output_file.txt
 *                          - Name of the file for output of parsed results. NOTE - WILL BE OVERWRITTEN if exists.
 *      -h,--help        Display help
 *
 */
class Madlib {

    /**
     * Application main
     *
     * @param args - Command line arguments...see above.
     */
    public static void main( String[] args ) {
        Madlib madlib = new Madlib();

        // Parse command line.
        HashMap<String, String> cmdOpts = madlib.parseCommandLine(args);

        // Get word list from JSON file.
        WordsReader wrdReader = madlib.getWords(cmdOpts.get("j"));

        // Get phrases from plain text file.
        PhrasesReader phraseRdr = madlib.getPhrases(cmdOpts.get("p"));

        // Parse phrases.
        List<String> parsedPhrases = madlib.parsePhrases(phraseRdr, wrdReader);

        // Write output of parsed phrases.
        madlib.writeMadlib(cmdOpts.get("o"), parsedPhrases);

        // Wrap it up.
        madlib.celebrate();

        // Done
        exit(0);
    }


    /**
     * Parse the command line options.
     *
     * @param  args  Command line arguments from main(String[] args)
     * @return HashMap of the validated options.
     */
    HashMap<String, String> parseCommandLine(String[] args) {
        // Parse the command line options.
        CmdLineParser clp = new CmdLineParser(args);
        try {
            clp.parse();
        } catch (HelpException e) {
            exit(0);
        }

        return clp.options();
    }


    /**
     * Get words list from JSON file.
     *
     * @param fileName of the JSON words file.
     * @return WordReader object.
     */
    WordsReader getWords(String fileName) {

        // Read the JSON words file.
        System.out.println("Reading JSON word list...");
        WordsReader wrdRdr = new WordsReader(fileName);
        try {
            wrdRdr.process();
        } catch (IOException e) {
            System.err.println(wrdRdr.getReason());
            exit(1);
        }

        return wrdRdr;
    }


    /**
     * Get phrases from the plain text file.
     *
     * @param fileName of the plain text file.
     * @return PhrasesReader object.
     */
    PhrasesReader getPhrases(String fileName) {
        // Read the plain text file, and parse if it's valid.
        System.out.println("Reading phrases file...");
        PhrasesReader phraseRdr = new PhrasesReader(fileName);
        try {
            phraseRdr.process();
        } catch (FileNotFoundException e) {
            System.err.println(phraseRdr.getReason() + " " + fileName);
            exit(1);
        } catch (IOException e) {
            System.err.println(phraseRdr.getReason() + " " + fileName);
            e.printStackTrace();
            exit(1);
        }

        return phraseRdr;
    }


    /**
     * Parse the phrases to replace tokens with the words from the JSON word list.
     *
     * @param phraseRdr PhraseReader object
     * @param wrdRdr Wordreader object
     * @return List of parsed phrases.
     */
    List<String> parsePhrases(PhrasesReader phraseRdr, WordsReader wrdRdr) {
        List<String> phrases = phraseRdr.phrases();
        List<String> parsedPhrases = new ArrayList<String>();
        if (!phraseRdr.isValid()) {
            System.err.println("Error reading phrases file.");
            exit(1);
        } else {
            System.out.println("Parsing tokens...");
            PhraseParser phrasePrsr = new PhraseParser();
            parsedPhrases = phrasePrsr.parsePhrases(phrases, wrdRdr);
        }

        return parsedPhrases;
    }


    /**
     * Write out the parsed strings to a file.
     *
     * @param fileName of the output file.
     * @param parsedPhrases List<String> of the parsed phrases.
     */
    void writeMadlib(String fileName, List<String> parsedPhrases) {
        // Write the output plain text file.
        // NOTE- Overwrite existing file...not bothering to check for existance, currently.
        // I know this is unfriendly, but...
        // TODO...make more friendly for overwrite/abort.
        try {
            //noinspection Since15
            Files.write(Paths.get(fileName), parsedPhrases);
        }
        catch (IOException e) {
            System.err.println("Error writing to file " + fileName + ". I give up. Sorry. :(");
            exit(1);
        }

        System.out.println("Output file " + fileName + " written.");
    }


    //

    /**
     * Celebrate. W00T! W00T! ;)
     */
    private void celebrate() {
        System.out.println("\nEnjoy your madlib! :)");
    }


    /**
     * Single System.exit()
     *
     * @param code Desired code to pass to System.exit();
     */
    private static void exit(int code) {
        System.out.println("Exiting...");
        System.exit(code);
    }
}
