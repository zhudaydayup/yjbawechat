package yjbd.yjbawechat.Controller.ma;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yjbd.yjbawechat.Model.ma.RectificationEntity;
import yjbd.yjbawechat.Service.ma.RectificationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/Rectification")
public class RectificationController {

    @Autowired
    RectificationService rectificationService;

    /**
     * 存入自己复查信息的表格
     * @param request
     */
    @RequestMapping(value = "/setRectificationInfo")
    @ResponseBody
    public void setRectificationInfo(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String CHECKE_UNIT=request.getParameter("CHECKE_UNIT");
        String CHECKED_START_TIME=request.getParameter("CHECKED_START_TIME");
        String CHECKE_OPINION=request.getParameter("CHECKE_OPINION");
        rectificationService.setRectificationInfo(RECORD_ID,CHECKE_UNIT,CHECKED_START_TIME,CHECKE_OPINION);
    }

    /**
     * 被复查单位执法者签名
     * @param request
     */

    @RequestMapping(value = "/UpdateCheckSign")
    @ResponseBody
    public void UpdateCheckSign(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String SIGNATURE_NO = request.getParameter("SIGNATURE_NO");
        String SIGNATURE_PATH=request.getParameter("SIGNATURE_PATH");
        rectificationService.UpdateCheckSign(RECORD_ID,SIGNATURE_NO,SIGNATURE_PATH);
    }


    @RequestMapping(value = "/GetIdRecord")
    @ResponseBody
    public String getIdRecord(HttpServletRequest request) {
        String RECORD_ID=request.getParameter("RECORD_ID");
        List<RectificationEntity> List=rectificationService.getIdRecord(RECORD_ID);
        JSONArray jsonArray = JSONArray.fromObject(List);
        String response = jsonArray.toString();
        return response;
    }

}
