



function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return (r[2]); return null;
}
var one_id=GetQueryString("one_id");

//初始加载提交材料
window.onload = function () {
    $.post("../Index/checkAllTemp",null, function (res)
    {
        var title=decodeURI(GetQueryString("title"));//decodeURI()将中文转换成字符串
        document.getElementById("titleName").innerHTML = title;
        var testJson = eval(res);
        if (testJson[0]["msg"]!='fail')
        {
            for (var i = 0; i < testJson.length; i++) {
                $("#List").append(
                    "<tr style='border: 1px solid;'>"+
                    "<td style='border: 1px solid;'>"+
                    "<li class='elemb'>"+
                    "<div>"+
                    "<p>"+"模板主题："+"</p>" +
                    "</div>" +
                    "<div style='width: 190px'>" +
                    "<p>" + testJson[i]["FOUR_NAME"] + "</p>" +
                    "</div>" +
                    "<button type='button' class='btn btn-primary' go='true' four_id='" + testJson[i]["FOUR_ID"] + "'  title='" + testJson[i]["FOUR_NAME"] + "'>查看模板</button>" +
                    "<li/>"+
                    "</td style='border: 1px solid;'> "+
                    "<tr style='border: 1px solid;'>"
                );
            }
            // $("button[go]").click(function () {
            //     location.href = "../page/fiveindex?one_id=" + one_id+"&two_id="+two_id+"&three_id="+three_id+"&four_id="+$(this).attr("four_id")+"&pri_title="+pri_title+"&title="+$(this).attr("title");
            // });

        }else {
            alert(testJson[0]["failInfo"]);
        }

    });

};



