package ua.com.zno.online.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

/**
 * Created by obalitskiy on 4/7/17.
 */
public final class SecurityUtils {

    public static String createURLSafeHash(String algorithm, String data) throws NoSuchAlgorithmException {
        return Base64.encodeBase64URLSafeString(createHash(algorithm, data));
    }

    public static byte[] createHash(String algorithm, String data) throws NoSuchAlgorithmException {
        java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(algorithm);
        return messageDigest.digest(data.getBytes());
    }

    public static String createSHA256URLSafeHash(String data) throws NoSuchAlgorithmException {
        return createURLSafeHash("SHA-256", data);
    }
}
