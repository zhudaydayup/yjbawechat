
$(function () {

    $('#uploaderInput').on('change', function (event) {
        var files = event.target.files;
        // 允许上传的图片类型
        var allowTypes = ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'];
        // 1024KB，也就是 1MB
        var maxSize = 10 * 1024 * 1024;
        // 图片最大宽度
        var maxWidth = 600;
        // 最大上传图片数量
        var maxCount = 9;
        // 如果没有选中文件，直接返回
        if (files.length === 0) {
            return;
        }

        for (var i = 0, len = files.length; i < len; i++) {
            var file = files[i];
            var reader = new FileReader();

            // 如果类型不在允许的类型范围内
            if (allowTypes.indexOf(file.type) === -1) {
                alert("该类型不允许上传");
                continue;
            }

            if (file.size > maxSize) {
                alert("图片太大，不允许上传");
                continue;
            }

            if ($('#topImgNum').length >= maxCount) {
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

                    /*var base64 = canvas.toDataURL('image/png');
                    var $preview = $('<li class="weui-uploader__file weui-uploader__file_status chat_head_flag topImgNum" style="background-image:url(' + base64 + ')"></li>');//<div class="weui-uploader__file-content" id="topImgPro">0%</div>
                    $('#uploaderFiles').append($preview);*/

                    var num = $('.topImgNum').length;
                    $('#topImgNum').text(num + '/' + maxCount);

                    UpladFiletop();
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





