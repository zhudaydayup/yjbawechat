package yjbd.yjbawechat.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Service.DataService;
import yjbd.yjbawechat.Util.HttpUtil;
import yjbd.yjbawechat.Util.MsgUtils;
import yjbd.yjbawechat.Util.PostUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Totle:DataController
 * @ProjectName:yjbawechat
 * @author:社会码农
 * @data:2019/4/117:58
 */
@RestController
@RequestMapping(value = "/data/")
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     * 传送执法数据
     * @param request
     * @return
     */
    @RequestMapping(value = "PushZhifaData")
    public String PushZhifaData(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String entId = request.getParameter("entId");
        return dataService.PushData(recordId,entId);
    }


    /**
     * 传送专家现场处理、责令整改、结束数据
     * @param request
     * @return
     */
    @RequestMapping(value = "PushExpertData")
    public String PushExpertData(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String review_type = request.getParameter("review_type");
        String user_id = request.getParameter("user_id");
        String entId = request.getParameter("entId");
        return dataService.PushExpertData(recordId,review_type,user_id,entId);
    }

    /**
     * 传送执法处罚数据给保通(执法流程)
     */
    @RequestMapping(value = "PushChuFaData")
    public String PushChuFaData(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        return dataService.PushChuFaData(recordId);
    }


    /**
     * 传送执法处罚数据给保通(专家流程)
     */
    @RequestMapping(value = "PushZhiFaChuFaData")
    public String PushZhiFaChuFaData(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        return dataService.PushZhiFaChuFaData(recordId);
    }


    /**
     * 传送一般隐患现场处理
     */
    @RequestMapping(value = "putGeneralHiddenDanger")
    public String putGeneralHiddenDanger(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String entId = request.getParameter("entId");
        return dataService.putGeneralHiddenDanger(recordId,entId);
    }






    /**
     * 传送自检封装数据
     */
    @RequestMapping(value = "putZiJianDate")
    public String putZiJianDate(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        return dataService.putZiJianDate(recordId);
    }


    /**
     * 获取企业信息
     * @param request
     * @return
     */
   /* @RequestMapping(value = "getCompanyMsg")
    public String getCompanyMsg(HttpServletRequest request) {
        String social_credit_code = request.getParameter("social_credit_code");
        return HttpUtil.sendGet("http://www.njyjgl.cn/em/enterprise?social_credit_code=" + social_credit_code );
    }*/

    /**
     * 获取企业信息
     * @param request
     * @return
     */
    @RequestMapping(value = "getCompanyMsg")
    public String getCompanyMsg(HttpServletRequest request ,HashMap<String, String> map ) {
        try {
            String ent_id = request.getParameter("social_credit_code");
            HashMap<String,String> map1=new HashMap<> (  );

            map1.put ( "query",ent_id );
            map1.put ( "field","pkid" );

            List<Map<String,String> > list = new ArrayList<> (  );
            list.add ( map1 );
            map.put ( "must" ,JSONObject.toJSONString(list) );
            String data="{\"must\":"+"[{\"field\":\"pkid\",\"query\":"+"\""+ent_id+"\""+"}]}";

            String url = "http://222.190.243.160/api/resource/query/unit_places_temp/1";
            String res= PostUtils.httpPost(url,data);
            return res;
        }catch (Exception e){
            e.printStackTrace ();
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }

    }


    /**
     * 获取执法人信息
     */
//    @RequestMapping(value = "getCheckeMsg")
//    public String getCheckeMsg(HttpServletRequest request) {
//        String china_name = request.getParameter("china_name");
//        return HttpUtil.sendGet(" http://datacenter.njyjgl.cn/api/resource/list/law_enforcement_officers/1" +china_name );
//    }
    @RequestMapping(value = "getZjCheckeMsg")
    @ResponseBody
    public Map<String,Object>  getZjCheckeMsg(@RequestParam(value = "word")String word){
        String url = "";
        Map<String,Object> map=new HashMap<> ();
        try {
            String china_name = URLEncoder.encode(word,"UTF-8");
            //url = "http://datacenter.njyjgl.cn/api/resource/list/law_enforcement_officers/1?china_name="+china_name;
            url = "http://222.190.243.160/api/resource/list/law_enforcement_officers/1?china_name="+china_name;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            map.put("encodeFail","编码失败");
        }
        String expert = HttpUtil.sendGet(url);
        org.json.JSONObject jsonObject = new org.json.JSONObject(expert);
        String data = jsonObject.get("data").toString();
        org.json.JSONArray jsonArray = new org.json.JSONArray(data);
        List<String> checkeName=new ArrayList<> ();
        List<String> checkeDepartment=new ArrayList<>();
        List<String> mobile=new ArrayList<>();
        List<String> enforcement_card=new ArrayList<>();
        for (Object e:jsonArray) {
            org.json.JSONObject jsonObject1 = new org.json.JSONObject(e.toString());
            String name = jsonObject1.get("china_name").toString();
            if(name.contains(word)){
                checkeName.add(name);
                checkeDepartment.add(jsonObject1.getString("department"));
                mobile.add(jsonObject1.getString("mobile"));
                enforcement_card.add(jsonObject1.getString("enforcement_card"));
            }
        }
        map.put("checkeName",checkeName);
        map.put("checkeDepartment",checkeDepartment);
        map.put("mobile",mobile);
        map.put("enforcement_card",enforcement_card);
        return map;
    }




    @RequestMapping(value = "getExpertMsg")
    @ResponseBody
    public Map<String,Object>  getExpertMsg(@RequestParam(value = "word")String word){
        String url = "";
        Map<String,Object> map=new HashMap<>();
        try {
            String name = URLEncoder.encode(word,"UTF-8");
            //url = "http://datacenter.njyjgl.cn/api/resource/list/security_expert/1?name="+name;
            url = "http://222.190.243.160/api/resource/list/security_expert/1?name="+name;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            map.put("encodeFail","编码失败");
        }
        String expert = HttpUtil.sendGet(url);
        org.json.JSONObject jsonObject = new org.json.JSONObject(expert);
        String data = jsonObject.get("data").toString();
        org.json.JSONArray jsonArray = new org.json.JSONArray(data);
        List<String> expertName=new ArrayList<>();
        List<String> expertDepartment=new ArrayList<>();
        for (Object e:jsonArray) {
            org.json.JSONObject jsonObject1 = new org.json.JSONObject(e.toString());
            String name = jsonObject1.get("name").toString();
            if(name.contains(word)){
                expertName.add(name);
                expertDepartment.add(jsonObject1.getString("department"));
            }
        }
        System.out.println(data);
        System.out.println(expertName);
        System.out.println(expertDepartment);
//        expertName = expertName.stream().distinct().collect(Collectors.toList());
//        expertDepartment = expertDepartment.stream().distinct().collect(Collectors.toList());
        map.put("expertName",expertName);
        map.put("expertDepartment",expertDepartment);
        return map;
    }

    /**
     * 传送专家较大隐患信息
     */
    @RequestMapping(value = "putExpertHiddenDangerDate")
    public String putExpertHiddenDangerDate(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String entId = request.getParameter("entId");
        return dataService.putExpertHiddenDangerDate(recordId,entId);
    }
    /**
     * 获取专家检查页面中的安检员信息
     */
    @RequestMapping(value = "getCheckDetailById")
    public String getCheckDetailById(HttpServletRequest request) {
        String social_credit_code = request.getParameter("social_credit_code");
        //return HttpUtil.sendGet(" http://api.njyjgl.cn/spp_grid_dev/security/staff/" + social_credit_code );
        return HttpUtil.sendGet(" http://222.190.243.160/spp_grid_dev/security/staff/" + social_credit_code );

    }

    /**
     * 获取执法通知书检查中的安检员信息
     */
    @RequestMapping(value = "getCheckerByUserId")
    public String getCheckerByUserId(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        return HttpUtil.sendGet(" http://api.njyjgl.cn/spp_grid_dev/security/staff/person/" + userId );

    }


    /**
     * 传送执法复查数据
     * @param
     * @return
     */
    @RequestMapping(value = "putRectificationDate")
    public String putRectificationDate(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String entId = request.getParameter("entId");
        return dataService.putRectificationDate(recordId,entId);
    }
    /**
     * 传送执法缓存数据
     */
    @RequestMapping(value = "putZhiFaTemporaryDate")
    public String putZhiFaTemporaryDate(HttpServletRequest request) {
        String RECORD_ID = request.getParameter("RECORD_ID");
        String URL = request.getParameter ( "URL" );
        String USER_ID = request.getParameter ( "USER_ID" );
        String SOCIAL_CREDIT_CODE = request.getParameter ( "SOCIAL_CREDIT_CODE" );

        return dataService.putZhiFaTemporaryDate(RECORD_ID,URL,USER_ID,SOCIAL_CREDIT_CODE);
    }

    /**
     * 传送专家缓存数据
     */
    @RequestMapping(value = "putTemporaryDate")
    public String putTemporaryDate(HttpServletRequest request) {
        String RECORD_ID = request.getParameter("RECORD_ID");
        String URL = request.getParameter ( "URL" );
        String USER_ID = request.getParameter ( "USER_ID" );
        String SOCIAL_CREDIT_CODE = request.getParameter ( "SOCIAL_CREDIT_CODE" );

        return dataService.putTemporaryDate(RECORD_ID,URL,USER_ID,SOCIAL_CREDIT_CODE);
    }


    /**
     * 传送专家检查合格数据
     */
    @RequestMapping(value = "putExpertHeGeDate")
    public String putExpertHeGeDate(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String entId = request.getParameter("entId");
        return dataService.putExpertHeGeDate(recordId,entId);
    }
    /**
     * 传送九小场所限期改正数据
     */
    @RequestMapping(value = "putFireFightingTimeLimitDate")
    public String putFireFightingTimeLimitDate(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String userId = request.getParameter("userId");
        String sCode = request.getParameter("sCode");
        return dataService.putFireFightingTimeLimitDate(recordId,userId,sCode);
    }
    /**
     * 传送九小场所立即改正数据
     */
    @RequestMapping(value = "putTimeRectificationDate")
    public String putTimeRectificationDate(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String userId = request.getParameter("userId");
        String sCode = request.getParameter("sCode");
        return dataService.putTimeRectificationDate(recordId,userId,sCode);
    }
    /**
     * 传送九小场所合格数据
     */
    @RequestMapping(value = "putFireFightingDate")
    public String putFireFightingDate(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        String userId = request.getParameter("userId");
        String sCode = request.getParameter("sCode");
        return dataService.putFireFightingDate(recordId,userId,sCode);
    }
    /**
     * 传送九小场所重大隐患数据
     */
   /* @RequestMapping(value = "putFireHiddenDangerRecordDate")
    public String putFireHiddenDangerRecordDate(HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        return dataService.putFireHiddenDangerRecordDate(recordId);
    }*/
}
