package yjbd.yjbawechat.Controller.FireFighting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Service.FireFighting.TimeLimitService;
import yjbd.yjbawechat.Util.MsgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/25 17:05
 */
@RestController
@RequestMapping("timeLimit")
public class TimeLimitController {
    @Autowired
    private TimeLimitService timeLimitService;

    /**
     * 根据ID获取被执法单位
     */
    @RequestMapping("/getEnforcerById")
    public String getEnforcerById(HttpServletRequest request ) {
        String response = "";
        String recordId=request.getParameter("recordId");
        response = timeLimitService.getEnforcerById(recordId);
        return response;
    }
    /**
     * 获取存在的问题列表
     */
    @RequestMapping("/getProblemsById")
    public String getProblemsById(HttpServletRequest request ){
        String response = "";
        String recordId=request.getParameter("recordId");
        response = timeLimitService.getProblemsById(recordId);
        return response;
    }
    /**
     * 获取PDF预览信息
     */
    @RequestMapping("/createTimeLimitTable")
    public String createTimeLimitTable(HttpServletRequest request) throws UnsupportedEncodingException {
        String responseText="";
        String CHECKE_UNIT = request.getParameter("CHECKE_UNIT");
        String EXECUTE_PEOPLE = request.getParameter("EXECUTE_PEOPLE");
        String CARD_NUMBER = request.getParameter("CARD_NUMBER");
        String EXECUTE_PEOPLE2 = request.getParameter("EXECUTE_PEOPLE2");
        String CARD_NUMBER2 = request.getParameter("CARD_NUMBER2");
        String CHECKE_DETAIL = request.getParameter("CHECKE_DETAIL");
//        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String RECORD_ID = request.getParameter("RECORD_ID");
        RECORD_ID = URLDecoder.decode(RECORD_ID, "UTF-8");
//        String PROCESS_DECISION = request.getParameter("PROCESS_DECISION");
        String TIME_IDS = request.getParameter("TIME_IDS");
        responseText = timeLimitService.createTimeLimitTable(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_DETAIL,RECORD_ID);
        return responseText;
    }
    /**
     * 上传执法者签名
     */
    @RequestMapping(value = "/UpdateEnforceSigh")
    public String UpdateEnforceSigh(HttpServletRequest request) {
        String responseText="";
        String CHECKE_SIGH=request.getParameter("CHECKE_SIGH");
        String RecordId=request.getParameter("RecordId");
        responseText = timeLimitService.UpdateEnforceSigh(CHECKE_SIGH,RecordId);
        return responseText;
    }
    /**
     * 上传被执法单位签名
     */
    @RequestMapping(value = "/UpdateInspectorSigh")
    public String UpdateInspectorSigh(HttpServletRequest request) {
        String responseText="";
        String REPRESENR_SIGN=request.getParameter("REPRESENR_SIGN");
        String RecordId=request.getParameter("RecordId");
        responseText = timeLimitService.UpdateInspectorSigh(REPRESENR_SIGN,RecordId);
        return responseText;
    }
    @RequestMapping(value = "/createTimeLimitPdf")
    public String applyCheckInfo(HttpSession session, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        String ID = request.getParameter("ID");
        try {
//            String RecordId="10";
            return timeLimitService.createTimeLimitPdf(RecordId,ID);//String id=request.getParameter("RecordId");
        }catch (Exception e){
            e.printStackTrace();
            return MsgUtil.errorMsg(e.toString());
        }
    }
    /**
     * 最后生成PDF页面以及二维码图片
     */
    @RequestMapping(value = "/getTimeLimitRecord")
    public String getTimeLimitRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = timeLimitService.getTimeLimitRecord(RecordId);
        return responseText;
    }

}
