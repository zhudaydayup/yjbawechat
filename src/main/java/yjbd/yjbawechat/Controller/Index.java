package yjbd.yjbawechat.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Service.IndexService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController//返回非页面类型的时候用@Controller+@ResponseBody或者直接用@RestController
@RequestMapping("/Index")
public class Index {

    @Autowired
    IndexService indexService;

    @RequestMapping("/InsertMap")
    public String InsertMap(HttpServletRequest request){
        String response="";
        String formattedAddress=request.getParameter("formattedAddress");
        response=indexService.InsertMap(formattedAddress);
        return response;
    }

    /*
     * 查询首页显示内容
     * parse 无
     */
    @RequestMapping("/checkOneItem")
    public String checkOneItem(){
        String response="";
        response=indexService.checkOneItemService();
        return response;
    }

    /*
     * 根据userId查询此人上一次的专家记录id
     * parse userId
     */
    @RequestMapping("/checkExPreRecordByUserId")
    public String checkExPreRecordByUserId(HttpServletRequest request){
        String response="";
        String userId=request.getParameter("userId");
        String social_credit_code=request.getParameter ( "social_credit_code" );
        response=indexService.checkExPreRecordByUserId(userId,social_credit_code);
        return response;
    }


    /*
     * 根据userId查询此人上一次的执法记录id
     * parse userId
     */
    @RequestMapping("/checkPreRecord")
    public String checkPreRecord(HttpServletRequest request){
        String response="";
        String userId=request.getParameter("userId");
        String social_credit_code=request.getParameter("social_credit_code");
        response=indexService.checkPreRecordService(userId,social_credit_code);
        return response;
    }



    /*
     * 查询第二级内容by oneId
     * parse  oneId
     */
    @RequestMapping("/checkTwoItem")
    public String checkTwoItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        response=indexService.checkTwoItemService(oneId);
        return response;
    }


    /* 执法通知书流程
     * 查询第二级内容by oneId
     * parse  oneId and twoId
     */
    @RequestMapping("/checkTwoItems")
    public String checkTwoItems(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        response=indexService.checkTwoItemServices(oneId,twoId);
        return response;
    }



    /*
     * 查询第三级内容by oneId and twoId
     * parse  oneId , twoId
     */
    @RequestMapping("/checkThreeItem")
    public String checkThreeItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        response=indexService.checkThreeItemService(oneId,twoId);
        return response;
    }


    /* 执法流程
     * 查询第四级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    @RequestMapping("/checkFourItem")
    public String checkFourItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.checkFourItemService(oneId,twoId,threeId,RecordId);
        return response;
    }

    /*
     * 查询专家第四级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    @RequestMapping("/exCheckFourItem")
    public String exCheckFourItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.exCheckFourItem(oneId,twoId,threeId,RecordId);
        return response;
    }

    /* 自检流程
     * 查询第四级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    @RequestMapping("/zjCheckFourItem")
    public String zjCheckFourItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.zjCheckFourItem(oneId,twoId,threeId,RecordId);
        return response;
    }


    /* 执法流程
     * 查询第五级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId ,fourId
     */
    @RequestMapping("/checkFiveItem")
    public String checkFiveItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String fourId=request.getParameter("fourId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.checkFiveItemService(oneId,twoId,threeId,fourId,RecordId);
        return response;
    }




    /*
     * 查询专家第五级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId ,fourId
     */
    @RequestMapping("/exCheckFiveItem")
    public String exCheckFiveItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String fourId=request.getParameter("fourId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.exCheckFiveItem(oneId,twoId,threeId,fourId,RecordId);
        return response;
    }

    /* 自检流程
     * 查询第五级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId ,fourId
     */
    @RequestMapping("/ZjcheckFiveItem")
    public String ZjcheckFiveItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String fourId=request.getParameter("fourId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.ZjcheckFiveItemService(oneId,twoId,threeId,fourId,RecordId);
        return response;
    }


    /* 执法流程
     * 查询第六级内容by oneId and twoId and threeId and fourId and fiveId
     * parse  oneId , twoId ,threeId ,fourId, fiveId
     */
    @RequestMapping("/checkSixItem")
    public String checkSixItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String fourId=request.getParameter("fourId");
        String fiveId=request.getParameter("fiveId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.checkSixItemService(oneId,twoId,threeId,fourId,fiveId,RecordId);
        return response;
    }



    /*
     * 查询专家第六级内容by oneId and twoId and threeId and fourId and fiveId
     * parse  oneId , twoId ,threeId ,fourId, fiveId
     */
    @RequestMapping("/exCheckSixItem")
    public String exCheckSixItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String fourId=request.getParameter("fourId");
        String fiveId=request.getParameter("fiveId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.exCheckSixItem(oneId,twoId,threeId,fourId,fiveId,RecordId);
        return response;
    }


    /* 自检流程
     * 查询第六级内容by oneId and twoId and threeId and fourId and fiveId
     * parse  oneId , twoId ,threeId ,fourId, fiveId
     */
    @RequestMapping("/ZjcheckSixItem")
    public String ZjcheckSixItem(HttpServletRequest request){
        String response="";
        String oneId=request.getParameter("oneId");
        String twoId=request.getParameter("twoId");
        String threeId=request.getParameter("threeId");
        String fourId=request.getParameter("fourId");
        String fiveId=request.getParameter("fiveId");
        String RecordId=request.getParameter("RecordId");
        response=indexService.ZjcheckSixItemService(oneId,twoId,threeId,fourId,fiveId,RecordId);
        return response;
    }


    /*
     * 关键字查询模板
     * parse  keyWord
     */
    @RequestMapping("/checkTempByKeyWord")
    public String checkTempByKeyWord(HttpServletRequest request){
        String response="";
        String keyWord=request.getParameter("keyWord");
        response=indexService.checkTempByKeyWordService(keyWord);
        return response;
    }


    /*
     * 查询所有的模板
     * parse
     */
    @RequestMapping("/checkAllTemp")
    public String checkAllTemp(){
        String response="";
        response=indexService.checkAllTempService();
        return response;
    }

    /*
     * 创建检查
     * parse
     */
    @RequestMapping("/CreateEnforce")
    @ResponseBody
    public String createEventSubmit(HttpServletRequest request){
        String responseText="";
        String TEMP_ID = request.getParameter("TEMP_ID");
        String TEMP_PDF = request.getParameter("TEMP_PDF");
        String TEMP_NAME = request.getParameter("TEMP_NAME");
        String USER_ID = request.getParameter("USER_ID");
        String CHECK_STATE = request.getParameter("CHECK_STATE");
        String SERVICE_ID = request.getParameter("SERVICE_ID");
        String CHECKED_START_TIME = request.getParameter("CHECKED_START_TIME");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String CHECKED_DETAIL = request.getParameter("CHECKED_DETAIL");
        String VIDEO_URL = request.getParameter("VIDEO_URL");
        String LOCATION_IMG = request.getParameter("LOCATION_IMG");
        String OTHER_IMG = request.getParameter("OTHER_IMG");
//        String CHECKED_MODEL = request.getParameter("CHECKED_MODEL");
        responseText = indexService.createEnforce(TEMP_ID,TEMP_PDF,TEMP_NAME,USER_ID, CHECK_STATE, SERVICE_ID,     CHECKED_START_TIME ,CHECKED_END_TIME,    CHECKED_DETAIL,   VIDEO_URL ,LOCATION_IMG,OTHER_IMG);
        return responseText;
    }

    /*
     * 创建检查记录(企业内查)
     * parse
     */
    @RequestMapping("/zjCreateEnforce")
    @ResponseBody
    public String zjCreateEnforce(HttpServletRequest request){
        String responseText="";
        String TEMP_ID = request.getParameter("TEMP_ID");
        String TEMP_PDF = request.getParameter("TEMP_PDF");
        String TEMP_NAME = request.getParameter("TEMP_NAME");
        String USER_ID = request.getParameter("USER_ID");
        String CHECK_STATE = request.getParameter("CHECK_STATE");
        String SERVICE_ID = request.getParameter("SERVICE_ID");
        String CHECKED_START_TIME = request.getParameter("CHECKED_START_TIME");
//        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String CHECKE_DETAIL = request.getParameter("CHECKED_DETAIL");
        String VIDEO_URL = request.getParameter("VIDEO_URL");
        String LOCATION_IMG = request.getParameter("LOCATION_IMG");
        String OTHER_IMG = request.getParameter("OTHER_IMG");
        String UNIT_NUMBER = request.getParameter("UNIT_NUMBER");
        responseText = indexService.zjCreateEnforce(TEMP_ID,TEMP_PDF,TEMP_NAME,USER_ID, CHECK_STATE, SERVICE_ID,     CHECKED_START_TIME ,    CHECKE_DETAIL,   VIDEO_URL ,LOCATION_IMG,OTHER_IMG,UNIT_NUMBER);
        return responseText;
    }

    /*
     * 创建企业内查信息 生成record_id
     * parse
     */
    @RequestMapping("/zjCreateEnforce2")
    @ResponseBody
    public String zjCreateEnforce2(HttpServletRequest request){
        String response="";
        String   CHECKED_UNIT=request.getParameter("CHECKED_UNIT");
        String   ADDRESS=request.getParameter("ADDRESS");
        String   REPRESENT_PEOPLE=request.getParameter("REPRESENT_PEOPLE");
        String   USER_ID=request.getParameter("USER_ID");
        String   UNIT_NUMBER=request.getParameter("UNIT_NUMBER");
        response = indexService.zjCreateEnforceService(CHECKED_UNIT,ADDRESS,REPRESENT_PEOPLE, USER_ID,UNIT_NUMBER);
        return  response;
    }


    /*
     * 创建检查记录(专家检查)
     * parse
     */
    @RequestMapping("/exCreateEnforce")
    @ResponseBody
    public String exCreateEnforce(HttpServletRequest request){
        String responseText="";
        String TEMP_ID = request.getParameter("TEMP_ID");
        String TEMP_PDF = request.getParameter("TEMP_PDF");
        String TEMP_NAME = request.getParameter("TEMP_NAME");
        String USER_ID = request.getParameter("USER_ID");
        String CHECK_STATE = request.getParameter("CHECK_STATE");
        String SERVICE_ID = request.getParameter("SERVICE_ID");
        String CHECKED_START_TIME = request.getParameter("CHECKED_START_TIME");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String CHECKED_DETAIL = request.getParameter("CHECKED_DETAIL");
        String VIDEO_URL = request.getParameter("VIDEO_URL");
        String LOCATION_IMG = request.getParameter("LOCATION_IMG");
        String OTHER_IMG = request.getParameter("OTHER_IMG");
        String UNIT_NUMBER = request.getParameter("UNIT_NUMBER");
        responseText = indexService.exCreateEnforce(TEMP_ID,TEMP_PDF,TEMP_NAME,USER_ID, CHECK_STATE, SERVICE_ID,     CHECKED_START_TIME ,CHECKED_END_TIME,    CHECKED_DETAIL,   VIDEO_URL ,LOCATION_IMG,OTHER_IMG,UNIT_NUMBER);
        return responseText;
    }

    /**
     * 专家检查暂存更新第四级目录
     * @param request
     * @return
     */
    @RequestMapping("/temporaryExCreateEnforce")
    @ResponseBody
    public String temporaryExCreateEnforce(HttpServletRequest request){
        String responseText="";
        String TEMP_ID = request.getParameter("TEMP_ID");
        String TEMP_PDF = request.getParameter("TEMP_PDF");
        String TEMP_NAME = request.getParameter("TEMP_NAME");
        String USER_ID = request.getParameter("USER_ID");
        String CHECK_STATE = request.getParameter("CHECK_STATE");
        String SERVICE_ID = request.getParameter("SERVICE_ID");
        String CHECKED_START_TIME = request.getParameter("CHECKED_START_TIME");
        String CHECKED_END_TIME = request.getParameter("CHECKED_END_TIME");
        String CHECKED_DETAIL = request.getParameter("CHECKED_DETAIL");
        String VIDEO_URL = request.getParameter("VIDEO_URL");
        String LOCATION_IMG = request.getParameter("LOCATION_IMG");
        String OTHER_IMG = request.getParameter("OTHER_IMG");
        String URL = request.getParameter ( "URL" );
        responseText = indexService.temporaryExCreateEnforce(TEMP_ID,TEMP_PDF,TEMP_NAME,USER_ID, CHECK_STATE, SERVICE_ID,
                CHECKED_START_TIME ,CHECKED_END_TIME,    CHECKED_DETAIL, VIDEO_URL ,LOCATION_IMG,OTHER_IMG,URL);
        return responseText;
    }



    /*
     * 创建专家检查信息 生成record_id
     * parse
     */
    @RequestMapping("/exCreateEnforce2")
    @ResponseBody
    public String exCreateEnforce2(HttpServletRequest request){
        String response="";
        String   CHECKED_UNIT=request.getParameter("CHECKED_UNIT");
        String   ADDRESS=request.getParameter("ADDRESS");
        String   REPRESENT_PEOPLE=request.getParameter("REPRESENT_PEOPLE");
        String   USER_ID=request.getParameter("USER_ID");
        String   UNIT_NUMBER=request.getParameter("UNIT_NUMBER");
        response = indexService.exCreateEnforceService(CHECKED_UNIT,ADDRESS,REPRESENT_PEOPLE, USER_ID,UNIT_NUMBER);
        return  response;
    }



}
