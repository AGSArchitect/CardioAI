package com.cardioai.tools.simulator;

import com.cardioai.tools.model.PayloadVO;
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
    public void run() {
        /**
         * TODO: Provide Implementation...
         */
    }
}
