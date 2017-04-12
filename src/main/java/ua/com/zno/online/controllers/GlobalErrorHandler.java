package ua.com.zno.online.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.com.zno.online.exceptions.ServerException;
import ua.com.zno.online.exceptions.UserException;

/**
 * Created by quento on 26.03.17.
 */

@ControllerAdvice
public class GlobalErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalErrorHandler.class.getName());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServerException.class)
    public void handleConflict(ServerException e) {
        LOG.debug("Handling ServerException in GlobalErrorHandler", e);

        //return error model and view
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UserException.class)
    public void handleConflict(UserException e) {
        LOG.debug("Handling UserException in GlobalErrorHandler", e);


    }

}
