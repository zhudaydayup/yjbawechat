function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "../hiddenDanger/getTemporaryCreateDangerTable",
        dataType: "json",
        data: { RecordId:RecordId},
        async: false,
        success: function (data) {
            /*var SECURITY_PEOPLE = data[0].SECURITY_PEOPLE;//安检员
            $("#job").val(SECURITY_PEOPLE);*/
            if (data[0]!= null){
                var CHECKE_DETAIL = data[0].CHECKE_DETAIL;//检查情况
                $("#CHECKE_DETAIL").val(CHECKE_DETAIL);
            }

        }
    })
});

var userId=GetQueryString("userId");
var social_credit_code=GetQueryString("social_credit_code");
var RecordId=GetQueryString("RecordId");
var url = window.location.href;
//暂存提交URL
function temporaryRecord() {
    var obj = document.getElementsByName("checkbox1");
    var checke_problems="";
    for (var i = 0; i < obj.length-1; i++) {
        if(obj[i].checked){
            checke_problems+=obj[i].value+"|";
        }
    }
    checke_problems+=obj[obj.length-1].value;
    var obj3 = document.getElementsByName("checkbox3");
    var execute_people="";
    for (var i = 0; i < obj3.length; i++) {
        execute_people+=obj3[i].value+"|";
    }
    var ajaxPram = {};
    ajaxPram["URL"] = url;
    ajaxPram["USER_ID"] = userId;
    ajaxPram["SOCIAL_CREDIT_CODE"] = social_credit_code;//社会信用代码
    ajaxPram["RECORD_ID"] = RecordId;

    ajaxPram["CHECKED_START_TIME"] = $("#CHECKED_START_TIME").val();
    ajaxPram["CHECKED_END_TIME"] = $("#CHECKED_END_TIME").val();
    ajaxPram["CHECKED_UNIT"] = $("#CHECKED_UNIT").val();

    ajaxPram["UNIT_NUMBER"] = $("#UNIT_NUMBER").val();//社会信用代码

    ajaxPram["ADDRESS"] = $("#ADDRESS").val();
    ajaxPram["REPRESENT_PEOPLE"] = $("#REPRESENT_PEOPLE").val();
    ajaxPram["CHECKE_PLACE"] = $("#CHECKE_PLACE").val();
    ajaxPram["EXPERT_PEOPLLE"] = execute_people;
    ajaxPram["DANGER_NAME"] = checke_problems;
    ajaxPram["CHECKE_DETAIL"] = $("#CHECKE_DETAIL").val();
    ajaxPram["VIDEO_URL"] = video;
    ajaxPram["LOCATION_IMG"] = main_pic;
    ajaxPram["OTHER_IMG"] = pics;
    ajaxPram["PKID"] = PKID;
    ajaxPram["SECURITY_PEOPLE"] = SECURITY_PEOPLE;


    $.post("../hiddenDanger/temporaryCreateDangerTable", ajaxPram , function (response) {
        var odj = response;
        if (odj['backFlag'] == "ok") {
            var ajaxPram1 = {};
            ajaxPram1["URL"] = url;
            ajaxPram1["USER_ID"] = userId;
            ajaxPram1["SOCIAL_CREDIT_CODE"] = social_credit_code;
            ajaxPram1["RECORD_ID"] = RecordId;
            $.post("../data/putTemporaryDate", ajaxPram1 , function (response) {
                var odj = response;
                alert(odj['msg']);
            },"json");
        } else {
            alert(odj['failInfo']);
        }
    },"json");
}