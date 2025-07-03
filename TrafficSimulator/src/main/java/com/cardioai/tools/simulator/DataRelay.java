package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;
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

    private static final String MESSAGE_TYPE = "cardioai.ekg.data";
    private static final int MESSAGE_VERSION = 1;

    private final DataRelayConfig config;
    private final List<PayloadVO> payloads;
    private final String originId;

    public DataRelay(DataRelayConfig config, List<PayloadVO> payloads) {
        this.config = config;
        this.payloads = payloads;
        this.originId = Utils.getNextOriginId();
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

        DataRelayAdapter adapter = DataRelayAdapterFactory.getAdapter(
                config.getAdapterName(), config.getResourceName());

        /**
         * TODO: Provide Implementation...
         */
        for (int i = 0; i < 500; i++) {
            MessageVO message = new MessageVO(
                    originId,
                    Utils.getNextMessageId(),
                    config.getOriginCode(),
                    config.getDeviceCode(),
                    MESSAGE_TYPE,
                    MESSAGE_VERSION,
                    controller.getNextPayload(),
                    Utils.getCurrentTime());

            adapter.sendMessage(message);
        }
    }
}