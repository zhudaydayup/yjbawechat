package yjbd.yjbawechat.Controller.FireFighting;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yjbd.yjbawechat.Service.FireFighting.ReexamineService;
import yjbd.yjbawechat.Util.MsgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/5/28 16:16
 */
@Controller
@RequestMapping(value = "/reexamine")
public class ReexamineController {

    @Autowired
    ReexamineService reexamineService;
    /**
     * 根据ID来查询数据
     */
    @RequestMapping(value = "/getPunishMeasureInfo1")
    @ResponseBody
    public String getPunishMeasureInfo1(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        List<Map> List=reexamineService.getReexamineDate(RECORD_ID);
        JSONArray jsonArray = JSONArray.fromObject(List);
        String response = jsonArray.toString();
        return response;
    }
    /**
     * 存入自己复查信息的表格
     * @param request
     */
    @RequestMapping(value = "/setReexamineInfo")
    @ResponseBody
    public void setReexamineInfo(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String CHECKE_UNIT=request.getParameter("CHECKE_UNIT");
        String CHECKED_END_TIME=request.getParameter("CHECKED_END_TIME");
        String CHECKE_OPINION=request.getParameter("CHECKE_OPINION");
        reexamineService.setReexamineInfo(RECORD_ID,CHECKE_UNIT,CHECKED_END_TIME,CHECKE_OPINION);
    }
    /**
     * 上传专家签名
     */
    @RequestMapping(value = "/updateExpertSign")
    @ResponseBody
    public String updateExpertSign(HttpServletRequest request) {
        String responseText="";
        String CHECKE_SIGNATURE=request.getParameter("CHECKE_SIGNATURE");
        String RecordId=request.getParameter("RecordId");
        responseText = reexamineService.updateExpertSign(CHECKE_SIGNATURE,RecordId);
        return responseText;
    }
    /**
     * 上传被执法单位签名
     */
    @RequestMapping(value = "/updateInspectedSign")
    @ResponseBody
    public String updateInspectedSign(HttpServletRequest request) {
        String responseText="";
        String REPRESENR_SIGNATURE=request.getParameter("REPRESENR_SIGNATURE");
        String RecordId=request.getParameter("RecordId");
        responseText = reexamineService.updateInspectedSign(REPRESENR_SIGNATURE,RecordId);
        return responseText;
    }
    /**
     * 获取PDF页面
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/createReexaminePdf")
    @ResponseBody
    public String createReexaminePdf(HttpSession session, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        try {
            return reexamineService.createReexaminePdf(RecordId);
        }catch (Exception e){
            e.printStackTrace();
            return MsgUtil.errorMsg(e.toString());
        }
    }
    /**
     * 最后预览PDF页面以及二维码图片
     */
    @RequestMapping(value = "/LookReexaminePdf")
    @ResponseBody
    public String LookReexaminePdf(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = reexamineService.LookReexaminePdf(RecordId);
        return responseText;
    }
}
