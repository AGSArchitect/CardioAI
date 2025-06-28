package com.cardioai.tools.model;

/**
 * CacheVO
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class CacheVO {

    private String cacheId;
    private int cacheReads;
    private MessageVO message;
    private long created;

    public String getCacheId() {
        return cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public int getCacheReads() {
        return cacheReads;
    }

    public void setCacheReads(int cacheReads) {
        this.cacheReads = cacheReads;
    }

    public MessageVO getMessage() {
        return message;
    }

    public void setMessage(MessageVO message) {
        this.message = message;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
