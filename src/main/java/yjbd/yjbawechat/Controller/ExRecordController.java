package yjbd.yjbawechat.Controller;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Model.ExpertPunishEntity;
import yjbd.yjbawechat.Model.exEnforceModel;
import yjbd.yjbawechat.Service.ExEnforceService;
import yjbd.yjbawechat.Service.PdfThreeService;
import yjbd.yjbawechat.Service.ma.PunishMeasureService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/exRecord")
public class ExRecordController {
    @Autowired
    private ExEnforceService exenforceService;

    @Autowired
    PunishMeasureService punishMeasureService;

    @Autowired
    private PdfThreeService pdfThreeService;


    @RequestMapping("/UpdateIdRecord")
    @ResponseBody
    public String UpdateIdRecord(HttpServletRequest request,exEnforceModel exEnforceModel){
        String responseText="";
        responseText = exenforceService.UpdateIdRecord(exEnforceModel);
        return responseText;
    }

    /**
     * 更新检查记录页面缓存
     * @param request
     * @return
     */
    @RequestMapping("/temporaryExUpdateIdRecord")
    @ResponseBody
    public String temporaryExUpdateIdRecord(HttpServletRequest request,exEnforceModel exEnforceModel){
        String responseText="";
        responseText = exenforceService.temporaryExUpdateIdRecord(exEnforceModel);
        return responseText;
    }

    /**
     * 获取更新检查记录页面缓存
     * @param
     * @return
     */
    @RequestMapping(value = "/getTemporaryExUpdateIdRecord")
    public String getTemporaryExUpdateIdRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = exenforceService.getTemporaryExUpdateIdRecord(RecordId);
        return responseText;
    }

    //UpdateExpertSign
    @RequestMapping(value = "/UpdateExpertSign")
    public String UpdateExpertSign(HttpServletRequest request) {
        String responseText="";
        String signName=request.getParameter("signName");
        String EventId=request.getParameter("EventId");
        String signTime=request.getParameter("signTime");
        responseText = exenforceService.UpdateExpertSign(signName,EventId,signTime);
        return responseText;
    }



    @RequestMapping(value = "/UpdateWitnessSign")
    public String UpdateWitnessSign(HttpServletRequest request) {
        String responseText="";
        String signName=request.getParameter("signName");
        String EventId=request.getParameter("EventId");
        String signTime=request.getParameter("signTime");
        responseText = exenforceService.UpdateWitnessSign(signName,EventId,signTime);
        return responseText;
    }

    @RequestMapping(value = "/GetTime")
    public String GetTime(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = exenforceService.GetTime(RecordId);
        return responseText;
    }

    @RequestMapping(value = "/GetIdRecord")
    public String getIdRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = exenforceService.getIdRecord(RecordId);
        return responseText;
    }

    @RequestMapping(value = "/GetIdCheckMsgs")
    public String getIdCheckMsgs(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = exenforceService.getIdCheckMsgs(RecordId);
        return responseText;
    }



    /**
     * 获取重大隐患记录信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/GetDangerRecord")
    public String GetDangerRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = exenforceService.getDangerRecord(RecordId);
        return responseText;
    }


    /**
     * 记录专家现场处理措施信息
     * @param request
     */
    @RequestMapping(value = "/setExPunishMeasureInfo")
    @ResponseBody
    public void setExPunishMeasureInfo(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String PROCESS_DECISION=request.getParameter("PROCESS_DECISION");
        String CHECKE_DETAIL=request.getParameter("CHECKE_DETAIL");
        String CHECKED_UNIT=request.getParameter("CHECKED_UNIT");
        String CHECKED_START_TIME=request.getParameter("CHECKED_START_TIME");
//        String ON_SCENE_AREA=request.getParameter ( "ON_SCENE_AREA" );
//        String ON_SCENE_RECORD=request.getParameter ( "ON_SCENE_RECORD" );
        punishMeasureService.setExPunishMeasureInfo(RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKED_UNIT,CHECKED_START_TIME);
    }
    /**
     * 缓存记录专家现场处理措施信息
     */
    @RequestMapping(value = "/temporarySetExPunishMeasureInfo")
    @ResponseBody
    public String temporarySetExPunishMeasureInfo(HttpServletRequest request){

        String responseText="";
        String PROCESS_DECISION=request.getParameter("PROCESS_DECISION");
        String CHECKE_DETAIL=request.getParameter("CHECKE_DETAIL");
        String CHECKED_UNIT=request.getParameter("CHECKED_UNIT");
        String CHECKED_START_TIME=request.getParameter("CHECKED_START_TIME");
        String RECORD_ID=request.getParameter("RECORD_ID");
        String URL=request.getParameter ( "URL" );
        responseText= punishMeasureService.temporarySetExPunishMeasureInfo(PROCESS_DECISION,CHECKE_DETAIL,CHECKED_UNIT,CHECKED_START_TIME,URL,RECORD_ID);
        return responseText;
    }

    /**
     *获取缓存记录专家现场处理措施信息
     */
    @RequestMapping(value = "/getTemporarySetExPunishMeasureInfo")
    @ResponseBody
    public String getTemporarySetExPunishMeasureInfo(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = punishMeasureService.getTemporarySetExPunishMeasureInfo(RecordId);
        return responseText;
    }


    /**
     * 更新现场处理措施的检查人签名、被检查单位负责人签名、见证人签名
     * @param request
     */
    @RequestMapping(value = "/UpdatePunishSign")
    @ResponseBody
    public void UpdatePunishSign(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String SIGNATURE_NO = request.getParameter("SIGNATURE_NO");
        String SIGNATURE_PATH=request.getParameter("SIGNATURE_PATH");
        punishMeasureService.UpdatePunishSign(RECORD_ID,SIGNATURE_NO,SIGNATURE_PATH);
    }


    @RequestMapping(value = "/GetExRecord")
    @ResponseBody
    public String GetExRecord(HttpServletRequest request) {
        String RECORD_ID=request.getParameter("RECORD_ID");
        List<ExpertPunishEntity> List=punishMeasureService.getExRecord(RECORD_ID);
        JSONArray jsonArray = JSONArray.fromObject(List);
        String response = jsonArray.toString();
        return response;
    }


    /**
     * 记录专家责令整改信息
     * @param request
     */
    @RequestMapping(value = "/setExZeLingInfo")
    @ResponseBody
    public String setExZeLingInfo(HttpServletRequest request){
        String responseText="";
        String CHECKED_UNIT = request.getParameter("CHECKED_UNIT");
        String EXECUTE_PEOPLE = request.getParameter("EXECUTE_PEOPLE");
        String CARD_NUMBER = request.getParameter("CARD_NUMBER");
        String EXECUTE_PEOPLE2 = request.getParameter("EXECUTE_PEOPLE2");
        String CARD_NUMBER2 = request.getParameter("CARD_NUMBER2");
        String CHECKE_PROBLEM_IDS = request.getParameter("CHECKE_PROBLEM_IDS");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String RECORD_ID = request.getParameter("RECORD_ID");
        String TIME_IDS = request.getParameter("TIME_IDS");
//        String ORDER_DEADLINE_AREA = request.getParameter("ORDER_DEADLINE_AREA");
//        String ORDER_DEADLINE_RECORD = request.getParameter("ORDER_DEADLINE_RECORD");
        responseText = pdfThreeService.setExZeLingInfo(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKED_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID);
        return responseText;
    }
    /**
     * 缓存更新记录专家责令整改信息
     */
    @RequestMapping(value = "/temporarySetExZeLingInfo")
    @ResponseBody
    public String temporarySetExZeLingInfo(HttpServletRequest request){
        String responseText="";
        String CHECKED_UNIT = request.getParameter("CHECKED_UNIT");
        String EXECUTE_PEOPLE = request.getParameter("EXECUTE_PEOPLE");
        String CARD_NUMBER = request.getParameter("CARD_NUMBER");
        String EXECUTE_PEOPLE2 = request.getParameter("EXECUTE_PEOPLE2");
        String CARD_NUMBER2 = request.getParameter("CARD_NUMBER2");
        String CHECKE_PROBLEM_IDS = request.getParameter("CHECKE_PROBLEM_IDS");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String RECORD_ID = request.getParameter("RECORD_ID");
        String TIME_IDS = request.getParameter("TIME_IDS");
        String URL = request.getParameter ( "URL" );
        responseText = pdfThreeService.temporarySetExZeLingInfo(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKED_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,URL,RECORD_ID);
        return responseText;
    }
    /**
     * 获取缓存更新记录专家责令整改信息
     */
    @RequestMapping(value = "/getTemporarySetExZeLingInfo")
    @ResponseBody
    public String getTemporarySetExZeLingInfo(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = pdfThreeService.getTemporarySetExZeLingInfo(RecordId);
        return responseText;
    }



    /**
     * 更新责令整改的检查人签名、被检查单位负责人签名、见证人签名
     * @param request
     */
    @RequestMapping(value = "/UpdateZeLingSign")
    @ResponseBody
    public void UpdateZeLingSign(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String SIGNATURE_NO = request.getParameter("SIGNATURE_NO");
        String SIGNATURE_PATH=request.getParameter("SIGNATURE_PATH");
        punishMeasureService.UpdateZeLingSign(RECORD_ID,SIGNATURE_NO,SIGNATURE_PATH);
    }


}
