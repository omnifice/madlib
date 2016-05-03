package com.thedogdidit.madlib;

import java.util.ArrayList;

/**
 * Class to read a plain text file into an stringBuilder array.
 */
class TextFileReader {
    private final String textFile;
    private String textFilePath;
    private ArrayList<StringBuilder> phrases = new ArrayList<StringBuilder>(30); // TODO: Probably need to handle capacity differently...
    private Boolean valid = false;


    /**
     * Constructor
     *
     * @param infile indicating the plain text file containing phrases with embedded tokens.
     */
    public TextFileReader(String infile) {
        this.textFile = infile;
    }


    /**
     * Check, read, etc., the plain text file.
     *
     * @return ArrayList of StringBuilders containing lines of the read in text file.
     */
    public ArrayList<StringBuilder> process() {


        return phrases;
    }


    /**
     * Return value of private valid variable, indicating good file read.
     *
     * @return Boolean indicating if file processed okay.
     */
    public Boolean isValid() { return this.valid; }


}
