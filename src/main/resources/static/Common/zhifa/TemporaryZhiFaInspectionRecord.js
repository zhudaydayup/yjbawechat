function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "../Record/gettemporaryUpdateIdRecord",
        dataType: "json",
        data: { RecordId:RecordId},
        async: false,
        success: function (data) {

            if (data.RecordInfo[0].CHECKED_LOCATION!=null){
                var CHECKED_LOCATION = data.RecordInfo[0].CHECKED_LOCATION;//6检查场所
                $("#CHECKED_LOCATION").val(CHECKED_LOCATION);
            }
            if (data.RecordInfo[0].CHECKED_REPRESENT_PEOPLE!=null){
                var CHECKED_REPRESENT_PEOPLE = data.RecordInfo[0].CHECKED_REPRESENT_PEOPLE;//被检查单位现场负责人
                $("#CHECKED_REPRESENT_PEOPLE").val(CHECKED_REPRESENT_PEOPLE);
            }
            if (data.RecordInfo[0].CHECKED_REPRESENT_NUMBER!=null){
                var CHECKED_REPRESENT_NUMBER = data.RecordInfo[0].CHECKED_REPRESENT_NUMBER;//身份证
                $("#CHECKED_REPRESENT_NUMBER").val(CHECKED_REPRESENT_NUMBER);
            }
            if (data.RecordInfo[0].CHECKED_REPRESENT_DUTY!=null){
                var CHECKED_REPRESENT_DUTY = data.RecordInfo[0].CHECKED_REPRESENT_DUTY;//职务
                $("#CHECKED_REPRESENT_DUTY").val(CHECKED_REPRESENT_DUTY);
            }
            if (data.RecordInfo[0].CHECKED_REPRESENT_MOBILE!=null){
                var CHECKED_REPRESENT_MOBILE = data.RecordInfo[0].CHECKED_REPRESENT_MOBILE;//电话号码
                $("#CHECKED_REPRESENT_MOBILE").val(CHECKED_REPRESENT_MOBILE);
            }
            if (data.RecordInfo[0].WITNESS_PEOPLE!=null){
                var WITNESS_PEOPLE = data.RecordInfo[0].WITNESS_PEOPLE;//见证人
                $("#WITNESS_PEOPLE").val(WITNESS_PEOPLE);
            }
            if (data.RecordInfo[0].WITNESS_NUMBER!=null){
                var WITNESS_NUMBER = data.RecordInfo[0].WITNESS_NUMBER;//见证人证件号
                $("#WITNESS_NUMBER").val(WITNESS_NUMBER);
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
    ajaxPram["RECORD_ID"] = RecordId;

    var NowDate = getFormat();


    ajaxPram["CHECKED_START_TIME"] = CHECKED_START_TIME;//7检查开始时间
    ajaxPram["CHECKED_END_TIME"] = NowDate;//8检查结束时间
    ajaxPram["CHECKED_UNIT"] = $("#CHECKED_UNIT").val();//2被检查单位
    ajaxPram["UNIT_NUMBER"] = $("#UNIT_NUMBER").val();//社会信用代码
    ajaxPram["ADDRESS"] = $("#ADDRESS").val();//3被检单位地址
    ajaxPram["REPRESENT_PEOPLE"] = $("#REPRESENT_PEOPLE").val();//4法定代表人
    ajaxPram["REPRESENT_MOBILE"] = $("#REPRESENT_MOBILE").val();//5法定代表人手机号码
    ajaxPram["CHECKED_LOCATION"] = $("#CHECKED_LOCATION").val();//6检查场所
    ajaxPram["CHECKED_REPRESENT_PEOPLE"] = $("#CHECKED_REPRESENT_PEOPLE").val();//24被检查单位现场负责人
    ajaxPram["CHECKED_REPRESENT_DUTY"] = $("#CHECKED_REPRESENT_DUTY").val();    //27现场负责人职务
    ajaxPram["CHECKED_REPRESENT_NUMBER"] = $("#CHECKED_REPRESENT_NUMBER").val();//26现场负责人证件
    ajaxPram["CHECKED_REPRESENT_MOBILE"] = $("#CHECKED_REPRESENT_MOBILE").val();//28现场负责人电话

    ajaxPram["CHECKED_SEX"] = $("#CHECKED_SEX").val();
    ajaxPram["CHECK_UNIT1"] = $("#CHECK_UNIT1").val();//9检查单位1
    ajaxPram["CHECK_PEOPLE1"] = $("#CHECK_PEOPLE1").val();//10检查人员1
    ajaxPram["CARD_NUMBER1"] = $("#CARD_NUMBER1").val();//11证件号1
    ajaxPram["AREA"] = $("#area").val();//检查方

    if ($("#othercheck").css("display")=="none") {
        ajaxPram["CHECK_UNIT2"] = "";//9检查单位2
        ajaxPram["CHECK_PEOPLE2"] = "";//10检查人员2
        ajaxPram["CARD_NUMBER2"] = "";//11证件号2
    }else{
        ajaxPram["CHECK_UNIT2"] = $("#CHECK_UNIT2").val();//9检查单位2
        ajaxPram["CHECK_PEOPLE2"] = $("#CHECK_PEOPLE2").val();//10检查人员2
        ajaxPram["CARD_NUMBER2"] = $("#CARD_NUMBER2").val();//11证件号2
    }
    ajaxPram["WITNESS_SEX"] = $("input[name='WITNESS_SEX']:checked").val();//性别
    ajaxPram["WITNESS_PEOPLE"] = $("#WITNESS_PEOPLE").val();//见证者名称
    ajaxPram["WITNESS_NUMBER"] = $("#WITNESS_NUMBER").val();//见证者身份证


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


    $.post("../Record/temporaryUpdateIdRecord", ajaxPram , function (response) {
        var odj = response;
        if (odj['backFlag'] == "ok") {
            var ajaxPram1 = {};
            ajaxPram1["URL"] = url;
            ajaxPram1["USER_ID"] = userId;
            ajaxPram1["SOCIAL_CREDIT_CODE"] = social_credit_code;
            ajaxPram1["RECORD_ID"] = RecordId;
            $.post("../data/putZhiFaTemporaryDate", ajaxPram1 , function (response) {
                var odj = response;
                alert(odj['msg']);
            },"json");
        } else {
            alert(odj['failInfo']);
        }
    },"json");
}