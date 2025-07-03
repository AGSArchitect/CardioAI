package com.cardioai.tools.simulator;

import com.cardioai.tools.model.CacheVO;
import com.cardioai.tools.model.PayloadVO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CacheController
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class CacheController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheController.class);

    private final List<CacheVO> caches;
    private int index;

    public CacheController() {
        caches = new ArrayList();
        index = 0;
    }

    public void initialize(List<PayloadVO> payloads) {
        if (payloads.isEmpty()) {
            Utils.logErrorMessage(
                    LOGGER, LogMessage.TS012);
        }

        for (PayloadVO payload : payloads) {
            caches.add(new CacheVO(
                    Utils.getNextCacheId(), payload, Utils.getCurrentTime()));
        }
    }

    public PayloadVO getNextPayload() {
        CacheVO cache = caches.get(index);
        cache.increaseCacheReads();
        cache.setAccessed(Utils.getCurrentTime());

        caches.set(index, cache);
        index = (caches.size() == (index + 1)) ? 0 : (index + 1);

        return cache.getPayload();
    }
}
