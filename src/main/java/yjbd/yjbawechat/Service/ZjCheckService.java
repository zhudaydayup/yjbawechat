package yjbd.yjbawechat.Service;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.ZjCheckDao;
import yjbd.yjbawechat.Model.EnforceModel;
import yjbd.yjbawechat.Util.DateTimeUtils;
import yjbd.yjbawechat.Util.MsgUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/20 19:14
 */
@Service
public class ZjCheckService {
    @Autowired
    ZjCheckDao zjCheckDao;
    static final Logger logger = LoggerFactory.getLogger(ZjCheckService.class);


    /**
     * 提交自检企业信息
     */
    public String PutZjCheckView(EnforceModel enforceModel) {
        String responseText="";
        String createTime = DateTimeUtils.getNowTime();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CHECKED_END_TIME=df1.format(new Date()).toString();
        try {
            int flag = zjCheckDao.PutZjCheckView(
                    enforceModel.getCHECKED_UNIT(),
                    enforceModel.getCHECKED_LOCATION(),CHECKED_END_TIME,
                    enforceModel.getCHECK_PEOPLE1(),enforceModel.getUNIT_NUMBER (),
                    createTime,enforceModel.getRECORD_ID());
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

    public String GetZjRecord(String recordId) {
        String responseText="";
        try {
            List<Map> list=zjCheckDao.GetZjRecord(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }

        return responseText;
    }

    public String GetZjCheckMsgs(String recordId) {
        String responseText="";
        try {
            List<Map> list=zjCheckDao.GetZjCheckMsgs(recordId);
            responseText = MsgUtils.successMsg("CheckInfo", list);
        }catch (Exception e){
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }

        return responseText;
    }


}
