package yjbd.yjbawechat.Controller.FireFighting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/25 22:03
 */
@Controller
@RequestMapping("/page")
public class FireLoginController {
    /**
     * 九小场所责令限期整改页面
     */
    //责令限期检查页面
    @RequestMapping("/fireFightinrOtherPdf")
    public String fireFightinrOtherPdf(){return "FireFighting/timeLimit/fireFightinrOtherPdf";}
    @RequestMapping("/timeLimitView")
    public String timeLimitView(){return "FireFighting/timeLimit/timeLimitView";}
    //检查者页面
    @RequestMapping("/enforcerSign")
    public String enforcerSign(){return "FireFighting/timeLimit/enforcerSign";}
    //被检查者页面
    @RequestMapping("/inSpectorSigh")
    public String inSpectorSigh(){return "FireFighting/timeLimit/inSpectorSigh";}
    //生成执法文书页面
    @RequestMapping("/lawEnforcementDocument")
    public String lawEnforcementDocument(){return "FireFighting/timeLimit/lawEnforcementDocument";}
    //预览责令PDF
    @RequestMapping("/SuccessfulTimeLimit")
    public String SuccessfulTimeLimit(){return "FireFighting/timeLimit/SuccessfulTimeLimit";}

    @RequestMapping("/timeLimitPdfYuLan")
    public String timeLimitPdfYuLan(){return "FireFighting/timeLimit/timeLimitPdfYuLan";}
    /**
     * 九小场所现场整改页面
     */
    //现场整改检查页面
    @RequestMapping("/timeRectificationView")
    public String timeRectificationView(){return "FireFighting/SiteRectification/timeRectificationView";}
    //检查者签名页面
    @RequestMapping("/checkSignature")
    public String checkSignature(){return "FireFighting/SiteRectification/checkSignature";}
    //被检查者签名页面
    @RequestMapping("/procuratorialSignature")
    public String procuratorialSignature(){return "FireFighting/SiteRectification/procuratorialSignature";}
    //生成现场检查页面
    @RequestMapping("/examinationDocument")
    public String examinationDocument(){return "FireFighting/SiteRectification/examinationDocument";}
    //预览PDF页面
    @RequestMapping("/successfulSiteRectification")
    public String successfulSiteRectification(){return "FireFighting/SiteRectification/successfulSiteRectification";}
    //九小场所重大隐患上报
    @RequestMapping("/fireFightingHiddenDanger")
    public String fireFightingHiddenDanger(){return "FireFighting/fireFightingHiddenDanger";}
    /**
     * 九小场所复查页面
     */
    //现场复查检查页面
    @RequestMapping("/fireFightingRectification")
    public String fireFightingRectification(){return "FireFighting/rectificate/fireFightingRectification";}
    //检查者签名页面
    @RequestMapping("/reexamineExpertSign")
    public String reexamineExpertSign(){return "FireFighting/rectificate/reexamineExpertSign";}
    //被检查者签名页面
    @RequestMapping("/reexamineInspectedSigh")
    public String reexamineInspectedSigh(){return "FireFighting/rectificate/reexamineInspectedSigh";}
    //复查
    @RequestMapping("/reexamineDocument")
    public String reexamineDocument(){return "FireFighting/rectificate/reexamineDocument";}
    //预览PDF页面
    @RequestMapping("/successfulReexamine")
    public String successfulReexamine(){return "FireFighting/rectificate/successfulReexamine";}




}
