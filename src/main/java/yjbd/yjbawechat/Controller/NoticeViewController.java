package yjbd.yjbawechat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Service.NoticeViewService;
import yjbd.yjbawechat.Util.MsgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/12 17:03
 */
@RestController
@RequestMapping("/NoticeView")
public class NoticeViewController {
    @Autowired
    private NoticeViewService noticeViewService;

    /**
     * 通过id获取开始时间
     */
    @RequestMapping("/getGreatTimeById")
    public String getGreatTimeById(HttpServletRequest request ) {
        String response = "";
        String RECORD_ID=request.getParameter("RECORD_ID");
        response = noticeViewService.getGreatTimeById(RECORD_ID);
        return response;
    }
    /**
     * 获取整改自检表格信息
     */
    @RequestMapping("/createNoticeBiao")
    public String createNoticeBiao(HttpServletRequest request){
        String responseText="";
        String CHECKE_UNIT = request.getParameter("CHECKE_UNIT");
        String EXECUTE_PEOPLE = request.getParameter("EXECUTE_PEOPLE");
        String CHECKED_START_TIME = request.getParameter("CHECKED_START_TIME");
        String CHECKE_PROBLEM_IDS = request.getParameter("CHECKE_PROBLEM_IDS");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String NOTICE_DETAIL = request.getParameter("NOTICE_DETAIL");
        String RECORD_ID = request.getParameter("RECORD_ID");
        responseText = noticeViewService.createNoticeBiao(CHECKE_UNIT, EXECUTE_PEOPLE,CHECKED_START_TIME,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,NOTICE_DETAIL,RECORD_ID);
        return responseText;
    }


    /**
     * 上传检查单位责任人签名
     */
    @RequestMapping(value = "/UpdateCheck1")
    public String UpdateCheck1(HttpServletRequest request) {
        String responseText="";
        String CHECKE_SIGH=request.getParameter("CHECKE_SIGH")+"|";
        String RecordId=request.getParameter("RecordId");
        responseText = noticeViewService.UpdateCheck1(CHECKE_SIGH,RecordId);
        return responseText;
    }
    /**
     * 上传被告知人签名
     */
    @RequestMapping(value = "/UpdateRepresent1")
    public String UpdateRepresent1(HttpServletRequest request) {
        String responseText="";
        String NOTICE_SIGH=request.getParameter("NOTICE_SIGH");
        String RecordId=request.getParameter("RecordId");
        responseText = noticeViewService.UpdateRepresent1(NOTICE_SIGH,RecordId);
        return responseText;
    }
    /**
     * 生成整改通知PDF
     */
    @RequestMapping(value = "/createNoticePdf")
    public String applyCheckInfo(HttpSession session, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        try {
//            String RecordId="10";
            return noticeViewService.createNoticePdf(RecordId);//String id=request.getParameter("RecordId");
        }catch (Exception e){
            e.printStackTrace();
            return MsgUtil.errorMsg(e.toString());
        }
    }
    /**
     * 最后生成PDF页面以及二维码图片
     */
    @RequestMapping(value = "/getIdRecord")
    public String getIdRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = noticeViewService.getIdRecord(RecordId);
        return responseText;
    }





}
