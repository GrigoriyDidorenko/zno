package ua.com.zno.online.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by quento on 26.03.17.
 */
public final class Constants {

    //TODO: could be session scoped later
    public static final long ID_APPENDER = (long) (Math.random() * 10_000);
}