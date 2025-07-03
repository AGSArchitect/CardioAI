package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;
import com.cardioai.tools.model.PayloadVO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DataRelay
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class DataRelay implements Runnable, Supervisable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRelay.class);

    private static final String MESSAGE_TYPE = "cardioai.ekg.data";
    private static final int MESSAGE_VERSION = 1;

    private final CountDownLatch sLatch;
    private final CountDownLatch eLatch;
    private final DataRelayConfig config;
    private final List<PayloadVO> payloads;
    private final String originId;
    private int messagesRelayed;
    private int messagesFailed;

    public DataRelay(CountDownLatch sLatch, CountDownLatch eLatch, DataRelayConfig config, List<PayloadVO> payloads) {
        this.sLatch = sLatch;
        this.eLatch = eLatch;
        this.config = config;
        this.payloads = payloads;
        this.originId = Utils.getNextOriginId();
        messagesRelayed = 0;
        messagesFailed = 0;
    }

    @Override
    @SuppressWarnings({"CallToPrintStackTrace", "UseSpecificCatch"})
    public void run() {
        try {
            sLatch.await();
            if (config.getPauseBeforeInitiating() > 0) {
                try {
                    synchronized (this) {
                        Utils.logErrorMessage(
                                LOGGER, LogMessage.TS101, String.valueOf(config.getPauseBeforeInitiating()));
                        wait(config.getPauseBeforeInitiating());
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

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

            for (int i = 0; i < config.getMaximun(); i++) {
                try {
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

                    messagesRelayed++;

                    if (config.getPauseAfterMessage() > 0) {
                        try {
                            synchronized (this) {
                                wait(config.getPauseAfterMessage());
                            }
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                            messagesFailed++;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            eLatch.countDown();
        }
    }

    @Override
    public synchronized DataRelayMetrics getMetrics() {
        return new DataRelayMetrics(
                originId, messagesRelayed, messagesFailed);
    }
}
