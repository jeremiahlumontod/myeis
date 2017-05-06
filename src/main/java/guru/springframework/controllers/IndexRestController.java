package guru.springframework.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jeremiahlumontod on 4/22/17.
 */
@Controller
public class IndexRestController {
    private final Logger logger = LoggerFactory.getLogger(IndexRestController.class);

    @GetMapping("/ajax")
    public String index() {
        System.out.println("/ajax controller fired!");
        return "ajax";
    }
}
