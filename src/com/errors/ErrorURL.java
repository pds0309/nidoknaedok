package com.errors;

import java.util.HashSet;
import java.util.Set;

public enum ErrorURL {
    U1("/login"),
    U2("/join"),
    U3("/kakaojoin"),
    U4("/kakaointeg"),
    U5("/mypage"),
    U6("/dashboard");

    private final String url;

    ErrorURL(String url) {
        this.url = url;
    }

    private static Set<String> urlSet;

    private static void initMapping() {
        urlSet = new HashSet<>();
        for (ErrorURL errorURL : values()) {
            urlSet.add(errorURL.url);
        }
    }

    public static Set<String> getURLSet() {
        if (urlSet == null) {
            initMapping();
        }
        return urlSet;
    }

    public static boolean exist(String url) {
        return getURLSet().contains(url);
    }
}
