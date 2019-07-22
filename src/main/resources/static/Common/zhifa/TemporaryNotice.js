function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

/*$(document).ready(function () {
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
});*/

var userId=GetQueryString("userId");
var social_credit_code=GetQueryString("social_credit_code");
var url = window.location.href;
//暂存提交URL
function temporaryRecord() {

    var msg = "";
    var obj = document.getElementsByName("checkbox1");
    for (var k=0;k<obj.length;k++){
        if(obj[k].checked){
            msg+=obj[k].value+"、";
        }
    }

    msg=msg.substring(0,msg.length-1);//字符串去除最后一位
    var ajaxPram = {};
    ajaxPram["CHECKED_UNIT"] = $("#CHECKED_UNIT").val();
    ajaxPram["UNIT_NUMBER"] = $("#UNIT_NUMBER").val();
    ajaxPram["REPRESENT_PEOPLE"] = $("#REPRESENT_PEOPLE").val();
    ajaxPram["REPRESENT_MOBILE"] = $("#REPRESENT_MOBILE").val();
    ajaxPram["ADDRESS"] = $("#ADDRESS").val();
    ajaxPram["CHECK_PEOPLE1"] = $("#CHECK_PEOPLE1").val();
    ajaxPram["CARD_NUMBER1"] = $("#CARD_NUMBER1").val();
    ajaxPram["CHECK_UNIT1"] = $("#CHECK_UNIT1").val();
    ajaxPram["CHECK_PEOPLE2"] = $("#CHECK_PEOPLE2").val();
    ajaxPram["CARD_NUMBER2"] = $("#CARD_NUMBER2").val();
    ajaxPram["CHECK_UNIT2"] = $("#CHECK_UNIT2").val();

    ajaxPram["CHECKED_START_TIME"] = NowDate;
    ajaxPram["NOTICE_CONTENT"] = msg;
    ajaxPram["NOTICE_WAY"] = $("#NOTICE_WAY").val();
    ajaxPram["NOTICE_CONTACTS"] = $("#NOTICE_CONTACTS").val();
    ajaxPram["NOTICE_PHONE"] = $("#NOTICE_PHONE").val();
    ajaxPram["userId"] = userId;
    ajaxPram["area"] = $("#area").val();

    var expert = new Array();
    var expertName = new Array();
    for(var i=1;i<=(document.getElementById("otherbox").children.length)/2;i++){
        var expertId = "EXPERT_UNIT" + i;
        var expertNameId = "EXPERT_NAME" + i;
        if(expertId!=null&&expertNameId!=null){
            var value1 = document.getElementById(expertNameId).value;
            var value2 = document.getElementById(expertId).value;
            expertName.push(value1);
            expert.push(value2);
        }

    }
    ajaxPram["EXPERT_UNIT1"] = expert.join('_');
    ajaxPram["EXPERT_NAME1"] = expertName.join('_');


    $.post("../Record/temporaryCreateEnforc", ajaxPram , function (response) {
        var odj = response;
        alert(odj['backFlag']);
        if (odj['backFlag'] == "ok") {

            var ajaxPram1 = {};
            ajaxPram1["URL"] = url;
            ajaxPram1["USER_ID"] = userId;
            ajaxPram1["SOCIAL_CREDIT_CODE"] = social_credit_code;
            $.post("../data/putZhiFaTemporaryDate", ajaxPram1 , function (response) {
                var odj = response;
                alert(odj['msg']);
            },"json");

        } else {
            alert(odj['failInfo']);
        }

    },"json");
}