package com.zero.library.base.utils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class UtilsCrypto {
    private static final String DEFAULT_CHARSET = "UTF-8";

    public static byte[] shorToBytes(short s) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(s);
        buffer.flip();
        return buffer.array();
    }

    public static byte[] intToBytes(int i) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(i);
        buffer.flip();
        return buffer.array();
    }

    public static byte[] longToBytes(long i) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(i);
        buffer.flip();
        return buffer.array();
    }

    public static short byteToShort(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getShort();
    }

    public static short byteToShort(byte[] array, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(array, offset, length);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getShort();
    }

    public static int byteToInt(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getInt();
    }

    public static int byteToInt(byte[] array, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(array, offset, length);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getInt();
    }

    public static long byteToLong(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getLong();
    }

    public static long byteToLong(byte[] array, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(array, offset, length);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getLong();
    }

    public static float byteToFloat(byte[] b) {
        ByteBuffer buffer = ByteBuffer.wrap(b);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getFloat();
    }

    public static byte[] mergeBytes(byte[] pByteA, byte[] pByteB) {
        int aCount = pByteA.length;
        int bCount = pByteB.length;
        byte[] b = new byte[aCount + bCount];
        for (int i = 0; i < aCount; i++) {
            b[i] = pByteA[i];
        }
        for (int i = 0; i < bCount; i++) {
            b[aCount + i] = pByteB[i];
        }
        return b;
    }

    public static byte[] encrypt(byte[] input, byte[] key) {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher;
        byte[] encrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            Logger.e(e.getMessage());
        } catch (NoSuchPaddingException e) {
            Logger.e(e.getMessage());
        } catch (InvalidKeyException e) {
            Logger.e(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Logger.e(e.getMessage());
        } catch (BadPaddingException e) {
            Logger.e(e.getMessage());
        }
        return encrypted;
    }

    public static byte[] decrypt(byte[] input, byte[] key) {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher;
        byte[] decrypted = null;
        try {
            // Instantiate the cipher
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decrypted = cipher.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            Logger.e(e.getMessage());
        } catch (NoSuchPaddingException e) {
            Logger.e(e.getMessage());
        } catch (InvalidKeyException e) {
            Logger.e(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Logger.e(e.getMessage());
        } catch (BadPaddingException e) {
            Logger.e(e.getMessage());
        }
        return decrypted;
    }

    public static byte[] getAutoCreateAESKey() {

        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES");
            kg.init(256);// 要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] key = sk.getEncoded();
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final static byte[] MD5(byte[] btInput) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            return md;
        } catch (Exception e) {
            Logger.e(e.getMessage());
            return null;
        }
    }

    public final static String MD5(String input) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(input.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            return new String(md);
        } catch (Exception e) {
            Logger.e(e.getMessage());
            return null;
        }
    }

    public final static String hashString(String input) {
        String str = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            Logger.e(e.getMessage());
        }
        return str;
    }

    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getFileMD5String(File file) throws IOException {
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                mdInst.update(buffer, 0, numRead);
            }
            fis.close();
            return bufferToHex(mdInst.digest());
        } catch (Exception e) {
            Logger.e(e.getMessage());
            fis.close();
        }

        return null;
    }

    public static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        // 取字节中高 4 位的数字转换 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        // 取字节中低 4 位的数字转换
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static byte[] xorEncryptBytes(byte[] values, String key) {
        byte[] temp = null;
        try {
            byte[] keys = key.getBytes(DEFAULT_CHARSET);
            temp = new byte[values.length];
            for (int i = 0, j = 0; i < values.length; i++, j++) {
                if (j == keys.length) {
                    j = 0;
                }
                temp[i] = (byte) (values[i] ^ keys[j]);
            }

        } catch (UnsupportedEncodingException e) {
            Logger.e(e.getMessage());
        }
        return temp;

    }

    public static byte[] xorEncryptBytes(String value, String key) {
        byte[] temp = null;
        try {
            byte[] values = value.getBytes(DEFAULT_CHARSET);
            byte[] keys = key.getBytes(DEFAULT_CHARSET);
            temp = new byte[values.length];
            for (int i = 0, j = 0; i < values.length; i++, j++) {
                if (j == keys.length) {
                    j = 0;
                }
                temp[i] = (byte) (values[i] ^ keys[j]);
            }

        } catch (UnsupportedEncodingException e) {
            Logger.e(e.getMessage());
        }
        return temp;

    }

    public static String xorEncryptString(String value, String key) {
        String result = null;
        char[] values = value.toCharArray();
        char[] keys = key.toCharArray();
        char[] temp = new char[values.length];
        for (int i = 0, j = 0; i < values.length; i++, j++) {
            if (j == keys.length) {
                j = 0;
            }
            temp[i] = (char) (values[i] ^ keys[j]);
        }
        result = new String(temp);
        return result;
    }

    public static String fileNameEncryptString(String value) {
        String result = null;
        char[] values = value.toCharArray();
        char[] temp = new char[values.length];
        for (int i = 0; i < values.length; i++) {
            temp[i] = encryptChar(values[i]);
        }
        result = new String(temp);
        return result;
    }

    public static String fileNamedecryptString(String value) {
        String result = null;
        char[] values = value.toCharArray();
        char[] temp = new char[values.length];
        for (int i = 0; i < values.length; i++) {
            temp[i] = decryptChar(values[i]);
        }
        result = new String(temp);
        return result;
    }

    public static char encryptChar(char c) {
        if (c == '.') {
            return '+';
        }
        return c;
    }

    public static char decryptChar(char c) {
        if (c == '+') {
            return '.';
        }
        return c;
    }
}
