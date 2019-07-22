package yjbd.yjbawechat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Service.HiddenDangerService;
import yjbd.yjbawechat.Util.MsgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: 随心的小新
 * @Date: 2019/5/7 16:21
 */
@RestController
@RequestMapping("/hiddenDanger")
public class HiddenDangerController {
    @Autowired
    private HiddenDangerService hiddenDangerService;
    /**
     * 根据ID来获取专家名称
     */
    @RequestMapping("/getExpertById")
    public String getExpertById(HttpServletRequest request ) {
        String response = "";
        String recordId=request.getParameter("recordId");
        response = hiddenDangerService.getExpertById(recordId);
        return response;
    }
    /**
     * 获取检查记录中的不合格问题
     */
    @RequestMapping("/GetExpertProblemAndMsgs")
    public String GetExpertProblemAndMsgs(HttpServletRequest request){
        String response = "";
        String RecordId=request.getParameter("RecordId");
        response = hiddenDangerService.GetExpertProblemAndMsgs(RecordId);
        return response;
    }
    /**
     * 获取重大隐患问题
     */
    @RequestMapping("/getHiddenDangerById")
    public String getHiddenDangerById(){
        String response = "";
        response = hiddenDangerService.getHiddenDangerById();
        return response;
    }
    /**
     * 获取被检查单位信息
     */
    @RequestMapping(value = "/GetCheckByRecord")
    public String GetCheckByRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("recordId");
        responseText = hiddenDangerService.GetCheckByRecord(RecordId);
        return responseText;
    }
    /**
     * 创建重大隐患表格信息
     */
    @RequestMapping("/createDangerTable")
    public String createDangerTable(HttpServletRequest request){
        String responseText="";
        String RECORD_ID = request.getParameter("RECORD_ID");
        String CHECKED_START_TIME = request.getParameter("CHECKED_START_TIME");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String CHECKED_UNIT = request.getParameter("CHECKED_UNIT");
        String UNIT_NUMBER = request.getParameter("UNIT_NUMBER");
        String ADDRESS = request.getParameter("ADDRESS");
        String REPRESENT_PEOPLE = request.getParameter("REPRESENT_PEOPLE");
        String CHECKE_PLACE = request.getParameter("CHECKE_PLACE");
        String EXPERT_PEOPLLE = request.getParameter("EXPERT_PEOPLLE");
        String DANGER_NAME = request.getParameter("DANGER_NAME");
        String CHECKE_DETAIL = request.getParameter("CHECKE_DETAIL");
        String VIDEO_URL = request.getParameter("VIDEO_URL");
        String LOCATION_IMG = request.getParameter("LOCATION_IMG");
        String OTHER_IMG = request.getParameter("OTHER_IMG");
        String PKID = request.getParameter ( "PKID" );
        String SECURITY_PEOPLE = request.getParameter ( "SECURITY_PEOPLE" );
        responseText = hiddenDangerService.createDangerTable(RECORD_ID,CHECKED_START_TIME,CHECKED_END_TIME,CHECKED_UNIT, UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,
                CHECKE_PLACE,EXPERT_PEOPLLE,DANGER_NAME,CHECKE_DETAIL,VIDEO_URL,LOCATION_IMG,OTHER_IMG,PKID,SECURITY_PEOPLE);
        return responseText;
    }
    /**
     * 缓存重大隐患表格信息
     */
    @RequestMapping("/temporaryCreateDangerTable")
    public String temporaryCreateDangerTable(HttpServletRequest request){
        String responseText="";
        String RECORD_ID = request.getParameter("RECORD_ID");
        String CHECKED_START_TIME = request.getParameter("CHECKED_START_TIME");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String CHECKED_UNIT = request.getParameter("CHECKED_UNIT");
        String UNIT_NUMBER = request.getParameter("UNIT_NUMBER");
        String ADDRESS = request.getParameter("ADDRESS");
        String REPRESENT_PEOPLE = request.getParameter("REPRESENT_PEOPLE");
        String CHECKE_PLACE = request.getParameter("CHECKE_PLACE");
        String EXPERT_PEOPLLE = request.getParameter("EXPERT_PEOPLLE");
        String DANGER_NAME = request.getParameter("DANGER_NAME");
        String CHECKE_DETAIL = request.getParameter("CHECKE_DETAIL");
        String VIDEO_URL = request.getParameter("VIDEO_URL");
        String LOCATION_IMG = request.getParameter("LOCATION_IMG");
        String OTHER_IMG = request.getParameter("OTHER_IMG");
        String PKID = request.getParameter ( "PKID" );
        String SECURITY_PEOPLE = request.getParameter ( "SECURITY_PEOPLE" );
        String URL = request.getParameter ( "URL" );
        responseText = hiddenDangerService.temporaryCreateDangerTable(RECORD_ID,CHECKED_START_TIME,CHECKED_END_TIME,CHECKED_UNIT, UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,
                CHECKE_PLACE,EXPERT_PEOPLLE,DANGER_NAME,CHECKE_DETAIL,VIDEO_URL,LOCATION_IMG,OTHER_IMG,PKID,SECURITY_PEOPLE,URL);
        return responseText;
    }
    /**
     *获取 缓存重大隐患表格信息
     */
    @RequestMapping(value = "/getTemporaryCreateDangerTable")
    public String getTemporaryCreateDangerTable(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = hiddenDangerService.getTemporaryCreateDangerTable(RecordId);
        return responseText;
    }
    /**
     * 上传专家签名
     */
    @RequestMapping(value = "/UpdateExpertSignature")
    public String UpdateExpertSignature(HttpServletRequest request) {
        String responseText="";
        String CHECKE_SIGNATURE=request.getParameter("CHECKE_SIGNATURE");
        String RecordId=request.getParameter("RecordId");
        responseText = hiddenDangerService.UpdateExpertSignature(CHECKE_SIGNATURE,RecordId);
        return responseText;
    }
    /**
     * 上传被执法单位签名
     */
    @RequestMapping(value = "/UpdateInspectedSignature")
    public String UpdateInspectedSignature(HttpServletRequest request) {
        String responseText="";
        String REPRESENR_SIGNATURE=request.getParameter("REPRESENR_SIGNATURE");
        String RecordId=request.getParameter("RecordId");
        responseText = hiddenDangerService.UpdateInspectedSignature(REPRESENR_SIGNATURE,RecordId);
        return responseText;
    }
    /**
     * 获取PDF页面
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/createExDangerPdf")
    public String createExDangerPdf(HttpSession session, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        try {
            return hiddenDangerService.createExDangerPdf(RecordId);
        }catch (Exception e){
            e.printStackTrace();
            return MsgUtil.errorMsg(e.toString());
        }
    }
    /**
     * 最后预览PDF页面以及二维码图片
     */
    @RequestMapping(value = "/LookExHiddenDangerPdf")
    public String LookExHiddenDangerPdf(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = hiddenDangerService.LookExHiddenDangerPdf(RecordId);
        return responseText;
    }

}
