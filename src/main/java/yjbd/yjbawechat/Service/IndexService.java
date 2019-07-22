package yjbd.yjbawechat.Service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.IndexDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yjbd.yjbawechat.Util.MsgUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class IndexService {
    @Resource
    IndexDao indexDao;

    static final Logger logger = LoggerFactory.getLogger(IndexService.class);

    public String InsertMap(String formattedAddress){
        String res = "";
        try{
            int result =indexDao.InsertMap(formattedAddress);
            if(result>0){
                res = "success";
            }else{
                res = "fail";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="服务器繁忙,请稍后再试";
        }
        return res;
    }

    /*
     * 查询首页显示内容
     * parse 无
     */
    public String checkOneItemService(){
        String res="";
        try{
            List<Map> result =indexDao.checkOneItemServiceDao();
            if(result.size()>0){
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }

    /*
     * 根据userId查询此人上一次的执法记录id
     * parse userId
     */
    public String checkExPreRecordByUserId(String userId,String social_credit_code){
        String res="";
        int count=0;
        try{
            count=indexDao.checkExPreRecordByUserId(userId,social_credit_code); //查询是否有检查记录
            if(count>0){
                List<Map> result =indexDao.checkExPreRecordNewListByUserId(userId,social_credit_code); //查询最近一条检查记录
                if(result.size()>0){
                    res= JSONObject.toJSONString(result);
                }else{
                    res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
                }
            }else{
                res="[{\"msg\":\"no\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }



    /*
     * 根据userId查询此人上一次的执法记录id
     * parse userId
     */
    public String checkPreRecordService(String userId,String social_credit_code){
        String res="";
        int count=0;
        try{
            count=indexDao.checkIsHaveRecordServiceDao(userId,social_credit_code); //查询是否有检查记录
            if(count>0){
                List<Map> result =indexDao.checkNewLastRecordServiceDao(userId,social_credit_code); //查询最近一条检查记录
                if(result.size()>0){
                    res= JSONObject.toJSONString(result);
                }else{
                    res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
                }
            }else{
                res="[{\"msg\":\"no\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }



    /*
     * 查询第二级内容by oneId
     * parse  oneId
     */
    public String checkTwoItemService(String oneId){
        String res="";
        try{
            List<Map> result =indexDao.checkTwoItemServiceDao(oneId);
            if(result.size()>0){
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }



    /* 执法通知书流程
     * 查询第二级内容by oneId and twoId
     * parse  oneId
     */
    public String checkTwoItemServices(String oneId,String twoId){
        String res="";
        try{
            String[] arr=twoId.split("\\|");
            String condition="and ( ";

            for(int i=0;i<arr.length;i++){
                if(i==0){
                    condition+=" TWO_ID ='" + arr[i]+"'";
                }else{
                    condition+=" or TWO_ID ='" + arr[i]+"'";
                }

            }

            condition+=")";

            List<Map> result =indexDao.checkTwoItemServiceDaos(oneId,condition);
            if(result.size()>0){
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }



    /*
     * 查询第三级级内容by oneId and twoId
     * parse  oneId , twoId
     */
    public String checkThreeItemService(String oneId,String twoId){
        String res="";
        try{
            List<Map> result =indexDao.checkThreeItemServiceDao(oneId,twoId);
            if(result.size()>0){
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }



    /* 执法流程
     * 查询第四级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    public String checkFourItemService(String oneId,String twoId ,String threeId,String  RecordId){
        String res="";
        String social_credit_code=""; //社会信用代码
        int count=0;        //当月检查记录条数
        try
        {
            social_credit_code=indexDao.checkSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.checkFourItemServiceeDao(oneId,twoId,threeId);
            if(result.size()>0){
                for(Map map : result){

                    String TEMP_ID=map.get("ONE_ID").toString()+"-"+map.get("TWO_ID").toString()+"-"+map.get("THREE_ID").toString()+"-"+map.get("FOUR_ID").toString();
                    count=indexDao.checkRecordCountBySocialCreditCodeAndTempId(social_credit_code,TEMP_ID); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if(count>0)
                        map.put("flag","1");
                    else
                        map.put("flag","0");
                }
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }


    public String exCheckFourItem(String oneId,String twoId ,String threeId,String  RecordId){
        String res="";
        String social_credit_code=""; //社会信用代码

        int count=0;        //当月检查记录条数
        try
        {
            social_credit_code=indexDao.exCheckSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.exCheckFourItemServiceeDao(oneId,twoId,threeId);
            if(result.size()>0){
                for(Map map : result){
                    String TEMP_ID=map.get("ONE_ID").toString()+"-"+map.get("TWO_ID").toString()+"-"+map.get("THREE_ID").toString()+"-"+map.get("FOUR_ID").toString();
                    count=indexDao.exCheckRecordCountBySocialCreditCodeAndTempId(social_credit_code,TEMP_ID); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if(count>0)
                        map.put("flag","1");
                    else
                        map.put("flag","0");
                }
                res= JSONObject.toJSONString(result);
            }
            else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }

        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }

    /* 自检流程
     * 查询第四级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    public String zjCheckFourItem(String oneId,String twoId ,String threeId,String  RecordId){
        String res="";
        String UNIT_NUMBER=""; //社会信用代码
        int count=0;        //当月检查记录条数
        try
        {
            UNIT_NUMBER=indexDao.zjCheckSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.zjCheckFourItemServiceeDao(oneId,twoId,threeId);
            if(result.size()>0){
                for(Map map : result){

                    String TEMP_ID=map.get("ONE_ID").toString()+"-"+map.get("TWO_ID").toString()+"-"+map.get("THREE_ID").toString()+"-"+map.get("FOUR_ID").toString();
                    count=indexDao.zjCheckRecordCountBySocialCreditCodeAndTempId(UNIT_NUMBER,TEMP_ID); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if(count>0)
                        map.put("flag","1");
                    else
                        map.put("flag","0");
                }
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }

    /* 执法流程
     * 查询第五级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId , fourId
     */
    public String checkFiveItemService(String oneId,String twoId ,String threeId,String fourId,String  RecordId){
        String res="";
        String social_credit_code=""; //社会信用代码
        int count=0;        //当月检查记录条数
        try{
            social_credit_code=indexDao.checkSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.checkFiveItemServiceeDao(oneId,twoId,threeId,fourId);
            if(result.size()>0){
                for (Map map : result) {
                    String TEMP_ID=map.get("ONE_ID").toString()+"-"+map.get("TWO_ID").toString()+"-"+map.get("THREE_ID").toString()+"-"+map.get("FOUR_ID").toString()+"-"+map.get("FIVE_ID").toString();
                    count=indexDao.checkRecordCountBySocialCreditCodeAndTempId(social_credit_code,TEMP_ID); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if(count>0)
                        map.put("flag","1");
                    else
                        map.put("flag","0");
                }
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }



    /*
     * 查询专家第五级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId , fourId
     */
    public String exCheckFiveItem(String oneId,String twoId ,String threeId,String fourId,String  RecordId){
        String res="";
        String RECORD_ID=""; //社会信用代码
        String social_credit_code=""; //社会信用代码
        int count=0;        //当月检查记录条数
        try
        {
            social_credit_code=indexDao.exCheckFiveSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.exCheckFiveItemServiceeDao(oneId,twoId,threeId,fourId);
            if(result.size()>0){
                for(Map map : result){

                    String TEMP_ID=map.get("ONE_ID").toString()+"-"+map.get("TWO_ID").toString()+"-"+map.get("THREE_ID").toString()+"-"+map.get ( "FOUR_ID" ).toString ()+"-"+map.get("FIVE_ID").toString();
                    count=indexDao.exCheckFiveRecordCountBySocialCreditCodeAndTempId(social_credit_code,TEMP_ID); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if(count>0)
                        map.put("flag","1");
                    else
                        map.put("flag","0");
                }
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }


        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }

    /* 自检流程
     * 查询第五级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId , fourId
     */
    public String ZjcheckFiveItemService(String oneId,String twoId ,String threeId,String fourId,String RecordId){
        String res="";
        String UNIT_NUMBER=""; //社会信用代码
        int count=0;        //当月检查记录条数
        try{
            UNIT_NUMBER=indexDao.zjCheckSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.ZjcheckFiveItemServiceeDao(oneId,twoId,threeId,fourId);
            if(result.size()>0){
                for(Map map : result) {

                    String TEMP_ID = map.get ( "ONE_ID" ).toString () + "-" + map.get ( "TWO_ID" ).toString () + "-" + map.get ( "THREE_ID" ).toString () + "-" + map.get ( "FOUR_ID" ).toString ()+ "-" + map.get ( "FIVE_ID" ).toString ();
                    count = indexDao.zjCheckRecordCountBySocialCreditCodeAndTempId ( UNIT_NUMBER, TEMP_ID ); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if (count > 0)
                        map.put ( "flag", "1" );
                    else
                        map.put ( "flag", "0" );
                }
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }




    /* 执法流程
     * 查询第六级内容by oneId and twoId and threeId and fourId and fiveId
     * parse  oneId , twoId ,threeId , fourId,fiveId
     */
    public String checkSixItemService(String oneId,String twoId ,String threeId,String fourId,String fiveId,String RecordId){
        String res="";
        String social_credit_code=""; //社会信用代码
        int count=0;        //当月检查记录条数
        try{
            social_credit_code=indexDao.checkSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.checkSixItemServiceeDao(oneId,twoId,threeId,fourId,fiveId);
            if(result.size()>0){
                for(Map map : result){
                    String TEMP_ID=map.get("ONE_ID").toString()+"-"+map.get("TWO_ID").toString()+"-"+map.get("THREE_ID").toString()+"-"+map.get("FOUR_ID").toString()+"-"+map.get("FIVE_ID").toString()+"-"+map.get("SIX_ID").toString();
                    count=indexDao.checkRecordCountBySocialCreditCodeAndTempId(social_credit_code,TEMP_ID); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if(count>0)
                        map.put("flag","1");
                    else
                        map.put("flag","0");
                }
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }




    /* 专家流程
     * 查询专家第六级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId , fourId
     */
    public String exCheckSixItem(String oneId,String twoId ,String threeId,String fourId,String fiveId,String  RecordId){
        String res="";
        String social_credit_code=""; //社会信用代码
        String RECORD_ID=""; //社会信用代码
        int count=0;        //当月检查记录条数
        try
        {
            social_credit_code=indexDao.exCheckSixSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.exCheckSixItemServiceeDao(oneId,twoId,threeId,fourId,fiveId);
            if(result.size()>0){
                for(Map map : result){

                    String TEMP_ID=map.get("ONE_ID").toString()+"-"+map.get("TWO_ID").toString()+"-"+map.get("THREE_ID").toString()+"-"+map.get ( "FOUR_ID" ).toString ()+"-"+map.get ("FIVE_ID").toString ()+"-"+map.get("SIX_ID").toString();
                    count=indexDao.exCheckSixRecordCountBySocialCreditCodeAndTempId(social_credit_code,TEMP_ID); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if(count>0)
                        map.put("flag","1");
                    else
                        map.put("flag","0");
                }
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }

    /* 自检流程
     * 查询第六级内容by oneId and twoId and threeId and fourId and fiveId
     * parse  oneId , twoId ,threeId , fourId,fiveId
     */
    public String ZjcheckSixItemService(String oneId,String twoId ,String threeId,String fourId,String fiveId,String RecordId){
        String res="";
        String UNIT_NUMBER=""; //社会信用代码
        int count=0;        //当月检查记录条数
        try{
            UNIT_NUMBER=indexDao.zjCheckSocialCreditCodeByRecordId(RecordId); //根据RecordId查询社会信用代码
            List<Map> result =indexDao.ZjcheckSixItemServiceeDao(oneId,twoId,threeId,fourId,fiveId);
            if(result.size()>0){
                for(Map map : result) {
                    String TEMP_ID = map.get ( "ONE_ID" ).toString () + "-" + map.get ( "TWO_ID" ).toString () + "-" + map.get ( "THREE_ID" ).toString () + "-" + map.get ( "FOUR_ID" ).toString ()+ "-" + map.get ( "FIVE_ID" ).toString ()+ map.get ( "SIX_ID" ).toString ();
                    count = indexDao.zjCheckRecordCountBySocialCreditCodeAndTempId ( UNIT_NUMBER, TEMP_ID ); //根据社会信用代码和模板id查询该模板当月执法检查记录数
                    if (count > 0)
                        map.put ( "flag", "1" );
                    else
                        map.put ( "flag", "0" );
                }
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙,请稍后再试...\"}]";
        }
        return res;
    }


    /*
     * 关键字查询模板
     * parse  keyWord
     */
    public String checkTempByKeyWordService(String keyWord){
        String res="";
        try{
            keyWord="%"+keyWord+"%";
            List<Map> result =indexDao.checkTempByKeyWordServiceDao(keyWord);
            if(result.size()>0){
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
        }
        return res;
    }



    /*
     * 查询所有的模板
     * parse
     */
    public String checkAllTempService(){
        String res="";
        try{
            List<Map> result =indexDao.checkAllTempServiceDao1();
            List<Map> result2 =indexDao.checkAllTempServiceDao2();
            for (int i=0;i<result2.size();i++)
            {
               result.add(result2.get(i));
            }


            if(result.size()>0){
                res= JSONObject.toJSONString(result);
            }else{
                res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
            }
        }catch(Exception e){
            logger.info(e.toString());
            res="[{\"msg\":\"fail\",\"failInfo\":\"服务器繁忙，请稍后再试...\"}]";
        }
        return res;
    }


    /*
     * 创建检查
     * parse
     */
    public String createEnforce(String TEMP_ID,String TEMP_PDF,String TEMP_NAME,String USER_ID, String CHECK_STATE, String SERVICE_ID,    String CHECKED_START_TIME ,String CHECKED_END_TIME,     String CHECKE_DETAIL,  String VIDEO_URL ,String LOCATION_IMG ,String OTHER_IMG) {
        String responseText="";
        Random random=new Random();
//        int count =0;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String createTime=df.format(new Date()).toString()+random.nextInt(100000);
        try {
//            count = indexDao.getGreatPaperInfoCount(SERVICE_ID,TEMP_ID);
//            if (count>0){
//                indexDao.createPaperInfo1( TEMP_ID,TEMP_PDF,TEMP_NAME , USER_ID ,CHECK_STATE, SERVICE_ID,       CHECKED_START_TIME ,CHECKED_END_TIME,      CHECKE_DETAIL,    VIDEO_URL, LOCATION_IMG, OTHER_IMG,createTime);
//                responseText = MsgUtils.successMsg();
//            }else {
                indexDao.createPaperInfo( TEMP_ID,TEMP_PDF,TEMP_NAME , USER_ID ,CHECK_STATE, SERVICE_ID,       CHECKED_START_TIME ,CHECKED_END_TIME,      CHECKE_DETAIL,    VIDEO_URL, LOCATION_IMG, OTHER_IMG,createTime);
                responseText = MsgUtils.successMsg();
//            }
        }
        catch (Exception e){ e.printStackTrace();
            logger.info(e.toString());
            return MsgUtils.errorMsg("服务器繁忙，请稍后再试...");
        }
        return responseText;
    }



    /*
     * 创建检查(企业内查)
     * parse
     */
    public String zjCreateEnforce(String TEMP_ID,String TEMP_PDF,String TEMP_NAME,String USER_ID, String CHECK_STATE, String SERVICE_ID,    String CHECKED_START_TIME  ,     String CHECKE_DETAIL,  String VIDEO_URL ,String LOCATION_IMG ,String OTHER_IMG ,String UNIT_NUMBER) {
        String responseText="";
        Random random=new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=df.format(new Date()).toString()+random.nextInt(100000);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CHECKED_END_TIME=df1.format(new Date()).toString();
        try {
            indexDao.zjCreatePaperInfo( TEMP_ID,TEMP_PDF,TEMP_NAME ,      USER_ID ,CHECK_STATE, SERVICE_ID,       CHECKED_START_TIME ,CHECKED_END_TIME,      CHECKE_DETAIL,    VIDEO_URL, LOCATION_IMG, OTHER_IMG,createTime,UNIT_NUMBER);
            responseText = MsgUtils.successMsg();
        }
        catch (Exception e){ e.printStackTrace();
        logger.info ( e.toString () );
            return MsgUtils.errorMsg("服务器繁忙，请稍后再试...");
        }
        return responseText;
    }
    /*
     * 创建企业内查信息 生成record_id
     * parse
     */
    public String zjCreateEnforceService(String CHECKED_UNIT,String ADDRESS,String REPRESENT_PEOPLE,String USER_ID,String UNIT_NUMBER) {
        String responseText="";
        Random random=new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=df.format(new Date()).toString();

        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String RecordId=df2.format(new Date()).toString()+random.nextInt(100000);

        try {
            indexDao.zjCreateEnforceCompanyDal( CHECKED_UNIT,ADDRESS,REPRESENT_PEOPLE, USER_ID,createTime,RecordId,UNIT_NUMBER);
            responseText ="[{\"backFlag\":\"ok\",\"RecordId\":\""+RecordId+"\"}]";
        }
        catch (Exception e){ e.printStackTrace();
        logger.info ( e.toString () );
            return MsgUtils.errorMsg("服务器繁忙，请稍后再试...");
        }
        return responseText;
    }


    /*
     * 创建检查(专家检查)
     * parse
     */
    public String exCreateEnforce(String TEMP_ID,String TEMP_PDF,String TEMP_NAME,String USER_ID, String CHECK_STATE, String SERVICE_ID,
                                  String CHECKED_START_TIME ,String CHECKED_END_TIME,     String CHECKE_DETAIL,  String VIDEO_URL ,String LOCATION_IMG ,String OTHER_IMG,String UNIT_NUMBER) {
        int count = 0;
        String responseText="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=df.format(new Date()).toString();
        try {
//            count=indexDao.checkRecordIdCount(SERVICE_ID,TEMP_ID);
//            if (count>0){
//                indexDao.exCreateEnforce( UNIT_NUMBER,TEMP_PDF,TEMP_NAME , USER_ID ,CHECK_STATE, CHECKED_START_TIME ,
//                        CHECKED_END_TIME,CHECKE_DETAIL,    VIDEO_URL, LOCATION_IMG, OTHER_IMG,createTime, SERVICE_ID,TEMP_ID);
//                responseText = MsgUtils.successMsg();
//            }else {
//                indexDao.exCreateEnforce1(UNIT_NUMBER, TEMP_ID,TEMP_PDF,TEMP_NAME , USER_ID ,CHECK_STATE, SERVICE_ID, CHECKED_START_TIME ,CHECKED_END_TIME,   CHECKE_DETAIL,
//                        VIDEO_URL, LOCATION_IMG, OTHER_IMG,createTime);
//                responseText = MsgUtils.successMsg();
//            }
            indexDao.exCreatePaperInfo( TEMP_ID,TEMP_PDF,TEMP_NAME ,      USER_ID ,CHECK_STATE, SERVICE_ID,       CHECKED_START_TIME ,CHECKED_END_TIME,      CHECKE_DETAIL,    VIDEO_URL, LOCATION_IMG, OTHER_IMG,createTime,UNIT_NUMBER);
            responseText = MsgUtils.successMsg();
        }
        catch (Exception e){ e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("服务器繁忙，请稍后再试...");
        }
        return responseText;
    }

    /**
     * 专家检查暂存更新第四级目录
     * @param
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    public String temporaryExCreateEnforce(String TEMP_ID,String TEMP_PDF,String TEMP_NAME,String USER_ID, String CHECK_STATE, String SERVICE_ID,
                                           String CHECKED_START_TIME ,String CHECKED_END_TIME,     String CHECKE_DETAIL,
                                           String VIDEO_URL ,String LOCATION_IMG ,String OTHER_IMG,String URL) {
        int count = 0;
        String responseText="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=df.format(new Date()).toString();
        try {
            count=indexDao.checkRecordIdCount(SERVICE_ID,TEMP_ID);
            if (count>0){
                indexDao.temporaryExCreateEnforce( TEMP_PDF,TEMP_NAME , USER_ID ,CHECK_STATE, CHECKED_START_TIME ,
                        CHECKED_END_TIME,CHECKE_DETAIL,    VIDEO_URL, LOCATION_IMG, OTHER_IMG,createTime,URL, SERVICE_ID,TEMP_ID);
                responseText = MsgUtils.successMsg();
            }else {
                indexDao.temporaryExCreateEnforce1( TEMP_ID,TEMP_PDF,TEMP_NAME , USER_ID ,CHECK_STATE, SERVICE_ID, CHECKED_START_TIME ,CHECKED_END_TIME,   CHECKE_DETAIL,
                        VIDEO_URL, LOCATION_IMG, OTHER_IMG,createTime,URL);
                responseText = MsgUtils.successMsg();
            }

        }
        catch (Exception e){ e.printStackTrace();
            logger.info(e.toString());
            return MsgUtils.errorMsg("服务器繁忙，请稍后再试...");
        }
        return responseText;
    }




    /*
     * 创建专家检查信息 生成record_id
     * parse
     */
    public String exCreateEnforceService(String CHECKED_UNIT,String ADDRESS,String REPRESENT_PEOPLE,String USER_ID,String UNIT_NUMBER) {
        String responseText="";
        Random random=new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=df.format(new Date()).toString();

        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String RecordId=df2.format(new Date()).toString()+random.nextInt(1000);

        try {
            indexDao.exCreateEnforceCompanyDal( CHECKED_UNIT,ADDRESS,REPRESENT_PEOPLE, USER_ID,createTime,RecordId,UNIT_NUMBER);
            responseText ="[{\"backFlag\":\"ok\",\"RecordId\":\""+RecordId+"\"}]";
        }
        catch (Exception e){ e.printStackTrace();
            logger.info(e.toString());
            return MsgUtils.errorMsg("服务器繁忙，请稍后再试...");
        }
        return responseText;
    }



}
