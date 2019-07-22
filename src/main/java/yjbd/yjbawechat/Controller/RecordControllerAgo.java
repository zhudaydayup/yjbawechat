package yjbd.yjbawechat.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Model.EnforceModel;
import yjbd.yjbawechat.Service.EnforceServiceAgo;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/RecordAgo")
public class RecordControllerAgo {
    @Autowired
    private EnforceServiceAgo enforceService;

    @RequestMapping("/UpdateIdRecord")
    @ResponseBody
    public String UpdateIdRecord(HttpServletRequest request, EnforceModel enforceModel){
        String responseText="";

        responseText = enforceService.UpdateIdRecord(enforceModel);
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
        enforceModel.setEXPERT_NAME2(request.getParameter("EXPERT_NAME2"));
        enforceModel.setEXPERT_UNIT2(request.getParameter("EXPERT_UNIT2"));
        enforceModel.setCHECKED_START_TIME(request.getParameter("CHECKED_START_TIME"));
        enforceModel.setNOTICE_CONTENT(request.getParameter("NOTICE_CONTENT"));
        enforceModel.setNOTICE_WAY(request.getParameter("NOTICE_WAY"));
        enforceModel.setNOTICE_CONTACTS(request.getParameter("NOTICE_CONTACTS"));
        enforceModel.setNOTICE_PHONE(request.getParameter("NOTICE_PHONE"));
        enforceModel.setUSER_ID(request.getParameter("userId"));
        enforceModel.setAREA(request.getParameter("area"));
        enforceModel.setONE_ID(request.getParameter("ONE_ID"));
        enforceModel.setTWO_ID(request.getParameter("TWO_ID"));
        return enforceService.createEnforce(enforceModel, enforceModel.getUSER_ID());
    }
//    UpdateCheckSign

    @RequestMapping(value = "/UpdateCheckSign")
    public String UpdateCheckSign(HttpServletRequest request) {
        String responseText="";
        String signName=request.getParameter("signName");
        String signTime=request.getParameter("signTime");

        String EventId=request.getParameter("EventId");
        responseText = enforceService.UpdateCheckSigh(signName,EventId,signTime);
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

}
