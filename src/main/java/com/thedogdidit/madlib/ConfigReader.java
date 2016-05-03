package com.thedogdidit.madlib;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.cli.Options;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Class to read a JSON formatted configuration file in the format:
 * [
 *   { "short": "<value>", "long": "<value>", "hasArgs": Boolean, "description": "<value>"},
 *   { "short": "<value>", "long": "<value>", "hasArgs": Boolean, "description": "<value>"},
 *   ...
 * ]
 */
class ConfigReader {
    private String configFile;
    private Options options = new Options();
    private Boolean valid = false;


    /**
     * Constructor
     *
     * @param cfgFile indicating the JSON configuration file.
     */
    public ConfigReader(String cfgFile) {
        this.configFile = cfgFile;
    }


    /**
     * Getter for the configuration file options.
     *
     * @return Options
     */
    public Options options() {
        return this.options;
    }


    /**
     * Process the JSON configuration file passed to the constructor.
     */
    public void process() {
        if (loadJSONConfig()) {
            this.valid = true;
        }
        else {
            this.options.getOptions().clear();
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
    private Boolean loadJSONConfig() {
        JSONParser parser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(this.configFile));
            HashMap<String, String> opts = new HashMap<String, String>();

            for (Object o : jsonArray) {
                String optShort;
                String optLong;
                Boolean optHasArgs = false;
                String optDesc;

                JSONObject optObj = (JSONObject) o;

                if (optObj.containsKey("short")) {
                    optShort = (String) optObj.get("short");

                    if (optObj.containsKey("hasArgs")) {
                        optHasArgs = (Boolean) optObj.get("hasArgs");
                    }

                    if (optObj.containsKey("description")) {
                        optDesc = (String) optObj.get("description");
                    }
                    else {
                        optDesc = "";
                    }

                    if (optObj.containsKey("long")) { // "long is optional"
                        optLong = (String) optObj.get("long");
                        this.options.addOption(optShort, optLong, optHasArgs, optDesc);
                    }
                    else {
                        this.options.addOption(optShort, optHasArgs, optDesc);
                    }
                }
                else {
                    System.err.println("Missing required 'short' key in options configuration.");
                    this.options.getOptions().clear();
                    return false;
                }
            }

            return true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            this.options.getOptions().clear();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            this.options.getOptions().clear();
            return false;
        }
        catch (ParseException e) {
            e.printStackTrace();
            this.options.getOptions().clear();
            return false;
        }
    }
}
