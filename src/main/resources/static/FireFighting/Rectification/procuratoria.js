var RecordId=getUrlParam("RecordId");
var ID = getUrlParam("ID");
var social_credit_code=getUrlParam("social_credit_code");
var userId=getUrlParam("userId");
var sCode = getUrlParam("sCode");
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
function UpdateRepresentSign(signName,EventId){
    $.ajax({
        type: "POST",
        url:  "../timeRectification/UpdateRepresentSignature",
        data: {
            REPRESENR_SIGNATURE: signName,
            RecordId: EventId
        },
        dataType: "json",
        async: true,
        success: function (data) {
            if (data.backFlag == "ok") {
 
                window.location.href="../page/examinationDocument?RecordId="+RecordId+"&social_credit_code="+social_credit_code+"&userId="+userId+"&ID="+ID+"&sCode="+sCode;
            }
            else {
                alert("提交失败")
            }
        }
    })
}
