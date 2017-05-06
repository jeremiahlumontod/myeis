package guru.springframework.controllers;

import guru.springframework.domain.AjaxResponseBody;
import guru.springframework.domain.SearchCriteria;
import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jeremiahlumontod on 4/22/17.
 */
@Controller
public class SearchRestController {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //@RequestMapping(value = "/api/search", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String index() {
        System.out.println("/api/search controller fired!");
        return "index";
    }

    @RequestMapping(value = "/api/search", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody SearchCriteria search, Errors errors) {
        System.out.println("/api/search controller fired!");
        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);

        }

        List<User> users = userService.findByUserNameOrEmail(search.getUsername());
        if (users.isEmpty()) {
            result.setMsg("no user found!");
        } else {
            result.setMsg("success");
        }
        result.setResult(users);

        return ResponseEntity.ok(result);
    }

}
