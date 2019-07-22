package yjbd.yjbawechat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author: 随心的小新
 * @Date: 2019/4/8 6:51
 */
@Controller        //返回页面类型的时候用Controller
@RequestMapping("/page")
public class LoginController {
    /**
     * 生成责令限期整改
     * @return
     */
    //生成责令限期整改指令书预看模板
    @RequestMapping("/zeLingXinQiView")
    public String zeLingXinQiView(){return "/ThreePdf/zeLingXinQiView";}

    //执法人员签字
    @RequestMapping("/zhiFaRenQianMingView")
    public String zhiFaRenQianMingView(){return "/ThreePdf/zhiFaRenQianMingView";}

    //执法人员2签字
    @RequestMapping("/zhiFaRenTwoQianMingView")
    public String zhiFaRenTwoQianMingView(){return "/ThreePdf/zhiFaRenTwoQianMingView";}

    //被检查单位负责人签名
    @RequestMapping("/beiJianChaRenQianMingView")
    public String beiJianChaRenQianMingView(){return "/ThreePdf/beiJianChaRenQianMingView";}

    //见证人签名
    @RequestMapping("/jianZhengRenQianMingView")
    public String jianZhengRenQianMingView(){return "/ThreePdf/jianZhengRenQianMingView";}

    //生成执法文书页面
    @RequestMapping("/zhiFaWenShuView")
    public String zhiFaWenShuView(){return "/ThreePdf/zhiFaWenShuView";}

    //成功页面
    @RequestMapping("/successView")
    public String successView(){return "/ThreePdf/successView";}

    /**
     * 生成整改通知书
     */
    @RequestMapping("/CompanyCheckView")
    public String CompanyCheckView(){return "/OwnCheckView/CompanyCheckView";}

    @RequestMapping("/LiableSighView")
    public String LiableSighView(){return "/OwnCheckView/LiableSighView";}

    @RequestMapping("/ReviewerSighView")
    public String ReviewerSighView(){return "/OwnCheckView/ReviewerSighView";}

    @RequestMapping("/CheckeWenShuView")
    public String CheckeWenShuView(){return "/OwnCheckView/CheckeWenShuView";}

    @RequestMapping("/CompanyWenShuView")
    public String CompanyWenShuView(){return "/OwnCheckView/CompanyWenShuView";}

    /**
     * 生成违章告知书
     */
    @RequestMapping("/ViolationNoticeView")
    public String ViolationNoticeView(){return "/ViolationNotice/ViolationNoticeView";}

    @RequestMapping("/CheckedSighView")
    public String CheckedSighView(){return "/ViolationNotice/CheckedSighView";}

    @RequestMapping("/CheckedPeopleSigh")
    public String CheckedPeopleSigh(){return "/ViolationNotice/CheckedPeopleSigh";}

    @RequestMapping("/SighnatureSignView")
    public String SighnatureSignView(){return "/ViolationNotice/SighnatureSignView";}

    @RequestMapping("/NoticeWenShuView")
    public String NoticeWenShuView(){return "/ViolationNotice/NoticeWenShuView";}

    @RequestMapping("/successfulNoticeView")
    public String successfulNoticeView(){return "/ViolationNotice/successfulNoticeView";}
    @RequestMapping("/ZjView")
    public String ZjView(){return "/ViolationNotice/ZjView";}



}
