package com.example.freightviewer.Model;

import java.util.HashMap;
import java.util.Map;

public enum StopType {
    PICK_UP("Pick Up"),
    DELIVERY("Delivery");

    private static Map<String, StopType> identifierMap = new HashMap();
    private String value;
    private StopType(String value) {
        this.value = value;
    }
    public static StopType fromValue(int value) {
        StopType result = (StopType)identifierMap.get(value);
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
        StopType[] var0 = values();
        int var1 = var0.length;
        for(int var2 = 0; var2 < var1; ++var2) {
            StopType value = var0[var2];
            identifierMap.put(value.getValue(), value);
        }
    }
}
