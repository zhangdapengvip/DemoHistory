package com.zero.library.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.util.Base64;

public class UtilsBase64 {
    public static String encodeToString(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static String decodeToString(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        return new String(Base64.decode(bytes, Base64.NO_WRAP));
    }

    public static byte[] decodeToBytes(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        return Base64.decode(bytes, Base64.NO_WRAP);
    }

    public static String encodeToStringWithURLEncode(byte[] bytes)
            throws UnsupportedEncodingException {
        if (null == bytes) {
            return null;
        }
        String enStr = Base64.encodeToString(bytes, Base64.DEFAULT);
        return URLEncoder.encode(enStr, "UTF-8");
    }

    public static byte[] encodeToBytesWithURLEncode(byte[] bytes)
            throws UnsupportedEncodingException {
        if (null == bytes) {
            return null;
        }
        byte[] bts = encodeToStringWithURLEncode(bytes).getBytes();

        return bts;
    }

    public static byte[] decodeToBytesWithURLDecode(String s) {
        if (null == s) {
            return null;
        }
        try {
            s = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.decode(s, Base64.DEFAULT);
    }

    public static byte[] decodeToBytesWithURLDecode(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        return decodeToBytesWithURLDecode(new String(bytes));
    }
}
