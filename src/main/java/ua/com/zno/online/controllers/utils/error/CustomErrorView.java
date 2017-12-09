package ua.com.zno.online.controllers.utils.error;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
