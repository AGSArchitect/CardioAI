package com.cardioai.tools.simulator;

import com.cardioai.tools.model.MessageVO;

public interface DataRelayAdapter {

    public abstract void sendMessage(MessageVO message);
}
