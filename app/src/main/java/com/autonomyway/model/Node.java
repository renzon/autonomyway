package com.autonomyway.model;

import java.io.Serializable;

public interface Node extends Identifiable, Serializable {

    String getName();

    void handleTransferCreationAsOrigin(Transfer transfer);

    void handleTransferCreationAsDestination(Transfer transfer);

    boolean hasRecurrentValues();

    long getRecurrentCash();

    long getRecurrentDuration();
}
