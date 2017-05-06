package org.jml.myeis.controllers;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.jml.myeis.domain.Customer;
import org.jml.myeis.domain.Product;
import org.jml.myeis.repository.BPMRepository;
import org.jml.myeis.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
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

    @RequestMapping(value = "/bpmprocess/start", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String newCustomer(Model model) {
    	System.out.println("step 1. create instance of the process");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("newstring", new String());
        //all bpm process files should be place in src/main/resources/processes
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("accountant_Fixed_Asset_Disposal", variables);
        String procID = processInstance.getId();
        System.out.println("processInstance.getId(): " + processInstance.getId());
        return null;
    }

    @RequestMapping(value = "/bpmprocess", method = RequestMethod.POST)
    public void process(@RequestBody Map<String, Object> payload) throws Exception {
    	System.out.println(payload);
    }
    
    @RequestMapping("/bpmprocess/new")
    public String processNew() {
        //return "/process/startprocess";
    	return "startprocess";
    }
    
}
