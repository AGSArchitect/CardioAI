package com.cardioai.tools.extractor;

/**
 * LogMessage
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public enum LogMessage {
    /**
     * PG001
     */
    PG001("code=\"{}\" message=\"The manifest value is either not a file or does not exist\" file=\"{}\""),
    /**
     * PG002
     */
    PG002("code=\"{}\" message=\"The destination value is either not a directory or does not exist\" directory=\"{}\""),
    /**
     * PG003
     */
    PG003("code=\"{}\" message=\"The command line argument is unknown\" argument=\"{}\""),
    /**
     * PG004
     */
    PG004("code=\"{}\" message=\"The header value is either not a file or does not exist\" file=\"{}\"");

    private final String message;

    private LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
