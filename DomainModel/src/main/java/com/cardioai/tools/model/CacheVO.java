package com.cardioai.tools.model;

/**
 * CacheVO
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class CacheVO {

    private final String cacheId;
    private final PayloadVO payload;
    private final long created;
    private int cacheReads;
    private long accessed;

    public CacheVO(String cacheId, PayloadVO payload, long created) {
        this.cacheId = cacheId;
        this.payload = payload;
        this.created = created;
    }

    public String getCacheId() {
        return cacheId;
    }

    public PayloadVO getPayload() {
        return payload;
    }

    public long getCreated() {
        return created;
    }

    public int getCacheReads() {
        return cacheReads;
    }

    public void increaseCacheReads() {
        cacheReads++;
    }

    public long getAccessed() {
        return accessed;
    }

    public void setAccessed(long accessed) {
        this.accessed = accessed;
    }
}
