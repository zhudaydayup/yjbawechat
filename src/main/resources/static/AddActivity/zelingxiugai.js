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
// var department =decodeURI(GetQueryString("department"));  //get传中文  //解码
// $(function () {
//     Session_err_jump();   //人员信息验证//非法侵入
// })

/*// 获取用户信息
function userInfoloding() {
    $.post("../signLogin/getInfoFromSession", null , function (response) {
        var odj = eval(response);
        if (odj[0]['msg'] == "ok") {
            userId=odj[0]['userId'];
            deviceId=odj[0]['deviceId'];
            name=odj[0]['name'];
            policeNumber=odj[0]['policeNumber'];
            phone=odj[0]['phone'];
            idCard=odj[0]['idCard'];
            department=odj[0]['department'];
            duty=odj[0]['duty'];
            $("#MANAGER_PHONE").val(phone);
            $("#MANAGER_NAME").val(name);
        } else {
            alert(odj[0]['failinfo']);
        }
    })
}*/
// //获取自定义选项
// function GetOwnDefinOption(){
//     var result="";
//     $(".again").each(function(){//循环遍历选项，有一个为空则为空
//         if($(this).val()==""){
//             opStatue="";
//             alert("自定义名称不能为空");
//         }
//         result=result+ $(this).val().replace(/,/g,"")+',';
//     });
//     // alert(result);
//     return result;
//
// }
//确认提交页面
function submitLaw() {
    // var options=GetOwnDefinOption();
    var ajaxPram = {};
    ajaxPram["CHECKE_UNIT"] = $("#CHECKE_UNIT").val();
    // ajaxPram["ADDRESS"] = $("#ADDRESS").val();
    // ajaxPram["REPRESENT_PEOPLE"] = $("#REPRESENT_PEOPLE").val();
    // ajaxPram["REPRESENT_JOB"] = $("#REPRESENT_JOB").val();
    // ajaxPram["MOBILE"] = $("#MOBILE").val();
    // ajaxPram["CHECKE_PROBLEM"] = $("#CHECKE_PROBLEM").val();
    // ajaxPram["CHECKED_START_TIME"] = $("#CHECKED_START_TIME").val();
    // ajaxPram["CHECKED_END_TIME"] = $("#CHECKED_END_TIME").val();
    // ajaxPram["EXECUTE_UNIT"] = $("#EXECUTE_UNIT").val();
    ajaxPram["CHECKED_END_TIME"] = $("#CHECKED_END_TIME").val();
    ajaxPram["EXECUTE_PEOPLE"] = $("#EXECUTE_PEOPLE").val();
    ajaxPram["CARD_NUMBER"] = $("#CARD_NUMBER").val();
    ajaxPram["CHECKE_PROBLEM"] = pinjke;
    // ajaxPram["LOCATION_IMG"] = main_pic;
    // ajaxPram["OTHER_IMG"] = pics;
    // ajaxPram["SELECTED_MODEL"]=SELECTED_MODEL;
    // ajaxPram["NOT_SELECTED_MODEL"]=NOT_SELECTED_MODEL;
    //
    // alert(pics);
    // alert(main_pic);
    // if (ajaxPram["LOCATION_IMG"] == "" ) {
    //     if(confirm("确认不上传图片!")){
    //         ajaxPram["LOCATION_IMG"] = "/withoutImg.png"
    //     }
    //     else{
    //        return false;
    //     }
    //     // ajaxPram["MIAN_PIC"] = ""
    //
    // }
    // if (ajaxPram["OTHER_IMG"] == ""){
    //     if(confirm("确认不上传其他图片!")){
    //         ajaxPram["OTHER_PICS"] = "/withoutImg.png"
    //
    //     }
    //     else{
    //         return false;
    //     }
    // }
    var isboolture =  ajaxPram["CHECKE_UNIT"] != "" && ajaxPram["EXECUTE_PEOPLE"] != "" && ajaxPram["CARD_NUMBER"] != "" &&  ajaxPram["CHECKE_PROBLEM"] != ""&&  ajaxPram["CHECKED_END_TIME"] != "" ;

    if (isboolture) {
            $.confirm("确认创建检查记录!", function (){
            document.getElementById("mainBody").style.display = "none";
            document.getElementById("loadingmore").style.display = "";
            $.post("../Pdf/createZeLingBiao", ajaxPram , function (response) {
                var odj = response;
                if (odj['backFlag'] == "ok") {
                    alert("创建成功!");
                    var seLingId = odj["EventId"];
                    location.href = "../page/zhiFaRenQianMingView?RecordId="+seLingId+"";
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
    if( $("#switchtp").prop("checked") == true){
        document.getElementById("topbox").style.display ="";
    }else{
        document.getElementById("topbox").style.display ="none";
    }
}
//是否上传其它图片
// function uploadop_Set() {
//     if( $("#switchop").prop("checked") == true){
//         document.getElementById("otherbox").style.display ="";
//     }else{
//         document.getElementById("otherbox").style.display ="none";
//     }
// }
// //错误页跳转
// function Session_err_jump() {
//     $.post("../signLogin/Session_err_jump",null, function (response) {
//         var jsonRecords = eval(response);
//         // alert(jsonRecords[0]['msg']);
//         if(jsonRecords[0]['msg']=="err"){
//             // location.href="../Login/ErrView";
//         }else if(jsonRecords[0]['msg']=="ok"){
//             userInfoloding();     //获取用户信息
//         }
//     });
// }

function fanhui() {
    window.history.back();
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