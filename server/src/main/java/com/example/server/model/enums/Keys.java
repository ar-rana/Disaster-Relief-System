package com.example.server.model.enums;

public enum Keys {
    RELIEF("relief/%s"),
    PROVIDER("provider/%s"),
    HQ("hq/%s"),
    ADMIN("amin/%s");
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
