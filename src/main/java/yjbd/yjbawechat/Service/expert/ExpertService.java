package yjbd.yjbawechat.Service.expert;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.expert.ExpertDao;
import yjbd.yjbawechat.Service.FireFighting.FireIndexService;
import yjbd.yjbawechat.Util.MsgUtils;
import yjbd.yjbawechat.VO.ResultVO;

import java.util.List;
import java.util.Map;

@Service
public class ExpertService {
    @Autowired
    private ExpertDao expertDao;
    static final Logger logger = LoggerFactory.getLogger(ExpertService.class);
    public List<Map> getExpertInfo(String RECORD_ID){
        return expertDao.getExpertInfo(RECORD_ID);
    }
    public List<Map> getExpertCheckInfo(String RECORD_ID,String CHECK_STATE){
        return expertDao.getExpertCheckInfo(RECORD_ID,CHECK_STATE);
    }
    public List<Map> getExpertRecordInfo(String RECORD_ID){
        return expertDao.getExpertRecordInfo(RECORD_ID);
    }
    //创建现场整改记录
    public void insertRectification(String SERVICE_ID,String CHECKED_START_TIME){
        int count = 0;
        try{
            count=expertDao.checkTemporaryRecordIdCount(SERVICE_ID);
            if (count>0){
                expertDao.insertRectification1(CHECKED_START_TIME,SERVICE_ID);
            }else {
                expertDao.insertRectification(SERVICE_ID,CHECKED_START_TIME);
            }


        }catch (Exception e){
            logger.info(e.toString());
        }
    }
    /**
     * 缓存更新一般隐患页面
     * @param
     * @param
     * @return
     */
    public String temporaryGeneralHiddenDanger(String PROCESS_DECISION,String URL,String RECORD_ID){
        String responseText="";
        try{
            int flag =expertDao.temporaryGeneralHiddenDanger(PROCESS_DECISION,URL,RECORD_ID);
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
    //插入专家签名
    public ResultVO updateExpertSign(String EXPERT_SIGN, String SERVICE_ID){
        try{
            expertDao.updateExpertSign(EXPERT_SIGN,SERVICE_ID);
            return ResultVO.successMsg("提交成功");
        }catch (Exception e){
            logger.info ( e.toString () );
            return ResultVO.failMsg("提交失败");
        }
    }
    //插入被检查单位负责人签名
    public ResultVO updateCheckedSign(String CHECKEDMAN_SIGN, String SERVICE_ID){
        try{
            expertDao.updateCheckedSign(CHECKEDMAN_SIGN,SERVICE_ID);
            return ResultVO.successMsg("提交成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
            return ResultVO.failMsg("提交失败");
        }
    }
    //插入处理决定
    public void updateDealView(String DEAL_VIEW, String SERVICE_ID){
        try{
            expertDao.updateDealView(DEAL_VIEW,SERVICE_ID);
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
        }
    }
    public void updateCheckPdf(String PDF_PATH, String QRCODE_PATH,String SERVICE_ID){
        try{
            expertDao.updateCheckPdf(PDF_PATH,QRCODE_PATH,SERVICE_ID);
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
        }
    }
    /**
     * 获取缓存一般隐患现场整改表格信息
     * @param
     * @return
     */
    public String getTemporaryGeneralHiddenDanger(String RecordId) {
        String responseText="";
        try {
            List<Map> mapList=expertDao.getTemporaryGeneralHiddenDanger(RecordId);
            responseText = JSON.toJSONString ( mapList);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;
    }
}
