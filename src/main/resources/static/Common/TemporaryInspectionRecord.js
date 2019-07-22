function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "../exRecord/getTemporaryExUpdateIdRecord",
        dataType: "json",
        data: { RecordId:RecordId},
        async: false,
        success: function (data) {

            if (data.RecordInfo[0].CHECKED_LOCATION!=null){
                var CHECKED_LOCATION = data.RecordInfo[0].CHECKED_LOCATION;//6检查场所
                $("#CHECKED_LOCATION").val(CHECKED_LOCATION);
            }
            if (data.RecordInfo[0].EXPERT_NAME1!=null&&data.RecordInfo[0].EXPERT_UNIT1!=null) {
                var EXPERT_NAME1= data.RecordInfo[0].EXPERT_NAME1;//专家1
                var expertName = EXPERT_NAME1.toString().split('_');
                var EXPERT_UNIT1=  data.RecordInfo[0].EXPERT_UNIT1;//专家1所在单位
                var expert = EXPERT_UNIT1.toString().split('_');
                if (expertName[0]!=""&&expert[0]!="") {
                    $("#EXPERT_NAME1").val(expertName[0]);
                    $("#EXPERT_UNIT1").val(expert[0]);
                }else {
                    $("#EXPERT_NAME1").val("");
                    $("#EXPERT_UNIT1").val("");
                }
                if (expertName.length!=0) {
                    for (var i=2;i<=expertName.length;i++) {
                        var id1 = "expert"+i;
                        var id2 = "expertName"+i;
                        var id3 = "EXPERT_UNIT" + i;
                        var id4 = "EXPERT_NAME" + i;
                        $("#otherbox").append(
                            "<div class='weui-cell' id="+id2+">" +
                            "<div class='weui-cell__hd'><label class='weui-label'>专家名称"+i+"</label></div>" +
                            "<div class='weui-cell__bd'>" +
                            "<input class='weui-input' type='text' placeholder='请输入专家名称' id="+id4+" >" +
                            "</div>" +
                            "</div>" +
                            "<div class='weui-cell' id="+id1+">" +
                            "<div class='weui-cell__hd'><label class='weui-label'>专家单位"+i+"</label></div>" +
                            "<div class='weui-cell__bd'>" +
                            "<input class='weui-input' type='text' placeholder='请输入专家单位' id="+id3+" >" +
                            "</div>" +
                            "</div>");
                        $("#"+id4).val(expertName[i-1]);
                        $("#"+id3).val(expert[i-1]);
                    }

                }
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

    var ajaxPram = {};
    ajaxPram["URL"] = url;
    ajaxPram["USER_ID"] = userId;
    ajaxPram["SOCIAL_CREDIT_CODE"] = social_credit_code;
    ajaxPram["RECORD_ID"] = RecordId;

    ajaxPram["CHECKED_START_TIME"] = $("#CHECKED_START_TIME1").val();//7检查开始时间
    ajaxPram["CHECKED_END_TIME"] = $("#CHECKED_END_TIME").val();//8检查结束时间
    ajaxPram["CHECKED_UNIT"] = $("#CHECKED_UNIT").val();//2被检查单位
    ajaxPram["UNIT_NUMBER"] = $("#UNIT_NUMBER").val();//社会信用代码
    ajaxPram["ADDRESS"] = $("#ADDRESS").val();//3被检单位地址
    ajaxPram["REPRESENT_PEOPLE"] = $("#REPRESENT_PEOPLE").val();//4法定代表人
    ajaxPram["REPRESENT_MOBILE"] = $("#REPRESENT_MOBILE").val();//5法定代表人手机号码
    ajaxPram["CHECKED_LOCATION"] = $("#CHECKED_LOCATION").val();//6检查场所

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


    $.post("../exRecord/temporaryExUpdateIdRecord", ajaxPram , function (response) {
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