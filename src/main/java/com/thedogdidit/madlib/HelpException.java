package com.thedogdidit.madlib;

/**
 * Custom exception for when help is displayed.
 */
class HelpException extends Exception {
    /**
     * Help exception constructor
     *
     * @param message String describing the exception.
     */
    HelpException(String message) {
        super(message);
    }
}
