package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;

/**
 * DataRelayAdapter
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public interface DataRelayAdapter {

    public abstract void sendMessage(MessageVO message);
}
