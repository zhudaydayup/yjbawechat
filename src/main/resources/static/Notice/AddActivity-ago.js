
var userId = GetQueryString("userId");
var one_id = GetQueryString("one_id");
var title = getUrlParam("title");
var two_id ="";

var NowDate=getFormat();//时间模式y-m-dTh-m-s
$(document).ready(function () {
    var NowDate1 = getFormat1();//时间模式y-m-d
    $("#CHECKED_START_TIME").val(NowDate1);
});

//确认提交页面
function submitRecord() {
    var a1=document.getElementById("CHECK_PEOPLE1").value;
    var a2=document.getElementById("CHECK_PEOPLE2").value;
    var b1=document.getElementById("EXPERT_NAME1").value;
    var b2=document.getElementById("EXPERT_NAME2").value;
    if(a1==a2){
        document.getElementById("CHECK_PEOPLE2").value=a1+"(重名)";
    }
    if(b2!=""&&b2!=null&&b1==b2){
        document.getElementById("EXPERT_NAME2").value=b1+"(重名)";
    }

    var msg = "";
    var twoId="";
    var obj = document.getElementsByName("checkbox1");
    for (var k=0;k<obj.length;k++){
        if(obj[k].checked){
            msg+=obj[k].value+"、";
            twoId+=obj[k].id+"|";
        }
    }
    two_id=twoId.substring(0,twoId.length-1);
    twoId=encodeURI(twoId);
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
    ajaxPram["EXPERT_NAME1"] = $("#EXPERT_NAME1").val();
    ajaxPram["EXPERT_UNIT1"] = $("#EXPERT_UNIT1").val();
    ajaxPram["EXPERT_NAME2"] = $("#EXPERT_NAME2").val();
    ajaxPram["EXPERT_UNIT2"] = $("#EXPERT_UNIT2").val();
    ajaxPram["CHECKED_START_TIME"] = NowDate;
    ajaxPram["NOTICE_CONTENT"] = msg;
    ajaxPram["NOTICE_WAY"] = $("#NOTICE_WAY").val();
    ajaxPram["NOTICE_CONTACTS"] = $("#NOTICE_CONTACTS").val();
    ajaxPram["NOTICE_PHONE"] = $("#NOTICE_PHONE").val();
    ajaxPram["userId"] = userId;
    ajaxPram["area"] = $("#area").val();
    ajaxPram["ONE_ID"] = one_id;
    ajaxPram["TWO_ID"] = two_id;

    var isBooltrue =  ajaxPram["area"] != "" && ajaxPram["CHECK_PEOPLE1"] != "" && ajaxPram["CARD_NUMBER1"] != "" && ajaxPram["CHECK_UNIT1"] != "" &&ajaxPram["CHECKED_UNIT"] != "" && ajaxPram["UNIT_NUMBER"] != "" && ajaxPram["ADDRESS"] != "" && ajaxPram["REPRESENT_PEOPLE"] != "" ;

    if (isBooltrue) {




        //$.confirm("确认创建检查记录!", function (){

            document.getElementById("mainBody").style.display = "none";
            document.getElementById("loadingmore").style.display = "";
            $.post("../Record/CreateEnforce", ajaxPram , function (response) {
                var odj = response;
                if (odj['backFlag'] == "ok") {
                    alert("创建成功!");
                    location.href = "../Notice/MsgSuccess?RecordId="+odj['EventId']+"&one_id="+one_id+"&userId="+userId+"&title="+title+"&two_id="+twoId;
                } else {
                    alert(odj['failInfo']);
                }
            },"json");


       /* }, function () {
            //取消操作
        });*/
    }

    else
        alert("请完善检查信息！");
}



//获取被检查单位信息
$(function () {
    var social_credit_code = getUrlParam("social_credit_code");
    $.ajax({
        type: "POST",
        url:  "../data/getCompanyMsg",
        data: {
            social_credit_code: social_credit_code
        },
        dataType: "json",
        async: true,
        success: function (data) {
            document.getElementById("CHECKED_UNIT").value = data.data[0].name;
            document.getElementById("UNIT_NUMBER").value = data.data[0].social_credit_code;
            document.getElementById("ADDRESS").value = data.data[0].address;
            document.getElementById("REPRESENT_PEOPLE").value = data.data[0].legal_person;
            document.getElementById("REPRESENT_MOBILE"). value = data.data[0].phone;
        }
    });
});



var Json={};
Json["oneId"]=one_id;
$(function () {
    $.post("../Index/checkTwoItem", Json, function (res)
    {
        var testJson = eval(res);
        if (testJson[0]["msg"]!='fail')
        {
            for (var i = 0; i < testJson.length; i++) {
                $("#List").append(
                    "<label class='weui-cell weui-check__label' for='" + testJson[i]["TWO_ID"] + "'>" +
                    "<div class='weui-cell__bd'>" +
                    "<p >" + testJson[i]["TWO_NAME"] + "</p>" +
                    "</div>" +
                    "<div class='weui-cell__hd'>" +
                    "<input type='checkbox' class='weui-check' name='checkbox1' id='" + testJson[i]["TWO_ID"] + "'  value='" + testJson[i]["TWO_NAME"] + "' length='"+testJson.length+"' >" +
                    "<i class='weui-icon-checked'></i>" +
                    "</div>" +
                    "</label>"
                // length='"+testJson.length+"'
                );
            }
        }else {
            alert(testJson[0]["failInfo"]);
        }
    });
});

/*从点击屏幕上的元素到触发元素的 click 事件
$(function () {
    FastClick.attach(document.body);
});*/

//是否添加其他人员
function addCheckMenSet() {

    if( $('#othercheck').css("display") == 'none'){

        document.getElementById("othercheck").style.display ="";
        document.getElementById("CHECK_UNIT2").value=document.getElementById("CHECK_UNIT1").value;
    }else{

        document.getElementById("othercheck").style.display ="none";
        document.getElementById("CHECK_UNIT2").value="";
    }
}

//是否添加其他专家
function addCheckExpertSet() {

    if( $('#otherexpert').css("display") == 'none'){

        document.getElementById("otherexpert").style.display ="";
    }else{

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

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

//被检查单位信息隐藏与显示
function model_Set() {

    if( $("#switchCP").prop("checked") == true){
        document.getElementById("box1").style.display ="";
    }else{
        document.getElementById("box1").style.display ="none";
    }
}

//时间模式y-m-d
function getFormat1(){
    format = "";
    var nTime = new Date();
    format += nTime.getFullYear()+"-";
    format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
    format += "-";
    format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
    return format;
}

//时间模式y-m-dTh-m-s
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