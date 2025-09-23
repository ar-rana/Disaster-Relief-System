package com.example.server.model.enums;

public enum Keys {
    RELIEF("relief/%s"),
    USER("user/%s"),
    HQ("hq/%s"),
    STATUS("status/%s");
    private final String prefixKey;
    Keys(String key) {
        this.prefixKey = key;
    }

    public String getPrefixKey() {
        return this.prefixKey;
    }

    public static String key(Keys type, Object id) {
        return String.format(type.getPrefixKey(), id.toString());
    }
}
