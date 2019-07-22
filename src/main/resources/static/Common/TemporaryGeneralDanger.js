function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "../exLawDictionary/getTemporaryGeneralHiddenDanger",
        dataType: "json",
        data: { RecordId:RecordId},
        async: false,
        success: function (data) {
            if (data[0].DEAL_VIEW != null) {
                var DEAL_VIEW = data[0].DEAL_VIEW;//处理决定
                $("#PROCESS_DECISION").val(DEAL_VIEW);
            }
        }
    });
});

var userId=GetQueryString("userId");
var social_credit_code=GetQueryString("social_credit_code");
var RecordId=GetQueryString("RecordId");
var url = window.location.href;
//暂存提交URL
function temporaryRecord() {

    var ajaxPram = {};
    ajaxPram["URL"] = url;
    ajaxPram["USER_ID"] = userId;
    ajaxPram["SOCIAL_CREDIT_CODE"] = social_credit_code;
    ajaxPram["RECORD_ID"] = RecordId;
    ajaxPram["PROCESS_DECISION"] = $("#PROCESS_DECISION").val();//处理决定

    $.post("../exLawDictionary/temporaryGeneralHiddenDanger", ajaxPram , function (response) {
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