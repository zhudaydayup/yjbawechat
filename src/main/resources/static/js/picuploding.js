//
// $(function () {
//     $('.js_file_chat_head').on('change', function (event) {
//         var files = event.target.files;
//         //允许上传的图片类型
//         var allowTypes = ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'];
//         // 1024KB，也就是 1MB
//         var maxSize = 5 * 1024 * 1024;
//         // 图片最大宽度
//         var maxWidth = 600;
//         // 最大上传图片数量
//         var maxCount = 1;
//         // 如果没有选中文件，直接返回
//         if (files.length === 0) {
//             return;
//         }
//
//         for (var i = 0, len = files.length; i < len; i++) {
//             var file = files[i];
//             var reader = new FileReader();
//
//             // 如果类型不在允许的类型范围内
//             if (allowTypes.indexOf(file.type) === -1) {
//                 alter("该类型不允许上传");
//                 continue;
//             }
//             if (file.size > maxSize) {
//                 alter("图片太大，不允许上传");
//                 continue;
//             }
//
//             if ($('.id_card_flag').length >= maxCount) {
//                 alter("最多只能上传" + maxCount + "张图片");
//                 return;
//             }
//             setTimeout( publish,1000);
//         }
//     });
// });
//
// function  publish() {
//     //alert("1");
//     var u = $("#file").val();
//     var chat_head_name = getFileName($("#file").val());
//     var formData = new FormData();
//     formData.append('file', $('#file')[0].files[0]);
//    // alert("2");
//     $.ajax({
//         type: "POST",
//         url: "../upLoderPic/uploadImg1?chat_head_name=" + chat_head_name + "&random=" + Math.floor(Math.random() * (100000 + 1)),
//         data: formData,
//         dataType: "text",
//         processData: false,
//         contentType: false,
//         async: true,
//         success: function (response) {
//             var obj=eval(response);
//             pics +=obj[0].url+"|";
//             $("#uploaderFiles").append(
//                 '<li  class="weui-uploader__file" style="background-image:url('+URLss+obj[0].url+');margin-left: 10px">'+
//                 '</li>'
//             );
//         }
//     })
// }
//
// $(function () {
//     $('.js_file_chat_head001').on('change', function (event) {
//         var files = event.target.files;
//         // 允许上传的图片类型
//         var allowTypes = ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'];
//         // 1024KB，也就是 1MB
//         var maxSize = 5 * 1024 * 1024;
//         // 图片最大宽度
//         var maxWidth = 600;
//         // 最大上传图片数量
//         var maxCount = 1;
//         // 如果没有选中文件，直接返回
//         if (files.length === 0) {
//             return;
//         }
//
//         for (var i = 0, len = files.length; i < len; i++) {
//             var file = files[i];
//             var reader = new FileReader();
//
//             // 如果类型不在允许的类型范围内
//             if (allowTypes.indexOf(file.type) === -1) {
//                 alter("该类型不允许上传");
//                 continue;
//             }
//             if (file.size > maxSize) {
//                 alter("图片太大，不允许上传");
//                 continue;
//             }
//
//             // if ($('.id_card_flag').length >= maxCount) {
//             //     alter("最多只能上传" + maxCount + "张图片");
//             //     return;
//             // }
//             setTimeout( publish001, 500);
//            // publish001();
//         }
//     });
// });
//
// function  publish001() {
//     var u = $("#file001").val();
//     var chat_head_name = getFileName($("#file001").val());
//     var formData = new FormData();
//     formData.append('file', $('#file001')[0].files[0]);
//     $.ajax({
//         type: "POST",
//         url: "../upLoderPic/uploadImg1?chat_head_name=" + chat_head_name + "&random=" + Math.floor(Math.random() * (100000 + 1)),
//         data: formData,
//         dataType: "text",
//         processData: false,
//         contentType: false,
//         async: true,
//         success: function (response) {
//             var obj=eval(response);
//             main_pic= obj[0].url;
//             document.getElementById("main_pic").src =URLss + main_pic;
//             document.getElementById("main_pic").style.height ="250px";
//             document.getElementById("main_pic").style.width ="350px";
//         }
//     })
// }
//
//
// var handler = function (e) { //禁止浏览器默认行为
//     e.preventDefault();
// };
//
// function getFileName(o){
//     var pos=o.lastIndexOf("\\");
//     return o.substring(pos+1);
// }


$(function () {

    $('#chat_head').on('change', function (event) {
        var files = event.target.files;
        // 允许上传的图片类型
        var allowTypes = ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'];
        // 1024KB，也就是 1MB
        var maxSize = 10 * 1024 * 1024;
        // 图片最大宽度
        var maxWidth = 600;
        // 最大上传图片数量
        var maxCount = 3;
        // 如果没有选中文件，直接返回
        if (files.length === 0) {
            return;
        }

        for (var i = 0, len = files.length; i < len; i++) {
            var file = files[i];
            var reader = new FileReader();

            // 如果类型不在允许的类型范围内
            if (allowTypes.indexOf(file.type) === -1) {
                alter("该类型不允许上传");
                continue;
            }

            if (file.size > maxSize) {
                alter("图片太大，不允许上传");
                continue;
            }

            if ($('.topimgNu').length >= maxCount) {
                alert("最多只能上传" + maxCount + "张图片");
                return;
            }

            reader.onload = function (e) {
                var img = new Image();
                img.onload = function () {
                    // 不要超出最大宽度
                    var w = Math.min(maxWidth, img.width);
                    // 高度按比例计算
                    var h = img.height * (w / img.width);
                    var canvas = document.createElement('canvas');
                    var ctx = canvas.getContext('2d');
                    // 设置 canvas 的宽度和高度
                    canvas.width = w;
                    canvas.height = h;
                    ctx.drawImage(img, 0, 0, w, h);
                    var base64 = canvas.toDataURL('image/png');

                    var $preview = $('<li class="weui-uploader__file weui-uploader__file_status chat_head_flag topimgNu" style="background-image:url(' + base64 + ')"></li>');//<div class="weui-uploader__file-content" id="topImgPro">0%</div>
                    $('#view_chat_head').append($preview);
                    var num = $('.topimgNu').length;
                    $('#topImgNum').text(num + '/' + maxCount);

                    var progress = 0;

                    UpladFiletop();
                };
                img.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

});




$(function () {

    $('#otherfile').on('change', function (event) {
        var files = event.target.files;

        // 允许上传的图片类型

        var allowTypes = ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'];
        // 1024KB，也就是 1MB
        var maxSize = 5 * 1024 * 1024;
        // 图片最大宽度
        var maxWidth = 600;
        // 最大上传图片数量
        var maxCount = 3;
        // 如果没有选中文件，直接返回
        if (files.length === 0) {
            return;
        }

        for (var i = 0, len = files.length; i < len; i++) {
            var file = files[i];
            var reader = new FileReader();

            // 如果类型不在允许的类型范围内
            if (allowTypes.indexOf(file.type) === -1) {
                alter("该类型不允许上传");
                continue;
            }

            if (file.size > maxSize) {
                alter("图片太大，不允许上传");
                continue;
            }

            if ($('.otherImg').length >= maxCount) {
                alert("最多只能上传" + maxCount + "张图片");
                return;
            }

            reader.onload = function (e) {
                var img = new Image();
                img.onload = function () {
                    // 不要超出最大宽度
                    var w = Math.min(maxWidth, img.width);
                    // 高度按比例计算
                    var h = img.height * (w / img.width);
                    var canvas = document.createElement('canvas');
                    var ctx = canvas.getContext('2d');
                    // 设置 canvas 的宽度和高度
                    canvas.width = w;
                    canvas.height = h;
                    ctx.drawImage(img, 0, 0, w, h);
                    var base64 = canvas.toDataURL('image/png');

                    var $preview1 = $('<li class="weui-uploader__file weui-uploader__file_status chat_head_flag otherImg" style="margin-left: 15px;background-image:url(' + base64 + ')"></li>');//<div class="weui-uploader__file-content otherPro" >0%</div>
                    $('#view_other_Img').append($preview1);
                    var num = $('.otherImg').length;
                    $('#otherImgNum').text(num + '/' + maxCount);


                    UpladFileother();
                };
                img.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }

    });

});

var handler = function (e) { //禁止浏览器默认行为
    e.preventDefault();
};

function getFileName1(o){
    var pos=o.lastIndexOf("\\");
    return o.substring(pos+1);
}
// function publish001() {
//     var u = $("#chat_head").val();
//     // alert(u);
//     var chat_head_name = getFileName1($("#chat_head").val());
//     // alert(chat_head_name);
//
//     var formData = new FormData();
//     formData.append('file', $('#chat_head')[0].files[0]);
//     $.ajax({
//         type: "POST",
//         url: "../upLoderPic/uploadImg1",
//         data: formData,
//         dataType: "text",
//         processData: false,
//         contentType: false,
//         timeout: 30000,
//         async: false,
//         success: function (response) {
//             var obj=eval(response);
//             main_pic= obj[0].url;
//             alert("上传成功");
//
//         },
//         error:function (XMLHttpRequest,textStatus,errorThrown){
//             alert("上传失败");
//             // alert(XMLHttpRequest.status);
//             // alert(XMLHttpRequest.readyState);
//             // alert(textStatus);
//
//
//
//
//         }
//     })
// }
// function  upotherpic() {
//     var u = $("#otherfile").val();
//     // alert(u);
//
//     var chat_head_name = getFileName1($("#otherfile").val());
//     // alert(chat_head_name);
//
//     var formData = new FormData();
//     formData.append('file', $('#otherfile')[0].files[0]);
//     $.ajax({
//         type: "POST",
//         url: "../upLoderPic/uploadImg1" ,
//         data: formData,
//         dataType: "text",
//         processData: false,
//         contentType: false,
//         timeout: 30000,
//         async: false,
//         success: function (response) {
//             var obj=eval(response);
//             pics +=obj[0].url+"|";
//             // $("#uploaderFiles").append(
//             //     '<li  class="weui-uploader__file" style="background-image:url('+URLss+obj[0].url+');margin-left: 10px">'+
//             //     '</li>'
//             // );
//             alert("上传成功");
//         },
//         error:function (XMLHttpRequest,textStatus,errorThrown){
//             alert("上传失败");
//             // alert(XMLHttpRequest.status);
//             // alert(XMLHttpRequest.readyState);
//             // alert(textStatus);
//
//         }
//     })
//
// }


