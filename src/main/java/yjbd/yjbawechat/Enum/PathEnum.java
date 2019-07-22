package yjbd.yjbawechat.Enum;


/**
 * Demo class
 *
 * @author 努力的小朱
 * @date 2019/4/26
 */
public enum PathEnum {
    /**
     * pdf文件存放地址
     */
    PDF_PATH("pdf文件存放地址","D:/zfbdzl/pdf"),
    /**
     * 图片保存地址
     */
    IMAGES_PATH("图片保存地址","D:/zfbdzl/images"),
    /**
     * 视频保存地址
     */
    VIDEO_PATH("视频保存地址","D:/zfbdzl/video"),
    /**
     * 二维码保存地址
     */
    QRCORD_PATH("二维码保存地址","D:/zfbdzl/qrcode");

    private String msg;
    private String path;

    PathEnum(String msg, String path) {
        this.msg = msg;
        this.path = path;
    }

    public String getMsg() {
        return msg;
    }

    public String getPath() {
        return path;
    }
}
