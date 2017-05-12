package org.jml.myeis.controllers;

import java.text.SimpleDateFormat;
import java.util.*;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.jml.myeis.domain.*;
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

        try {
            Set keys = payload.keySet();
            String bpmid = "", usertype="",usercode="", statuscode = "";
            bpmid = (String) payload.get("bpmid");
            usertype = (String) payload.get("usertype");
            usercode = (String) payload.get("usercode");
            statuscode = (String) payload.get("statuscode");

            System.out.println("bpmid: " + bpmid + ", usertype: " + usertype + ", usercode: " + usercode);

            System.out.println("step 1. create instance of the process");
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("newstring", new String());
            //all bpm process files should be place in src/main/resources/processes
            //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("accountant_Fixed_Asset_Disposal", variables);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(bpmid, variables);
            String procID = processInstance.getId();

            //initial extended data
            ProcessDetails processDetails = new ProcessDetails();
            com.cbody.cbody2 doc = com.cbody.cbody2.createDocument();
            com.cbody.detailsType details = doc.details.append();
            com.cbody.descriptionType description = details.description.append();
            description.setValue(bpmid);
            com.cbody.processinfoType processinfo = details.processinfo.append();
            com.cbody.statusType status = processinfo.status.append();
            status.setValue(statuscode);
            com.cbody.typeType type = processinfo.type.append();
            type.setValue(usertype);
            com.cbody.codeType code = processinfo.code.append();
            code.setValue(usercode);
            com.cbody.stepsType steps = details.steps.append();
            com.cbody.nextstepType nextstep = steps.nextstep.append();
            com.cbody.procidinstanceType procidinstance = nextstep.procidinstance.append();
            procidinstance.setValue(procID);

            //fetch next available task
            Task task = taskService.createTaskQuery().processInstanceId(procID).singleResult();

            com.cbody.taskidType taskidExtData = nextstep.taskid.append();
            taskidExtData.setValue(task.getId());
            com.cbody.tasknameType taskname = nextstep.taskname.append();
            taskname.setValue(task.getName());
            com.cbody.stepType step = nextstep.step.append();
            step.setValue(task.getId());
            com.cbody.usertypeType usertypeP = nextstep.usertype.append();
            usertypeP.setValue(usertype);
            com.cbody.usercodeType usercodeP = nextstep.usercode.append();
            usercodeP.setValue(usercode);
            com.cbody.bflagType bflag = nextstep.bflag.append();
            bflag.setValue("false");
            com.cbody.completedType completed = nextstep.completed.append();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String d = dateFormat.format(date);
            completed.setValue(usertype + "|" + usercode + "|" + d);

            com.cbody.descriptionType desc = nextstep.description.append();
            desc.setValue(task.getDescription());

            String s = doc.saveToString(true);
            processDetails.setCbody(s);
            processDetails.setProcidinstance(procID);
            processDetails.setProcid(bpmid);

            processDetails = bpmRepository.save(processDetails);
            //end, initial extended data


            System.out.println("processInstance.getId(): " + processInstance.getId());
            String result = "<result><processinstanceid>" + processInstance.getId() + "</processinstanceid><firsttaskid>" + task.getId() + ":" + task.getName() + "</firsttaskid><processdetailsid>" + processDetails.getId() + "</processdetailsid></result>";
            JSONObject obj = XML.toJSONObject(result);
            result = obj.toString();
            //processInstance = runtimeService.startProcessInstanceById(procID, variables);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/bpm/workflowprocess/task/finish", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> workflowProcessTaskFinish(@RequestBody HashMap<String, Object> payload) {

        System.out.println("payload: " + payload);
        try {
            Set keys = payload.keySet();
            String bpmid = "", taskid = "", processdetailsid = "", usertype="",usercode="", statuscode = "";
            bpmid = (String) payload.get("bpmidinstance");
            taskid = (String) payload.get("bpmidtask");
            processdetailsid = (String) payload.get("processdetailsid");

            usertype = (String) payload.get("usertype");
            usercode = (String) payload.get("usercode");
            statuscode = (String) payload.get("statuscode");

            System.out.println("bpmid: " + bpmid + ", taskid: " + taskid + ", processdetailsid: " + processdetailsid + ", usertype: " + usertype + ", usercode: " + usercode);

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("newstring", new String());
            //all bpm process files should be place in src/main/resources/processes
            //ProcessInstance processInstance = runtimeService.startProcessInstanceById(bpmid, variables);

            //String procID = processInstance.getId();

            //fetch the task
            Task task = taskService.createTaskQuery().taskId(taskid).processInstanceId(bpmid).singleResult();

            ProcessDetails processDetails = new ProcessDetails();
            com.cbody.cbody2 doc;
            com.cbody.detailsType details;
            com.cbody.nextstepType nextstep;
            com.cbody.completedType completed;
            com.cbody.stepsType steps;

            //update completed entry for this task with today's date
            if(task!=null) {

                processDetails = bpmRepository.findOne(Long.valueOf(processdetailsid));
                doc = com.cbody.cbody2.loadFromString(processDetails.getCbody());
                details = doc.details.first();
                steps = details.steps.first();
                nextstep = steps.nextstep.last(); //the last task of the list, to be completed now
                completed = nextstep.completed.first(); //this is the node that we need to update]
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String d = dateFormat.format(date);
                completed.setValue(usertype + "|" + usercode + "|" + d);
                String s = doc.saveToString(true);
                processDetails.setCbody(s);
                processDetails.setProcidinstance(bpmid);
                processDetails.setProcid(bpmid);
                processDetails = bpmRepository.save(processDetails);
                //end, update completed entry for this task with today's date
            }

            //mark this task as completed
            taskService.complete(task.getId(), verifyYNflag(task));
            //then fetch the next task. when task is completed the next task is automatically returned
            task = taskService.createTaskQuery().processInstanceId(bpmid).singleResult();

            System.out.println("processInstance.getId(): " + bpmid);
            String result = "";

            if (task != null) {
                //is the next task available?
                result = "<result><processinstanceid>" + bpmid + "</processinstanceid><nexttaskid>" + task.getId() + "</nexttaskid> <nexttaskname>" + task.getName() + "</nexttaskname><processdetailsid>" + processdetailsid + "</processdetailsid></result>";

                //update extended data
                processDetails = bpmRepository.findOne(Long.valueOf(processdetailsid));

                //object model for extended data
                doc = com.cbody.cbody2.loadFromString(processDetails.getCbody());
                details = doc.details.first();
                steps = details.steps.first();
                nextstep = steps.nextstep.append();
                com.cbody.procidinstanceType procidinstance = nextstep.procidinstance.append();
                procidinstance.setValue(bpmid);
                String desc;
                desc = task.getDescription();
                com.cbody.taskidType taskidExtData = nextstep.taskid.append();
                taskidExtData.setValue(task.getId());
                com.cbody.tasknameType taskname = nextstep.taskname.append();
                taskname.setValue(task.getName());
                com.cbody.stepType step = nextstep.step.append();
                step.setValue(task.getId());
                com.cbody.usertypeType usertypeP = nextstep.usertype.append();
                usertypeP.setValue(usertype);
                com.cbody.usercodeType usercodeP = nextstep.usercode.append();
                usercodeP.setValue(usercode);
                com.cbody.bflagType bflag = nextstep.bflag.append();
                bflag.setValue("false");
                com.cbody.descriptionType description = nextstep.description.append();
                description.setValue(task.getDescription());
                completed = nextstep.completed.append();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String d = dateFormat.format(date);
                completed.setValue(usertype + "|" + usercode + "|" + d);

                String s = doc.saveToString(true);
                processDetails.setCbody(s);
                processDetails.setProcidinstance(bpmid);
                processDetails = bpmRepository.save(processDetails); //returned processDetails will contain persisted object with id generated by jpa system
                //end, update extended data

            } else {

                //no next task available?
                result = "<result><processinstanceid>" + bpmid + "</processinstanceid><nexttaskid>end of process</nexttaskid> <nexttaskname>end of process</nexttaskname><processdetailsid>" + processdetailsid + "</processdetailsid></result>";
                //update extended data and mark this process finish
                processDetails = bpmRepository.findOne(Long.valueOf(processdetailsid));
                doc = com.cbody.cbody2.loadFromString(processDetails.getCbody());
                details = doc.details.first();
                com.cbody.processinfoType processinfo = details.processinfo.append(); //add new process info, this is the second entry
                com.cbody.statusType status = processinfo.status.append();
                status.setValue("finish");
                com.cbody.typeType type = processinfo.type.append();
                type.setValue(usertype);
                com.cbody.codeType code = processinfo.code.append();
                code.setValue(usercode);
                String s = doc.saveToString(true);
                processDetails.setCbody(s);
                processDetails = bpmRepository.save(processDetails);

            }

            JSONObject obj = XML.toJSONObject(result);
            result = obj.toString();
            return ResponseEntity.ok(result);

        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
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
