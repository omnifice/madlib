package com.thedogdidit.madlib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to read a plain text file into an stringBuilder array.
 */
class PhrasesReader {
    private final String textFile;
    private List<String> phrases = new ArrayList<String>();
    private Boolean valid = false;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private Path textFilePath;

    
    /**
     * Constructor
     *
     * @param infile indicating the plain text file containing phrases with embedded tokens.
     */
    public PhrasesReader(String infile) {
        this.textFile = infile;
    }


    /**
     * Getter for the phrase read from the plain text file.
     *
     * @return phrases as List<String>
     */
    public List<String> phrases() {
        return this.phrases;
    }


    /**
     * Check, read, etc., the plain text file.
     */
    public void process() {
        // Check for valid file.
        // NOTE: Purposefully used a different method (Path, etc.) than in other classes just to try.
        try {
            Path path = Paths.get(this.textFile);
            if (Files.exists(path)
                    && Files.isRegularFile(path)
                    && Files.isReadable(path))
            {
                this.textFilePath = path;
            }
            else {
                System.err.println("Plain text file not found or not regular file: " + this.textFile);
                System.exit(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Call to read in the phrases file.
        if (loadPhrases()) {
            this.valid = true;
        }
        else {
            this.phrases.clear();
        }
    }


    /**
     * Return value of private valid variable, indicating good file read.
     *
     * @return Boolean indicating if file processed okay.
     */
    public Boolean isValid() { return this.valid; }


    /**
     * Read in the phrases plain text file.
     *
     * @return Boolean indicating good or bad loading of phrases file.
     */
    private Boolean loadPhrases() {
        try {
            // Assuming small files for this usage...
            //noinspection Since15
            this.phrases = Files.readAllLines(this.textFilePath, ENCODING);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            this.phrases.clear();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            this.phrases.clear();
            return false;
        }

        return true;
    }
}
