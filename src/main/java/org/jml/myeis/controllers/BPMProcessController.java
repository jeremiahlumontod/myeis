package org.jml.myeis.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.jml.myeis.domain.AjaxResponseBody;
import org.jml.myeis.domain.Customer;
import org.jml.myeis.domain.Product;
import org.jml.myeis.domain.User;
import org.jml.myeis.repository.BPMRepository;
import org.jml.myeis.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jeremiahlumontod on 4/23/17.
 */
@Controller
public class BPMProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private BPMRepository bpmRepository;

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value = "/bpm/workflowprocess/start", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> workflowProcessStart(@RequestBody Map<String, Object> payload) {
        System.out.println("payload: " + payload);
    	System.out.println("step 1. create instance of the process");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("newstring", new String());
        //all bpm process files should be place in src/main/resources/processes
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("accountant_Fixed_Asset_Disposal", variables);
        String procID = processInstance.getId();
        System.out.println("processInstance.getId(): " + processInstance.getId());
        User user = new User("username","password","email");
        AjaxResponseBody result = new AjaxResponseBody();
        List<User> users = new ArrayList<>();
        users.add(user);
        result.setMsg("success");
        result.setResult(users);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/bpm/infoprocess", method = RequestMethod.POST)
    public void process(@RequestBody Map<String, Object> payload) throws Exception {
    	System.out.println(payload);
    }
    
    @RequestMapping("/bpm/startprocess")
    public String processNew() {
        //return "/process/startprocess";
    	return "bpm/startprocess";
    }
    
}
