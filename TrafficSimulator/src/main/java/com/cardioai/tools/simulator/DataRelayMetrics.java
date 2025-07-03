package com.cardioai.tools.simulator;

/**
 * DataRelayMetrics
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class DataRelayMetrics {

    private final String originId;
    private final int messagesRelayed;
    private final int messagesFailed;

    public DataRelayMetrics(String originId, int messagesRelayed, int messagesFailed) {
        this.originId = originId;
        this.messagesRelayed = messagesRelayed;
        this.messagesFailed = messagesFailed;
    }

    public String getOriginId() {
        return originId;
    }

    public int getMessagesRelayed() {
        return messagesRelayed;
    }

    public int getMessagesFailed() {
        return messagesFailed;
    }
}
