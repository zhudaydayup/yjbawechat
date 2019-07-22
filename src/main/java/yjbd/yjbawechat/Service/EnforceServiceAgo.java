package yjbd.yjbawechat.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.EnforceRepositoryAgo;
import yjbd.yjbawechat.Model.EnforceModel;
import yjbd.yjbawechat.Util.DateTimeUtils;
import yjbd.yjbawechat.Util.MsgUtils;

import java.util.List;
import java.util.Map;

@Service
public class EnforceServiceAgo {
    @Autowired
    private EnforceRepositoryAgo enforceRepository;
    static final Logger logger = LoggerFactory.getLogger(EnforceServiceAgo.class);
    //String CHECKE_UNIT, String ADDRESS, String REPRESENT_PEOPLE,
// String REPRESENT_JOB, String MOBILE, String CHECKED_LOCATION,
// String CHECKED_START_TIME, String CHECKED_END_TIME,
// String EXECUTE_UNIT,String EXECUTE_PEOPLE ,String CARD_NUMBER,
// String CHECKE_DETAIL ,String REPRESENR_SIGN,String CHECKE_SIGH,
// String LOCATION_IMG,String OTHER_IMG

    public String UpdateIdRecord(EnforceModel enforceModel) {
        String responseText="";
        String createTime = DateTimeUtils.getNowTime();
        try {
            int flag = enforceRepository.UpdateIdRecord(
                    enforceModel.getCHECKED_UNIT(),enforceModel.getADDRESS(),enforceModel.getREPRESENT_PEOPLE(), enforceModel.getREPRESENT_MOBILE(),
                    enforceModel.getCHECKED_LOCATION(),enforceModel.getCHECKED_START_TIME(),enforceModel.getCHECKED_END_TIME(), enforceModel.getCHECK_UNIT1(),
                    enforceModel.getCHECK_PEOPLE1(),enforceModel.getCARD_NUMBER1(),enforceModel.getUNIT_NUMBER(),enforceModel.getCHECK_UNIT2(),
                    enforceModel.getCHECK_PEOPLE2(),enforceModel.getCARD_NUMBER2(),enforceModel.getCHECKED_REPRESENT_PEOPLE(),enforceModel.getCHECKED_SEX(),
                    enforceModel.getCHECKED_REPRESENT_NUMBER(),enforceModel.getCHECKED_REPRESENT_DUTY(),enforceModel.getCHECKED_REPRESENT_MOBILE(),
                    enforceModel.getWITNESS_PEOPLE(),enforceModel.getWITNESS_SEX(),enforceModel.getWITNESS_NUMBER(),enforceModel.getEXPERT_NAME1(),
                    enforceModel.getEXPERT_UNIT1(), enforceModel.getEXPERT_NAME2(),enforceModel.getEXPERT_UNIT2(),createTime, enforceModel.getAREA(),
                    enforceModel.getRECORD_ID());
            if (flag > 0) {
//                List<EnforceModel> resultSet= enforceRepository.getEnforceId(createTime);
//                String id=resultSet.get(0).getRECORD_ID();
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }


        return responseText;
    }

    public String createEnforce(EnforceModel enforceModel, String userId) {
        try {
            enforceModel.setCREATE_TIME(DateTimeUtils.getNowTime());
            int flag = enforceRepository.createEnforce(
                    enforceModel.getCHECKED_UNIT(), enforceModel.getUNIT_NUMBER(), enforceModel.getREPRESENT_PEOPLE(), enforceModel.getREPRESENT_MOBILE(),
                    enforceModel.getADDRESS(), enforceModel.getCHECK_PEOPLE1(), enforceModel.getCARD_NUMBER1(), enforceModel.getCHECK_UNIT1(),
                    enforceModel.getCHECK_PEOPLE2(), enforceModel.getCARD_NUMBER2(), enforceModel.getCHECK_UNIT2(), enforceModel.getEXPERT_NAME1(),
                    enforceModel.getEXPERT_UNIT1(), enforceModel.getEXPERT_NAME2(), enforceModel.getEXPERT_UNIT2(), enforceModel.getCHECKED_START_TIME(),
                    enforceModel.getNOTICE_CONTENT(), enforceModel.getNOTICE_WAY(), enforceModel.getNOTICE_CONTACTS(), enforceModel.getNOTICE_PHONE(),
                    enforceModel.getUSER_ID(), enforceModel.getAREA(), enforceModel.getCREATE_TIME(),enforceModel.getONE_ID(),enforceModel.getTWO_ID());
            if (flag > 0) {
                List<EnforceModel> resultSet= enforceRepository.getEnforceByUserId(userId);
                String id = resultSet.get(0).getRECORD_ID();
                return MsgUtils.successMsg("EventId",id);
            }
            else {
                return MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }
    }
    public String UpdateCheckSigh(String signName, String eventId,String signTime) {
        String responseText="";

        try {
            int flag = enforceRepository.UpdateCheckSigh(signName, eventId,signTime);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }

        return responseText;
    }
    //
    public String UpdateExpertSign(String signName, String eventId,String signTime) {
        String responseText="";

        try {
            int flag = enforceRepository.UpdateExpertSign(signName, eventId,signTime);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }

        return responseText;
    }
    public String UpdateWitnessSign(String signName, String eventId,String signTime) {
        String responseText="";

        try {
            int flag = enforceRepository.UpdateWitnessSign(signName, eventId,signTime);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch ( Exception e){
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }

        return responseText;
    }
    //    UpdateRepresentSign
    public String UpdateRepresentSign(String signName, String eventId,String signTime) {
        String responseText="";

        try {
            int flag = enforceRepository.UpdateRepresentSign(signName,eventId,signTime);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }

        return responseText;
    }

    public String getIdRecord(String recordId) {
        String responseText="";
        try {
            List<EnforceModel> list=enforceRepository.getIdRecord(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;

    }

    public String getIdCheckMsgs(String recordId) {
        String responseText="";
        try {
            List<Map> list=enforceRepository.getIdCheckMsgs(recordId);
            responseText = MsgUtils.successMsg("CheckInfo", list);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;
    }


}
