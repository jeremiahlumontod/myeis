package org.jml.myeis.controllers;

import org.jml.myeis.domain.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        //curl -H "Content-Type: application/json" -X POST -d '{"username":"mkyong","password":"abc"}' http://localhost:8080/springmvc/api/login/
        logger.debug("login : {}", login);

        //validate login here

        return new ResponseEntity("\nSuccessfully login: " + login.getUsername() + "/" + login.getPassword() + "\n", new HttpHeaders(), HttpStatus.OK);

    }

    @RequestMapping(value = "/myeis/login", method = RequestMethod.POST)
    public String showLogin() {
        logger.debug("login");
        return "/bpm/login";

    }

    @RequestMapping(value = "/myeis/loginform", method = RequestMethod.POST)
    public String loginForm() {
        logger.debug("loginForm");
        return "/";

    }

}