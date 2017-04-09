package ua.com.zno.online.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.NoSuchAlgorithmException;

/**
 * Created by obalitskiy on 4/7/17.
 */
public final class SecurityUtils {

    public static String createURLSafeHash(String algorithm, String data) throws NoSuchAlgorithmException {
        java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(algorithm);
        return Base64.encodeBase64URLSafeString(messageDigest.digest(data.getBytes()));
    }

    public static String createSHA256URLSafeHash(String data) throws NoSuchAlgorithmException {
        return createURLSafeHash("SHA-256", data);
    }
}
