function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "../exRecord/getTemporarySetExPunishMeasureInfo",
        dataType: "json",
        data: { RecordId:RecordId},
        async: false,
        success: function (data) {
            if(data[0] != null){
                var PROCESS_DECISION = data[0].PROCESS_DECISION;//处理决定
                $("#PROCESS_DECISION").val(PROCESS_DECISION);
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
    var msg="";
    for (var k=0;k<obj.length;k++){
        if(obj[k].checked){
            msg+=obj[k].value+"|";
        }
    }
    msg=msg.substring(0,msg.length-1);

    var ajaxPram = {};
    ajaxPram["URL"] = url;
    ajaxPram["USER_ID"] = userId;
    ajaxPram["SOCIAL_CREDIT_CODE"] = social_credit_code;//社会信用代码
    ajaxPram["RECORD_ID"] = RecordId;

    ajaxPram["PROCESS_DECISION"] = $('#PROCESS_DECISION').val();
    ajaxPram["CHECKED_UNIT"] = $("#CHECKED_UNIT").val();
    ajaxPram["CHECKED_START_TIME"] = CHECKED_START_TIME;
    ajaxPram["CHECKE_DETAIL"] = msg;


    $.post("../exRecord/temporarySetExPunishMeasureInfo", ajaxPram , function (response) {
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