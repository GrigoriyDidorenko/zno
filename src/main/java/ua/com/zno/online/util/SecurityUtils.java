package ua.com.zno.online.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by obalitskiy on 4/7/17.
 */
public final class SecurityUtils {

    public static String createHash(String algorithm, String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = java.security.MessageDigest.getInstance(algorithm);
        return Base64.encodeBase64URLSafeString(messageDigest.digest(data.getBytes()));
    }
}
