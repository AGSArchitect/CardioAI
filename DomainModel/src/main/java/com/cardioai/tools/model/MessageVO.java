package com.cardioai.tools.model;

/**
 * MessageVO
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class MessageVO {

    private String originId;
    private String messageId;
    private String messageType;
    private int messageVersion;
    private PayloadVO payload;
    private long created;

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(int messageVersion) {
        this.messageVersion = messageVersion;
    }

    public PayloadVO getPayload() {
        return payload;
    }

    public void setPayload(PayloadVO payload) {
        this.payload = payload;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
