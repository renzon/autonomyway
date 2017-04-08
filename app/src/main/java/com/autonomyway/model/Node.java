package com.autonomyway.model;

public interface Node extends Identifiable {

    String getName();

    void handleTransferCreationAsOrigin(Transfer transfer);

    void handleTransferCreationAsDestination(Transfer transfer);
}
