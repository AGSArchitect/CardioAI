package com.cardioai.tools.extractor;

/**
 * LogMessage
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public enum LogMessage {
    /**
     * DE001
     */
    DE001("code=\"{}\" message=\"The manifest value is either not a file or does not exist\" file=\"{}\""),
    /**
     * DE002
     */
    DE002("code=\"{}\" message=\"The destination value is either not a directory or does not exist\" directory=\"{}\""),
    /**
     * DE003
     */
    DE003("code=\"{}\" message=\"The command line argument is unknown\" argument=\"{}\""),
    /**
     * DE004
     */
    DE004("code=\"{}\" message=\"The header value is either not a file or does not exist\" file=\"{}\"");

    private final String message;

    private LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
