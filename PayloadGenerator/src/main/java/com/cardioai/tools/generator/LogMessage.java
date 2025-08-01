package com.cardioai.tools.generator;

/**
 * LogMessage
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public enum LogMessage {
    /**
     * PG001. The source value is either not a directory or does not exist.
     */
    PG001("code=\"{}\" message=\"The source value is either not a directory or does not exist\" directory=\"{}\""),
    /**
     * PG002. The destination value is either not a directory or does not exist.
     */
    PG002("code=\"{}\" message=\"The destination value is either not a directory or does not exist\" directory=\"{}\""),
    /**
     * PG003. The command line argument is unknown.
     */
    PG003("code=\"{}\" message=\"The command line argument is unknown\" argument=\"{}\"");

    private final String message;

    private LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
