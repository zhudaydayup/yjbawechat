package yjbd.yjbawechat.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Model.EnforceModel;
import yjbd.yjbawechat.Service.EnforceService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/Record")
public class RecordController {
    @Autowired
    private EnforceService enforceService;


    @RequestMapping("/UpdateIdRecord")
    @ResponseBody
    public String UpdateIdRecord(HttpServletRequest request, EnforceModel enforceModel){
        String responseText="";

        responseText = enforceService.UpdateIdRecord(enforceModel);
        return responseText;
    }
    /**
     * 现场检查记录缓存
     * @param
     * @return
     */
    @RequestMapping("/temporaryUpdateIdRecord")
    @ResponseBody
    public String temporaryUpdateIdRecord(HttpServletRequest request, EnforceModel enforceModel){
        String responseText="";
        responseText = enforceService.temporaryUpdateIdRecord(enforceModel);
        return responseText;
    }

    /**
     * 获取现场检查记录缓存数据
     * @param
     * @return
     */
    @RequestMapping(value = "/gettemporaryUpdateIdRecord")
    public String gettemporaryUpdateIdRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = enforceService.gettemporaryUpdateIdRecord(RecordId);
        return responseText;
    }

    @RequestMapping("/CreateEnforce")
    @ResponseBody
    public String createEventSubmit(HttpServletRequest request){
        EnforceModel enforceModel = new EnforceModel();
        enforceModel.setCHECKED_UNIT(request.getParameter("CHECKED_UNIT"));
        enforceModel.setUNIT_NUMBER(request.getParameter("UNIT_NUMBER"));
        enforceModel.setREPRESENT_PEOPLE(request.getParameter("REPRESENT_PEOPLE"));
        enforceModel.setREPRESENT_MOBILE(request.getParameter("REPRESENT_MOBILE"));
        enforceModel.setADDRESS(request.getParameter("ADDRESS"));
        enforceModel.setCHECK_PEOPLE1(request.getParameter("CHECK_PEOPLE1"));
        enforceModel.setCARD_NUMBER1(request.getParameter("CARD_NUMBER1"));
        enforceModel.setCHECK_UNIT1(request.getParameter("CHECK_UNIT1"));
        enforceModel.setCHECK_PEOPLE2(request.getParameter("CHECK_PEOPLE2"));
        enforceModel.setCARD_NUMBER2(request.getParameter("CARD_NUMBER2"));
        enforceModel.setCHECK_UNIT2(request.getParameter("CHECK_UNIT2"));
        enforceModel.setEXPERT_NAME1(request.getParameter("EXPERT_NAME1"));
        enforceModel.setEXPERT_UNIT1(request.getParameter("EXPERT_UNIT1"));

        enforceModel.setCHECKED_START_TIME(request.getParameter("CHECKED_START_TIME"));
        enforceModel.setNOTICE_CONTENT(request.getParameter("NOTICE_CONTENT"));
        enforceModel.setNOTICE_WAY(request.getParameter("NOTICE_WAY"));
        enforceModel.setNOTICE_CONTACTS(request.getParameter("NOTICE_CONTACTS"));
        enforceModel.setNOTICE_PHONE(request.getParameter("NOTICE_PHONE"));
        enforceModel.setUSER_ID(request.getParameter("userId"));
        enforceModel.setAREA(request.getParameter("area"));
        enforceModel.setONE_ID(request.getParameter("ONE_ID"));
        enforceModel.setTWO_ID(request.getParameter("TWO_ID"));
        enforceModel.setNOTIFICATION_NUMBER(request.getParameter("NOTIFICATION_NUMBER"));
        return enforceService.createEnforce(enforceModel, enforceModel.getUSER_ID());
    }


    /**
     * 更新检查人签名
     * @param request
     * @return
     */
    @RequestMapping(value = "/UpdateCheckSign")
    public String UpdateCheckSign(HttpServletRequest request) {
        String responseText="";
        String signName=request.getParameter("signName");
        String signTime=request.getParameter("signTime");

        String EventId=request.getParameter("EventId");
        responseText = enforceService.UpdateCheckSigh(signName,EventId,signTime);
        return responseText;
    }

    /**
     * 更新执法人员2的签名
     * @param request
     * @return
     */
    @RequestMapping(value = "/UpdateCheckPeopleSign")
    public String UpdateCheckPeopleSign(HttpServletRequest request) {
        String responseText="";
        String signName=request.getParameter("signName");
        String EventId=request.getParameter("EventId");
        responseText = enforceService.UpdateCheckPeopleSign(signName,EventId);
        return responseText;
    }

    @RequestMapping(value = "/UpdateRepresentSign")
    public String UpdateRepresentSign(HttpServletRequest request) {
        String responseText="";
        String signName=request.getParameter("signName");
        String EventId=request.getParameter("EventId");
        String signTime=request.getParameter("signTime");
        responseText = enforceService.UpdateRepresentSign(signName,EventId,signTime);
        return responseText;
    }
    //UpdateExpertSign
    @RequestMapping(value = "/UpdateExpertSign")
    public String UpdateExpertSign(HttpServletRequest request) {
        String responseText="";
        String signName=request.getParameter("signName");
        String EventId=request.getParameter("EventId");
        String signTime=request.getParameter("signTime");
        responseText = enforceService.UpdateExpertSign(signName,EventId,signTime);
        return responseText;
    }
    @RequestMapping(value = "/UpdateWitnessSign")
    public String UpdateWitnessSign(HttpServletRequest request) {
        String responseText="";
        String signName=request.getParameter("signName");
        String EventId=request.getParameter("EventId");
        String signTime=request.getParameter("signTime");
        responseText = enforceService.UpdateWitnessSign(signName,EventId,signTime);
        return responseText;
    }
    //    GetIdRecord
    @RequestMapping(value = "/GetIdRecord")
    public String getIdRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = enforceService.getIdRecord(RecordId);
        return responseText;
    }

    @RequestMapping(value = "/GetIdCheckMsgs")
    public String getIdCheckMsgs(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = enforceService.getIdCheckMsgs(RecordId);
        return responseText;
    }

    /**
     * @Description 获取签名人信息，判断是否需要跳转到签名人页面
     * @Author 洛城天使
     * @Date 15:06 2019/7/11
     * @Param [RecordId]
     * @return java.lang.String
     **/
    @RequestMapping("/getName")
    public List<Map> getName(HttpServletRequest request){
        String RecordId=request.getParameter("RecordId");
        List<Map> list = enforceService.getName(RecordId);
        return list;
    }

}
