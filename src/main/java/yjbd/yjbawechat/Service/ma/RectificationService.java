package yjbd.yjbawechat.Service.ma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.ma.RectificationDao;
import yjbd.yjbawechat.Model.ma.RectificationEntity;

import java.util.Calendar;
import java.util.List;

@Service
public class RectificationService {
    @Autowired
    RectificationDao rectificationDao;


    public void setRectificationInfo(String RECORD_ID,String CHECKE_UNIT,String CHECKED_START_TIME,String CHECKE_OPINION){
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        String RECORD_TIME = now.get(Calendar.YEAR)+"年"+month+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
        rectificationDao.setRectificationInfo(RECORD_ID,CHECKE_UNIT,CHECKED_START_TIME,CHECKE_OPINION,RECORD_TIME);
    }

    /**
     * 各种签名
     * @param RECORD_ID
     * @param SIGNATURE_NO
     * @param SIGNATURE_PATH
     */
    public void UpdateCheckSign(String RECORD_ID,String SIGNATURE_NO,String SIGNATURE_PATH){
        if(SIGNATURE_NO.equals("4")){rectificationDao.UpdateCheckSign4(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("5")){rectificationDao.UpdateCheckSign5(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("6")){rectificationDao.UpdateCheckSign6(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("7")){rectificationDao.UpdateCheckSign7(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("8")){rectificationDao.UpdateCheckSign8(RECORD_ID,SIGNATURE_PATH);}
        else if(SIGNATURE_NO.equals("9")){rectificationDao.UpdateCheckSign9(RECORD_ID,SIGNATURE_PATH);}
    }

    public List<RectificationEntity> getIdRecord(String RECORD_ID){
        return rectificationDao.getIdRecord(RECORD_ID);
    }
}
