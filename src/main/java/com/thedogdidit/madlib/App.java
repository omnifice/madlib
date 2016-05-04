package com.thedogdidit.madlib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * madlib
 *
 * Accepts three command line parameters which indicate a JSON formatted word list, a plain text file of strings (the
 * story) which contains tokens indicating word types corresponding to those in the JSON file, and an output file name
 * for which to write the complete madlib to.
 *
 * Additionally, this app reads a configuration file in JSON format which indicates how command line parsing should be
 * handled, This files will be named opts_config.json.
 *
 * Usage: madlib -j words.json -p plaintext_phrases.txt -o output_file.txt
 *
 */
public class App {

    public static void main( String[] args ) {

        // Parse the command line options.
        CmdLineParser clp = new CmdLineParser(args);
        clp.parse();

        // Problem with command line parsing...exit!
        if (! clp.isValid()) {
            System.err.println("Unknown problem parsing command line options."); // TODO: Probably need to look for reasons and modify this...
            System.exit(1);
        }

        // Read the JSON words file.
        System.out.println("Reading JSON word list...");
        WordsReader wrdRdr = new WordsReader(clp.option("j"));
        wrdRdr.process();
        if (! wrdRdr.isValid()) {
            System.err.println("Error reading JSON words file.");
            System.exit(1);
        }

        // Read the plain text file, and parse if it's valid.
        System.out.println("Reading phrases file...");
        PhrasesReader phraseRdr = new PhrasesReader(clp.option("p"));
        phraseRdr.process();
        List<String> phrases = phraseRdr.phrases();
        List<String> parsedPhrases = new ArrayList<String>();
        if (! phraseRdr.isValid()) {
            System.err.println("Error reading phrases file.");
            System.exit(1);
        }
        else {
            System.out.println("Parsing tokens...");
            PhraseParser phrasePrsr = new PhraseParser();
            parsedPhrases = phrasePrsr.parsePhrases(phrases, wrdRdr);
        }

        // Write the output plain text file.
        // Seems silly to put this in it's own class...
        // NOTE- Overwrite existing...not bothering to check for existance, currently. I know this is unfriendly, but...
        // TODO...make more friendly for overwrite/abort.
        try {
            //noinspection Since15
            Files.write(Paths.get(clp.option("o")), parsedPhrases);
        }
        catch (IOException e) {
            System.err.println("Error writing to file " + clp.option("o") + ". I give up. Sorry. :(");
            System.exit(1);
        }

        // Celebrate and exit. W00T! W00T! ;)
        System.out.println("Output file " + clp.option("o") + " written.");
        System.out.println("\nEnjoy your madlib! :)");
        System.exit(0);

    }
}
