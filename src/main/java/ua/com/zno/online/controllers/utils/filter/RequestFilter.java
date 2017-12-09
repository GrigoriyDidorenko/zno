package ua.com.zno.online.controllers.utils.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Created by quento on 01.04.17.
 */

@Service
//@Scope(value = "session")
@PropertySource("classpath:custom.properties")
public class RequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestFilter.class.getName());

    @Value("${request.filter.timeout}")
    private int secondsBetweenSameRequest;

    private Date lastTimeAccessed;

    public synchronized boolean isSpamming(String clientIp) {
        Optional<Date> lastAccess = Optional.ofNullable(lastTimeAccessed);
        if (lastAccess.isPresent()) {
            Date temp = new Date(System.currentTimeMillis() - secondsBetweenSameRequest * 1_000);
            if (temp.after(lastTimeAccessed)) {
                lastTimeAccessed = new Date();
                return false;
            }
        }

        LOG.info("Somebody is spamming us, ip: {}", clientIp);
        return true;
    }
}
