package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jeremiahlumontod on 4/22/17.
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
