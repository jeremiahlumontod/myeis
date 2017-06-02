package org.jml.myeis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class InboxController {
    @RequestMapping("/inbox")
    public String getInbox(HttpServletRequest request, Model model) {

        model.addAttribute("product", null);
        return "bpm/index";
    }
}
