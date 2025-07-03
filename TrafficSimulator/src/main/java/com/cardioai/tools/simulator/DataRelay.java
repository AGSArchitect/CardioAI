package com.cardioai.tools.simulator;

import com.cardioai.tools.model.PayloadVO;
import java.util.ArrayList;
import java.util.List;

/**
 * DataRelay
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class DataRelay implements Runnable, Supervisable {

    private final DataRelayConfig config;
    private final List<PayloadVO> payloads;

    public DataRelay(DataRelayConfig config, List<PayloadVO> payloads) {
        this.config = config;
        this.payloads = payloads;
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {
        List<PayloadVO> toCache = new ArrayList<>();
        while (!payloads.isEmpty()) {
            PayloadVO e = payloads.remove(0);
            if (e != null) {
                toCache.add(e);
            }

            try {
                synchronized (payloads) {
                    payloads.wait(3);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        CacheController controller = new CacheController();
        controller.initialize(toCache);
    }
}
