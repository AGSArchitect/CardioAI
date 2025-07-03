package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;
import com.cardioai.tools.model.PayloadVO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * DataRelay
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class DataRelay implements Runnable, Supervisable {

    private static final String MESSAGE_TYPE = "cardioai.ekg.data";
    private static final int MESSAGE_VERSION = 1;

    private final CountDownLatch sLatch;
    private final CountDownLatch eLatch;
    private final DataRelayConfig config;
    private final List<PayloadVO> payloads;
    private final String originId;

    public DataRelay(CountDownLatch sLatch, CountDownLatch eLatch, DataRelayConfig config, List<PayloadVO> payloads) {
        this.sLatch = sLatch;
        this.eLatch = eLatch;
        this.config = config;
        this.payloads = payloads;
        this.originId = Utils.getNextOriginId();
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {
        try {
            sLatch.await();
            List<PayloadVO> toCache = new ArrayList<>();
            while (!payloads.isEmpty()) {
                PayloadVO e = payloads.remove(0);
                if (e != null) {
                    toCache.add(e);
                }

                try {
                    synchronized (payloads) {
                        payloads.wait(1);
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
            for (int i = 0; i < config.getMaximun(); i++) {
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
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            eLatch.countDown();
        }
    }
}
