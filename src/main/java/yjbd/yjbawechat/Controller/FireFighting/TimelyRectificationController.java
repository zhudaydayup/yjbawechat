package yjbd.yjbawechat.Controller.FireFighting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Service.FireFighting.TimelyRectificationService;
import yjbd.yjbawechat.Util.MsgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/25 20:29
 */
@RestController
@RequestMapping("/timeRectification")
public class TimelyRectificationController {
    @Autowired
    TimelyRectificationService timelyRectificationService;

    /**
     * 根据ID来查询数据
     * @param request
     * @return
     */
//    @RequestMapping(value = "/getMessageById")
//    public String getMessageById(HttpServletRequest request){
//        String RECORD_ID=request.getParameter("RECORD_ID");
//        List<PunishMeasureEntity> List=timelyRectificationService.getMessageById(RECORD_ID);
//        JSONArray jsonArray = JSONArray.fromObject(List);
//        String response = jsonArray.toString();
//        return response;
//    }
    @RequestMapping("/getMessageById")
    public String getMessageById(HttpServletRequest request ) {
        String response = "";
        String RECORD_ID=request.getParameter("RECORD_ID");
        response = timelyRectificationService.getMessageById(RECORD_ID);
        return response;
    }

    /**
     * 建立现场检查记录表
     * @param request
     */
    @RequestMapping(value = "/setInspectionRecord")
    public void setInspectionRecord(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String PROCESS_DECISION=request.getParameter("PROCESS_DECISION");
        String CHECKE_DETAIL=request.getParameter("CHECKE_DETAIL");
        String CHECKE_UNIT=request.getParameter("CHECKE_UNIT");
        String CHECKED_START_TIME=request.getParameter("CHECKED_START_TIME");
        timelyRectificationService.setInspectionRecord(RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME);
    }
    /**
     * 上传执法者签名
     */
    @RequestMapping(value = "/UpdateCheckSignature")
    public String UpdateCheckSignature(HttpServletRequest request) {
        String responseText="";
        String CHECKE_SIGNATURE=request.getParameter("CHECKE_SIGNATURE");
        String RecordId=request.getParameter("RecordId");
        responseText = timelyRectificationService.UpdateCheckSignature(CHECKE_SIGNATURE,RecordId);
        return responseText;
    }
    /**
     * 上传被执法单位签名
     */
    @RequestMapping(value = "/UpdateRepresentSignature")
    public String UpdateRepresentSignature(HttpServletRequest request) {
        String responseText="";
        String REPRESENR_SIGNATURE=request.getParameter("REPRESENR_SIGNATURE");
        String RecordId=request.getParameter("RecordId");
        responseText = timelyRectificationService.UpdateRepresentSignature(REPRESENR_SIGNATURE,RecordId);
        return responseText;
    }

    /**
     * 获取PDF页面
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/createDocumentPdf")
    public String applyCheckInfo(HttpSession session, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        String ID = request.getParameter("ID");
        try {
//            String RecordId="10";
            return timelyRectificationService.createDocumentPdf(RecordId,ID);//String id=request.getParameter("RecordId");
        }catch (Exception e){
            e.printStackTrace();
            return MsgUtil.errorMsg(e.toString());
        }
    }
    /**
     * 最后生成PDF页面以及二维码图片
     */
    @RequestMapping(value = "/LookUpPdfById")
    public String LookUpPdfById(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = timelyRectificationService.LookUpPdfById(RecordId);
        return responseText;
    }

    /**
     * 九小场所重大隐患上报记录
     */
    @RequestMapping(value = "/setFireHiddenDangerRecord")
    public void setFireHiddenDangerRecord(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String PROCESS_DECISION=request.getParameter("PROCESS_DECISION");
        String Risk_Level=request.getParameter("Risk_Level");
        String CHECKE_UNIT=request.getParameter("CHECKE_UNIT");
        String CHECKED_START_TIME=request.getParameter("CHECKED_START_TIME");
        timelyRectificationService.setFireHiddenDangerRecord(RECORD_ID,PROCESS_DECISION,Risk_Level,CHECKE_UNIT,CHECKED_START_TIME);
    }

}
