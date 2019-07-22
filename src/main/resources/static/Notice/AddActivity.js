function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}
var userId = GetQueryString("userId");
var one_id = GetQueryString("one_id");
var ent_id = GetQueryString("social_credit_code");
var title = getUrlParam("title");
var two_id ="";
 var NowDate=getFormat();//时间模式y-m-dTh-m-s
$(document).ready(function () {
    var NowDate1 = getFormat1();//时间模式y-m-d
    $("#CHECKED_START_TIME").val(NowDate1);
});

//确认提交页面
function submitRecord() {
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
    twoId=encodeURI(two_id);
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

    ajaxPram["CHECKED_START_TIME"] = NowDate;
    ajaxPram["NOTICE_CONTENT"] = msg;
    ajaxPram["NOTICE_WAY"] = $("#NOTICE_WAY").val();
    ajaxPram["NOTICE_CONTACTS"] = $("#NOTICE_CONTACTS").val();
    ajaxPram["NOTICE_PHONE"] = $("#NOTICE_PHONE").val();
    ajaxPram["userId"] = userId;
    ajaxPram["area"] = $("#area").val();
    ajaxPram["ONE_ID"] = one_id;
    ajaxPram["TWO_ID"] = twoId;
    ajaxPram["NOTIFICATION_NUMBER"] = $("#NOTIFICATION_NUMBER").val();
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


    var isBooltrue =  ajaxPram["area"] != "" && ajaxPram["CHECK_PEOPLE1"] != "" && ajaxPram["CARD_NUMBER1"] != "" && ajaxPram["CHECK_UNIT1"] != "" &&ajaxPram["CHECKED_UNIT"] != "" && ajaxPram["UNIT_NUMBER"] != "" && ajaxPram["ADDRESS"] != "" && ajaxPram["REPRESENT_PEOPLE"] != ""&& ajaxPram["NOTIFICATION_NUMBER"] != "" ;

    if (isBooltrue) {
            document.getElementById("mainBody").style.display = "none";
            document.getElementById("loadingmore").style.display = "";
            $.post("../Record/CreateEnforce", ajaxPram , function (response) {
                var odj = response;
                if (odj['backFlag'] == "ok") {
                    alert("创建成功!");
                    location.href = "../Notice/MsgSuccess?RecordId="+odj['EventId']+"&one_id="+one_id+"&userId="+userId+"&title="+title+"&two_id="+twoId+"&social_credit_code="+ent_id;
                } else {
                    alert(odj['failInfo']);
                }
            },"json");
    }

    else
        alert("请完善检查信息！");
}


var ent_id = getUrlParam("social_credit_code");
//获取被检查单位信息
$(function () {
    $.ajax({
        type: "POST",
        url:  "../data/getCompanyMsg",
        data: {
            social_credit_code: ent_id
        },
        dataType: "json",
        async: true,
        success: function (data) {

            if (typeof(data.data[0].unit_name)==="undefined" || data.data[0].unit_name == "") {
                document.getElementById("CHECKED_UNIT").value = "无记录";
            }else{
                document.getElementById("CHECKED_UNIT").value = data.data[0].unit_name;
            }

            if (typeof(data.data[0].pkid)==="undefined" || data.data[0].pkid == "") {
                document.getElementById("UNIT_NUMBER").value = "无记录";
            }else{
                document.getElementById("UNIT_NUMBER").value = data.data[0].pkid;
            }

            if (typeof(data.data[0].unit_address)==="undefined" || data.data[0].unit_address == "") {
                document.getElementById("ADDRESS").value = "无记录";
            }else{
                document.getElementById("ADDRESS").value = data.data[0].unit_address;
            }

            if (typeof(data.data[0].legal_person)==="undefined" || data.data[0].legal_person == "") {
                document.getElementById("REPRESENT_PEOPLE").value = "无记录";
            }else{
                document.getElementById("REPRESENT_PEOPLE").value = data.data[0].legal_person;
            }

            document.getElementById("REPRESENT_MOBILE"). value = "无记录";
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
                );
            }
        }else {
            alert(testJson[0]["failInfo"]);
        }
    });
});


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

//添加其他专家
function addCheckExpertSet() {

    var h = (document.getElementById("otherbox").children.length)/2;
    h = h+1;
    var id1 = "expert"+h;
    var id2 = "expertName"+h;
    var id3 = "EXPERT_UNIT" + h;
    var id4 = "EXPERT_NAME" + h;
    var id5 = "tbcontent" + h;
    $("#otherbox").append(
        /*"<div class='weui-cell' id="+id2+">" +
        "<div class='weui-cell__hd'><label class='weui-label'>专家名称"+h+"</label></div>" +
        "<div class='weui-cell__bd'>" +
        "<input class='weui-input' type='text' placeholder='请输入专家名称' id="+id4+" >" +
        "</div>" +
        "</div>" +
        "<div class='weui-cell' id="+id1+">" +
        "<div class='weui-cell__hd'><label class='weui-label'>专家单位"+h+"</label></div>" +
        "<div class='weui-cell__bd'>" +
        "<input class='weui-input' type='text' placeholder='请输入专家单位' id="+id3+" >" +
        "</div>" +
        "</div>"*/
        "            <div class=\"weui-cell\" id="+id2+">\n" +
        "                <div class=\"weui-cell__hd\"><label class=\"weui-label\" >专家名称"+h+"</label></div>\n" +
        "                <div class=\"weui-cell__bd\">\n" +
        "                    <div>\n" +
        "                        <table>\n" +
        "                            <tr>\n" +
        "                                <td>\n" +
        "                                    <input class=\"weui-input\" type=\"text\" placeholder=\"请输入专家名称\" id="+id4+"   onkeyup=\"txtchange1("+h+")\" onblur=\"lost1("+h+")\"/>\n" +
        "                                </td>\n" +
        "                            </tr>\n" +
        "                            <tr>\n" +
        "                                <td>\n" +
        "                                    <div id="+id5+" class=\"hidden\">\n" +
        "                                    </div>\n" +
        "                                </td>\n" +
        "                            </tr>\n" +
        "                        </table>\n" +
        "                    </div>\n" +
        "                    <!--<input class=\"weui-input\" type=\"text\" placeholder=\"请输入专家名称\"id=\"EXPERT_NAME1\"  onkeyup=\"txtChange()\">-->\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <div class=\"weui-cell\" id="+id1+">\n" +
        "                <div class=\"weui-cell__hd\"><label class=\"weui-label\" >专家单位"+h+"</label></div>\n" +
        "                <div class=\"weui-cell__bd\">\n" +
        "                    <input class=\"weui-input\" type=\"text\" placeholder=\"请输入专家单位\" id="+id3+" >\n" +
        "                </div>\n" +
        "            </div>"
    );

}
//删除其他专家
function deleteCheckExpertSet() {
    var h = (document.getElementById("otherbox").children.length)/2;
    var id1 = "expert"+h;
    var id2 = "expertName"+h;
    document.getElementById(id1).remove();
    document.getElementById(id2).remove();
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
    format += " ";
    format += nTime.getHours()<10?"0"+(nTime.getHours()):(nTime.getHours());
    format += ":";
    format += nTime.getMinutes()<10?"0"+(nTime.getMinutes()):(nTime.getMinutes());
    format += ":";
    format += nTime.getSeconds()<10?"0"+(nTime.getSeconds()):(nTime.getSeconds());
    return format;
}