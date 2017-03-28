package ua.com.zno.online.services.mail;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by quento on 28.03.17.
 */

@Service
public class MailService {

    //consider this realization https://github.com/ozimov/spring-boot-email-tools

    @Async
    public Future<Boolean> sendEmail() {
        return new AsyncResult<>(false);
    }
}
