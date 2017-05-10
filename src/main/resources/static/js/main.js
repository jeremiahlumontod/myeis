
$(document).ready(function() {
	//alert("$(document).ready(function() fired!");


    $('#btnSearch').click(function() {
        //alert("btnSearch fired!");
        fire_ajax_submit();
        return false;
    });

    $('#btnStart').click(function() {
        //alert("btnStart fired!");
        start_the_workflow_process();
        return false;
    });

    $('#btnFinishTask').click(function() {
        //alert("btnStart fired!");
        finish_the_workflow_task();
        return false;
    });

});


function start_the_workflow_process() {
    //alert($("#username").val());
    var bpmdata = {}
    bpmdata["bpmid"] = $("#bpmid").val();

    $("#btnStart").prop("disabled", true);
    var sdata = JSON.stringify(bpmdata);
    //alert("bpmdata: " + bpmdata);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/springmvc/bpm/workflowprocess/start",
        data: JSON.stringify(bpmdata),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            //alert("success! " + data);
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btnStart").prop("disabled", false);

        },
        error: function (e) {
            //alert("error! " + e.toString());

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";

            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btnStart").prop("disabled", false);

        }
    });

}


function finish_the_workflow_task() {
    //alert($("#username").val());
    var bpmdata = {}
    bpmdata["bpmidinstance"] = $("#bpmidinstance").val();
    bpmdata["bpmidtask"] = $("#bpmidtask").val();
    bpmdata["processdetailsid"] = $("#processdetailsid").val();
    //search["email"] = $("#email").val();

    $("#btnFinishTask").prop("disabled", true);
    var sdata = JSON.stringify(bpmdata);
    //alert("bpmdata: " + sdata);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/springmvc/bpm/workflowprocess/task/finish",
        data: JSON.stringify(bpmdata),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            //alert("success! " + data);
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btnFinishTask").prop("disabled", false);

        },
        error: function (e) {
            alert("error! " + e.toString());

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";

            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btnFinishTask").prop("disabled", false);

        }
    });

}



function fire_ajax_submit() {

    var search = {}
    search["username"] = $("#username").val();
    //search["email"] = $("#email").val();

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/springmvc/api/search",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            //alert("success! " + data);
            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {
            //alert("error! " + e);
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}
