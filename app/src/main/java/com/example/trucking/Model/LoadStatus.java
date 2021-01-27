package com.example.trucking.Model;

import java.util.HashMap;
import java.util.Map;

public enum LoadStatus {
    BOOKED_ASSIGNED("Booked-Assigned"),
    BOOKED_NOT_ASSIGNED("Booked-Not Assigned"),
    DISPATCHED("Dispatched"),
    IN_TRANSIT_TO_PU("In Transit-To PU"),
    IN_TRANSIT_LOADED_ON_TIME("In Transit-Loaded-On Time"),
    IN_TRANSIT_LOADED_DELAYED("In Transit-Loaded-Delayed"),
    DELIVERED_NO_LUMPER("Delivered-No Lumper"),
    DELIVERED_LUMPER_REPORTED("Delivered-Lumper Reported"),
    DELIVERED_LUMPER_NOT_REPORTED("Delivered-Lumper Not Reported"),
    DELIVERED_DETENTION_REQUESTED("Delivered-Detention Requested"),
    DELIVERED_DETENTION_COMPLETED("Delivered-Detention Completed"),
    CANCELLED("Cancelled"),
    TONU("Truck Ordered Not Used");

    private static Map<String, LoadStatus> identifierMap = new HashMap();
    private String value;
    private LoadStatus(String value) {
        this.value = value;
    }
    public static LoadStatus fromValue(int value) {
        LoadStatus result = (LoadStatus)identifierMap.get(value);
        if (result == null) {
            throw new IllegalArgumentException("No Status for value: " + value);
        } else {
            return result;
        }
    }
    public String getValue() {
        return this.value;
    }
    static {
        LoadStatus[] var0 = values();
        int var1 = var0.length;
        for(int var2 = 0; var2 < var1; ++var2) {
            LoadStatus value = var0[var2];
            identifierMap.put(value.getValue(), value);
        }
    }
}
