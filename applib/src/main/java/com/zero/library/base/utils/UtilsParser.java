package com.zero.library.base.utils;

import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;

public class UtilsParser {
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static String decryptWithBase64NoUrlEncode(byte[] input, byte[] key) {
        // decode base64
        byte[] base = null;
        try {
            base = UtilsBase64.decodeToBytes(input);
        } catch (Exception e) {
            Logger.e(
                    "failed to get the decode base64 for info" + e.getMessage());
            return null;
        }
        // decrypt aes256
        byte[] zip = UtilsCrypto.decrypt(base, key);
        if (null == zip) {
            Logger.e("failed to decrypt the file.");
            return null;
        }
        // unzip
        byte[] unzip = UtilsZip.unzip(zip);
        String ret = null;
        try {
            ret = new String(unzip, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            Logger.e("failed to get the decrypt info" + e.getMessage());
        }

        return ret;
    }

    public static String decryptWithBase64(byte[] input, byte[] key) {
        // decode base64
        byte[] base = null;
        try {
            base = UtilsBase64.decodeToBytesWithURLDecode(new String(input,
                    DEFAULT_CHARSET));
        } catch (Exception e) {
            Logger.e(
                    "failed to get the decode base64 for info" + e.getMessage());
            return null;
        }
        // decrypt aes256
        byte[] zip = UtilsCrypto.decrypt(base, key);
        if (null == zip) {
            Logger.e("failed to decrypt the file.");
            return null;
        }
        // unzip
        byte[] unzip = UtilsZip.unzip(zip);
        String ret = null;
        try {
            ret = new String(unzip, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            Logger.e("failed to get the decrypt info" + e.getMessage());
        }

        return ret;
    }

    public static String decryptWithoutBase64(byte[] input, byte[] key) {
        // decrypt aes256
        byte[] zip = UtilsCrypto.decrypt(input, key);
        if (null == zip) {
            Logger.e("failed to decrypt the file.");
            return null;
        }
        // unzip
        byte[] unzip = UtilsZip.unzip(zip);
        String ret = null;
        try {
            ret = new String(unzip, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            Logger.e("failed to get the decrypt info" + e.getMessage());
        }

        return ret;
    }

    public static String encryptWithBase64(String input, byte[] key) {
        // to bytes
        byte[] in = null;
        try {
            in = input.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            Logger.e("failed to get the encrypted info" + e.getMessage());
            return null;
        }
        // zip
        byte[] zip = UtilsZip.zip(in);
        // ecrypt
        byte[] enc = UtilsCrypto.encrypt(zip, key);
        // ecode base64
        String base64 = null;
        try {
            base64 = UtilsBase64.encodeToStringWithURLEncode(enc);
        } catch (UnsupportedEncodingException e) {
            Logger.e("failed to encode the base64 info" + e.getMessage());
        }
        return base64;
    }

    public static String encryptWithBase64NoUrlEncode(String input, byte[] key) {
        // to bytes
        byte[] in = null;
        try {
            in = input.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        // zip
        byte[] zip = UtilsZip.zip(in);
        // ecrypt
        byte[] enc = UtilsCrypto.encrypt(zip, key);
        // ecode base64
        String base64 = null;
        try {
            base64 = UtilsBase64.encodeToString(enc);
        } catch (Exception e) {
        }
        return base64;
    }

    public static byte[] encryptWithoutBase64(String input, byte[] key) {
        // to bytes
        byte[] in = null;
        try {
            in = input.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            Logger.e("failed to get the encrypted info" + e.getMessage());
            return null;
        }
        // zip
        byte[] zip = UtilsZip.zip(in);
        // ecrypt
        byte[] enc = UtilsCrypto.encrypt(zip, key);
        // ecode base64
        return enc;
    }

}
