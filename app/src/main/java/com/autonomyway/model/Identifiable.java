package com.autonomyway.model;

import org.json.JSONObject;

public interface Identifiable {
    Long getId();
    JSONObject toJson();

    JSONObject toSummaryJson();

}
