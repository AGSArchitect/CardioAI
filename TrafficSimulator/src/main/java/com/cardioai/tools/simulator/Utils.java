package com.cardioai.tools.simulator;

import java.util.UUID;
import org.slf4j.Logger;

/**
 * Utils
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class Utils {

    public static String getNextOriginId() {
        return UUID.randomUUID().toString();
    }

    public static String getNextMessageId() {
        return UUID.randomUUID().toString();
    }

    public static String getNextCacheId() {
        return UUID.randomUUID().toString();
    }

    public static long getCurrentTime() {
        return System.nanoTime();
    }

    public static void logErrorMessage(Logger logger, LogMessage error) {
        logErrorMessage(logger, error, null);
    }

    public static void logErrorMessage(Logger logger, LogMessage error, String value) {
        if (value != null) {
            logger.error(error.getMessage(),
                    error.name(), value);
        } else {
            logger.error(error.getMessage(),
                    error.name());
        }
    }

    public static void logDataRelayMetrics(Logger logger, LogMessage message, String originId, int messagesRelayed, int messagesFailed) {
        logger.info(message.getMessage(),
                message.name(), originId,
                String.valueOf(messagesRelayed),
                String.valueOf(messagesFailed));
    }
}
