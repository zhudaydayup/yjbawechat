function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "../PunishMeasure/getTemporaryPunishMeasureInfo",
        dataType: "json",
        data: { RecordId:RecordId},
        async: false,
        success: function (data) {
            if(data[0] != null){
                var PROCESS_DECISION = data[0].PROCESS_DECISION;//处理决定
                $("#PROCESS_DECISION").val(PROCESS_DECISION);
                var ZF_ON_SCENE_AREA = data[0].ZF_ON_SCENE_AREA;//处理决定
                $("#ZF_ON_SCENE_AREA").val(ZF_ON_SCENE_AREA);
                var ZF_ON_SCENE_RECORD = data[0].ZF_ON_SCENE_RECORD;//处理决定
                $("#ZF_ON_SCENE_RECORD").val(ZF_ON_SCENE_RECORD);
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

    var msg1="";
    for (var k=0;k<obj.length;k++){
        if(obj[k].checked){
            msg1+=obj[k].value+"|";
        }
    }

    var ajaxPram = {};
    ajaxPram["PROCESS_DECISION"] = $('#PROCESS_DECISION').val();
    ajaxPram["RECORD_ID"] = RECORD_ID;
    ajaxPram["CHECKE_UNIT"] = $("#CHECKE_UNIT").val();
    ajaxPram["CHECKED_START_TIME"] = CHECKED_START_TIME;
    ajaxPram["CHECKE_DETAIL"] = msg1;
    ajaxPram["URL"] = url;
    ajaxPram["ZF_ON_SCENE_AREA"] = $("#ZF_ON_SCENE_AREA").val();
    ajaxPram["ZF_ON_SCENE_RECORD"] = $("#ZF_ON_SCENE_RECORD").val();
    if (ajaxPram["ZF_ON_SCENE_AREA"] == "") {
        alert("请填写文书地区！");
        return false;
    }
    if (ajaxPram["ZF_ON_SCENE_RECORD"] == "") {
        alert("请填写文书编号！");
        return false;
    }


    $.post("../PunishMeasure/setTemporaryPunishMeasureInfo", ajaxPram , function (response) {
        var odj = response;
        if (odj['backFlag'] == "ok") {
            var ajaxPram1 = {};
            ajaxPram1["URL"] = url;
            ajaxPram1["USER_ID"] = userId;
            ajaxPram1["SOCIAL_CREDIT_CODE"] = social_credit_code;
            ajaxPram1["RECORD_ID"] = RecordId;
            $.post("../data/putZhiFaTemporaryDate", ajaxPram1 , function (response) {
                var odj = response;
                alert(odj['msg']);
            },"json");
        } else {
            alert(odj['failInfo']);
        }

    },"json");
}