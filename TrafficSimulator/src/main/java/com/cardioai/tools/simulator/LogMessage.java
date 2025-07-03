package com.cardioai.tools.simulator;

/**
 * LogMessage
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public enum LogMessage {
    /**
     * TS001. The source value is either not a directory or does not exist.
     */
    TS001("code=\"{}\" message=\"The source value is either not a directory or does not exist\" directory=\"{}\""),
    /**
     * TS002. Invalid adapter type.
     */
    TS002("code=\"{}\" message=\"Invalid adapter type\" adapter=\"{}\""),
    /**
     * TS003. Missing resource name.
     */
    TS003("code=\"{}\" message=\"Missing resource name\""),
    /**
     * TS004. Invalid origin code.
     */
    TS004("code=\"{}\" message=\"Invalid origin code\" originCode=\"{}\""),
    /**
     * TS005. Missing device code.
     */
    TS005("code=\"{}\" message=\"Missing device code\""),
    /**
     * TS006. Invalid before pause value.
     */
    TS006("code=\"{}\" message=\"Invalid before pause value\" before=\"{}\""),
    /**
     * TS007. Invalid after pause value.
     */
    TS007("code=\"{}\" message=\"Invalid after pause value\" after=\"{}\""),
    /**
     * TS008. Invalid cycle pause value.
     */
    TS008("code=\"{}\" message=\"Invalid cycle pause value\" cycle=\"{}\""),
    /**
     * TS009. Invalid number of threads.
     */
    TS009("code=\"{}\" message=\"Invalid number of threads\" threads=\"{}\""),
    /**
     * TS010. The command line argument is unknown.
     */
    TS010("code=\"{}\" message=\"The command line argument is unknown\" argument=\"{}\""),
    /**
     * TS011. The maximum number of messages most be at least one.
     */
    TS011("code=\"{}\" message=\"The maximum number of messages most be at least one\""),
    /**
     * TS012. The payload collection is empty.
     */
    TS012("code=\"{}\" message=\"The payload collection is empty\"");

    private final String message;

    private LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
