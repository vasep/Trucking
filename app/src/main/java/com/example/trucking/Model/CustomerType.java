package com.example.trucking.Model;

import java.util.HashMap;
import java.util.Map;

enum  CustomerType {
    BROKER("Broker"),
    CUSTOMER("Customer");

    private static Map<String, CustomerType> identifierMap = new HashMap();
    private String value;
    private CustomerType(String value) {
        this.value = value;
    }
    public static CustomerType fromValue(String value) {
        CustomerType result = (CustomerType)identifierMap.get(value);
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
        CustomerType[] var0 = values();
        int var1 = var0.length;
        for(int var2 = 0; var2 < var1; ++var2) {
            CustomerType value = var0[var2];
            identifierMap.put(value.getValue(), value);
        }
    }
}
