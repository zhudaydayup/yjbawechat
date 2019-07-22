package yjbd.yjbawechat.Service.ma;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.ma.PunishMeasureDao;
import yjbd.yjbawechat.Model.ExpertPunishEntity;
import yjbd.yjbawechat.Model.ma.PunishMeasureEntity;
import yjbd.yjbawechat.Util.MsgUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class PunishMeasureService {
    @Autowired
    PunishMeasureDao punishMeasureDao;
    static final Logger logger = LoggerFactory.getLogger(PunishMeasureService.class);

    /**  查询产品类别 */
    public List<Map> getPunishMeasureInfo1(String RECORD_ID){
        return punishMeasureDao.getPunishMeasureInfo1(RECORD_ID);
    }

    public List<PunishMeasureEntity> getPunishMeasureInfo(String RECORD_ID){
        return punishMeasureDao.getPunishMeasureInfo(RECORD_ID);
    }

    public List<PunishMeasureEntity> getZhenggaiMeasureInfo(String RECORD_ID){
        return punishMeasureDao.getZhenggaiMeasureInfo(RECORD_ID);
    }

    public void setPunishMeasureInfo(String RECORD_ID,String PROCESS_DECISION,String CHECKE_DETAIL,String CHECKE_UNIT,String CHECKED_START_TIME,String ZF_ON_SCENE_AREA,String ZF_ON_SCENE_RECORD ){
        int count = 0;
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        String RECORD_TIME = now.get(Calendar.YEAR)+"年"+month+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
        try {
            count = punishMeasureDao.getTemporaryPunishMeasureInfoCount(RECORD_ID);
            if (count>0){
                punishMeasureDao.setPunishMeasureInfo1(PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME,RECORD_TIME,ZF_ON_SCENE_AREA,ZF_ON_SCENE_RECORD,RECORD_ID);
            }else {
                punishMeasureDao.setPunishMeasureInfo(RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME,RECORD_TIME,ZF_ON_SCENE_AREA,ZF_ON_SCENE_RECORD);
            }
        }catch (Exception e){
            logger.info ( e.toString () );
            e.printStackTrace ();
        }


    }


    public void UpdateCheckSign(String RECORD_ID,String SIGNATURE_NO,String SIGNATURE_PATH){
        if(SIGNATURE_NO.equals("0")){punishMeasureDao.UpdateCheckSign(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("1")){punishMeasureDao.UpdateCheckSign1(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("2")){punishMeasureDao.UpdateCheckSign2(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("3")){punishMeasureDao.UpdateCheckSign3(RECORD_ID,SIGNATURE_PATH);}

    }


    public List<PunishMeasureEntity> getIdRecord(String RECORD_ID){
        return punishMeasureDao.getIdRecord(RECORD_ID);
    }


    /**
     * 记录专家现场处理措施信息
     */
    public void setExPunishMeasureInfo(String RECORD_ID,String PROCESS_DECISION,String CHECKE_DETAIL,String CHECKED_UNIT,String CHECKED_START_TIME){
        int count=0;
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        String RECORD_TIME = now.get(Calendar.YEAR)+"年"+month+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
        count=punishMeasureDao.getTemporarySetExPunishMeasureInfoCount(RECORD_ID);
        if (count>0){
            punishMeasureDao.setExPunishMeasureInfo1(PROCESS_DECISION,CHECKE_DETAIL,CHECKED_UNIT,CHECKED_START_TIME,RECORD_TIME,RECORD_ID);
        }else {
            punishMeasureDao.setExPunishMeasureInfo(RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKED_UNIT,CHECKED_START_TIME,RECORD_TIME);
        }

    }
    /**
     * 缓存记录专家现场处理措施信息
     */
    public String temporarySetExPunishMeasureInfo(String PROCESS_DECISION,String CHECKE_DETAIL,String CHECKED_UNIT,String CHECKED_START_TIME,String URL,String RECORD_ID){
        int count=0;
        String responseText="";
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        String RECORD_TIME = now.get(Calendar.YEAR)+"年"+month+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
        try {
            count=punishMeasureDao.getTemporarySetExPunishMeasureInfoCount(RECORD_ID);
            if (count>0){
                int flag= punishMeasureDao.temporarySetExPunishMeasureInfo(PROCESS_DECISION,CHECKE_DETAIL,CHECKED_UNIT,CHECKED_START_TIME,RECORD_TIME,URL,RECORD_ID);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }else {
                int flag = punishMeasureDao.temporarySetExPunishMeasureInfo1(RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKED_UNIT,CHECKED_START_TIME,RECORD_TIME,URL);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }
        return responseText;


    }

    /**
     *获取缓存记录专家现场处理措施信息
     */
    public String getTemporarySetExPunishMeasureInfo(String RecordId) {
        String responseText="";
        List<Map> mapList=punishMeasureDao.getTemporarySetExPunishMeasureInfo(RecordId);
        responseText = JSON.toJSONString ( mapList);
        return responseText;
    }

    /**
     * 更新现场处理措施的检查人签名、被检查单位负责人签名、见证人签名
     */
    public void UpdatePunishSign(String RECORD_ID,String SIGNATURE_NO,String SIGNATURE_PATH){
        if(SIGNATURE_NO.equals("0")){punishMeasureDao.UpdatePunishSign(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("1")){punishMeasureDao.UpdatePunishSign1(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("2")){punishMeasureDao.UpdatePunishSign2(RECORD_ID,SIGNATURE_PATH);}
    }


    public List<ExpertPunishEntity> getExRecord(String RECORD_ID){
        return punishMeasureDao.getExRecord(RECORD_ID);
    }



    /**
     * 更新责令整改的检查人签名、被检查单位负责人签名、见证人签名
     */
    public void UpdateZeLingSign(String RECORD_ID,String SIGNATURE_NO,String SIGNATURE_PATH){
        if(SIGNATURE_NO.equals("3")){punishMeasureDao.UpdateZeLingSign3(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("4")){punishMeasureDao.UpdateZeLingSign4(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("5")){punishMeasureDao.UpdateZeLingSign5(RECORD_ID,SIGNATURE_PATH);}
    }

    /**
     * 缓存政府执法现场处理措施决定
     * @param
     */
    public String setTemporaryPunishMeasureInfo(String RECORD_ID,String PROCESS_DECISION,String CHECKE_DETAIL,String CHECKE_UNIT,String CHECKED_START_TIME,String URL,String ZF_ON_SCENE_AREA,String ZF_ON_SCENE_RECORD) {
        int count = 0;
        String responseText ="";
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        String RECORD_TIME = now.get(Calendar.YEAR)+"年"+month+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
        try {
            count = punishMeasureDao.getTemporaryPunishMeasureInfoCount(RECORD_ID);
            if (count>0){
                int flag = punishMeasureDao.temporaryPunishMeasureInfoTable(PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME,RECORD_TIME,URL,ZF_ON_SCENE_AREA,ZF_ON_SCENE_RECORD,RECORD_ID);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }else {
                int flag = punishMeasureDao.temporaryPunishMeasureInfo(RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME,RECORD_TIME,URL,ZF_ON_SCENE_AREA,ZF_ON_SCENE_RECORD);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
            return MsgUtils.errorMsg("数值异常，无法上传");
        }
        return responseText;
    }
    /**
     * 获取缓存政府执法现场处理措施决定
     * @param
     */
    public String getTemporaryPunishMeasureInfo(String RecordId) {
        String responseText="";
        try {
            List<Map> mapList=punishMeasureDao.getTemporaryPunishMeasureInfo(RecordId);
            responseText = JSON.toJSONString ( mapList);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }
        return responseText;
    }


}
