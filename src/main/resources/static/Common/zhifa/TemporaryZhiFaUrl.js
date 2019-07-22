function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

var userId=GetQueryString("userId");
var social_credit_code=GetQueryString("social_credit_code");
var RecordId=GetQueryString("RecordId");

//暂存提交URL
function temporaryRecord() {
    var url = window.location.href;
    var ajaxPram = {};
    ajaxPram["URL"] = url;
    ajaxPram["USER_ID"] = userId;
    ajaxPram["SOCIAL_CREDIT_CODE"] = social_credit_code;
    ajaxPram["RECORD_ID"] = RecordId;
    $.post("../data/putZhiFaTemporaryDate", ajaxPram , function (response) {
        var odj = response;
        alert(odj['msg']);
    },"json");

}
