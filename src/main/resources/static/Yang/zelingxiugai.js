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
    var obj = document.getElementsByName("checkbox1");
    var checke_problem_id="";
    var time_id = [];


    for (var i = 0; i < obj.length; i++) {
        if(obj[i].checked){
            checke_problem_id+=obj[i].value+"|";
            time_id.push(document.getElementById("time"+i).value);
        }
    }

    var ajaxPram = {};
    ajaxPram["CHECKE_UNIT"] = $("#CHECKE_UNIT").val();
    ajaxPram["EXECUTE_PEOPLE"] = $("#EXECUTE_PEOPLE").val();
    ajaxPram["CARD_NUMBER"] = $("#CARD_NUMBER").val();
    ajaxPram["TIME_IDS"] = time_id.join("|");
    ajaxPram["CHECKE_PROBLEM_IDS"] = checke_problem_id;
    ajaxPram["RECORD_ID"] = zeLingId;

    ajaxPram["CHECKED_END_TIME"] = getFormat1();
    ajaxPram["ZF_ORDER_DEADLINE_AREA"] = $("#ZF_ORDER_DEADLINE_AREA").val();
    ajaxPram["ZF_ORDER_DEADLINE_RECORD"] = $("#ZF_ORDER_DEADLINE_RECORD").val();
    if (ajaxPram["ZF_ORDER_DEADLINE_AREA"] == "") {
        alert("请填写文书地区！");
        return false;
    }
    if (ajaxPram["ZF_ORDER_DEADLINE_RECORD"] == "") {
        alert("请填写文书编号！");
        return false;
    }

    if ($("#EXECUTE_PEOPLE2").val()!=""){
        ajaxPram["EXECUTE_PEOPLE2"] = $("#EXECUTE_PEOPLE2").val();
        ajaxPram["CARD_NUMBER2"] = $("#CARD_NUMBER2").val();
    }else{
        ajaxPram["EXECUTE_PEOPLE2"] ="";
        ajaxPram["CARD_NUMBER2"] = "";
    }


    var isboolture =  ajaxPram["ZF_ORDER_DEADLINE_AREA"] != "" && ajaxPram["ZF_ORDER_DEADLINE_RECORD"] != "" && ajaxPram["CHECKE_UNIT"] != "" &&ajaxPram["CHECKE_UNIT"] != "" &&ajaxPram["CHECKE_UNIT"] != "" && ajaxPram["EXECUTE_PEOPLE"] != "" && ajaxPram["CARD_NUMBER"] != "" && ajaxPram["CHECKE_PROBLEM_IDS"] != ""&&  ajaxPram["RECORD_ID"] != ""&& time_id!=""&&time_id!=null  ;

    if (isboolture) {
            $.confirm("确认创建检查记录!", function (){
            document.getElementById("mainBody").style.display = "none";
            document.getElementById("loadingmore").style.display = "";
            $.post("../PdfThree/createZeLingBiao", ajaxPram , function (response) {
                var odj = response;
                if (odj['backFlag'] == "ok") {
                    alert("创建成功!");
                    var record_id = odj["recordId"];
                    location.href = "../page/zhiFaRenQianMingView?RecordId="+record_id+"&userId="+userId+"&social_credit_code="+social_credit_code;
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
        alert("请选择违规项及整改时间!");


}



function addlist(){
    var temp=
        '<div class="weui-cell">'+
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
function getFormat1(){
    format = "";
    var nTime = new Date();
    format += nTime.getFullYear()+"-";
    format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
    format += "-";
    format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
    return format;
}