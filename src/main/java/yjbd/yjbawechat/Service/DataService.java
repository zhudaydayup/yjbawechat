package yjbd.yjbawechat.Service;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yjbd.yjbawechat.Dao.DataDao;
import yjbd.yjbawechat.Dao.expert.ExpertDao;
import yjbd.yjbawechat.Util.HttpUtil;
import yjbd.yjbawechat.Util.MsgUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Totle:DataService
 * @ProjectName:yjbawechat
 * @author:社会码农
 * @data:2019/4/118:05
 */
@Service
public class DataService {

    @Autowired
    private DataDao dataDao;
    @Autowired
    private ExpertDao expertDao;
    static final Logger logger = LoggerFactory.getLogger(DataService.class);

    /**
     * 传送执法数据
     * @param recordId
     * @return
     */
    public String PushData(String recordId,String entId) {

        try {
            HashMap<String, String> map = new HashMap<>();

            Map data1 = dataDao.getSceneRecord(recordId);
            String social_credit_code = data1.get("UNIT_NUMBER").toString();//企业社会信用代码
            String enterprise_name = data1.get("CHECKED_UNIT").toString();//被检单位

            String checker_id = data1.get("USER_ID").toString();//记录创建者的USER_ID

            StringBuffer supervisor_id = new StringBuffer();
            if (data1.get("CARD_NUMBER1") != null) {
                supervisor_id.append(data1.get("CARD_NUMBER1").toString());//检查人用户标识1
            }
            if (data1.get("CARD_NUMBER2") != null) {
                supervisor_id.append("|");
                supervisor_id.append(data1.get("CARD_NUMBER2").toString());//检查人用户标识2
            }
            StringBuffer checker = new StringBuffer();
            if (data1.get("CHECK_PEOPLE1") != null) {
                checker.append(data1.get("CHECK_PEOPLE1").toString());//检查人1
            }
            if (data1.get("CHECK_PEOPLE2") != null) {
                checker.append("|");
                checker.append(data1.get("CHECK_PEOPLE2").toString());//检查人2
            }


            String checked_start_time = data1.get("CHECKED_START_TIME").toString();//检查开始时间
            String checked_end_time = data1.get("CHECKED_END_TIME").toString();//检查结束时间


            StringBuffer checked_detail=new StringBuffer();//检查情况
            StringBuffer checked_status=new StringBuffer();//检查状态
            List<Map> data2 = dataDao.getITEMRecord(recordId);
            for (int i=0;i<data2.size();i++){
                if (i+1<data2.size()){
                    checked_detail.append(data2.get(i).get("CHECKE_DETAIL").toString());//检查情况
                    checked_detail.append("|");
                    checked_status.append(data2.get(i).get("CHECK_STATE").toString());//检查状态
                    checked_status.append("|");
                }else{
                    checked_detail.append(data2.get(i).get("CHECKE_DETAIL").toString());//检查情况
                    checked_status.append(data2.get(i).get("CHECK_STATE").toString());//检查状态
                }

            }


            StringBuffer checked_experts = new StringBuffer();
            if (data1.get("EXPERT_NAME1") != null) {
                checked_experts.append(data1.get("EXPERT_NAME1").toString());//检查专家1
            }
            if (data1.get("EXPERT_NAME2") != null) {
                checked_experts.append("|");
                checked_experts.append(data1.get("EXPERT_NAME2").toString());//检查专家2
            }

            StringBuffer checked_deps = new StringBuffer();
            if (data1.get("CHECK_UNIT1") != null) {
                checked_deps.append(data1.get("CHECK_UNIT1").toString());//检查人1单位
            }
            if (data1.get("CHECK_UNIT2") != null) {
                checked_deps.append("|");
                checked_deps.append(data1.get("CHECK_UNIT2").toString());//检查人2单位
            }

            String is_correction="";
            String correction_time="";
            Map data3 = dataDao.getZelingRecord(recordId);
            if (data3.size()>0) {
                correction_time=data3.get("TIME_IDS").toString();//整改限制时间
            }

            if (correction_time!=null&&!correction_time.equals("")){
                is_correction="4";//整改
                map.put("risk_level", "责令限期整改");
            }else{
                is_correction="3";//不整改
                map.put("risk_level", "");
            }

            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";


            String notice_law_documents =pdf+"notice/"+ data1.get("NOTICE_PDF").toString();//通知文书pdf
            String notice_law_documents_qr =qrcode+"notice/"+ data1.get("NOTICE_QR_CODE").toString();//通知文书二维码
            String record_law_documents =pdf+"record/"+ data1.get("RECORD_PDF").toString();//记录文书PDF
            String record_law_documents_qr =qrcode+"record/"+ data1.get("RECORD_QR_CODE").toString();//记录文书二维码

            List<Map> data4 = dataDao.getPUNISHRecord(recordId);//PROCESS_DECISION
            StringBuffer PROCESS_DECISION=new StringBuffer();//处罚决定
            StringBuffer punish_law_documents =new StringBuffer();//处理文书PDF
            StringBuffer punish_law_documents_qr =new StringBuffer();//处理文书二维码

            if (data4.size()>0){
                for (int i=0;i<data4.size();i++){
                    if (i+1<data4.size()){
                        punish_law_documents.append(pdf+"xianchangchuli/"+data4.get(i).get("PDF_PATH").toString());
                        punish_law_documents.append("|");
                        punish_law_documents_qr.append(qrcode+"xianchangchuli/"+data4.get(i).get("QRCODE_PATH").toString());
                        punish_law_documents_qr.append("|");
                        PROCESS_DECISION.append("PROCESS_DECISION");
                        PROCESS_DECISION.append("|");
                    }else{
                        punish_law_documents.append(pdf+"xianchangchuli/"+data4.get(i).get("PDF_PATH").toString());
                        punish_law_documents_qr.append(qrcode+"xianchangchuli/"+data4.get(i).get("QRCODE_PATH").toString());
                        PROCESS_DECISION.append("PROCESS_DECISION");
                    }

                }
            }else {
                punish_law_documents.append("");
                punish_law_documents_qr.append("");
                PROCESS_DECISION.append("");
            }


            map.put("id", recordId);
            map.put("ent_name", enterprise_name);
            map.put("social_credit_code", social_credit_code);
            map.put("checker_id", checker_id);
            map.put("checker", checker.toString());
            map.put("checked_start_time",checked_start_time);
            map.put("checked_end_time", checked_end_time);

            map.put("checked_detail", checked_detail.toString());
            map.put("checked_status", checked_status.toString());
            map.put("checked_experts", checked_experts.toString());
            map.put("checked_deps", checked_deps.toString());
            map.put("is_correction",is_correction);

            map.put("checked_type", "执法监督");
            map.put("supervisor_id",checker_id);

            map.put("record_id", recordId);
            map.put("correction_detail", PROCESS_DECISION.toString());
            if (correction_time != null) {
                map.put("rectify_end_time", correction_time);
            } else {
                map.put("rectify_end_time", "无");
            }

            map.put("notice_law_document", notice_law_documents);
            map.put("notice_law_document_qr", notice_law_documents_qr);
            map.put("record_law_document", record_law_documents);
            map.put("record_law_document_qr", record_law_documents_qr);
            map.put("punish_law_document", punish_law_documents.toString());
            map.put("punish_law_document_qr",punish_law_documents_qr.toString());
            if (is_correction=="1"){
                String rectify_law_documents =pdf+"zeling/"+ data3.get("ENFORCE_PAPER").toString();//整改文书PDF
                String rectify_law_documents_qr =qrcode+"zeling/"+ data3.get("QR_CODE_URL").toString();//整改文书二维码
                map.put("rectify_law_document", rectify_law_documents);
                map.put("rectify_law_document_qr",rectify_law_documents_qr);
            }else{
                map.put("rectify_law_document", "");
                map.put("rectify_law_document_qr","");
            }
            map.put("review_law_document","");
            map.put("review_law_document_qr","");
            map.put("ent_id",entId);
            String url="http://api.njyjgl.cn/spp_grid_dev/resource/pre_storage/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }


    /**
     * 传送执法复查数据
     */
    public String putRectificationDate(String recordId,String entId) {
        String id = recordId;//记录标识
        String ent_name ="";//企业名称
        String social_credit_code ="";//企业社会信用代码
        String checker_id="";//检查人用户标识
        StringBuffer checker = new StringBuffer();//检查人
        String checked_start_time="";//检查开始时间
        String checked_end_time="";//检查结束时间
        StringBuffer checked_detail=new StringBuffer();//检查情况
        StringBuffer checked_status=new StringBuffer();//检查状态
        StringBuffer checked_experts = new StringBuffer();//执法专家
        StringBuffer checked_deps = new StringBuffer();//执法部门
        String is_correction="";//是否限期整改
        String record_id=recordId;//执法记录ID
        String correction_detail="";//处罚决定/整改措施

        String notice_law_document="";//通知书
        String notice_law_document_qr="";//通知书二维码
        String record_law_document="";//检查记录文书
        String record_law_document_qr="";//检查记录二维码
        String punish_law_document="";//现场整改文书
        String punish_law_document_qr="";//现场整改二维码
        String rectify_law_document="";//责令整改文书
        String rectify_law_document_qr="";//责令整改文书二维码

        String review_law_document="";//复查文书
        String review_law_document_qr="";//复查文书二维码
        String review_type="核查结束";//复查结果类型(现场处置决定，限期整改，执法处罚，核查结束)
        String rectify_end_time="";//整改期限（当复查结果类型为限期整改的时候，需要传这个字段）
        String is_end="1";//核查是否结束（当复查结果类型为现场处理决定和核查结束时，传1，否则0）
        String ent_id=entId;//企业的唯一标识

        try {
            HashMap<String, String> map = new HashMap<>();

            Map data1 = dataDao.getSceneRecord(recordId);
            social_credit_code = data1.get("UNIT_NUMBER").toString();//企业社会信用代码
            ent_name = data1.get("CHECKED_UNIT").toString();//被检单位
            checker_id = data1.get("USER_ID").toString();//记录创建者的USER_ID

            if (data1.get("CHECK_PEOPLE1") != null) {
                checker.append(data1.get("CHECK_PEOPLE1").toString());//检查人1
            }
            if (data1.get("CHECK_PEOPLE2") != null) {
                checker.append("|");
                checker.append(data1.get("CHECK_PEOPLE2").toString());//检查人2
            }

            checked_start_time = data1.get("CHECKED_START_TIME").toString();//检查开始时间
            checked_end_time = data1.get("CHECKED_END_TIME").toString();//检查结束时间

            List<Map> data2 = dataDao.getITEMRecord(recordId);
            for (int i=0;i<data2.size();i++){
                if (i+1<data2.size()){
                    checked_detail.append(data2.get(i).get("CHECKE_DETAIL").toString());//检查情况
                    checked_detail.append("|");
                    checked_status.append(data2.get(i).get("CHECK_STATE").toString());//检查状态
                    checked_status.append("|");
                }else{
                    checked_detail.append(data2.get(i).get("CHECKE_DETAIL").toString());//检查情况
                    checked_status.append(data2.get(i).get("CHECK_STATE").toString());//检查状态
                }

            }


            if (data1.get("EXPERT_NAME1") != null) {
                checked_experts.append(data1.get("EXPERT_NAME1").toString());//检查专家1
            }
            if (data1.get("EXPERT_NAME2") != null) {
                checked_experts.append("|");
                checked_experts.append(data1.get("EXPERT_NAME2").toString());//检查专家2
            }

            if (data1.get("CHECK_UNIT1") != null) {
                checked_deps.append(data1.get("CHECK_UNIT1").toString());//检查人1单位
            }
            if (data1.get("CHECK_UNIT2") != null) {
                checked_deps.append("|");
                checked_deps.append(data1.get("CHECK_UNIT2").toString());//检查人2单位
            }

            Map data3 = dataDao.getZelingRecord(recordId);
            if (data3.size()>0) {
                rectify_end_time=data3.get("TIME_IDS").toString();//整改限制时间
            }

            if (rectify_end_time!=null&&!rectify_end_time.equals("")){
                is_correction="1";//整改
            }else{
                is_correction="0";//不整改
            }
            Map data4 = dataDao.getFuChaRecord(recordId);
            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";
            review_law_document =pdf+"fucha/"+ data4.get("PDF_PATH").toString();//复查文书
            review_law_document_qr =qrcode+"fucha/"+ data4.get("QRCODE_PATH").toString();//复查文书二维码
             notice_law_document =pdf+"notice/"+ data1.get("NOTICE_PDF").toString();//通知文书pdf
             notice_law_document_qr =qrcode+"notice/"+ data1.get("NOTICE_QR_CODE").toString();//通知文书二维码
             record_law_document =pdf+"record/"+ data1.get("RECORD_PDF").toString();//记录文书PDF
             record_law_document_qr =qrcode+"record/"+ data1.get("RECORD_QR_CODE").toString();//记录文书二维码


            map.put("id", id);
            map.put("ent_name", ent_name);
            map.put("social_credit_code", social_credit_code);
            map.put("checker_id", checker_id);
            map.put("checker", checker.toString());
            map.put("checked_start_time", checked_start_time);
            map.put("checked_end_time", checked_end_time);
            map.put("checked_detail", checked_detail.toString());
            map.put("checked_status", checked_status.toString());
            map.put("checked_experts", checked_experts.toString());
            map.put("checked_deps", checked_deps.toString());
            map.put("is_correction", is_correction);
            map.put("record_id", record_id);
            map.put("correction_detail", correction_detail);
            map.put("notice_law_document", notice_law_document);
            map.put("notice_law_document_qr", notice_law_document_qr);
            map.put("record_law_document", record_law_document);
            map.put("record_law_document_qr", record_law_document_qr);
            map.put("punish_law_document", punish_law_document);
            map.put("punish_law_document_qr", punish_law_document_qr);
            map.put("rectify_law_document", rectify_law_document);
            map.put("rectify_law_document_qr", rectify_law_document_qr);
            map.put("review_law_document", review_law_document);
            map.put("review_law_document_qr", review_law_document_qr);
            map.put("review_type", review_type);
            map.put("rectify_end_time", rectify_end_time);
            map.put("is_end", is_end);
            map.put("ent_id", ent_id);

            String url="http://api.njyjgl.cn/spp_grid_dev/resource/law_enforcement/enforcement_nust";
            String res= HttpUtil.httpPost2(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }






    /**
     * 传送执法处罚数据给保通(专家流程)
     */
    public String PushZhiFaChuFaData(String recordId){
        try {
            HashMap<String, String> map = new HashMap<>();
            Map data1 = dataDao.PushZhiFaChuFaData1(recordId);//ZHIFA_EXPERT_DANGER_RECORD
            Map data2 = dataDao.PushZhiFaChuFaData2(recordId);//ZHIFA_EXPERT_PUNISH_RECORD
            String pdf="http://zfxc.njyjgl.cn/yjbd2/";

            String UPID =  data2.get("UPID").toString();//记录编号 主键
            String CHECKED_UNIT =  data2.get("CHECKED_UNIT").toString();//被检查单位名称
            String UNIT_NUMBER =  data1.get("UNIT_NUMBER").toString();//单位机构编号
            String ADDRESS =  data1.get("ADDRESS").toString();//检查地址
            String RECORD_TIME =  data2.get("RECORD_TIME").toString();//检查日期
            String CHECKED_START_TIME =  data2.get("CHECKED_START_TIME").toString();//检查开始时间
            String CHECKED_END_TIME =  data2.get("CHECKED_START_TIME").toString();//检查结束时间
            String CHECKE_PLACE =  data1.get("CHECKE_PLACE").toString();//检查场所
            String SECURITY_PEOPLE =  data1.get("SECURITY_PEOPLE").toString();//检查员一
            String SECURITY_PEOPLE2 =  "";//检查员二
            String PROCESS_DECISION =  data2.get("PROCESS_DECISION").toString();//检查情况
            String Rectification_measures =  data2.get("PROCESS_DECISION").toString();//整改措施（整改意见）
            String PDF_PATH =  pdf+"expert_xianchangchuli/"+data2.get("PDF_PATH").toString();//执法文书URL
            String CHECKE_DETAIL =  data2.get("CHECKE_DETAIL").toString();//检查内容
            String Inspection_mode =  "现场检查";//检查方式
            String Illegal_activities =  "Illegal activities";//违法行为
            String Instrument_name =  "现场处置决定书";//文书名称

            String data = "VALUES('"+UPID+"','"+CHECKED_UNIT+"','"+UNIT_NUMBER+"','"+ADDRESS+"','"+RECORD_TIME+"','"+CHECKED_START_TIME+"','" +
                    CHECKED_END_TIME+"','"+CHECKE_PLACE+"','"+SECURITY_PEOPLE+"','"+SECURITY_PEOPLE2+"','"+PROCESS_DECISION+"','" +
                    Rectification_measures+"','"+PDF_PATH+"','"+CHECKE_DETAIL+"','"+Inspection_mode+"','"+Illegal_activities+"','"+Instrument_name+"')";
            map.put("cmd", data);

            String url="http://10.101.86.174:9999/basicopertionservices/event/services/insertXcjc";
            String res= HttpUtil.httpPost3(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }

    }



    /**
     * 传送执法处罚数据给保通(执法流程)
     */
    public String PushChuFaData(String recordId){
        try {
            HashMap<String, String> map = new HashMap<>();
            Map data = dataDao.getSceneRecord(recordId);//ZHIFA_SCENE_RECORD
            List<Map> data1 = dataDao.getITEMRecord(recordId);//ZHIFA_ITEM_RECORD
            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String RECORD_ID =  data.get("RECORD_ID").toString();//记录编号 主键
            String CHECKED_UNIT =  data.get("CHECKED_UNIT").toString();//被检查单位名称

            String UNIT_NUMBER = "无";//单位机构编号
            if (data.get("UNIT_NUMBER") != null) {
                UNIT_NUMBER = data.get("UNIT_NUMBER").toString();
            }
            String ADDRESS = "无";//检查地址
            if (data.get("ADDRESS") != null) {
                ADDRESS = data.get("ADDRESS").toString();
            }

            String CREATE_TIME =  data.get("CREATE_TIME").toString();//检查日期
            CREATE_TIME = CREATE_TIME.substring(0,10).replace("/","");
            String CHECKED_START_TIME =  data.get("CHECKED_START_TIME").toString();//检查开始时间
            CHECKED_START_TIME = CHECKED_START_TIME.split(" ")[1].toString().substring(0,5).replace(":","");

            String CHECKED_END_TIME =  data.get("CHECKED_END_TIME").toString();//检查结束时间
            CHECKED_END_TIME = CHECKED_END_TIME.split("T")[1].toString().substring(0,5).replace(":","");
            String CHECKED_LOCATION =  data.get("CHECKED_LOCATION").toString();//检查场所

            String CHECK_PEOPLE1 = "无";//检查员一
            if (data.get("CHECK_PEOPLE1") != null) {
                CHECK_PEOPLE1 = data.get("CHECK_PEOPLE1").toString();
            }
            String CARD_NUMBER1 = "无";//证件号1
            if (data.get("CARD_NUMBER1") != null) {
                CARD_NUMBER1 = data.get("CARD_NUMBER1").toString();
            }
            String CHECK_PEOPLE2 = "无";//检查员二
            if (data.get("CHECK_PEOPLE2") != null) {
                CHECK_PEOPLE2 = data.get("CHECK_PEOPLE2").toString();
            }
            String CHECK_UNIT1 = "";//检查单位
            if (data.get("CHECK_UNIT1") != null) {
                CHECK_UNIT1 = data.get("CHECK_UNIT1").toString();
            }

            String CHECKE_DETAIL =  data1.get(0).get("CHECKE_DETAIL").toString();//检查情况
            String Rectification_measures =  "无";//整改措施（整改意见）
            String RECORD_PDF =  pdf+"record/"+data.get("RECORD_PDF").toString();//执法文书URL
            String NOTICE_CONTENT =  data.get("NOTICE_CONTENT").toString();//检查内容
            String Inspection_mode =  "现场检查";//检查方式
            String Illegal_activities =  "Illegal activities";//违法行为
            String Instrument_name =  "现场检查记录";//文书名称

            String registrant_name = CHECK_PEOPLE1;//登记人姓名
            String registration_unit = "";//登记单位
            String registration_unit_name = CHECK_UNIT1;//登记单位名称
            String registration_time = CREATE_TIME;//登记时间
            String registrar = CARD_NUMBER1;//登记人

            String data0 = "VALUES('"+RECORD_ID+"','"+CHECKED_UNIT+"','"+UNIT_NUMBER+"','"+ADDRESS+"','"+CREATE_TIME+"','"+CHECKED_START_TIME+"','" +
                    CHECKED_END_TIME+"','"+CHECKED_LOCATION+"','"+CHECK_PEOPLE1+"','"+CHECK_PEOPLE2+"','"+CHECKE_DETAIL+"','" +
                    Rectification_measures+"','"+RECORD_PDF+"','"+NOTICE_CONTENT+"','"+Inspection_mode+"','"+Illegal_activities+"','"+Instrument_name+"','" +
                    registrant_name+"','"+registration_unit+"','"+registration_unit_name+"','"+registration_time+"','"+registrar+"')";
            map.put("cmd", data0);
            map.put("fwfbh","S3201-BT000033");
            map.put("zdxtmc","执法宝典平台");
            map.put("zdyhdwbm","");
            map.put("fwfmc","现场检查基本信息新增接口");
            map.put("zdyhgmsfhm","");
            map.put("qqfmc","南理工");
            map.put("zdyhjh",CARD_NUMBER1);
            map.put("qqfbh","C3201-XCJC000001");
            map.put("zdyhdwmc",CHECK_UNIT1);
            map.put("zdyhxm",CHECK_PEOPLE1);

            String url="http://10.101.86.174:9999/basicopertionservices/event/services/insertXcjc";
            String res= HttpUtil.httpPost3(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }

    }



    /**
     * 传送自检封装数据
     */
    public String putZiJianDate(String recordId) {
        try {
            HashMap<String, String> map = new HashMap<>(8);
            Map data2 = dataDao.putZJRecord(recordId);
            String social_credit_code =  data2.get("UNIT_NUMBER").toString();
            String checked_start_time =  data2.get("CREATE_TIME").toString();
            String recording_time =  data2.get("CREATE_TIME").toString();
            List <Map> data1 = dataDao.putZiJianDate(recordId);
            String id =  recordId;
            StringBuffer checked_end_time=new StringBuffer();//结束时间
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checked_end_time.append(data1.get(i).get("CHECKED_END_TIME").toString());//结束时间
                    checked_end_time.append("|");
                }else{
                    checked_end_time.append(data1.get(i).get("CHECKED_END_TIME").toString());//结束时间
                }
            }

            StringBuffer checker=new StringBuffer();//结束时间
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checker.append(data1.get(i).get("CHECK_PEOPLE").toString());//结束时间
                    checker.append("|");
                }else{
                    checker.append(data1.get(i).get("CHECK_PEOPLE").toString());//结束时间
                }
            }

            StringBuffer checked_detail=new StringBuffer();//结束时间
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checked_detail.append(data1.get(i).get("CHECKE_DETAIL").toString());//结束时间
                    checked_detail.append("|");
                }else{
                    checked_detail.append(data1.get(i).get("CHECKE_DETAIL").toString());//结束时间
                }
            }

            StringBuffer checked_status=new StringBuffer();//结束时间
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checked_status.append(data1.get(i).get("CHECK_STATE").toString());//结束时间
                    checked_status.append("|");
                }else{
                    checked_status.append(data1.get(i).get("CHECK_STATE").toString());//结束时间
                }
            }

            StringBuffer ent_name=new StringBuffer();//结束时间
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    ent_name.append(data1.get(i).get("CHECKE_UNIT").toString());//结束时间
                    ent_name.append("|");
                }else{
                    ent_name.append(data1.get(i).get("CHECKE_UNIT").toString());//结束时间
                }
            }


            map.put("id", id);
            map.put("checked_start_time", checked_start_time);
            map.put("checked_end_time", checked_end_time.toString());
            map.put("checker", checker.toString());
            map.put("checked_detail", checked_detail.toString());
            map.put("checked_status", checked_status.toString());
            map.put("recording_time", recording_time.toString());
            map.put("social_credit_code", social_credit_code);
            map.put("ent_name", ent_name.toString());

            map.put("checker_id", id);   //记得修改

            String url="http://datacenter.njyjgl.cn/api/resource/storage/enterprise_examination";
            url = "http://222.190.243.160/api/resource/storage/enterprise_examination";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }


    /**
     * 传送专家较大隐患信息
     */
    public String putExpertHiddenDangerDate(String recordId,String entId) {
        String checked_end_time = "";
        String risk_level ="较大以上隐患";
        String checked_type ="专业检查";
        String supervisor_id ="";
        String id ="";
        String social_credit_code ="";
        String checked_start_time ="";
        String qrcode_path ="";
        String pdf_path ="";
        String qrcode_path3 ="";
        String pdf_path3 ="";
        try {
            HashMap<String, String> map = new HashMap<>(29);
            List<Map> data2 = dataDao.putExpertDangerRecord(recordId);
            if (data2.get ( 0 ).get ("UNIT_NUMBER")!=null){
                social_credit_code =  data2.get ( 0 ).get ("UNIT_NUMBER").toString();
            }
            if (data2.get ( 0 ).get ("CHECKED_START_TIME")!=null){
                checked_start_time =  data2.get ( 0 ).get ("CHECKED_START_TIME").toString();
            }
            if (data2.get ( 0 ).get ("QRCODE_PATH")!=null){
                qrcode_path =  data2.get ( 0 ).get ("QRCODE_PATH").toString();
            }
            if (data2.get ( 0 ).get ("PDF_PATH")!=null){
                pdf_path =  data2.get ( 0 ).get ("PDF_PATH").toString();
            }
//            String checked_start_time =  data2.get ( 0 ).get ("CHECKED_START_TIME").toString();
//            String qrcode_path =  data2.get ( 0 ).get("QRCODE_PATH").toString();
//            String pdf_path = data2.get ( 0 ).get("PDF_PATH").toString();


            List <Map> data1 = dataDao.putExpertHidddenDangerDate(recordId);

            if(data1.get(0).get("CHECKED_END_TIME")!=null)  {
                checked_end_time = data1.get(0).get("CHECKED_END_TIME").toString ();
            }
            if(data1.get(0).get("ID")!=null)  {
                id = data1.get(0).get("ID").toString ();
            }
            if(data1.get(0).get("PKID")!=null)  {
                supervisor_id = data1.get(0).get("PKID").toString ();
            }
            StringBuffer checked_experts=new StringBuffer();//专家名称
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checked_experts.append(data1.get(i).get("EXPERT_PEOPLLE").toString());//专家名称
                    checked_experts.append("|");
                }else{
                    checked_experts.append(data1.get(i).get("EXPERT_PEOPLLE").toString());//专家名称
                }
            }

            StringBuffer danger_name=new StringBuffer();//较大隐患
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    danger_name.append(data1.get(i).get("DANGER_NAME").toString());//较大隐患
                    danger_name.append("|");
                }else{
                    danger_name.append(data1.get(i).get("DANGER_NAME").toString());//较大隐患
                }
            }

            StringBuffer checked_detail=new StringBuffer();//检查内容
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checked_detail.append(data1.get(i).get("CHECKE_DETAIL").toString());//检查内容
                    checked_detail.append("|");
                }else{
                    checked_detail.append(data1.get(i).get("CHECKE_DETAIL").toString());//检查内容
                }
            }

            StringBuffer checked_unit=new StringBuffer();//检查单位
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checked_unit.append(data1.get(i).get("CHECKED_UNIT").toString());//检查单位
                    checked_unit.append("|");
                }else{
                    checked_unit.append(data1.get(i).get("CHECKED_UNIT").toString());//检查单位
                }
            }
            StringBuffer checke_place=new StringBuffer();//检查场所
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checke_place.append(data1.get(i).get("CHECKE_PLACE").toString());//检查场所
                    checke_place.append("|");
                }else{
                    checke_place.append(data1.get(i).get("CHECKE_PLACE").toString());//检查场所
                }
            }

            List<Map> data3 = dataDao.putExpertHeGeDate(recordId);
            if (data3.get ( 0 ).get ("RECORD_QR_CODE")!=null){
                qrcode_path3 =  data3.get ( 0 ).get ("RECORD_QR_CODE").toString();
            }
            if (data3.get ( 0 ).get ("RECORD_PDF")!=null){
                pdf_path3 =  data3.get ( 0 ).get ("RECORD_PDF").toString();
            }



            map.put("id", id);
            map.put("ent_name", checked_unit.toString ());

            map.put("social_credit_code", social_credit_code);

            Map data4 = dataDao.getExSceneRecord2(recordId);//ZHIFA_EXPERT_SCENE_RECORD
            String checker_id = data4.get("USER_ID").toString();//检查人用户标识

            map.put("checker_id", checker_id);   //记得修改
            map.put("checker", "");
            map.put("checked_start_time", checked_start_time);
            map.put("checked_end_time", checked_end_time);
            map.put("checked_detail", checked_detail.toString());
            map.put("checked_status", danger_name.toString());
            map.put("checked_experts", checked_experts.toString());
            map.put("checked_deps", "");
            map.put("is_correction", "2");
            map.put("record_id", recordId);
            map.put("correction_detail","");
            map.put ( "notice_law_document", "");
            map.put ( "notice_law_document_qr","" );

            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";

            map.put ( "record_law_document", pdf+"expert_record/"+pdf_path3);
            map.put ( "record_law_document_qr",qrcode+"expert_record/"+qrcode_path3 );
            map.put ( "major_hazard_document", pdf+"xianchangchuli/"+pdf_path);
            map.put ( "major_hazard_document_qr",qrcode+"xianchangchuli/"+qrcode_path );
            map.put ( "punish_law_document", "");
            map.put ( "punish_law_document_qr", "");
            map.put ( "rectify_law_document", "");
            map.put ( "rectify_law_document_qr", "");
            map.put ( "review_law_document", "");
            map.put ( "review_law_document_qr", "");
//            map.put("danger_name", danger_name.toString());
            map.put("checke_place", checke_place.toString());
            map.put ( "risk_level",risk_level );
            map.put ( "checked_type" ,checked_type);
            map.put ( "supervisor_id", supervisor_id);
            map.put("ent_id",entId);
            String url="http://222.190.243.160/spp_grid_dev/resource/pre_storage/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }


    /**
     * 传送一般隐患处理记录
     */

    public String putGeneralHiddenDanger(String RECORD_ID,String entId){
        try {
            String pdf_path ="";
            String qrcode_path ="";
            List<Map> SCENE = expertDao.getExpertInfo(RECORD_ID);
            List<Map> ITEM = expertDao.getExpertRecordInfo(RECORD_ID);
            List<Map> data2 = dataDao.putExpertHeGeDate(RECORD_ID);
            Map<String,String> map = new HashMap<>();
            map.put("id",RECORD_ID);
            map.put("ent_name",SCENE.get(0).get("CHECKED_UNIT").toString());
            map.put("social_credit_code",SCENE.get(0).get("UNIT_NUMBER").toString());

            Map data4 = dataDao.getExSceneRecord2(RECORD_ID);//ZHIFA_EXPERT_SCENE_RECORD
            String checker_id = data4.get("USER_ID").toString();//检查人用户标识

            map.put("checker_id", checker_id);
            map.put("checker","");
            map.put("checked_start_time",SCENE.get(0).get("CHECKED_START_TIME").toString());
            map.put("checked_end_time",SCENE.get(0).get("CHECKED_END_TIME").toString());
            map.put("checked_detail","");
            map.put("checked_status","不合格");
            String expert = SCENE.get(0).get("EXPERT_NAME1").toString();
            if(expert.contains("_")){
                map.put("checked_experts",expert.split("_").toString());
            } else {
                map.put("checked_experts",expert);
            }
            if (data2.get ( 0 ).get ("RECORD_QR_CODE")!=null){
                qrcode_path =  data2.get ( 0 ).get ("RECORD_QR_CODE").toString();
            }
            if (data2.get ( 0 ).get ("RECORD_PDF")!=null){
                pdf_path =  data2.get ( 0 ).get ("RECORD_PDF").toString();
            }
            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";
            map.put("checked_deps","");
            map.put("is_correction","1");
            map.put("record_id",ITEM.get(0).get("ID").toString());
            map.put("correction_detail","现场整改");
            map.put("notice_law_document","");
            map.put("notice_law_document_qr","");
            map.put ( "record_law_document", pdf+"expert_record/"+pdf_path);
            map.put ( "record_law_document_qr",qrcode+"expert_record/"+qrcode_path );

            map.put("punish_law_document","http://zfxc.njyjgl.cn/yjbd2/"+ITEM.get(0).get("PDF_PATH").toString());
            map.put("punish_law_document_qr","http://zfxc.njyjgl.cn/yjbd2/"+ITEM.get(0).get("QRCODE_PATH").toString());
            map.put("rectify_law_document","");
            map.put("rectify_law_document_qr","");
            map.put("review_law_document","");
            map.put("review_law_document_qr","");
            map.put("risk_level","一般隐患");
            map.put("checked_type","专业检查");
            map.put("supervisor_id","");
            map.put("ent_id",entId);
            String url="http://222.190.243.160/spp_grid_dev/resource/pre_storage/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }catch (Exception e){
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }


    }

    /**
     * 传送专家检查合格数据
     */
    public String putExpertHeGeDate(String recordId,String entId) {
        String checked_end_time = "";
        String risk_level ="合格";
        String checked_type ="专业检查";
        String supervisor_id ="";
        String social_credit_code ="";
        String checked_start_time ="";
        String qrcode_path ="";
        String checked_unit ="";
        String pdf_path ="";
        String checked_experts ="";
        String checked_deps ="";
        try {
            HashMap<String, String> map = new HashMap<>(29);
            List<Map> data2 = dataDao.putExpertHeGeDate(recordId);
            if (data2.get ( 0 ).get ("UNIT_NUMBER")!=null){
                social_credit_code =  data2.get ( 0 ).get ("UNIT_NUMBER").toString();
            }
            if (data2.get ( 0 ).get ("CHECKED_UNIT")!=null){
                checked_unit =  data2.get ( 0 ).get ("CHECKED_UNIT").toString();
            }
            if (data2.get ( 0 ).get ("EXPERT_NAME1")!=null){
                checked_experts =  data2.get ( 0 ).get ("EXPERT_NAME1").toString();
            }
            if (data2.get ( 0 ).get ("EXPERT_UNIT1")!=null){
                checked_deps =  data2.get ( 0 ).get ("EXPERT_UNIT1").toString();
            }

            if (data2.get ( 0 ).get ("CHECKED_START_TIME")!=null){
                checked_start_time =  data2.get ( 0 ).get ("CHECKED_START_TIME").toString();
            }
            if(data2.get(0).get("CREATE_TIME")!=null)  {
                checked_end_time = data2.get(0).get("CREATE_TIME").toString ();
            }
            if (data2.get ( 0 ).get ("RECORD_QR_CODE")!=null){
                qrcode_path =  data2.get ( 0 ).get ("RECORD_QR_CODE").toString();
            }
            if (data2.get ( 0 ).get ("RECORD_PDF")!=null){
                pdf_path =  data2.get ( 0 ).get ("RECORD_PDF").toString();
            }


            List <Map> data1 = dataDao.putExpertHeGeDate1(recordId);


            StringBuffer checked_detail=new StringBuffer();//检查内容
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checked_detail.append(data1.get(i).get("CHECKE_DETAIL").toString());//检查内容
                    checked_detail.append("|");
                }else{
                    checked_detail.append(data1.get(i).get("CHECKE_DETAIL").toString());//检查内容
                }
            }


            StringBuffer checked_status=new StringBuffer();//检查场所
            for (int i=0;i<data1.size();i++){
                if (i+1<data1.size()){
                    checked_status.append(data1.get(i).get("CHECK_STATE").toString());//检查场所
                    checked_status.append("|");
                }else{
                    checked_status.append(data1.get(i).get("CHECK_STATE").toString());//检查场所
                }
            }
            map.put("id", "");
            map.put("ent_name", checked_unit);

            map.put("social_credit_code", social_credit_code);

            Map data4 = dataDao.getExSceneRecord2(recordId);
            String checker_id = data4.get("USER_ID").toString();

            map.put("checker_id", checker_id);
            map.put("checker", "");
            map.put("checked_start_time", checked_start_time);
            map.put("checked_end_time", checked_end_time);
            map.put("checked_detail", checked_detail.toString());
            map.put("checked_status", checked_status.toString());
            map.put("checked_experts", checked_experts);
            map.put("checked_deps", checked_deps);
            map.put("is_correction", "0");
            map.put("record_id", recordId);
            map.put("correction_detail","");
            map.put ( "notice_law_document", "");
            map.put ( "notice_law_document_qr","" );

            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";

            map.put ( "record_law_document", pdf+"expert_record/"+pdf_path);
            map.put ( "record_law_document_qr",qrcode+"expert_record/"+qrcode_path );
            map.put ( "punish_law_document", "");
            map.put ( "punish_law_document_qr", "");
            map.put ( "rectify_law_document", "");
            map.put ( "rectify_law_document_qr", "");
            map.put ( "review_law_document", "");
            map.put ( "review_law_document_qr", "");
//            map.put("danger_name", danger_name.toString());

            map.put ( "risk_level",risk_level );
            map.put ( "checked_type" ,checked_type);
            map.put ( "supervisor_id", supervisor_id);
            map.put ( "ent_id", entId);
            String url="http://222.190.243.160/spp_grid_dev/resource/pre_storage/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }

    /**
     * 传送专家现场处理、责令整改、结束数据
     * @param recordId
     * @return
     */
    public String PushExpertData(String recordId,String review_type,String user_id,String entId) {

        try {
            HashMap<String, String> map = new HashMap<>();

            Map data1 = dataDao.getExSceneRecord(recordId);//ZHIFA_EXPERT_DANGER_RECORD
            String social_credit_code = data1.get("UNIT_NUMBER").toString();//企业社会信用代码
            String enterprise_name = data1.get("CHECKED_UNIT").toString();//被检单位

            String checker = data1.get("SECURITY_PEOPLE").toString();//检查人
            String checked_start_time = data1.get("CHECKED_START_TIME").toString();//检查开始时间
            String checked_end_time = data1.get("CHECKED_END_TIME").toString();//检查结束时间
            String checked_detail = data1.get("CHECKE_DETAIL").toString();//检查情况
            String checked_status = "no message";//检查状态
            String checked_experts = data1.get("EXPERT_PEOPLLE").toString();//检查专家
            String checked_deps = "no message";//执法部门


            String is_correction="";
            String correction_time="";
            Map data2 = dataDao.getExZelingRecord(recordId);//ZHIFA_EXPERT_Zeling_RECORD
            if (data2.size()>0) {
                correction_time=data2.get("TIME_IDS").toString();//整改限制时间
            }

            if (correction_time!=null&&!correction_time.equals("")){
                is_correction="2";//整改
            }else{
                is_correction="1";//不整改
            }

            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";

            String notice_law_documents ="no message";//通知文书pdf
            String notice_law_documents_qr ="no message";//通知文书二维码
            String record_law_documents =pdf+"expert_record/"+ data1.get("PDF_PATH").toString();//记录文书PDF
            String record_law_documents_qr =qrcode+"expert_record/"+ data1.get("QRCODE_PATH").toString();//记录文书二维码

            List<Map> data3 = dataDao.getExPUNISHRecord(recordId);//ZHIFA_EXPERT_PUNISH_RECORD
            String PROCESS_DECISION="";//处罚决定
            String punish_law_documents="";//处理文书PDF
            String punish_law_documents_qr="";//处理文书二维码
            if (data3.size()>0){
                punish_law_documents=pdf+"expert_xianchangchuli/"+data3.get(0).get("PDF_PATH").toString();
                punish_law_documents_qr=qrcode+"expert_xianchangchuli/"+data3.get(0).get("QRCODE_PATH").toString();
                PROCESS_DECISION="PROCESS_DECISION";
            }

            map.put("id", recordId);
            map.put("ent_name", enterprise_name);
            map.put("social_credit_code", social_credit_code);
            map.put("checker_id", user_id);//检查人用户标识
            map.put("checker", checker);
            map.put("checked_start_time",checked_start_time);
            map.put("checked_end_time", checked_end_time);
            map.put("checked_detail", checked_detail);
            map.put("checked_status", checked_status);
            map.put("checked_experts", checked_experts);
            map.put("checked_deps", checked_deps);
            map.put("is_correction",is_correction);
            map.put("record_id", recordId);
            map.put("correction_detail", PROCESS_DECISION);
            map.put("notice_law_document", notice_law_documents);
            map.put("notice_law_document_qr", notice_law_documents_qr);
            map.put("record_law_document", record_law_documents);
            map.put("record_law_document_qr", record_law_documents_qr);
            map.put("punish_law_document", punish_law_documents);
            map.put("punish_law_document_qr",punish_law_documents_qr);
            if (is_correction.equals ( "1" )){
                String rectify_law_documents =pdf+"expert_zeling/"+ data2.get("ENFORCE_PAPER").toString();//整改文书PDF
                String rectify_law_documents_qr =qrcode+"expert_zeling/"+ data2.get("QR_CODE_URL").toString();//整改文书二维码
                map.put("rectify_law_document", rectify_law_documents);
                map.put("rectify_law_document_qr",rectify_law_documents_qr);
            }else{
                map.put("rectify_law_document", "no message");
                map.put("rectify_law_document_qr","no message");
            }
            map.put("review_law_document","no message");
            map.put("review_law_document_qr","no message");
            map.put("review_type", review_type);
            if (review_type.equals("限期整改")) {
                map.put("rectify_end_time", correction_time);
            }else{
                map.put("rectify_end_time", "no message");
            }
            if (review_type.equals("现场处置决定") || review_type.equals("核查结束")) {
                map.put("is_end", "1");
            } else {
                map.put("is_end", "0");
            }
            map.put("ent_id",entId);
            String url="http://222.190.243.160/spp_grid_dev/resource/review_storage/enforcement_nust";
            String res= HttpUtil.httpPost2(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }

    /**
     * 推送执法缓存数据
     */
    public String putZhiFaTemporaryDate(String RECORD_ID,String URL,String USER_ID,String SOCIAL_CREDIT_CODE){
        try {
            String temp_name = "";
            Map<String, String> map = new HashMap<>(5);
            URL = URL.replace("http://zfxc.njyjgl.cn/yjbd2/","");
            map.put ( "record_id",RECORD_ID );
            map.put ( "url" , URL);
            map.put ( "user_id",USER_ID );

            List<Map>list =  dataDao.getTemporaryZhiFaTempName(RECORD_ID);
            String CREATE_TIME = list.get(0).get("CREATE_TIME").toString();
            String[] time1 = CREATE_TIME.split(" ");
            String[] time2 = time1[0].split("/");
            String time = time2[0]+"年"+time2[1]+"月"+time2[2]+"日";
            temp_name = list.get(0).get("CHECKED_UNIT").toString()+"-"+time+"-"+"暂存";

            map.put ( "tempName",temp_name);

       /*     Map<String, String> map1 = new HashMap<>(5);
            map1.put ( "record_id",RECORD_ID );
            map1.put ( "url" , URL);
            map1.put ( "user_id",USER_ID );
            map1.put ( "tempName",temp_name);*/

            String url="http://222.190.243.160/spp_grid_dev/resource/template_storage/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }


    /**
     * 推送专家缓存数据
     */
    public String putTemporaryDate(String RECORD_ID,String URL,String USER_ID,String SOCIAL_CREDIT_CODE) {
        try {
            String temp_name = "";
            Map<String, String> map = new HashMap<>(5);
            URL = URL.replace("http://zfxc.njyjgl.cn/yjbd2/","");
            map.put ( "record_id",RECORD_ID );
            map.put ( "url" , URL);
            map.put ( "user_id",USER_ID );

            /*String url2= URLDecoder.decode(URLEncoder.encode(URL, "UTF-8"), "UTF-8");
            List<Map>list = dataDao.getgetTemporaryExTempName(RECORD_ID);

            if (list.size ()!=0){
                for (int i =0;i<list.size ();i++){
                    if (list.get ( i ).get ( "TEMP_NAME" )!=null ){
                        temp_name+=list.get ( i ).get ( "TEMP_NAME" ).toString ()+"|";
                    }else {
                        temp_name="no message";
                    }
                }
            }else {
                temp_name="no message";
            }*/

            List<Map>list =  dataDao.getgetTemporaryExTempName(RECORD_ID);
            String CREATE_TIME = list.get(0).get("CREATE_TIME").toString();
            String[] time1 = CREATE_TIME.split(" ");
            String[] time2 = time1[0].split("-");
            String time = time2[0]+"年"+time2[1]+"月"+time2[2]+"日";
            temp_name = list.get(0).get("CHECKED_UNIT").toString()+"-"+time+"-"+"暂存";

            map.put ( "tempName",temp_name);

            /*Map<String, String> map1 = new HashMap<>(5);
            map1.put ( "record_id","RECORD_ID" );
            map1.put ( "url" , "URL");
            map1.put ( "user_id","USER_ID" );
            map1.put ( "tempName","temp_name");*/

            String url="http://222.190.243.160/spp_grid_dev/resource/template_storage/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }






    /**
     * 传送九小场所限期改正数据
     */
    public String putFireFightingTimeLimitDate(String recordId,String userId,String sCode) {
        String risk_level ="责令整改";
        String id ="";
        String social_credit_code ="";
        String checked_start_time ="";
        String ent_name ="";
        String checker_id ="";
        String checker ="";
        String checked_detail ="";
        String risk_point ="";
        String risk_content ="";
        String rectify_law_document ="";
        String rectify_law_document_qr ="";
        String unit_address ="";
        String inspect_type ="";
        String region_id ="";
        String fire_safety_behavior = "";
        String rectification_deadline = "";

        try {
            HashMap<String, String> map = new HashMap<>(31);
            List<Map> data = dataDao.putFireFightingTimeLimitDate(recordId);
            Date date=new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String today=df.format(date);
            if (data.get ( 0 ).get ("CREATE_TIME")!=null){
                id =  data.get ( 0 ).get ("CREATE_TIME").toString();
            }
            if (data.get ( 0 ).get ("CHECKED_UNIT")!=null){
                ent_name =  data.get ( 0 ).get ("CHECKED_UNIT").toString();
            }
            if (data.get ( 0 ).get ("UNIT_NUMBER")!=null){
                social_credit_code =  data.get ( 0 ).get ("UNIT_NUMBER").toString();
            }
            if (data.get ( 0 ).get ("CARD_NUMBER")!=null){
                checker_id =  data.get ( 0 ).get ("CARD_NUMBER").toString();
            }
            if (data.get ( 0 ).get ("CHECK_PEOPLE")!=null){
                checker =  data.get ( 0 ).get ("CHECK_PEOPLE").toString();
            }
            if (data.get ( 0 ).get ("CHECK_START_TIME")!=null){
                checked_start_time =  data.get ( 0 ).get ("CHECK_START_TIME").toString();
            }
            if (data.get ( 0 ).get ("CHECK_DESC")!=null){
                checked_detail =  data.get ( 0 ).get ("CHECK_DESC").toString();
            }
            if (data.get ( 0 ).get ("RISK_POINT")!=null){
                risk_point =  data.get ( 0 ).get ("RISK_POINT").toString();
            }
            if (data.get ( 0 ).get ("CHECK_ITEM")!=null){
                risk_content =  data.get ( 0 ).get ("CHECK_ITEM").toString();
            }
            if (data.get ( 0 ).get ("ADDRESS")!=null){
                unit_address =  data.get ( 0 ).get ("ADDRESS").toString();
            }
            List<Map> data1 = dataDao.putFireFightingTimeLimitDate1(recordId);
            if (data1.get ( 0 ).get ("ENFORCE_PAPER")!=null){
                rectify_law_document =  data1.get ( 0 ).get ("ENFORCE_PAPER").toString();
            }
            if (data1.get ( 0 ).get ("QR_CODE_URL")!=null){
                rectify_law_document_qr =  data1.get ( 0 ).get ("QR_CODE_URL").toString();
            }
            if (data1.get ( 0 ).get ("TIME_IDS")!=null){
                rectification_deadline =  data1.get ( 0 ).get ("TIME_IDS").toString();
            }
            List<Map> data3 = dataDao.putFireFightingTimeLimitDate3(recordId);
            if (data3.get ( 0 ).get ("INSPECT_TYPE")!=null){
                inspect_type =  data3.get ( 0 ).get ("INSPECT_TYPE").toString();
            }
            if (data3.get ( 0 ).get ("REGION_ID")!=null){
                region_id =  data3.get ( 0 ).get ("REGION_ID").toString();
            }
            String CHECK_ITEM = "";
            if (data3.get ( 0 ).get ("CHECK_ITEM")!=null){
                CHECK_ITEM = data3.get ( 0 ).get ("CHECK_ITEM").toString();
            }
            String[] arr = CHECK_ITEM.split("__");
            for (int i=0;i<arr.length;i++)
            {
                fire_safety_behavior = fire_safety_behavior + "{value:"+(i+1)+",text:"+arr[i]+"},";
            }
            fire_safety_behavior ="["+ fire_safety_behavior.substring(0, fire_safety_behavior.length() - 1)+"]";

            map.put("id", id);   //记得修改
            map.put("ent_name", ent_name);
            map.put("social_credit_code", sCode);
            map.put("checker_id", userId);
            map.put("checker", checker);
            map.put("checked_start_time", checked_start_time);
            map.put("checked_end_time", today);
            map.put("checked_detail", checked_detail);
            map.put("checked_status", "");
            map.put("checked_experts", "");
            map.put("checked_deps","");
            map.put ( "is_correction", "1");
            map.put ( "record_id",recordId );
            map.put("correction_detail","");
            map.put ( "notice_law_document", "");
            map.put ( "notice_law_document_qr","" );
            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";
//            map.put ( "record_law_document", pdf+"expert_record/"+pdf_path);
//            map.put ( "record_law_document_qr",qrcode+"expert_record/"+qrcode_path );

            map.put ( "record_law_document", "http://zfxc.njyjgl.cn/yjbd2/"+data.get(0).get("CHECK_PDF").toString());
            map.put ( "record_law_document_qr","http://zfxc.njyjgl.cn/yjbd2/"+data.get(0).get("QR_CODE").toString() );
            map.put ( "punish_law_document", "");
            map.put ( "punish_law_document_qr", "");
            map.put ( "rectify_law_document", pdf+"zeling/"+rectify_law_document);
            map.put ( "rectify_law_document_qr", qrcode+"zeling/"+rectify_law_document_qr);
            map.put ( "review_law_document", "");
            map.put ( "review_law_document_qr", "");
            map.put ( "risk_level",risk_level );
            map.put ( "risk_content" ,risk_content);
            map.put ( "checked_type", "");
            map.put ( "supervisor_id", "");
            map.put ( "risk_point" ,risk_point);
            map.put ( "unit_address", unit_address);
            map.put ( "ent_id", social_credit_code);
            map.put ( "inspect_type", inspect_type);
            map.put ( "department_id", region_id);
            map.put ( "fire_safety_behavior", fire_safety_behavior);
            map.put("rectification_deadline",rectification_deadline);
            String url="http://api.njyjgl.cn/spp_grid_dev/resource/place_inspecting/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }

    /**
     * 传送九小场所现场改正数据
     */
    public String putTimeRectificationDate(String recordId,String userId,String sCode) {
        String risk_level ="现场整改";
        String id ="";
        String social_credit_code ="";
        String checked_start_time ="";
        String ent_name ="";
        String checker_id ="";
        String checker ="";
        String checked_detail ="";
        String risk_point ="";
        String risk_content ="";
        String punish_law_document ="";
        String punish_law_document_qr ="";
        String unit_address ="";
        String correction_detail ="";
        String inspect_type ="";
        String region_id ="";
        try {
            HashMap<String, String> map = new HashMap<>(31);
            List<Map> data = dataDao.putFireFightingTimeLimitDate(recordId);
            Date date=new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String today=df.format(date);
            if (data.get ( 0 ).get ("CREATE_TIME")!=null){
                id =  data.get ( 0 ).get ("CREATE_TIME").toString();
            }
            if (data.get ( 0 ).get ("CHECKED_UNIT")!=null){
                ent_name =  data.get ( 0 ).get ("CHECKED_UNIT").toString();
            }
            if (data.get ( 0 ).get ("UNIT_NUMBER")!=null){
                social_credit_code =  data.get ( 0 ).get ("UNIT_NUMBER").toString();
            }

            if (data.get ( 0 ).get ("CHECK_PEOPLE")!=null){
                checker =  data.get ( 0 ).get ("CHECK_PEOPLE").toString();
            }
            if (data.get ( 0 ).get ("CHECK_START_TIME")!=null){
                checked_start_time =  data.get ( 0 ).get ("CHECK_START_TIME").toString();
            }
            if (data.get ( 0 ).get ("CHECK_DESC")!=null){
                checked_detail =  data.get ( 0 ).get ("CHECK_DESC").toString();
            }
            if (data.get ( 0 ).get ("RISK_POINT")!=null){
                risk_point =  data.get ( 0 ).get ("RISK_POINT").toString();
            }
            if (data.get ( 0 ).get ("CHECK_ITEM")!=null){
                risk_content =  data.get ( 0 ).get ("CHECK_ITEM").toString();
            }
            if (data.get ( 0 ).get ("ADDRESS")!=null){
                unit_address =  data.get ( 0 ).get ("ADDRESS").toString();
            }
            List<Map> data1 = dataDao.putTimeRectificationDate(recordId);
            if (data1.get ( 0 ).get ("PDF_PATH")!=null){
                punish_law_document =  data1.get ( 0 ).get ("PDF_PATH").toString();
            }
            if (data1.get ( 0 ).get ("QRCODE_PATH")!=null){
                punish_law_document_qr =  data1.get ( 0 ).get ("QRCODE_PATH").toString();
            }
            if (data1.get ( 0 ).get ("PROCESS_DECISION")!=null){
                correction_detail =  data1.get ( 0 ).get ("PROCESS_DECISION").toString();
            }
            List<Map> data3 = dataDao.putFireFightingTimeLimitDate3(recordId);
            if (data3.get ( 0 ).get ("INSPECT_TYPE")!=null){
                inspect_type =  data3.get ( 0 ).get ("INSPECT_TYPE").toString();
            }
            if (data3.get ( 0 ).get ("REGION_ID")!=null){
                region_id =  data3.get ( 0 ).get ("REGION_ID").toString();
            }
            map.put("id", id);   //记得修改
            map.put("ent_name", ent_name);
            map.put("social_credit_code", sCode);
            map.put("checker_id", userId);
            map.put("checker", checker);
            map.put("checked_start_time", checked_start_time);
            map.put("checked_end_time", today);
            map.put("checked_detail", checked_detail);
            map.put("checked_status", "");
            map.put("checked_experts", "");
            map.put("checked_deps","");
            map.put ( "is_correction", "否");
            map.put ( "record_id",recordId );
            map.put("correction_detail",correction_detail);
            map.put ( "notice_law_document", "");
            map.put ( "notice_law_document_qr","" );

            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";
            map.put ( "record_law_document", "http://zfxc.njyjgl.cn/yjbd2/"+data.get(0).get("CHECK_PDF").toString());
            map.put ( "record_law_document_qr","http://zfxc.njyjgl.cn/yjbd2/"+data.get(0).get("QR_CODE").toString() );
            map.put ( "punish_law_document", pdf+"xianchangchuli/"+punish_law_document);
            map.put ( "punish_law_document_qr", qrcode+"xianchangchuli/"+punish_law_document_qr);
            map.put ( "rectify_law_document","" );
            map.put ( "rectify_law_document_qr","" );
            map.put ( "review_law_document", "");
            map.put ( "review_law_document_qr", "");
            map.put ( "risk_level",risk_level );
            map.put ( "risk_content" ,risk_content);
            map.put ( "checked_type", "");
            map.put ( "supervisor_id", "");
            map.put ( "risk_point" ,risk_point);
            map.put ( "unit_address", unit_address);
            map.put ( "ent_id", social_credit_code);
            map.put ( "inspect_type", inspect_type);
            map.put ( "department_id", region_id);
            String url="http://api.njyjgl.cn/spp_grid_dev/resource/place_inspecting/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }

    /**
     * 传送九小场所合格数据
     */
    public String putFireFightingDate(String recordId,String userId,String sCode) {
        String checked_end_time = "";
        String risk_level ="合格";
        String id ="";
        String social_credit_code ="";
        String checked_start_time ="";
        String ent_name ="";
        String checker_id ="";
        String checker ="";
        String checked_detail ="";
        String risk_point ="";
        String risk_content ="";
        String unit_address ="";
        String correction_detail ="";
        String inspect_type ="";
        String region_id ="";
        try {
            HashMap<String, String> map = new HashMap<>(31);
            List<Map> data = dataDao.putFireFightingTimeLimitDate(recordId);
            Date date=new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String today=df.format(date);
            if (data.get ( 0 ).get ("CREATE_TIME")!=null){
                id =  data.get ( 0 ).get ("CREATE_TIME").toString();
            }
            if (data.get ( 0 ).get ("CHECKED_UNIT")!=null){
                ent_name =  data.get ( 0 ).get ("CHECKED_UNIT").toString();
            }
            if (data.get ( 0 ).get ("UNIT_NUMBER")!=null){
                social_credit_code =  data.get ( 0 ).get ("UNIT_NUMBER").toString();
            }

            if (data.get ( 0 ).get ("CHECK_PEOPLE")!=null){
                checker =  data.get ( 0 ).get ("CHECK_PEOPLE").toString();
            }
            if (data.get ( 0 ).get ("CHECK_START_TIME")!=null){
                checked_start_time =  data.get ( 0 ).get ("CHECK_START_TIME").toString();
            }

            if (data.get ( 0 ).get ("CHECK_DESC")!=null){
                checked_detail =  data.get ( 0 ).get ("CHECK_DESC").toString();
            }
            if (data.get ( 0 ).get ("RISK_POINT")!=null){
                risk_point =  data.get ( 0 ).get ("RISK_POINT").toString();
            }
            if (data.get ( 0 ).get ("CHECK_ITEM")!=null){
                risk_content =  data.get ( 0 ).get ("CHECK_ITEM").toString();
            }
            if (data.get ( 0 ).get ("ADDRESS")!=null){
                unit_address =  data.get ( 0 ).get ("ADDRESS").toString();
            }
            List<Map> data3 = dataDao.putFireFightingTimeLimitDate3(recordId);
            if (data3.get ( 0 ).get ("INSPECT_TYPE")!=null){
                inspect_type =  data3.get ( 0 ).get ("INSPECT_TYPE").toString();
            }
            if (data3.get ( 0 ).get ("REGION_ID")!=null){
                region_id =  data3.get ( 0 ).get ("REGION_ID").toString();
            }
//            List<Map> data1 = dataDao.putTimeRectificationDate(recordId);
//            if (data1.get ( 0 ).get ("PDF_PATH")!=null){
//                punish_law_document =  data1.get ( 0 ).get ("PDF_PATH").toString();
//            }
//            if (data1.get ( 0 ).get ("QRCODE_PATH")!=null){
//                punish_law_document_qr =  data1.get ( 0 ).get ("QRCODE_PATH").toString();
//            }
//            if (data1.get ( 0 ).get ("PROCESS_DECISION")!=null){
//                correction_detail =  data1.get ( 0 ).get ("PROCESS_DECISION").toString();
//            }
            map.put("id", id);   //记得修改
            map.put("ent_name", ent_name);
            map.put("social_credit_code", sCode);
            map.put("checker_id", userId);
            map.put("checker", checker);
            map.put("checked_start_time", checked_start_time);
            map.put("checked_end_time", today);
            map.put("checked_detail", checked_detail);
            map.put("checked_status", "");
            map.put("checked_experts", "");
            map.put("checked_deps","");
            map.put ( "is_correction", "否");
            map.put ( "record_id",recordId );
            map.put("correction_detail",correction_detail);
            map.put ( "notice_law_document", "");
            map.put ( "notice_law_document_qr","" );
//            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
//            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";
            map.put ( "record_law_document", "http://zfxc.njyjgl.cn/yjbd2/"+data.get(0).get("CHECK_PDF").toString());
            map.put ( "record_law_document_qr","http://zfxc.njyjgl.cn/yjbd2/"+data.get(0).get("QR_CODE").toString() );
            map.put ( "punish_law_document", "");
            map.put ( "punish_law_document_qr", "");
            map.put ( "rectify_law_document","" );
            map.put ( "rectify_law_document_qr","" );
            map.put ( "review_law_document", "");
            map.put ( "review_law_document_qr", "");
            map.put ( "risk_level",risk_level );
            map.put ( "risk_content" ,risk_content);
            map.put ( "checked_type", "");
            map.put ( "supervisor_id", "");
            map.put ( "risk_point" ,risk_point);
            map.put ( "unit_address", unit_address);
            map.put ( "ent_id", social_credit_code);
            map.put ( "inspect_type", inspect_type);
            map.put ( "department_id", region_id);
            String url="http://api.njyjgl.cn/spp_grid_dev/resource/place_inspecting/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }





    /**
     * 传送九小场所重大隐患数据
     */
    /*public String putFireHiddenDangerRecordDate(String recordId) {
        String checked_end_time = "";
        String risk_level ="重大隐患上报";
        String id ="";
        String social_credit_code ="";
        String checked_start_time ="";
        String ent_name ="";
        String checker_id ="";
        String checker ="";
        String checked_detail ="";
        String risk_point ="";
        String risk_content ="";
        String unit_address ="";
        String correction_detail ="";
        String inspect_type ="";
        String region_id ="";
        try {
            HashMap<String, String> map = new HashMap<>(31);
            List<Map> data = dataDao.putFireFightingTimeLimitDate(recordId);
            if (data.get ( 0 ).get ("ID")!=null){
                id =  data.get ( 0 ).get ("ID").toString();
            }
            if (data.get ( 0 ).get ("CHECKED_UNIT")!=null){
                ent_name =  data.get ( 0 ).get ("CHECKED_UNIT").toString();
            }
            if (data.get ( 0 ).get ("UNIT_NUMBER")!=null){
                social_credit_code =  data.get ( 0 ).get ("UNIT_NUMBER").toString();
            }
            if (data.get ( 0 ).get ("CARD_NUMBER")!=null){
                checker_id =  data.get ( 0 ).get ("CARD_NUMBER").toString();
            }
            if (data.get ( 0 ).get ("CHECK_PEOPLE")!=null){
                checker =  data.get ( 0 ).get ("CHECK_PEOPLE").toString();
            }
            if (data.get ( 0 ).get ("CHECK_START_TIME")!=null){
                checked_start_time =  data.get ( 0 ).get ("CHECK_START_TIME").toString();
            }
            if (data.get ( 0 ).get ("CREATE_TIME")!=null){
                checked_end_time =  data.get ( 0 ).get ("CREATE_TIME").toString();
            }
            if (data.get ( 0 ).get ("CHECK_DESC")!=null){
                checked_detail =  data.get ( 0 ).get ("CHECK_DESC").toString();
            }
            if (data.get ( 0 ).get ("RISK_POINT")!=null){
                risk_point =  data.get ( 0 ).get ("RISK_POINT").toString();
            }
//            if (data.get ( 0 ).get ("CHECK_ITEM")!=null){
//                risk_content =  data.get ( 0 ).get ("CHECK_ITEM").toString();
//            }
            if (data.get ( 0 ).get ("ADDRESS")!=null){
                unit_address =  data.get ( 0 ).get ("ADDRESS").toString();
            }
            List<Map> data1 = dataDao.putFireHiddenDangerRecordDate(recordId);
            if (data1.get ( 0 ).get ("PROCESS_DECISION")!=null){
                risk_content =  data1.get ( 0 ).get ("PROCESS_DECISION").toString();
            }
            List<Map> data3 = dataDao.putFireFightingTimeLimitDate3(recordId);
            if (data3.get ( 0 ).get ("INSPECT_TYPE")!=null){
                inspect_type =  data3.get ( 0 ).get ("INSPECT_TYPE").toString();
            }
            if (data3.get ( 0 ).get ("REGION_ID")!=null){
                region_id =  data3.get ( 0 ).get ("REGION_ID").toString();
            }
//            if (data1.get ( 0 ).get ("QRCODE_PATH")!=null){
//                punish_law_document_qr =  data1.get ( 0 ).get ("QRCODE_PATH").toString();
//            }
//            if (data1.get ( 0 ).get ("PROCESS_DECISION")!=null){
//                correction_detail =  data1.get ( 0 ).get ("PROCESS_DECISION").toString();
//            }
            map.put("id", id);   //记得修改
            map.put("ent_name", ent_name);
            map.put("social_credit_code", "");
            map.put("checker_id", checker_id);
            map.put("checker", checker);
            map.put("checked_start_time", checked_start_time);
            map.put("checked_end_time", checked_end_time);
            map.put("checked_detail", checked_detail);
            map.put("checked_status", "");
            map.put("checked_experts", "");
            map.put("checked_deps","");
            map.put ( "is_correction", "0");
            map.put ( "record_id",recordId );
            map.put("correction_detail",correction_detail);
            map.put ( "notice_law_document", "");
            map.put ( "notice_law_document_qr","" );
//            String pdf="http://zfxc.njyjgl.cn/yjbd2/";
//            String qrcode="http://zfxc.njyjgl.cn/yjbd2/";
            map.put ( "record_law_document", "http://zfxc.njyjgl.cn/yjbd2/"+data.get(0).get("CHECK_PDF").toString());
            map.put ( "record_law_document_qr","http://zfxc.njyjgl.cn/yjbd2/"+data.get(0).get("QR_CODE").toString() );
            map.put ( "punish_law_document", "");
            map.put ( "punish_law_document_qr", "");
            map.put ( "rectify_law_document","" );
            map.put ( "rectify_law_document_qr","" );
            map.put ( "review_law_document", "");
            map.put ( "review_law_document_qr", "");
            map.put ( "risk_level",risk_level );
            map.put ( "risk_content" ,risk_content);
            map.put ( "checked_type", "专业检查");
            map.put ( "supervisor_id", "网格巡查");
            map.put ( "risk_point" ,risk_point);
            map.put ( "unit_address", unit_address);
            map.put ( "ent_id", social_credit_code);
            map.put ( "inspect_type", inspect_type);
            map.put ( "department_id", region_id);
            String url="http://api.njyjgl.cn/spp_grid_dev/resource/place_inspecting/enforcement_nust";
            String res= HttpUtil.httpPost(url,map);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return  MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
    }*/
}
