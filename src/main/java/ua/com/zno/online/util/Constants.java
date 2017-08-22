package ua.com.zno.online.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by quento on 26.03.17.
 */
public final class Constants {

    //TODO: 0 for session purpose
    public static final long ID_APPENDER = 0/*(long) (Math.random() * 10_000)*/;

    public static final String EMAIL_VALIDATOR = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
}
