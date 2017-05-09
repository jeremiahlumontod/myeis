package org.jml.myeis.controllers;

import java.util.*;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.jml.myeis.domain.AjaxResponseBody;
import org.jml.myeis.domain.Customer;
import org.jml.myeis.domain.Product;
import org.jml.myeis.domain.User;
import org.jml.myeis.repository.BPMRepository;
import org.jml.myeis.services.CustomerService;
import org.json.JSONObject;
import org.json.XML;
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
    public ResponseEntity<?> workflowProcessStart(@RequestBody HashMap<String, Object> payload) {
        System.out.println("payload: " + payload);

        Set keys = payload.keySet();
        String bpmid = "";
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            String value = (String) payload.get(key);
            bpmid = key.equalsIgnoreCase("bpmid")?value:"";
            System.out.println("key: " + key + ", value: " + value);
        }

    	System.out.println("step 1. create instance of the process");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("newstring", new String());
        //all bpm process files should be place in src/main/resources/processes
        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("accountant_Fixed_Asset_Disposal", variables);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(bpmid, variables);
        String procID = processInstance.getId();

        Task task = taskService.createTaskQuery().processInstanceId(procID).singleResult();

        System.out.println("processInstance.getId(): " + processInstance.getId());
        String result = "<result><processinstanceid>" + processInstance.getId() + "</processinstanceid><taskid>" + task.getId() + "</taskid></result>";
        JSONObject obj = XML.toJSONObject(result);
        result = obj.toString();
        //processInstance = runtimeService.startProcessInstanceById(procID, variables);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/bpm/workflowprocess/task/finish", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> workflowProcessTaskFinish(@RequestBody HashMap<String, Object> payload) {
        System.out.println("payload: " + payload);

        Set keys = payload.keySet();
        String bpmid = "",taskid="";
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            String value = (String) payload.get(key);
            if(key.equalsIgnoreCase("bpmidinstance")) {
                bpmid = value;
            }
            if(key.equalsIgnoreCase("bpmidtask")) {
                taskid  = value;
            }
            System.out.println("key: " + key + ", value: " + value);
        }

        System.out.println("step 1. create instance of the process");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("newstring", new String());
        //all bpm process files should be place in src/main/resources/processes
        //ProcessInstance processInstance = runtimeService.startProcessInstanceById(bpmid, variables);

        //String procID = processInstance.getId();

        Task task = taskService.createTaskQuery().taskId(taskid).processInstanceId(bpmid).singleResult();
        taskService.complete(task.getId(), verifyYNflag(task));
        task = taskService.createTaskQuery().processInstanceId(bpmid).singleResult();

        System.out.println("processInstance.getId(): " + bpmid);
        String result = "<result><processinstanceid>" + bpmid + "</processinstanceid><nexttaskid>" + task.getId() + "</nexttaskid> <nexttaskname>" + task.getName() + "</nexttaskname></result>";
        JSONObject obj = XML.toJSONObject(result);
        result = obj.toString();
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

    @RequestMapping("/bpm/finishprocesstask")
    public String processFinishTask() {
        return "bpm/finishprocesstask";
    }


    public int getRandom() {
        Random rand = new Random();
        int  n = rand.nextInt(50) + 1;
        //int  n = 5;
        System.out.println("getRandom is: " + n);
        return n;
    }

    public boolean yn(int n) {
        return n%2==0;
    }

    public HashMap verifyYNflag(Task task) {
        String abflag[] = task.getName().split("\\|");
        HashMap variables = new HashMap<String, Object>();
        if(abflag.length>2) {
            //we got ourselves an exclusiveGateway. simulate a user decision of yes or no
            //by generating a random decision
            boolean yn = yn(getRandom());
            String randomDecision = yn?"Yes":"No";
            System.out.println("random decision is: " + randomDecision);
            variables.put("bflag", yn);
        }else{
            variables = new HashMap<String, Object>();
            variables.put("bflag", false);
            System.out.println("random decision is: No");
        }
        return variables;
    }

}
