# myeis

1) start the process
http://localhost:8080/springmvc/bpm/startprocess

{
    "result": {
        "firsttaskid": "280006:1 | Facilities Manager",
        "processinstanceid": 280001,
        "processdetailsid": 891
    }
}

2) input first task id, process instance id, process details id to the following url:
http://localhost:8080/springmvc/bpm/finishprocesstask