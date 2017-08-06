package ua.com.zno.online.controllers.error;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by g.didorenko on 06.08.17.
 */
@Controller
public class CustomErrorView implements ErrorController {

    private static final String ERROR_PATH = "/errors";

    @RequestMapping(value = ERROR_PATH)
    public String error() {
        return "error.html";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
