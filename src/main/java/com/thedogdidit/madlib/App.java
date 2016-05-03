package com.thedogdidit.madlib;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.cli.Options;

/**
 * MadLibApp
 *
 */
public class App {

    public static void main( String[] args ) {
        final String CONFIG_FILE = "src/main/resources/opts_config.json";
        Options cfgOpts;
        HashMap<String, String> cmdOpts;
        HashMap<String, ArrayList<String>> wordList;
        File configFile;
        String configFilePath = "";

        // Check for valid CONFIG_FILE
        try {
            configFile = new File(CONFIG_FILE);
            if (configFile.exists()
                    && configFile.canRead()
                    && configFile.isFile()) {
                configFilePath = configFile.getAbsolutePath();
            }
            else {
                System.err.println("Configuration file not found: " + CONFIG_FILE);
                System.exit(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


        // Use the ConfigReader to process the JSON configuration file.
        ConfigReader cfgRdr = new ConfigReader(configFilePath);
        //noinspection unchecked
        cfgOpts = cfgRdr.process();

        if (cfgOpts.getOptions().isEmpty() || ! cfgRdr.isValid()) {
            System.err.println("Invalid configuration file: " + CONFIG_FILE);
            System.exit(1);
        }

        // Parse the command line options.
        CmdLineParser clp = new CmdLineParser(args, cfgOpts);
        //noinspection unchecked
        cmdOpts = clp.parse(); // Possible exit from this call...

        // Problem with command line parsing...exit!
        if (! clp.isValid()) {
            System.err.println("Unknown problem parsing command line options."); // TODO: Probably need to look for reasons and modify this...
            System.exit(1);
        }
/* TODO: Debug
        else {
            for (String key : cmdOpts.keySet()) {
                System.out.println("key: " + key + ", value: " + cmdOpts.get(key));
            }
        }
*/

        // Read the JSON words file.
        WordsReader wrdRdr = new WordsReader(cmdOpts.get("j"));
        //noinspection unchecked
        wordList = wrdRdr.process();
        if (! wrdRdr.isValid()) {
            System.err.println("Error reading JSON words file.");
            System.exit(1);
        }
/* TODO: Debug
        else {
            for (String key : wordList.keySet()) {
                System.out.println("key: " + key + ", value: " + wordList.get(key).toString());
            }
        }
*/


        // Read the plain text file.



        // Parse tokens in plain text file and replace with words of correct type from JSON file.



        // Write the output plain text file.



        // Celebrate and exit. ;)
        System.out.println("Output file " + cmdOpts.get("o") + " written.");
        System.out.println("Enjoy your MadLib! :)");
        System.exit(0);

    }
}
