package yjbd.yjbawechat.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CheckMsgModel {
    @Id
    @GeneratedValue
    public String SERVICE_ID;//现场检查记录id/单个模板
    public String CHECKED_START_TIME;//被检查单位
    public String CHECKED_END_TIME;//被检单位地址
    public String CHECKE_DETAIL;//法定代表人
    public String VIDEO_URL;//法定代表人手机号码
    public String LOCATION_IMG;//检查场所
    public String OTHER_IMG;//检查开始时间
    public String CREATE_TIME;//检查结束时间
    public String CHECK_STATE;//执法单位
    public String TEMP_ID;//执法人
    public String TEMP_PDF;//执法人证件
    public String TEMP_NAME;//检查情况
    public String USER_ID;//视频地址

    public String getSERVICE_ID() {
        return SERVICE_ID;
    }

    public void setSERVICE_ID(String SERVICE_ID) {
        this.SERVICE_ID = SERVICE_ID;
    }

    public String getCHECKED_START_TIME() {
        return CHECKED_START_TIME;
    }

    public void setCHECKED_START_TIME(String CHECKED_START_TIME) {
        this.CHECKED_START_TIME = CHECKED_START_TIME;
    }

    public String getCHECKED_END_TIME() {
        return CHECKED_END_TIME;
    }

    public void setCHECKED_END_TIME(String CHECKED_END_TIME) {
        this.CHECKED_END_TIME = CHECKED_END_TIME;
    }

    public String getCHECKE_DETAIL() {
        return CHECKE_DETAIL;
    }

    public void setCHECKE_DETAIL(String CHECKE_DETAIL) {
        this.CHECKE_DETAIL = CHECKE_DETAIL;
    }

    public String getVIDEO_URL() {
        return VIDEO_URL;
    }

    public void setVIDEO_URL(String VIDEO_URL) {
        this.VIDEO_URL = VIDEO_URL;
    }

    public String getLOCATION_IMG() {
        return LOCATION_IMG;
    }

    public void setLOCATION_IMG(String LOCATION_IMG) {
        this.LOCATION_IMG = LOCATION_IMG;
    }

    public String getOTHER_IMG() {
        return OTHER_IMG;
    }

    public void setOTHER_IMG(String OTHER_IMG) {
        this.OTHER_IMG = OTHER_IMG;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getCHECK_STATE() {
        return CHECK_STATE;
    }

    public void setCHECK_STATE(String CHECK_STATE) {
        this.CHECK_STATE = CHECK_STATE;
    }

    public String getTEMP_ID() {
        return TEMP_ID;
    }

    public void setTEMP_ID(String TEMP_ID) {
        this.TEMP_ID = TEMP_ID;
    }

    public String getTEMP_PDF() {
        return TEMP_PDF;
    }

    public void setTEMP_PDF(String TEMP_PDF) {
        this.TEMP_PDF = TEMP_PDF;
    }

    public String getTEMP_NAME() {
        return TEMP_NAME;
    }

    public void setTEMP_NAME(String TEMP_NAME) {
        this.TEMP_NAME = TEMP_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }
}
