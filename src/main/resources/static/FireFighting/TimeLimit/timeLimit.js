$(function () {
    FastClick.attach(document.body);
});

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}
var social_credit_code=GetQueryString("social_credit_code");
var userId=GetQueryString("userId");
var sCode = GetQueryString("sCode");

var deviceId="";
var name="";
var policeNumber="";
var phone="";
var idCard="";
var department="";
var duty="";
var main_pic="";
var pics="";
var opStatue="exist";

//确认提交页面
function submitLaw() {
    // var options=GetOwnDefinOption();
    var obj = document.getElementsByName("checkbox1");
    var checke_problems="";
    // var obj = false;//标记判断是否选中一个
    for (var i = 0; i < obj.length; i++) {
        if(obj[i].checked){
            // alert("选中:"+obj[k].id);
            checke_problems+=obj[i].value+"|";
        }
    }

    var obj2 = document.getElementsByName("checkbox2");
    var time_id="";
    for (var i = 0; i < obj2.length; i++) {
            time_id+=obj2[i].value+"|";
    }

    var obj3 = document.getElementsByName("checkbox3");
    var execute_people="";
    // var obj = false;//标记判断是否选中一个
    for (var i = 0; i < obj3.length; i++) {
            // alert("选中:"+obj[k].id);
            execute_people+=obj3[i].value+"|";
    }

    var obj4 = document.getElementsByName("checkbox4");
    var card_number="";
    // var obj = false;//标记判断是否选中一个
    for (var i = 0; i < obj4.length; i++) {
            // alert("选中:"+obj[k].id);
            card_number+=obj4[i].value+"|";
    }

    var ajaxPram = {};
    ajaxPram["CHECKE_UNIT"] = $("#CHECKE_UNIT").val();
    ajaxPram["EXECUTE_PEOPLE"] = execute_people;
     ajaxPram["CARD_NUMBER"] = card_number;
    ajaxPram["TIME_IDS"] = time_id;
    ajaxPram["CHECKE_DETAIL"] = checke_problems;
    ajaxPram["RECORD_ID"] = recordId;
    // ajaxPram["CHECKED_END_TIME"] = "";


    if ($("#EXECUTE_PEOPLE2").val()!=""){
        ajaxPram["EXECUTE_PEOPLE2"] = $("#EXECUTE_PEOPLE2").val();
        ajaxPram["CARD_NUMBER2"] = $("#CARD_NUMBER2").val();
    }else{
        ajaxPram["EXECUTE_PEOPLE2"] ="";
        ajaxPram["CARD_NUMBER2"] = "";
    }
    var isboolture =  ajaxPram["CHECKE_UNIT"] != "" && ajaxPram["EXECUTE_PEOPLE"] != ""  && ajaxPram["CHECKE_DETAIL"] != ""&&  ajaxPram["RECORD_ID"] != ""&& ajaxPram["TIME_IDS"] !="" ;

    if (isboolture) {
            $.confirm("确认创建责令限期改正通知书!", function (){
            document.getElementById("mainBody").style.display = "none";
            document.getElementById("loadingmore").style.display = "";
            $.post("../timeLimit/createTimeLimitTable", ajaxPram , function (response) {
                var odj = response;
                if (odj['backFlag'] == "ok") {
                    alert("创建成功!");
                    var record_id = odj["recordId"];
                    location.href = "../page/enforcerSign?RecordId="+record_id+"&social_credit_code="+social_credit_code+"&userId="+userId+"&ID="+ID+"&sCode="+sCode;
                } else {
                    alert(odj['failInfo']);
                }
            }
            ,"json");
        }, function () {
            //取消操作
        });
    }
    else
        alert("请选择检查项或日期！");


}



function addlist(){
    var temp=                '<div class="weui-cell">'+
        '<div class="weui-cell-bd">'+
        '<a class="iconfont icon-shanchu " onclick="dellist(this)"style="color: red;">'+'</a>'+
        '</div>'+
        '<div class="weui-cell__bd">'+
        '<input  class="weui-input again" type="text" placeholder="   选项" style="margin-left: 20px">'+
        '</div>'+
        '</div>';
    $("#opts").append(temp)
}
function dellist(obj){
    $(obj).parent().parent().remove();
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

var SELECTED_MODEL=decodeURI(getUrlParam("model1"));
var NOT_SELECTED_MODEL=decodeURI(getUrlParam("model2"));
// alert(SELECTED_MODEL);