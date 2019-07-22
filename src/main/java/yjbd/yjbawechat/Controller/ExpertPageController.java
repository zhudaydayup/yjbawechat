package yjbd.yjbawechat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yjbd.yjbawechat.Service.ExEnforceService;
import yjbd.yjbawechat.Service.expert.ExpertService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/exLawDictionary")
public class ExpertPageController {
    @Autowired
    private ExpertService expertService;
    @Autowired
    private ExEnforceService exenforceService;

    @RequestMapping("/Index")
    public String index(){
        return "/exRecord/ExecuteLawIndex";
    }

    @RequestMapping("/MsgSuccess")
    public String msgSuccess(){
        return "/exRecord/MsgSuccess";
    }

    @RequestMapping("/TwoWeiCode")
    public String twoWeiCode(){
        return "/exRecord/TwoWeiCode";
    }
    @RequestMapping("/ExpertSign")
    public String expertSign(){
        return "/exRecord/ExpertSign";
    }
    @RequestMapping("/WitnessSign")
    public String witnessSign(){
        return "/exRecord/WitnessSign";
    }

    /**
     * 专家重大隐患页面
     */
    @RequestMapping("/hiddenDangerIndex")
    public String hiddenDangerIndex(){
        return "/ExHiddenDanger/hiddenDangerIndex";
    }
    @RequestMapping("/expertSignDanger")
    public String expertSignDanger(){
        return "/ExHiddenDanger/expertSignDanger";
    }
    @RequestMapping("/inspectedUnitSignDanger")
    public String inspectedUnitSignDanger(){
        return "/ExHiddenDanger/inspectedUnitSignDanger";
    }
    @RequestMapping("/exHiddenDangerDocument")
    public String exHiddenDangerDocument(){
        return "/ExHiddenDanger/exHiddenDangerDocument";
    }
    @RequestMapping("/successfulExHiddenDanger")
    public String successfulExHiddenDanger(){
        return "/ExHiddenDanger/successfulExHiddenDanger";
    }
    /**
     * 一般隐患现场整改
     */
    @RequestMapping("/AlterHiddenDanger")
    public String alterHiddenDanger(){
        return "/exRecord/zhu/AlterHiddenDanger";
    }
    @RequestMapping("/GeneralHiddenDanger")
    public String generalHiddenDanger(HttpServletRequest request, Map<String,Object> map){
        String RECORD_ID = request.getParameter("RecordId");
        String CHECK_STATE = "不合格";
        List<Map> list = expertService.getExpertInfo(RECORD_ID);
        String CHECKED_START_TIME = list.get(0).get("CHECKED_START_TIME").toString();
        //创建一般隐患现场整改记录
        expertService.insertRectification(RECORD_ID,CHECKED_START_TIME);
        List<Map> checkItem = expertService.getExpertCheckInfo(RECORD_ID,CHECK_STATE);
        map.put("CHECKED_UNIT",list.get(0).get("CHECKED_UNIT").toString());
        map.put("CHECKED_START_TIME",CHECKED_START_TIME);
        map.put("checkItem",checkItem);
        return "/exRecord/zhu/GeneralHiddenDanger";
    }
    /**
     * 获取缓存一般隐患现场整改表格信息
     */
    @RequestMapping(value = "/getTemporaryGeneralHiddenDanger")
    @ResponseBody
    public String getTemporaryGeneralHiddenDanger(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = expertService.getTemporaryGeneralHiddenDanger(RecordId);
        return responseText;
    }

    /**
     * 缓存更新一般隐患页面
     */
    @ResponseBody
    @RequestMapping("/temporaryGeneralHiddenDanger")
    public String temporaryGeneralHiddenDanger(HttpServletRequest request){
        String responseText="";
        String RECORD_ID = request.getParameter("RECORD_ID");
        String URL = request.getParameter ( "URL" );
        String PROCESS_DECISION = request.getParameter ( "PROCESS_DECISION" );
        responseText =  expertService.temporaryGeneralHiddenDanger(PROCESS_DECISION,URL,RECORD_ID);
        return responseText;
    }


    @RequestMapping("/expertSignPage")
    public String expertSignPage(HttpServletRequest request){
        String dealView = request.getParameter("text");
        String RecordId = request.getParameter("RecordId");
        expertService.updateDealView(dealView,RecordId);
        return "/exRecord/zhu/expertSignPage";
    }
    @RequestMapping("/checkedSignPage")
    public String checkedSignPage(){
        return "/exRecord/zhu/checkedSignPage";
    }
    @RequestMapping("/previewIndex")
    public String previewIndex(){
        return "/exRecord/zhu/previewIndex";
    }
    @RequestMapping("/pdfView")
    public String pdfView(){
        return "/exRecord/zhu/pdfView";
    }
    @RequestMapping("/qrView")
    public String qrView(){
        return "/exRecord/zhu/qrView";
    }
    @RequestMapping("/ExMsgSuccess")
    public String getExMsgSuccess(){
        return "/exRecord/zhu/ExMsgSuccess";
    }




    /**
     * 隐患展示页面
     */
    @RequestMapping("/ShowDanger")
    public String ShowDanger(){
        return "/exRecord/ShowDanger";
    }

    @RequestMapping("/otherPdtPage")
    public String otherPdtPage(){
        return "/exRecord/otherPdtPage";
    }

    /**
     *现场处理措施决定书页面
     */
    @RequestMapping("/PunishMeasure")
    public String PunishMeasure(){
        return "/exRecord/PunishMeasure";
    }

    /**
     * 现场处理检查人签名页面、被检查单位负责人签名页面、见证人签名页面
     */
    @RequestMapping("/Signature0")
    public String Signature0(){
        return "/exRecord/Signature";
    }

    @RequestMapping("/Signature1")
    public String Signature1(){
        return "/exRecord/Signature1";
    }

    @RequestMapping("/Signature2")
    public String Signature2(){
        return "/exRecord/Signature2";
    }

    @RequestMapping("/OperatingSuccess")
    public String OperatingSuccess(){
        return "/exRecord/OperatingSuccess";
    }

    @RequestMapping("/Result")
    public String Result(){
        return "/exRecord/Result";
    }


    /**
     *责令限期整改页面
     */
    @RequestMapping("/zeLingXinQiView")
    public String zeLingXinQiView(){
        return "/exRecord/zeLingXinQiView";
    }

    /**
     * 责令限期整改检查人签名页面、被检查单位负责人签名页面、见证人签名页面
     */
    @RequestMapping("/Signature3")
    public String Signature3(){
        return "/exRecord/Signature3";
    }

    @RequestMapping("/Signature4")
    public String Signature4(){
        return "/exRecord/Signature4";
    }

    @RequestMapping("/Signature5")
    public String Signature5(){
        return "/exRecord/Signature5";
    }

    @RequestMapping("/zhiFaWenShuView")
    public String zhiFaWenShuView(){
        return "/exRecord/zhiFaWenShuView";
    }

    @RequestMapping("/successView")
    public String successView(){
        return "/exRecord/successView";
    }


    /**
     *执法处罚页面
     */
    @RequestMapping("/ZhiFaChuFa")
    public String ZhiFaChuFa(Map<String,Object> map, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        List<Map> mapList = exenforceService.getDangerRecord2(RecordId);
        map.put("dangerList",mapList.get(0).get("CHECKE_DETAIL").toString().split("\\|"));
        return "/exRecord/ZhiFaChuFa";
    }













}
