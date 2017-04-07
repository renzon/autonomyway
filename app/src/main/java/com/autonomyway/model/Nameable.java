package com.autonomyway.model;

public interface Nameable extends Identifiable {

    String getName();

    void handleTransferCreationAsOrigin(Transfer transfer);

    void handleTransferCreationAsDestination(Transfer transfer);
}
