package yjbd.yjbawechat.Service.FireFighting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.FireFighting.FireIndexDao;
import yjbd.yjbawechat.VO.ResultVO;

import java.util.List;
import java.util.Map;

@Service
public class FireIndexService {
    @Autowired
    private FireIndexDao fireIndexDao;
    static final Logger logger = LoggerFactory.getLogger(FireIndexService.class);
    public List<Map> getAddItem(){
        return fireIndexDao.getFireChecked();
    }
    public ResultVO insetCheckItem(String CHECKED_UNIT, String UNIT_NUMBER, String ADDRESS, String REPRESENT_PEOPLE, String REPRESENT_MOBILE, String RISK_POINT, String CHECK_START_TIME, String CHECK_PEOPLE, String CARD_NUMBER, String CHECK_UNIT, String CHECK_ITEM, String CHECK_DESC, String CHECK_IMG, String CHECK_VIDEO, String CREATE_TIME, String INSPECT_TYPE, String REGION_ID){

        try {
            fireIndexDao.insertCheckItem(CHECKED_UNIT,UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,REPRESENT_MOBILE,RISK_POINT,CHECK_START_TIME,CHECK_PEOPLE,CARD_NUMBER,CHECK_UNIT,CHECK_ITEM,CHECK_DESC,CHECK_IMG,CHECK_VIDEO,CREATE_TIME,INSPECT_TYPE,REGION_ID);
            return ResultVO.successMsg("提交成功");
        }catch (Exception e){
            logger.info(e.toString());
            return ResultVO.failMsg("提交失败");
        }
    }
    //插入检查人签名
    public ResultVO updateCheckSign(String CHECKMAN_SIGN, String CREATE_TIME){
        try{
            fireIndexDao.updateCheckSign(CHECKMAN_SIGN,CREATE_TIME);
            return ResultVO.successMsg("提交成功");
        }catch (Exception e){
            logger.info(e.toString());
            return ResultVO.failMsg("提交失败");
        }

    }
    //插入被检查单位负责人签名
    public ResultVO updateCheckedSign(String CHECKEDMAN_SIGN, String CREATE_TIME){
        try{
            fireIndexDao.updateCheckedSign(CHECKEDMAN_SIGN,CREATE_TIME);
            return ResultVO.successMsg("提交成功");
        }catch (Exception e){
            logger.info(e.toString());
            return ResultVO.failMsg("提交失败");
        }

    }
    //为pdf文件获取所需信息
    public List<Map> getCheckInfo(String CREATE_TIME){
        return fireIndexDao.getCheckInfo(CREATE_TIME);
    }
    //插入pdf文件
    public ResultVO updateCheckPdf(String CHECK_PDF, String QR_CODE, String CREATE_TIME){
        try{
            if(!"".equals(CHECK_PDF)){
                fireIndexDao.updateCheckPdf(CHECK_PDF,QR_CODE,CREATE_TIME);
                return ResultVO.successMsg("提交成功");
            }
            else {
                return ResultVO.failMsg("提交失败");
            }
        }catch (Exception e){
            logger.info(e.toString());
            return ResultVO.failMsg("提交失败");
        }
    }
}
