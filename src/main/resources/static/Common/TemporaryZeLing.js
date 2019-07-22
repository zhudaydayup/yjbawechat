function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

/*$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "../exRecord/getTemporarySetExZeLingInfo",
        dataType: "json",
        data: { RecordId:RecordId},
        async: false,
        success: function (data) {
            if (data==""||data==null){
                alert("没有缓存数据！")
            }else {
                var DANGER=  data[0].CHECKE_PROBLEM_IDS;//隐患内容
                var danger = DANGER.toString().split('|');
                var TIME_IDS=  data[0].TIME_IDS;//整改时间
                var time_ids = TIME_IDS.toString().split('|');
                for (var i=0;i<danger.length;i++) {

                }
            }

        }
    })
});*/


var userId=GetQueryString("userId");
var social_credit_code=GetQueryString("social_credit_code");
var RecordId=GetQueryString("RecordId");
var url = window.location.href;
//暂存提交URL
function temporaryRecord() {
    var obj = document.getElementsByName("checkbox1");
    var checke_problem_id="";
    var time_id = [];
    for (var i = 0; i < obj.length; i++) {
        if(obj[i].checked){
            checke_problem_id+=obj[i].value+"|";
            time_id.push(document.getElementById("time"+i).value);
        }
    }

    var ajaxPram = {};
    ajaxPram["URL"] = url;
    ajaxPram["USER_ID"] = userId;
    ajaxPram["SOCIAL_CREDIT_CODE"] = social_credit_code;//社会信用代码
    ajaxPram["RECORD_ID"] = RecordId;

    ajaxPram["CHECKED_UNIT"] = $("#CHECKED_UNIT").val();
    ajaxPram["EXECUTE_PEOPLE"] = $("#EXECUTE_PEOPLE").val();
    ajaxPram["CARD_NUMBER"] = "写死的";
    ajaxPram["TIME_IDS"] = time_id.join("|");
    ajaxPram["CHECKE_PROBLEM_IDS"] = checke_problem_id;
    ajaxPram["CHECKED_END_TIME"] = getFormat1();


    if ($("#EXECUTE_PEOPLE2").val()!=""){
        ajaxPram["EXECUTE_PEOPLE2"] = $("#EXECUTE_PEOPLE2").val();
        ajaxPram["CARD_NUMBER2"] = $("#CARD_NUMBER2").val();
    }else{
        ajaxPram["EXECUTE_PEOPLE2"] ="";
        ajaxPram["CARD_NUMBER2"] = "";
    }


    $.post("../exRecord/temporarySetExZeLingInfo", ajaxPram , function (response) {
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