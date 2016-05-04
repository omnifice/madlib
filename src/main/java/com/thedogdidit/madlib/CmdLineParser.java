package com.thedogdidit.madlib;

import org.apache.commons.cli.*;
import java.util.HashMap;


/**
 * Class to parse passed command line options and return necessary values.
 *
 */
class CmdLineParser {
    private String[] args = null;
    private final Options cfgOpts = new Options();
    private final HashMap<String, String> parsedOpts = new HashMap<String, String>();
    private Boolean valid = false;


    /**
     * Constructor
     *
     * @param args type String[] containing the command line arguments
     */
    public CmdLineParser(String[] args) {
        this.args = args;

        // Build options.
        cfgOpts.addOption("h", "help", false, "Show help.");
        cfgOpts.addOption("j", "json", true, "Input file containing a JSON array of words.");
        cfgOpts.addOption("p", "plaintext", true, "Input file containing plain text sentences and appropriate tokens.");
        cfgOpts.addOption("o", "output", true, "Output file for writing resulting MadLib to.");
    }


    /**
     * Get requested option value by name.
     *
     * @return option value or empty string if none.
     */
    public String option(String key) {
        return (this.parsedOpts.get(key).isEmpty()) ? "" : this.parsedOpts.get(key);
    }

    /**
     * Return whether or not parsing the command line was good.
     *
     * @return Boolean
     */
    public Boolean isValid() {
        return this.valid;
    }


    /**
     * Getter for the phrase read from the plain text file.
     *
     * @return phrases as List<String>
     */
    public HashMap<String, String> options() {
        return this.parsedOpts;
    }


    /**
     * Parse command line arguments and load a HashMap with those needed elsewhere, as determined by the Options passed
     * via the constructor.
     */
     public void parse() {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(this.cfgOpts, this.args);

            if (cmd.hasOption("h")) {
                displayHelp(); // Exits
            }

            for (Object o1 : cfgOpts.getOptions()) {

                Option o = (Option) o1;
                if (cmd.hasOption(o.getOpt()) && !o.getOpt().equals("h")) {
                    this.parsedOpts.put(o.getOpt(), cmd.getOptionValue(o.getOpt()));
                }
            }

            this.valid = true;
        }
        catch (ParseException e) {
            System.out.println("Error parsing command line options.");
            displayHelp(); // Exits
        }
    }


    /**
     * Display program help and exit program.
     */
    private void displayHelp() {
        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp("madlib", this.cfgOpts);

        System.exit(0);
    }
}
