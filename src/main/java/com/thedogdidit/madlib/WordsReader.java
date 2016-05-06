package com.thedogdidit.madlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
     * Provides a random word of passed type from the words list.
     *
     * @param type Word type, e.g. verb, noun, adjective, etc.
     * @return random word of type String.
     */
    public String randomWord(String type) {
        if (! words.containsKey(type) || words.get(type).isEmpty()) {
            return type;
        }
        else {
            ArrayList<String> ary = words.get(type);
            Random rndm = new Random();

            return ary.get(rndm.nextInt(ary.size()));
        }
    }


    /**
     * Getter for the words list.
     *
     * @return HashMap of words as HashMap<String, ArrayList<String>>
     */
    public HashMap<String, ArrayList<String>> words() {
        return this.words;
    }


    /**
     * Process the JSON word file passed to the constructor.
     */
    public void process() throws IOException {
        // Check for valid file.
        File fWord = new File(this.wordFile);
        if (fWord.exists()
                && fWord.canRead()
                && fWord.isFile())
        {
            this.wordFilePath = fWord.getAbsolutePath();
        }
        else {
            throw new FileNotFoundException();
        }

        // Call to read in the words file.
        if (loadWords()) {
            this.valid = true;
        }
        else {
            this.words.clear();
        }
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

            return (count > 0); // Assume, or decide as bad, if no words.
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
