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

var RecordId=getUrlParam("RecordId");
var social_credit_code=getUrlParam("social_credit_code");
var userId=getUrlParam("userId");
var pics="";


//获取url参数

//更新签名参数
function UpdateRepresentSign(signName,RecordId){
    $.ajax({
        type: "POST",
        url:  "../reexamine/updateInspectedSign",
        data: {
            REPRESENR_SIGNATURE: signName,
            RecordId: RecordId
        },
        dataType: "json",
        async: true,
        success: function (data) {
            if (data.backFlag == "ok") {

                window.location.href="../page/reexamineDocument?RecordId="+RecordId+"&social_credit_code="+social_credit_code+"&userId="+userId;
            }
            else {
                alert("提交失败")
            }
        }
    })
}
