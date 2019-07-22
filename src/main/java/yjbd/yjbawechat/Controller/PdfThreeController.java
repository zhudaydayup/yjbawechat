package yjbd.yjbawechat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Service.PdfThreeService;
import yjbd.yjbawechat.Util.MsgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/7 9:21
 */
@RestController
@RequestMapping("/PdfThree")
public class PdfThreeController {
    @Autowired
    private PdfThreeService pdfThreeService;

    @RequestMapping(value = "/createThreePdf")
    public String applyCheckInfo(HttpSession session, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        try {
//            String RecordId="10";
            return pdfThreeService.createThreePDF(RecordId);//String id=request.getParameter("RecordId");
        }catch (Exception e){
            e.printStackTrace();
            return MsgUtil.errorMsg(e.toString());
        }
    }

    /**
     * 获取被执法单位
     */
    @RequestMapping("/getZhiFaRenById")
    public String getZhiFaRenById(HttpServletRequest request ) {
        String response = "";
        String zeLingId=request.getParameter("zeLingId");
        response = pdfThreeService.getZhiFaRenById(zeLingId);
        return response;
    }

    /**
     * 获取存在的问题列表
     */
    @RequestMapping("/getProblemById")
    public String getProblemById(HttpServletRequest request ){
        String response = "";
        String zeLingId=request.getParameter("zeLingId");
        response = pdfThreeService.getProblemById(zeLingId);
        return response;
    }

    /**
     * 获取PDF预览信息
     */
    @RequestMapping("/createZeLingBiao")
    public String createZeLingBiao(HttpServletRequest request){
        String responseText="";
        String CHECKE_UNIT = request.getParameter("CHECKE_UNIT");
        String EXECUTE_PEOPLE = request.getParameter("EXECUTE_PEOPLE");
        String CARD_NUMBER = request.getParameter("CARD_NUMBER");
        String EXECUTE_PEOPLE2 = request.getParameter("EXECUTE_PEOPLE2");
        String CARD_NUMBER2 = request.getParameter("CARD_NUMBER2");
        String CHECKE_PROBLEM_IDS = request.getParameter("CHECKE_PROBLEM_IDS");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String RECORD_ID = request.getParameter("RECORD_ID");
//        String PROCESS_DECISION = request.getParameter("PROCESS_DECISION");
        String TIME_IDS = request.getParameter("TIME_IDS");
        String ZF_ORDER_DEADLINE_AREA = request.getParameter("ZF_ORDER_DEADLINE_AREA");
        String ZF_ORDER_DEADLINE_RECORD = request.getParameter("ZF_ORDER_DEADLINE_RECORD");
        responseText = pdfThreeService.createZeLingBiao(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID,ZF_ORDER_DEADLINE_AREA,ZF_ORDER_DEADLINE_RECORD);
        return responseText;
    }

    /**
     * 上传执法者1签名
     */
    @RequestMapping(value = "/UpdateCheck")
    public String UpdateCheck(HttpServletRequest request) {
        String responseText="";
        String CHECKE_SIGH=request.getParameter("CHECKE_SIGH");
        String RecordId=request.getParameter("RecordId");
        responseText = pdfThreeService.UpdateCheck(CHECKE_SIGH,RecordId);
        return responseText;
    }

    /**
     * 上传执法者1签名
     */
    @RequestMapping(value = "/UpdateCheckTwoSign")
    public String UpdateCheckTwoSign(HttpServletRequest request) {
        String responseText="";
        String CHECKETWO_SIGH=request.getParameter("CHECKE_SIGH");
        String RecordId=request.getParameter("RecordId");
        responseText = pdfThreeService.UpdateCheckTwoSign(CHECKETWO_SIGH,RecordId);
        return responseText;
    }


    /**
     * 上传被执法单位签名
     */
    @RequestMapping(value = "/UpdateRepresent")
    public String UpdateRepresent(HttpServletRequest request) {
        String responseText="";
        String REPRESENR_SIGN=request.getParameter("REPRESENR_SIGN");
        String RecordId=request.getParameter("RecordId");
        responseText = pdfThreeService.UpdateRepresent(REPRESENR_SIGN,RecordId);
        return responseText;
    }

    /**
     * 上传见证人签名
     */
    @RequestMapping(value = "/UpdateJianZhengRen")
    public String UpdateJianZhengRen(HttpServletRequest request) {
        String responseText="";
        String WITNESS_SIGH=request.getParameter("WITNESS_SIGH");
        String RecordId=request.getParameter("RecordId");
        responseText = pdfThreeService.UpdateJianZhengRen(WITNESS_SIGH,RecordId);
        return responseText;
    }

    /**
     * 最后生成PDF页面以及二维码图片
     */
    @RequestMapping(value = "/getIdRecord")
    public String getIdRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = pdfThreeService.getIdRecord(RecordId);
        return responseText;
    }


    /**
     * 专家责令整改PDF
     */
    @RequestMapping(value = "/createExZeLingPdf")
    public String createExZeLingPdf(HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        try {
            return pdfThreeService.createExZeLingPdf(RecordId);
        }catch (Exception e){
            e.printStackTrace();
            return MsgUtil.errorMsg(e.toString());
        }
    }


    @RequestMapping(value = "/getExRecord")
    public String getExRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = pdfThreeService.getExRecord(RecordId);
        return responseText;
    }
    /**
     * 缓存政府执法责令限期表格信息
     */
    @RequestMapping("/createTemporaryZeLingBiao")
    public String createTemporaryZeLingBiao(HttpServletRequest request){
        String responseText="";
        String CHECKE_UNIT = request.getParameter("CHECKE_UNIT");
        String EXECUTE_PEOPLE = request.getParameter("EXECUTE_PEOPLE");
        String CARD_NUMBER = request.getParameter("CARD_NUMBER");
        String EXECUTE_PEOPLE2 = request.getParameter("EXECUTE_PEOPLE2");
        String CARD_NUMBER2 = request.getParameter("CARD_NUMBER2");
        String CHECKE_PROBLEM_IDS = request.getParameter("CHECKE_PROBLEM_IDS");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String RECORD_ID = request.getParameter("RECORD_ID");
//        String PROCESS_DECISION = request.getParameter("PROCESS_DECISION");
        String URL = request.getParameter ( "URL" );
        String TIME_IDS = request.getParameter("TIME_IDS");

        responseText = pdfThreeService.createTemporaryZeLingBiao(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,URL,RECORD_ID);
        return responseText;
    }

}
