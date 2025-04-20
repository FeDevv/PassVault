import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class CryptoUtils {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16;
    private static final int SALT_SIZE = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public static SecretKeySpec deriveKey(String masterPassword, byte[] salt) throws CryptoException {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (Exception e) {
            throw new CryptoException("Key derivation failed", e);
        }
    }

    public static String encrypt(String plainText, String masterPassword) {
        try {
            byte[] salt = new byte[SALT_SIZE];
            new SecureRandom().nextBytes(salt);

            SecretKeySpec key = deriveKey(masterPassword, salt);
            byte[] iv = new byte[IV_SIZE];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());

            byte[] combined = new byte[salt.length + iv.length + encrypted.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(iv, 0, combined, salt.length, iv.length);
            System.arraycopy(encrypted, 0, combined, salt.length + iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new CryptoException("Encryption failed", e);
        }
    }

    public static String decrypt(String encryptedText, String masterPassword) {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptedText);
            byte[] salt = Arrays.copyOfRange(combined, 0, SALT_SIZE);
            byte[] iv = Arrays.copyOfRange(combined, SALT_SIZE, SALT_SIZE + IV_SIZE);
            byte[] encrypted = Arrays.copyOfRange(combined, SALT_SIZE + IV_SIZE, combined.length);

            SecretKeySpec key = deriveKey(masterPassword, salt);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);
        } catch (Exception e) {
            throw new CryptoException("Decryption failed", e);
        }
    }

    // Eccezione personalizzata
    public static class CryptoException extends RuntimeException {
        public CryptoException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
