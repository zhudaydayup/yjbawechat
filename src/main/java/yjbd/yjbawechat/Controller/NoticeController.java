package yjbd.yjbawechat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Totle:NoticeController
 * @ProjectName:yjbawechat
 * @author:社会码农
 * @data:2019/4/717:22
 */
@Controller
@RequestMapping("/Notice/")
public class NoticeController {
    @RequestMapping("NoticeIndex")
    public String index(){
        return "/Notice/NoticeIndex";
    }
    @RequestMapping("MsgSuccess")
    public String msgSuccess(){
        return "/Notice/MsgSuccess";
    }
    @RequestMapping("QRCode")
    public String twoWeiCode(){
        return "/Notice/QRCode";
    }
}
