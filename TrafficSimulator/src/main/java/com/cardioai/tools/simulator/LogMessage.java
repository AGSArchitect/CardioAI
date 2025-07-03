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
    TS001("code=\"{}\" message=\"The source value is either not a directory or does not exist\" directory=\"{}\""),
    /**
     * TS002
     */
    TS002("code=\"{}\" message=\"Invalid adapter type\" adapter=\"{}\""),
    /**
     * TS003
     */
    TS003("code=\"{}\" message=\"Missing resource name\""),
    /**
     * TS004
     */
    TS004("code=\"{}\" message=\"Invalid origin code\" originCode=\"{}\""),
    /**
     * TS005
     */
    TS005("code=\"{}\" message=\"Missing device code\""),
    /**
     * TS006
     */
    TS006("code=\"{}\" message=\"Invalid pause value\" before=\"{}\""),
    /**
     * TS007
     */
    TS007("code=\"{}\" message=\"Invalid pause value\" after=\"{}\""),
    /**
     * TS008
     */
    TS008("code=\"{}\" message=\"Invalid pause value\" cycle=\"{}\""),
    /**
     * TS009
     */
    TS009("code=\"{}\" message=\"Invalid number of threads\" threads=\"{}\""),
    /**
     * TS010
     */
    TS010("code=\"{}\" message=\"The command line argument is unknown\" argument=\"{}\"");

    private final String message;

    private LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
