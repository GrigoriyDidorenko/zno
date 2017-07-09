package ua.com.zno.online.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.com.zno.online.exceptions.ZnoServerException;
import ua.com.zno.online.exceptions.ZnoUserException;

/**
 * Created by quento on 26.03.17.
 */

@ControllerAdvice
public class GlobalErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalErrorHandler.class.getName());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ZnoServerException.class)
    public
    @ResponseBody
    String handleConflict(ZnoServerException e) {
        LOG.debug("Handling ZnoServerException in GlobalErrorHandler", e);
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ZnoUserException.class)
    public
    @ResponseBody
    String handleConflict(ZnoUserException e) {
        LOG.debug("Handling ZnoUserException in GlobalErrorHandler", e);
        return e.getMessage();
    }

}
