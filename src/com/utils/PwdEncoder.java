package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PwdEncoder {

    private static final String AG = "SHA-256";

    private PwdEncoder() {
        //
    }

    public static String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(AG);
        md.update(text.getBytes());
        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
