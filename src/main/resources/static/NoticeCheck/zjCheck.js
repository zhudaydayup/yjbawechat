$(function () {
    FastClick.attach(document.body);
});

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}
var userId="";
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
var social_credit_code=GetQueryString("social_credit_code");

//确认提交页面
function submitRecord() {
    var ajaxPram = {};
    ajaxPram["RECORD_ID"]=recordId;
    ajaxPram["CHECKED_UNIT"] = $("#CHECKED_UNIT").val();//2被检查单位
    ajaxPram["UNIT_NUMBER"] = $("#UNIT_NUMBER").val();//社会信用代码
    ajaxPram["CHECKED_LOCATION"] = $("#CHECKED_LOCATION").val();//6检查场所
    ajaxPram["CHECK_PEOPLE1"] = $("#CHECK_PEOPLE1").val();//检查方

    if ($("#othercheck").css("display")=="none") {
        ajaxPram["CHECK_PEOPLE2"] = "";//10检查人员2
    }else{
        ajaxPram["CHECK_PEOPLE1"] =$("#CHECK_PEOPLE1").val()+"|"+ $("#CHECK_PEOPLE2").val();//10检查人员2
    }
    var CHECK_PEOPLE_ID= new Array(ajaxPram["CHECK_PEOPLE1"].split("\\|"));


        var isboolture =  ajaxPram["RECORD_ID"]!="" && ajaxPram["CHECKED_LOCATION"] != "" ;//

        if (isboolture) {
            $.confirm("确认创建检查记录!", function (){
                 document.getElementById("mainBody").style.display = "none";
                document.getElementById("loadingmore").style.display = "";
                $.post("../ZjCheck/PutZjCheckView", ajaxPram , function (response) {
                    var odj = response;
                    if (odj['backFlag'] == "ok") {
                        alert("创建成功!");
                        location.href = "../page/CheckedSighView?RecordId="+recordId+"&social_credit_code="+social_credit_code;
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


function uploadtp_Set() {
    if( $("#switchtp").prop("checked") == true){
        document.getElementById("topbox").style.display ="none";
    }else{
        document.getElementById("topbox").style.display ="";
        // document.getElementById("otherexpert").style.display ="none";
    }
}
//是否添加其他人员
function addCheckMenSet() {
    // $('#othercheck').css('display','block');
    if( $('#othercheck').css("display") == 'none'){
        // alert($('#othercheck').css("display"));
        document.getElementById("othercheck").style.display ="";
    }else{
        // alert($('#othercheck').css("display"));
        document.getElementById("othercheck").style.display ="none";
    }
}
//是否添加其他专家
function addCheckExpertSet() {
    // $('#othercheck').css('display','block');
    if( $('#otherexpert').css("display") == 'none'){
        // alert($('#othercheck').css("display"));
        document.getElementById("otherexpert").style.display ="";
    }else{
        // alert($('#othercheck').css("display"));
        document.getElementById("otherexpert").style.display ="none";
    }
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





//获取当前时间显示
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
    format += "T";
    format += nTime.getHours()<10?"0"+(nTime.getHours()):(nTime.getHours());
    format += ":";
    format += nTime.getMinutes()<10?"0"+(nTime.getMinutes()):(nTime.getMinutes());
    format += ":00";
    return format;
}


function checkTime(){
    var start= $("#CHECKED_START_TIME").val();
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
        url: "../ZjCheck/GetZjRecord",
        dataType: "json",
        data: { RecordId:recordId},
        async: false,
        success: function (data) {
            if (data.backFlag == "ok") {
                if (data.RecordInfo.length!=0){
                    var CHECKED_UNIT = data.RecordInfo[0].CHECKED_UNIT;//2被检查单位
                    var UNIT_NUMBER= data.RecordInfo[0].UNIT_NUMBER;//19社会信用代码
                    var ADDRESS= data.RecordInfo[0].ADDRESS;//3被检单位地址
                    var REPRESENT_PEOPLE= data.RecordInfo[0].REPRESENT_PEOPLE;//4法定代表人
                    var REPRESENT_MOBILE=data.RecordInfo[0].REPRESENT_MOBILE;//5法定代表人手机号码
                    var area = data.RecordInfo[0].AREA;

                    $("#CHECKED_UNIT").val(CHECKED_UNIT);
                    $("#UNIT_NUMBER").val(UNIT_NUMBER);
                    $("#ADDRESS").val(ADDRESS);
                    $("#REPRESENT_PEOPLE").val(REPRESENT_PEOPLE);
                    $("#REPRESENT_MOBILE").val(REPRESENT_MOBILE);
                    $("#area").val(area);

                }
            }
        }
    })
}
$(function () {
    getGreatTimeById();
});

function getGreatTimeById() {
    recordId=decodeURI(getUrlParam("RecordId"));
    $.post("../NoticeView/getGreatTimeById", {RECORD_ID: recordId}, function (response) {
        var jsonObj =  eval("("+response+")");
        $("#CHECKED_START_TIME").val(jsonObj[0][0]["CREATE_TIME"]);
    })
}
function GetIdCheckMsgs(recordId){
    $.ajax({
        type: "POST",
        url: "../ZjCheck/GetZjCheckMsgs",
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
                            NotOkmodel+='<div class="weui-cells weui-cells_form">'+
                                '<div class="weui-cell">'+
                                '<div class="weui-cell__hd"><label class="weui-label">违规</label></div>'+
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
