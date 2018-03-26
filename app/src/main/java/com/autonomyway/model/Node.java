package com.autonomyway.model;

import com.autonomyway.business.transfer.TransferVisitor;

import java.io.Serializable;

public interface Node extends Identifiable, Serializable {

    String getName();

    void handleTransferCreationAsOrigin(Transfer transfer);

    void handleTransferCreationAsDestination(Transfer transfer);

    boolean hasRecurrentValues();

    long getRecurrentCash();

    long getRecurrentDuration();

    void acceptAsOrigin(TransferVisitor visitor, long transferCash, long transferDuration);

    void acceptAsDestination(TransferVisitor visitor, long transferCash, long transferDuration);

    int getColorId();


}
