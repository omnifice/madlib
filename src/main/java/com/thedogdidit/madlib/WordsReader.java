package com.thedogdidit.madlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class to read in a list of words and associated types from a JSON file formatted as:
 * [
 *   {"word": "<string>", "type": "noun|verb|adjective|number|adverb|place|person"},
 *   {"word": "<string>", "type": "noun|verb|adjective|number|adverb|place|person"},
 *   ...
 * ]
 */
class WordsReader {
    private final String wordFile;
    private String wordFilePath;
    private final HashMap<String, ArrayList<String>> words = new HashMap<String, ArrayList<String>>();
    private Boolean valid = false;


    /**
     * Constructor
     *
     * @param infile indicating file name of the JSON formatted words file.
     */
    public WordsReader(String infile) {
        this.wordFile = infile;
    }


    /**
     * Process the JSON word file passed to the constructor.
     *
     * @return a HashMap of words, broken down by type (noun, verb, etc.).
     */
    public HashMap<String, ArrayList<String>> process() {
        // Check for valid file.
        try {
            File fWord = new File(wordFile);
            if (fWord.exists()
                    && fWord.canRead()
                    && fWord.isFile()) {
                this.wordFilePath = fWord.getAbsolutePath();
            }
            else {
                System.err.println("Configuration file not found: " + wordFile);
                System.exit(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (loadWords()) {
            this.valid = true;
        }
        else {

            this.words.clear();
        }

        return this.words;
    }


    /**
     * Return value of private valid variable, indicating good file read.
     *
     * @return Boolean
     */
    public Boolean isValid() {
        return this.valid;
    }


    /**
     * Read the JSON configuration file.
     *
     * @return Boolean indicating if file parsing was good.
     */
    private Boolean loadWords() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(this.wordFilePath));
            int count = 0;

            for (Object o : jsonArray) {
                String word;
                String type;

                JSONObject wObj = (JSONObject) o;

                if (wObj.containsKey("word") && wObj.containsKey("type")) {
                    count++;
                    word = (String) wObj.get("word");
                    type = (String) wObj.get("type");

                    if (words.containsKey(type)) {
                        words.get(type).add(word);
                    }
                    else {
                        // TODO: Gotta be a better way to do this...look into...
                        ArrayList<String> aryLst = new ArrayList<String>();
                        aryLst.add(word);
                        words.put(type, aryLst);
                    }
                }
            }

            return (count > 0);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            this.words.clear();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            this.words.clear();
            return false;
        }
        catch (ParseException e) {
            e.printStackTrace();
            this.words.clear();
            return false;
        }
    }
}
