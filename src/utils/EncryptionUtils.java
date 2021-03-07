package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class EncryptionUtils {
    private EncryptionUtils() {
    }

    public static String generateHash(String algorithm, String value) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(value.getBytes());

        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
}
