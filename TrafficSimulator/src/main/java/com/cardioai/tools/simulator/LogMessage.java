package com.cardioai.tools.simulator;

/**
 * LogMessage
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public enum LogMessage {
    /**
     * TS001
     */
    TS001("code=\"{}\" ..."),
    /**
     * TS002
     */
    TS002("code=\"{}\" ..."),
    /**
     * TS003
     */
    TS003("code=\"{}\" ..."),
    /**
     * TS004
     */
    TS004("code=\"{}\" ..."),
    /**
     * TS005
     */
    TS005("code=\"{}\" ..."),
    /**
     * TS006
     */
    TS006("code=\"{}\" ..."),
    /**
     * TS007
     */
    TS007("code=\"{}\" ..."),
    /**
     * TS008
     */
    TS008("code=\"{}\" ..."),
    /**
     * TS009
     */
    TS009("code=\"{}\" ...");    

    private final String message;

    private LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
