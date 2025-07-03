package com.cardioai.tools.simulator;

import java.util.UUID;

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

    public static long getCurrentTime() {
        return System.nanoTime();
    }
}
