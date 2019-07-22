package yjbd.yjbawechat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller        //返回页面类型的时候用Controller
@RequestMapping("/page")
public class PageController {


    /*//登录页面
    @RequestMapping("/login")
    public String login(){return "/Index/loginView";}

    //注册页面
    @RequestMapping("/register")
    public String register(){return "/Index/registerView";}*/

    //一级九宫格页面
    @RequestMapping("/index")
    public String index(){
        return "/Index/indexView";
    }

    //二级页面
    @RequestMapping("/twoindex")
    public String twoindex(){return "/Index/secondView";}

    //三级页面
    @RequestMapping("/threeindex")
    public String threeindex(){return "/Index/thirdView";}

    //四级页面
    @RequestMapping("/fourindex")
    public String fourindex(){return "/Index/fourthView";}

    //五级页面
    @RequestMapping("/fiveindex")
    public String fiveindex(){return "/Index/fifthView";}

    //六级页面
    @RequestMapping("/sixindex")
    public String sixindex(){return "/Index/sixthView";}

    //执法检查页面
    @RequestMapping("/zfCheckView")
    public String zfCheckView(){return "/Index/zfCheckView";}
    /**
     * 增加四级模板检查项
     * @return
     */
    @RequestMapping("/increaseMouldIndex")
    public String increaseMouldIndex(){
        return "/Index/increaseMouldIndex";
    }

    /**
     * 增加五级模板检查项
     * @return
     */
    @RequestMapping("/increaseZfFiveMould")
    public String increaseZfFiveMould(){
        return "/Index/increaseZfFiveMould";
    }

    /**
     * 增加六级模板检查项
     * @return
     */
    @RequestMapping("/increaseZfSixMould")
    public String increaseZfSixMould(){
        return "/Index/increaseZfSixMould";
    }


    //五级执法检查页面
    @RequestMapping("/zfFiveCheckView")
    public String zfFiveCheckView(){return "/Index/zfFiveCheckView";}

    //六级执法检查页面
    @RequestMapping("/zfSixCheckView")
    public String zfSixCheckView(){return "/Index/zfSixCheckView";}



    /**
     *以下为企业内查模块页面
     * @return
     */
    //一级九宫格页面（企业内查首页）
    @RequestMapping("/zjindex")
    public String index2(){
        return "/Index/Zj/zjIndexView";
    }

    //二级九宫格页面（企业内查首页）
    @RequestMapping("/zjtwoindex")
    public String zjtwoindex(){return "/Index/Zj/zjsecondView";}

    //三级九宫格页面（企业内查首页）
    @RequestMapping("/zjthreeindex")
    public String zjthreeindex(){return "/Index/Zj/zjthirdView";}

    //四级页面（企业内查首页）
    @RequestMapping("/zjfourindex")
    public String zjfourindex(){return "/Index/Zj/zjfourthView";}

    //五级页面（企业内查首页）
    @RequestMapping("/zjfiveindex")
    public String zjfiveindex(){return "/Index/Zj/zjfifthView";}

    //六级页面（企业内查首页）
    @RequestMapping("/zjsixindex")
    public String zjsixindex(){return "/Index/Zj/zjsixthView";}

    //执法检查页面（企业内查首页）
    @RequestMapping("/zjCheckView")
    public String zjCheckView(){return "/Index/Zj/zjCheckView";}

    //五级执法检查页面（企业内查首页）
    @RequestMapping("/zjFiveCheckView")
    public String zjFiveCheckView(){return "/Index/Zj/zjFiveCheckView";}

    //六级执法检查页面（企业内查首页）
    @RequestMapping("/zjSixCheckView")
    public String zjSixCheckView(){return "/Index/Zj/zjSixCheckView";}


    @RequestMapping("/t")
    public String t(){return "/Index/Expert/test";}

    /**
     *以下为专家模块页面
     * @return
     */
    //一级九宫格页面（以下为专家模块页面）
    @RequestMapping("/exindex")
    public String index3(){
        return "/Index/Expert/expertIndexView";
    }

    //二级九宫格页面（以下为专家模块页面）
    @RequestMapping("/extwoindex")
    public String extwoindex(){return "/Index/Expert/expertsecondView";}

    //三级九宫格页面（以下为专家模块页面）
    @RequestMapping("/exthreeindex")
    public String exthreeindex(){return "/Index/Expert/expertthirdView";}

    //四级页面（以下为专家模块页面）
    @RequestMapping("/exfourindex")
    public String exfourindex(){return "/Index/Expert/expertfourthView";}

    //五级页面（以下为专家模块页面）
    @RequestMapping("/exfiveindex")
    public String exfiveindex(){return "/Index/Expert/expertfifthView";}

    //六级页面（以下为专家模块页面）
    @RequestMapping("/exsixindex")
    public String exsixindex(){return "/Index/Expert/expertsixthView";}

    //执法检查页面（以下为专家模块页面）
    @RequestMapping("/exCheckView")
    public String exCheckView(){return "/Index/Expert/expertCheckView";}

    //五级执法检查页面（以下为专家模块页面
    @RequestMapping("/exFiveCheckView")
    public String exFiveCheckView(){return "/Index/Expert/exFiveCheckView";}

    //六级执法检查页面（以下为专家模块页面
    @RequestMapping("/exSixCheckView")
    public String exSixCheckView(){return "/Index/Expert/exSixCheckView";}



    //pdf浏览页面
    @RequestMapping("/PdfView")
    public String PdfView(){return "/Index/PdfView";}

    //所有模板页面
    @RequestMapping("/allModel")
    public String allModel(){return "/Model/allModelView";}

}
