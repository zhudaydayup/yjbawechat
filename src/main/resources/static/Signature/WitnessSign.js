var RecordId=getUrlParam("RecordId");
var userId=getUrlParam("userId");
var social_credit_code=getUrlParam("social_credit_code");
var pics="";



//获取url参数
function getUrlParam(key){
    // 获取参数
    var url = window.location.search;
    // 正则筛选地址栏
    var reg = new RegExp("(^|&)"+ key +"=([^&]*)(&|$)");
    // 匹配目标参数
    var result = url.substr(1).match(reg);
    //返回参数值
    return result ? decodeURIComponent(result[2]) : null;
}
//更新签名参数
function updateSighRecord(signName,EventId){
    var signTime=$("#WITNESS_SIGN_TIME").val();
    $.ajax({
        type: "POST",
        url:  "../Record/UpdateWitnessSign",
        data: {
            signName: signName,
            EventId: EventId,
            signTime:signTime
        },
        dataType: "json",
        async: true,
        success: function (data) {
            if (data.backFlag == "ok") {

                window.location.href="../LawDictionary/MsgSuccess?RecordId="+EventId+"&userId="+userId+"&social_credit_code="+social_credit_code;

            }
            else {
                alert("提交失败")
            }
        }
    })
}
$(document).ready(function () {
    var NowDate = getFormat();
    // alert(NowDate);
    $("#WITNESS_SIGN_TIME").val(NowDate);
})
function getFormat(){
    format = "";
    var nTime = new Date();
    format += nTime.getFullYear()+"-";
    format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
    format += "-";
    format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
    format += "T";
    format += nTime.getHours()<10?"0"+(nTime.getHours()):(nTime.getHours());
    format += ":";
    format += nTime.getMinutes()<10?"0"+(nTime.getMinutes()):(nTime.getMinutes());
    format += ":00";
    return format;
}
