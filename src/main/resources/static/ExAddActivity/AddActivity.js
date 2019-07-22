$(function () {
    FastClick.attach(document.body);
});

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}
var deviceId="";
var recordId="";
var name="";
var policeNumber="";
var phone="";
var idCard="";
var department="";
var duty="";
var main_pic="";
var pics="";
var opStatue="exist";
var sCode = GetQueryString("sCode");
var social_credit_code=GetQueryString("social_credit_code");
var userId=GetQueryString("userId");
//确认提交页面
function submitRecord() {

    var ajaxPram = {};
    ajaxPram["RECORD_ID"]=recordId;
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

    var isboolture =  ajaxPram["RECORD_ID"]!="" && ajaxPram["CHECKED_UNIT"] != "" &&  ajaxPram["UNIT_NUMBER"] != "" && ajaxPram["ADDRESS"] != ""&& ajaxPram["REPRESENT_PEOPLE"] != "" != "" && ajaxPram["CHECKED_LOCATION"] != "" ;//

    if (isboolture) {
        $.confirm("确认创建检查记录!", function (){
            document.getElementById("mainBody").style.display = "none";
            document.getElementById("loadingmore").style.display = "";
            $.post("../exRecord/UpdateIdRecord", ajaxPram , function (response) {
                var odj = response;
                if (odj['backFlag'] == "ok") {
                    alert("创建成功!");
                    if(ajaxPram["EXPERT_UNIT1"]!="" && ajaxPram["EXPERT_NAME1"]!=""){

                    }
                    location.href = "../exLawDictionary/ExpertSign?RecordId="+recordId+"&userId="+userId+"&social_credit_code="+social_credit_code;

                } else {
                    alert(odj['failInfo']);
                }
            },"json");
        }, function () {
            //取消操作
        });
    }
    else
        alert("请完善检查信息！");

}

//活动开始时间截止时间显示
function model_Set() {
    var model;
    if( $("#switchCP").prop("checked") == true){
        document.getElementById("time001").style.display ="";
        document.getElementById("time002").style.display ="";
    }else{
        document.getElementById("time001").style.display ="none";
        document.getElementById("time002").style.display ="none";
    }
}

function uploadtp_Set() {
    if( $("#switchop").prop("checked") == true){
        document.getElementById("otherbox").style.display ="";
    }else{
        document.getElementById("otherbox").style.display ="none";
        document.getElementById("otherexpert").style.display ="none";
    }
}
//是否添加其他人员
function addCheckMenSet() {

    if( $('#othercheck').css("display") == 'none'){
        document.getElementById("othercheck").style.display ="";
    }else{
        document.getElementById("othercheck").style.display ="none";
    }
}

//是否添加其他专家
function addCheckExpertSet() {
    var h = (document.getElementById("otherbox").children.length)/2;
    h = h+1;
    var id1 = "expert"+h;
    var id2 = "expertName"+h;
    var id3 = "EXPERT_UNIT" + h;
    var id4 = "EXPERT_NAME" + h;
    $("#otherbox").append(
        "<div class=\"weui-cell\" id="+id2+">\n" +
        "<div class=\"weui-cell__hd\"><label class=\"weui-label\">专家名称"+h+"</label></div>\n" +
        "<div class=\"weui-cell__bd\">\n" +
        "<input class=\"weui-input\" type=\"text\" placeholder=\"请输入专家名称\" id="+id4+" >\n" +
        "</div>\n" +
        "</div>\n" +
        "<div class=\"weui-cell\" id="+id1+">\n" +
        "<div class=\"weui-cell__hd\"><label class=\"weui-label\">专家单位"+h+"</label></div>\n" +
        "<div class=\"weui-cell__bd\">\n" +
        "<input class=\"weui-input\" type=\"text\" placeholder=\"请输入专家单位\"id="+id3+">\n" +
        "</div>\n" +
        "</div>"
    );
}

function deleteCheckExpertSet() {
    var h = (document.getElementById("otherbox").children.length)/2;
    var id1 = "expert"+h;
    var id2 = "expertName"+h;
    document.getElementById(id1).remove();
    document.getElementById(id2).remove();
}

function fanhui() {
    window.history.back();
}


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



$(document).ready(function () {
    recordId=decodeURI(getUrlParam("RecordId"));
    GetIdRecord(recordId);
    GetIdCheckMsgs(recordId);
});

function getFormat(){
    format = "";
    var nTime = new Date();
    format += nTime.getFullYear()+"-";
    format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
    format += "-";
    format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
    format += " ";
    format += nTime.getHours()<10?"0"+(nTime.getHours()):(nTime.getHours());
    format += ":";
    format += nTime.getMinutes()<10?"0"+(nTime.getMinutes()):(nTime.getMinutes());
    format += ":00";
    return format;
}

function checkTime(){
    var start= $("#CHECKED_START_TIME1").val();
    var end= $("#CHECKED_END_TIME").val();
    var starttime = new Date(Date.parse(start));
    var endtime = new Date(Date.parse(end));
    if (starttime>endtime)  {
        alert("截止时间不能小于开始时间");
        $("#CHECKED_END_TIME").val("");
    }
}

function   GetIdRecord(recordId){
    $.ajax({
        type: "POST",
        url: "../exRecord/GetIdRecord",
        dataType: "json",
        data: { RecordId:recordId},
        async: false,
        success: function (data) {
            if (data.backFlag == "ok") {
                if (data.RecordInfo.length!=0){
                    var CHECKED_UNIT = data.RecordInfo[0].cHECKED_UNIT;//2被检查单位
                    var UNIT_NUMBER= social_credit_code;//19社会信用代码
                    var ADDRESS= data.RecordInfo[0].aDDRESS;//3被检单位地址
                    var REPRESENT_PEOPLE= data.RecordInfo[0].rEPRESENT_PEOPLE;//4法定代表人
                    var REPRESENT_MOBILE=data.RecordInfo[0].rEPRESENT_MOBILE;//5法定代表人手机号码
                    //var CHECKED_START_TIME= data.RecordInfo[0].cREATE_TIME;//7检查开始时间
                    // CHECKED_START_TIME=CHECKED_START_TIME.substring(0,10)+" "+CHECKED_START_TIME.substring(11,16);

                    $("#CHECKED_UNIT").val(CHECKED_UNIT);
                    $("#UNIT_NUMBER").val(UNIT_NUMBER);
                    $("#ADDRESS").val(ADDRESS);
                    $("#REPRESENT_PEOPLE").val(REPRESENT_PEOPLE);
                    $("#REPRESENT_MOBILE").val(REPRESENT_MOBILE);
                    // $("#CHECKED_START_TIME").val(CHECKED_START_TIME);


                }
            }
        }
    })
}

function GetIdCheckMsgs(recordId){
    $.ajax({
        type: "POST",
        url: "../exRecord/GetIdCheckMsgs",
        dataType: "json",
        data: { RecordId:recordId},
        async: false,
        success: function (data) {
            if (data.backFlag == "ok") {
                if (data.CheckInfo.length!=0){
                    var Okmodel='';
                    var NotOkmodel='';
                    for (var i = 0; i < data.CheckInfo.length; i++) {

                        if (data.CheckInfo[i].CHECK_STATE=="合格") {
                            var img=data.CheckInfo[i].LOCATION_IMG;
                            var array=img.toString().split('|');
                            var imgDiv="";
                            for (var j = 0; j < array.length; j++) {
                                var url="../"+array[j];
                                imgDiv+='<li class="weui-uploader__file "style="background-image:url('+url+')">'+
                                    '</li>'
                            }

                            Okmodel+=
                                '<div class="weui-cells weui-cells_form">'+
                                '<div class="weui-cell">'+
                                '<div class="weui-cell__hd"><label class="weui-label">合格</label></div>'+
                                // '<div class="weui-cell__bd">'+
                                // '<input class="weui-input" type="text" placeholder="检查内容名称"value="'+data.CheckInfo[i].TEMP_NAME+'" >'+
                                // '</div>'+
                                '</div>'+
                                '<div class="weui-cells__title">检查内容</div>'+
                                '<div class="weui-cell">'+
                                '<div class="weui-cell__bd">'+
                                '<textarea class="weui-textarea" placeholder="" rows="3" >'+
                                data.CheckInfo[i].CHECKE_DETAIL+
                                '</textarea>'+
                                '</div>'+
                                '</div>'+
                                '<div class="weui-cells__title">现场图片</div>'+
                                '<div class="weui-uploader__bd">'+
                                '<ul class="weui-uploader__files " style="margin-left: 1px">'+
                                imgDiv+
                                '</ul>'+
                                '</div>'+
                                '</div>'
                        }

                        if (data.CheckInfo[i].CHECK_STATE=="不合格") {
                            var img=data.CheckInfo[i].LOCATION_IMG;
                            var array=img.toString().split('|');
                            var imgDiv="";
                            for (var j = 0; j < array.length; j++) {
                                var url="../"+array[j];
                                imgDiv+='<li class="weui-uploader__file" style="background-image:url('+url+')">'+
                                    '</li>'
                            }
                            // alert(imgDiv);
                            NotOkmodel+='<div class="weui-cells weui-cells_form">'+
                                '<div class="weui-cell">'+
                                '<div class="weui-cell__hd"><label class="weui-label">不合格</label></div>'+
                                // '<div class="weui-cell__bd">'+
                                // '<input class="weui-input" type="text" placeholder="检查内容名称"value="'+data.CheckInfo[i].TEMP_NAME+'" >'+
                                // '</div>'+
                                '</div>'+
                                '<div class="weui-cells__title">检查内容</div>'+
                                '<div class="weui-cell">'+
                                '<div class="weui-cell__bd">'+
                                '<textarea class="weui-textarea" placeholder="" rows="3" id="">'+
                                data.CheckInfo[i].CHECKE_DETAIL+
                                '</textarea>'+
                                '</div>'+
                                '</div>'+
                                '<div class="weui-cells__title">现场图片</div>'+
                                '<div class="weui-uploader__bd">'+
                                '<ul class="weui-uploader__files " style="margin-left: 1px">'+
                                imgDiv+
                                '</ul>'+
                                '</div>'+
                                '</div>'
                        }
                    }
                    $("#legalCheck").html(Okmodel);
                    $("#notLegalCheck").html(NotOkmodel);

                }
            }
            else{
                alert("检查情况加载异常")
            }
        }
    })
}
