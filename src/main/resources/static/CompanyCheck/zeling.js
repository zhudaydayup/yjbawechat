var RecordId=getUrlParam("RecordId");
var pics="";

/**
 * 通过ueditor将base64图片提交到服务器,依赖jquery.
 * var base64str = "data:image/png;base64,/9j/4AAQSkZJRg................ABAQAAAQABAAD/2wBDAAMCAgICOK//Z";
 * sumitImageFile(base64str).then(function (data) {
 *     console.log(data);
 * });
 */
/*function sumitImageFile(base64Codes) {

    var convertBase64UrlToBlob = function (urlData) {
        var arr = urlData.split(',');
        var mime = arr[0].match(/:(.*?);/)[1];
        var bytes = window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte
        //处理异常,将ascii码小于0的转换为大于0
        var ab = new ArrayBuffer(bytes.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < bytes.length; i++) {
            ia[i] = bytes.charCodeAt(i);
        }
        return new Blob([ab], { type: mime });
    };
    var getFileExt = function (urlData) {
        var arr = urlData.split(',');
        var mime = arr[0].match(/:(.*?);/)[1];
        return mime.replace("image/", "");

    };
    var deferred = $.Deferred();
    var form = document.forms[0];
    //var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
    var formData = new FormData();
    var fileExt = getFileExt(base64Codes);
    //convertBase64UrlToBlob函数是将base64编码转换为Blob
    formData.append("file", convertBase64UrlToBlob(base64Codes), "file_" + Date.parse(new Date()) + "." + fileExt);  //append函数的第一个参数是后台获取数据的参数名,和html标签的input的name属性功能相同
    //ajax 提交form
    $.ajax({
        url: "../upLoderPic/uploadImg1",
        type: "POST",
        data: formData,
        //dataType: "text",
        processData: false,         // 告诉jQuery不要去处理发送的数据
        contentType: false,        // 告诉jQuery不要去设置Content-Type请求头
        success: function (data) {
            if (data) {
                data = eval(data);
                if (data[0].error == "0") {
                    // deferred.resolve(window.location.origin + data.url);
                    alert("签名提交成功");
                    var signName=data[0].url;
                    alert(signName);
                    alert(EventId);
                    updateSighRecord(signName,EventId);
                }

                else {
                    //提交失败
                    alert("提交失败请重新提交");
                    deferred.reject("error");
                }
            }
            else {
                alert("出现错误请重新提交");

                deferred.reject("error");
            }
            //window.location.href = "${ctx}" + data;
        },
        xhr: function () {            //在jquery函数中直接使用ajax的XMLHttpRequest对象
            var xhr = new XMLHttpRequest();
            xhr.upload.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
                    var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                    console.log("正在提交." + percentComplete.toString() + '%');        //在控制台打印上传进度
                }
            }, false);
            return xhr;
        }
    });
    return deferred.promise();
}*/

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
  //更新签名参数
  function updateSighRecord(signName,EventId){
      $.ajax({
          type: "POST",
          url:  "../companyChecked/UpdateCheck",
          data: {

              LIABLE_SIGH: signName,
              RecordId: EventId
          },
          dataType: "json",
          async: true,
          success: function (data) {
              if (data.backFlag == "ok") {

                  window.location.href="../page/ReviewerSighView?RecordId="+EventId+"";

              }
              else {
                  alert("提交失败")
              }
          }
      })
}
