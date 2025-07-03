package com.cardioai.tools.model;

/**
 * MessageVO
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class MessageVO {

    private final String originId;
    private final String messageId;
    private final String originCode;
    private final String deviceCode;
    private final String messageType;
    private final int messageVersion;
    private final PayloadVO payload;
    private final long created;

    public MessageVO(String originId, String messageId, String originCode, String deviceCode, String messageType, int messageVersion, PayloadVO payload, long created) {
        this.originId = originId;
        this.messageId = messageId;
        this.originCode = originCode;
        this.deviceCode = deviceCode;
        this.messageType = messageType;
        this.messageVersion = messageVersion;
        this.payload = payload;
        this.created = created;
    }

    public String getOriginId() {
        return originId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getOriginCode() {
        return originCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getMessageType() {
        return messageType;
    }

    public int getMessageVersion() {
        return messageVersion;
    }

    public PayloadVO getPayload() {
        return payload;
    }

    public long getCreated() {
        return created;
    }
}
