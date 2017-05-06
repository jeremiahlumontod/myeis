
$(document).ready(function() {
	//alert("$(document).ready(function() fired!");


    $('#btnSearch').click(function() {
        //alert("btnSearch fired!");
        fire_ajax_submit();
        return false;
    });

    $('#btnStart').click(function() {
        alert("btnStart fired!");
        fire_ajax_submit();
        return false;
    });

});


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