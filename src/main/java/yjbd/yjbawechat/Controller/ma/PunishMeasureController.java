package yjbd.yjbawechat.Controller.ma;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Model.ma.PunishMeasureEntity;
import yjbd.yjbawechat.Service.ma.PunishMeasureService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/PunishMeasure")
public class PunishMeasureController {
    @Autowired
    PunishMeasureService punishMeasureService;


    /**  根据ID来查询数据 */
    @RequestMapping(value = "/getPunishMeasureInfo1")
    public String getPunishMeasureInfo1(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        List<Map> List=punishMeasureService.getPunishMeasureInfo1(RECORD_ID);
        JSONArray jsonArray = JSONArray.fromObject(List);
        String response = jsonArray.toString();
        return response;
    }

    @RequestMapping(value = "/getPunishMeasureInfo")
    public String getPunishMeasureInfo(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        List<PunishMeasureEntity> List=punishMeasureService.getPunishMeasureInfo(RECORD_ID);
        JSONArray jsonArray = JSONArray.fromObject(List);
        String response = jsonArray.toString();
        return response;
    }




    @RequestMapping(value = "/getZhenggaiMeasureInfo")
    public String getZhenggaiMeasureInfo(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        List<PunishMeasureEntity> List=punishMeasureService.getZhenggaiMeasureInfo(RECORD_ID);
        JSONArray jsonArray = JSONArray.fromObject(List);
        String response = jsonArray.toString();
        return response;
    }

    @RequestMapping(value = "/setPunishMeasureInfo")
    public void setPunishMeasureInfo(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String PROCESS_DECISION=request.getParameter("PROCESS_DECISION");
        String CHECKE_DETAIL=request.getParameter("CHECKE_DETAIL");
        String CHECKE_UNIT=request.getParameter("CHECKE_UNIT");
        String CHECKED_START_TIME=request.getParameter("CHECKED_START_TIME");
        String ZF_ON_SCENE_AREA=request.getParameter("ZF_ON_SCENE_AREA");
        String ZF_ON_SCENE_RECORD=request.getParameter("ZF_ON_SCENE_RECORD");
        punishMeasureService.setPunishMeasureInfo(RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME,ZF_ON_SCENE_AREA,ZF_ON_SCENE_RECORD);

    }



    @RequestMapping(value = "/UpdateCheckSign")
    public void UpdateCheckSign(HttpServletRequest request){
        String RECORD_ID=request.getParameter("RECORD_ID");
        String SIGNATURE_NO = request.getParameter("SIGNATURE_NO");
        String SIGNATURE_PATH=request.getParameter("SIGNATURE_PATH");
        punishMeasureService.UpdateCheckSign(RECORD_ID,SIGNATURE_NO,SIGNATURE_PATH);
    }


    @RequestMapping(value = "/GetIdRecord")
    public String getIdRecord(HttpServletRequest request) {
        String RECORD_ID=request.getParameter("RECORD_ID");
        List<PunishMeasureEntity> List=punishMeasureService.getIdRecord(RECORD_ID);
        JSONArray jsonArray = JSONArray.fromObject(List);
        String response = jsonArray.toString();
        return response;
    }
    /**
     * 缓存政府执法现场处理措施决定
     * @param
     */
    @RequestMapping(value = "/setTemporaryPunishMeasureInfo")
    public String setTemporaryPunishMeasureInfo(HttpServletRequest request){
        String responseText="";
        String RECORD_ID=request.getParameter("RECORD_ID");
        String PROCESS_DECISION=request.getParameter("PROCESS_DECISION");
        String CHECKE_DETAIL=request.getParameter("CHECKE_DETAIL");
        String CHECKE_UNIT=request.getParameter("CHECKE_UNIT");
        String CHECKED_START_TIME=request.getParameter("CHECKED_START_TIME");
        String URL = request.getParameter ( "URL" );
        String ZF_ON_SCENE_AREA=request.getParameter("ZF_ON_SCENE_AREA");
        String ZF_ON_SCENE_RECORD = request.getParameter ( "ZF_ON_SCENE_RECORD" );
        responseText =punishMeasureService.setTemporaryPunishMeasureInfo(RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME,URL,ZF_ON_SCENE_AREA,ZF_ON_SCENE_RECORD);
        return responseText;
    }

    /**
     * 获取缓存政府执法现场处理措施决定
     * @param request
     */
    @RequestMapping(value = "/getTemporaryPunishMeasureInfo")
    public String getTemporaryPunishMeasureInfo(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = punishMeasureService.getTemporaryPunishMeasureInfo(RecordId);
        return responseText;
    }

}
