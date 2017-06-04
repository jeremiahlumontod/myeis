package org.jml.myeis.controllers;

import org.jml.myeis.domain.Login;
import org.jml.myeis.utils.ApplicationProperties;
import org.jml.myeis.utils.HTTPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ApplicationProperties properties;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        //curl -H "Content-Type: application/json" -X POST -d '{"username":"mkyong","password":"abc"}' http://localhost:8080/springmvc/api/login/
        logger.debug("login : {}", login);

        //validate login here

        return new ResponseEntity("\nSuccessfully login: " + login.getUsername() + "/" + login.getPassword() + "\n", new HttpHeaders(), HttpStatus.OK);

    }

    @RequestMapping(value = "/myeis/login", method = RequestMethod.GET)
    public String showLogin(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("login");
        HTTPUtils.resetLoginFlagToTrue(request);
        return "/bpm/login";

    }

    @RequestMapping(value = "/myeis/loginform", method = RequestMethod.POST)
    public String loginForm(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("loginForm");
        HTTPUtils.setLoggedFlag(request);
        HTTPUtils.resetLoginFlagToFalse(request);
        String inboxUri = properties.getInbox();
        return inboxUri;
    }

    @RequestMapping(value = "/myeis/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("logout");
        HTTPUtils.setLoggedFlagToFalse(request);
        HTTPUtils.resetLoginFlagToFalse(request);
        String inboxUri = properties.getInbox();
        return inboxUri;
    }

}

