package ua.com.zno.online;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Optional;

@SpringBootApplication
@EnableAsync
public class ZnoOnlineApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ZnoOnlineApplication.class.getName());

    public static void main(String[] args) {
        if (valid(args))
            SpringApplication.run(ZnoOnlineApplication.class, args);
    }

    private static boolean valid(String[] args) {
        try {
            String activeProfile = System.getProperty("spring.profiles.active");
            return validProd(activeProfile, args);
        } catch (NullPointerException e) {
            LOG.error("Pass as VM option active profile to run");
            return false;
        }

    }

    private static boolean validProd(String activeProfile, String[] args) {

        final String jasyptProperty = "--jasypt.encryptor.password";
        final String productionProfile = "prod";

        if (productionProfile.equals(activeProfile)) {

            Optional<String> param = param(args, jasyptProperty);

            if (!param.isPresent()) {
                LOG.error("You run in production mode, pass as program argument jasypt password");
                return false;
            }
        }

        return true;
    }

    private static Optional<String> param(String[] args, String param) {
        for (String arg : args) {
            String key = arg.substring(0, arg.indexOf("="));
            String value = arg.substring(arg.indexOf("="), arg.length());
            if (key.equals(param))
                return Optional.of(value);
        }

        return Optional.empty();
    }

  /*  public static void main(String[] args) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("mySecretPassword");
        String encryptedText = encryptor.encrypt("Hello World");
        System.out.println("Encrypted text is: " + encryptedText);

        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword("mySecretPassword");
        String decryptedText = decryptor.decrypt(encryptedText);
        System.out.println("Decrypted text is: " + decryptedText);
    }*/
}
