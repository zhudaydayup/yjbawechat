package yjbd.yjbawechat.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.ExEnforceRepository;
import yjbd.yjbawechat.Model.exEnforceModel;
import yjbd.yjbawechat.Util.DateTimeUtils;
import yjbd.yjbawechat.Util.MsgUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExEnforceService {
    @Autowired
    private ExEnforceRepository exenforceRepository;
    static final Logger logger = LoggerFactory.getLogger(ExEnforceService.class);
    public String UpdateIdRecord(exEnforceModel exenforceModel) {
        String responseText="";
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String createTime=df.format(new Date ()).toString();
        try {
            int flag = exenforceRepository.UpdateIdRecord(

                    exenforceModel.getCHECKED_UNIT(),exenforceModel.getADDRESS(),exenforceModel.getREPRESENT_PEOPLE(), exenforceModel.getREPRESENT_MOBILE(),
                    exenforceModel.getCHECKED_LOCATION(),exenforceModel.getCHECKED_START_TIME(),exenforceModel.getCHECKED_END_TIME(),
                    exenforceModel.getUNIT_NUMBER(),exenforceModel.getEXPERT_NAME1(), exenforceModel.getEXPERT_UNIT1(), exenforceModel.getEXPERT_NAME2(),
                    exenforceModel.getEXPERT_UNIT2(), exenforceModel.getRECORD_ID()

            );
            if (flag > 0) {
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

    /**
     * 更新检查记录页面缓存
     * @param
     * @param
     * @param
     * @return
     */
    public String temporaryExUpdateIdRecord(exEnforceModel exenforceModel) {
        String responseText="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=df.format(new Date()).toString();
        try {

            int flag = exenforceRepository.temporaryExUpdateIdRecord(

                    exenforceModel.getCHECKED_UNIT(),exenforceModel.getADDRESS(),exenforceModel.getREPRESENT_PEOPLE(), exenforceModel.getREPRESENT_MOBILE(),
                    exenforceModel.getCHECKED_LOCATION(),exenforceModel.getCHECKED_START_TIME(),exenforceModel.getCHECKED_END_TIME(),
                    exenforceModel.getUNIT_NUMBER(),exenforceModel.getEXPERT_NAME1(), exenforceModel.getEXPERT_UNIT1(),
                    createTime, exenforceModel.getURL (),exenforceModel.getRECORD_ID()

            );
            if (flag > 0) {
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
    /**
     * 获取更新检查记录页面缓存
     * @param
     * @return
     */
    public String getTemporaryExUpdateIdRecord(String recordId) {
        String responseText="";
        try {
            List<Map> list=exenforceRepository.getTemporaryExUpdateIdRecord(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;
    }

    //
    public String UpdateExpertSign(String signName, String eventId,String signTime) {
        String responseText="";

        try {
            int flag = exenforceRepository.UpdateExpertSign(signName, eventId,signTime);
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



    public String UpdateWitnessSign(String signName, String eventId,String signTime) {
        String responseText="";

        try {
            int flag = exenforceRepository.UpdateWitnessSign(signName, eventId,signTime);
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

    public String GetTime(String recordId) {
        String responseText="";
        try {
            responseText=exenforceRepository.GetTime(recordId);
        }catch ( Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;

    }


    public String getIdRecord(String recordId) {
        String responseText="";
        try {
            List<exEnforceModel> list=exenforceRepository.getIdRecord(recordId);
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
            List<Map> list=exenforceRepository.getIdCheckMsgs(recordId);
            responseText = MsgUtils.successMsg("CheckInfo", list);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;
    }


    /**
     * 获取重大隐患记录信息
     * @param recordId
     * @return
     */
    public String getDangerRecord(String recordId) {
        String responseText="";
        try {
            List<Map> list=exenforceRepository.getDangerRecord(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;
    }

    public List<Map>  getDangerRecord2(String recordId) {
        List<Map> list=exenforceRepository.getDangerRecord(recordId);
        return list;
    }


}
