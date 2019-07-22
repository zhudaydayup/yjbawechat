package yjbd.yjbawechat.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class EnforceModel {
    @Id
    @GeneratedValue
    private String RECORD_ID;
    private String CHECKED_UNIT;
    private String ADDRESS;
    private String REPRESENT_PEOPLE;
    private String REPRESENT_MOBILE;
    private String CHECKED_LOCATION;
    private String CHECKED_START_TIME;
    private String CHECKED_END_TIME;
    private String CHECK_UNIT1;
    private String CHECK_PEOPLE1;
    private String CARD_NUMBER1;
    private String REPRESENT_SIGN;
    private String CHECK_SIGN;
    private String CREATE_TIME;
    private String RECORD_PDF;
    private String RECORD_QR_CODE;
    private String REPRESENT_SIGN_TIME;
    private String CHECK_SIGN_TIME;
    private String UNIT_NUMBER;
    private String USER_ID;
    private String CHECK_UNIT2;
    private String CHECK_PEOPLE2;
    private String CARD_NUMBER2;
    private String CHECKED_REPRESENT_PEOPLE;
    private String CHECKED_SEX;
    private String CHECKED_REPRESENT_NUMBER;
    private String CHECKED_REPRESENT_DUTY;
    private String CHECKED_REPRESENT_MOBILE;
    private String WITNESS_PEOPLE;
    private String WITNESS_SEX;
    private String WITNESS_NUMBER;
    private String EXPERT_NAME1;
    private String EXPERT_UNIT1;
    private String EXPERT_NAME2;
    private String EXPERT_UNIT2;
    private String EXPERT_SIGN;
    private String EXPERT_SIGN_TIME;
    private String WITNESS_SIGN;
    private String WITNESS_SIGN_TIME;
    private String NOTICE_PDF;
    private String NOTICE_QR_CODE;
    private String NOTICE_CONTENT;
    private String NOTICE_WAY;
    private String NOTICE_CONTACTS;
    private String NOTICE_PHONE;
    private String AREA;
    private String TWO_ID;
    private String ONE_ID;
    private String URL;
    private String NOTIFICATION_NUMBER;
    private String AUDIT_AREA;
    private String AUDIT_RECORD;

    public EnforceModel(String CHECKED_UNIT, String ADDRESS, String REPRESENT_PEOPLE, String REPRESENT_MOBILE, String CHECKED_LOCATION, String CHECKED_START_TIME, String CHECKED_END_TIME, String CHECK_UNIT1, String CHECK_PEOPLE1, String CARD_NUMBER1, String REPRESENT_SIGN, String CHECK_SIGN, String CREATE_TIME, String RECORD_PDF, String RECORD_QR_CODE, String REPRESENT_SIGN_TIME, String CHECK_SIGN_TIME, String UNIT_NUMBER, String USER_ID, String CHECK_UNIT2, String CHECK_PEOPLE2, String CARD_NUMBER2, String CHECKED_REPRESENT_PEOPLE, String CHECKED_SEX, String CHECKED_REPRESENT_NUMBER, String CHECKED_REPRESENT_DUTY, String CHECKED_REPRESENT_MOBILE, String WITNESS_PEOPLE, String WITNESS_SEX, String WITNESS_NUMBER, String EXPERT_NAME1, String EXPERT_UNIT1, String EXPERT_NAME2, String EXPERT_UNIT2, String EXPERT_SIGN, String EXPERT_SIGN_TIME, String WITNESS_SIGN, String WITNESS_SIGN_TIME, String NOTICE_PDF, String NOTICE_QR_CODE, String NOTICE_CONTENT, String NOTICE_WAY, String NOTICE_CONTACTS,
                        String NOTICE_PHONE, String AREA,String TWO_ID, String ONE_ID,String URL,String NOTIFICATION_NUMBER,String AUDIT_AREA,String AUDIT_RECORD) {
        this.CHECKED_UNIT = CHECKED_UNIT;
        this.ADDRESS = ADDRESS;
        this.REPRESENT_PEOPLE = REPRESENT_PEOPLE;
        this.REPRESENT_MOBILE = REPRESENT_MOBILE;
        this.CHECKED_LOCATION = CHECKED_LOCATION;
        this.CHECKED_START_TIME = CHECKED_START_TIME;
        this.CHECKED_END_TIME = CHECKED_END_TIME;
        this.CHECK_UNIT1 = CHECK_UNIT1;
        this.CHECK_PEOPLE1 = CHECK_PEOPLE1;
        this.CARD_NUMBER1 = CARD_NUMBER1;
        this.REPRESENT_SIGN = REPRESENT_SIGN;
        this.CHECK_SIGN = CHECK_SIGN;
        this.CREATE_TIME = CREATE_TIME;
        this.RECORD_PDF = RECORD_PDF;
        this.RECORD_QR_CODE = RECORD_QR_CODE;
        this.REPRESENT_SIGN_TIME = REPRESENT_SIGN_TIME;
        this.CHECK_SIGN_TIME = CHECK_SIGN_TIME;
        this.UNIT_NUMBER = UNIT_NUMBER;
        this.USER_ID = USER_ID;
        this.CHECK_UNIT2 = CHECK_UNIT2;
        this.CHECK_PEOPLE2 = CHECK_PEOPLE2;
        this.CARD_NUMBER2 = CARD_NUMBER2;
        this.CHECKED_REPRESENT_PEOPLE = CHECKED_REPRESENT_PEOPLE;
        this.CHECKED_SEX = CHECKED_SEX;
        this.CHECKED_REPRESENT_NUMBER = CHECKED_REPRESENT_NUMBER;
        this.CHECKED_REPRESENT_DUTY = CHECKED_REPRESENT_DUTY;
        this.CHECKED_REPRESENT_MOBILE = CHECKED_REPRESENT_MOBILE;
        this.WITNESS_PEOPLE = WITNESS_PEOPLE;
        this.WITNESS_SEX = WITNESS_SEX;
        this.WITNESS_NUMBER = WITNESS_NUMBER;
        this.EXPERT_NAME1 = EXPERT_NAME1;
        this.EXPERT_UNIT1 = EXPERT_UNIT1;
        this.EXPERT_NAME2 = EXPERT_NAME2;
        this.EXPERT_UNIT2 = EXPERT_UNIT2;
        this.EXPERT_SIGN = EXPERT_SIGN;
        this.EXPERT_SIGN_TIME = EXPERT_SIGN_TIME;
        this.WITNESS_SIGN = WITNESS_SIGN;
        this.WITNESS_SIGN_TIME = WITNESS_SIGN_TIME;
        this.NOTICE_PDF = NOTICE_PDF;
        this.NOTICE_QR_CODE = NOTICE_QR_CODE;
        this.NOTICE_CONTENT = NOTICE_CONTENT;
        this.NOTICE_WAY = NOTICE_WAY;
        this.NOTICE_CONTACTS = NOTICE_CONTACTS;
        this.NOTICE_PHONE = NOTICE_PHONE;
        this.AREA = AREA;
        this.TWO_ID = TWO_ID;
        this.ONE_ID = ONE_ID;
        this.URL = URL;
        this.NOTIFICATION_NUMBER = NOTIFICATION_NUMBER;
        this.AUDIT_RECORD = AUDIT_RECORD;
        this.AUDIT_AREA = AUDIT_AREA;

    }

    public EnforceModel() {
        super();
    }

    public String getTWO_ID() {
        return TWO_ID;
    }

    public void setTWO_ID(String TWO_ID) {
        this.TWO_ID = TWO_ID;
    }

    public String getONE_ID() {
        return ONE_ID;
    }

    public void setONE_ID(String ONE_ID) {
        this.ONE_ID = ONE_ID;
    }

    public String getRECORD_ID() {
        return RECORD_ID;
    }

    public void setRECORD_ID(String RECORD_ID) {
        this.RECORD_ID = RECORD_ID;
    }

    public String getCHECKED_UNIT() {
        return CHECKED_UNIT;
    }

    public void setCHECKED_UNIT(String CHECKED_UNIT) {
        this.CHECKED_UNIT = CHECKED_UNIT;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getREPRESENT_PEOPLE() {
        return REPRESENT_PEOPLE;
    }

    public void setREPRESENT_PEOPLE(String REPRESENT_PEOPLE) {
        this.REPRESENT_PEOPLE = REPRESENT_PEOPLE;
    }

    public String getREPRESENT_MOBILE() {
        return REPRESENT_MOBILE;
    }

    public void setREPRESENT_MOBILE(String REPRESENT_MOBILE) {
        this.REPRESENT_MOBILE = REPRESENT_MOBILE;
    }

    public String getCHECKED_LOCATION() {
        return CHECKED_LOCATION;
    }

    public void setCHECKED_LOCATION(String CHECKED_LOCATION) {
        this.CHECKED_LOCATION = CHECKED_LOCATION;
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

    public String getCHECK_UNIT1() {
        return CHECK_UNIT1;
    }

    public void setCHECK_UNIT1(String CHECK_UNIT1) {
        this.CHECK_UNIT1 = CHECK_UNIT1;
    }

    public String getCHECK_PEOPLE1() {
        return CHECK_PEOPLE1;
    }

    public void setCHECK_PEOPLE1(String CHECK_PEOPLE1) {
        this.CHECK_PEOPLE1 = CHECK_PEOPLE1;
    }

    public String getCARD_NUMBER1() {
        return CARD_NUMBER1;
    }

    public void setCARD_NUMBER1(String CARD_NUMBER1) {
        this.CARD_NUMBER1 = CARD_NUMBER1;
    }

    public String getREPRESENT_SIGN() {
        return REPRESENT_SIGN;
    }

    public void setREPRESENT_SIGN(String REPRESENT_SIGN) {
        this.REPRESENT_SIGN = REPRESENT_SIGN;
    }

    public String getCHECK_SIGN() {
        return CHECK_SIGN;
    }

    public void setCHECK_SIGN(String CHECK_SIGN) {
        this.CHECK_SIGN = CHECK_SIGN;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getRECORD_PDF() {
        return RECORD_PDF;
    }

    public void setRECORD_PDF(String RECORD_PDF) {
        this.RECORD_PDF = RECORD_PDF;
    }

    public String getRECORD_QR_CODE() {
        return RECORD_QR_CODE;
    }

    public void setRECORD_QR_CODE(String RECORD_QR_CODE) {
        this.RECORD_QR_CODE = RECORD_QR_CODE;
    }

    public String getREPRESENT_SIGN_TIME() {
        return REPRESENT_SIGN_TIME;
    }

    public void setREPRESENT_SIGN_TIME(String REPRESENT_SIGN_TIME) {
        this.REPRESENT_SIGN_TIME = REPRESENT_SIGN_TIME;
    }

    public String getCHECK_SIGN_TIME() {
        return CHECK_SIGN_TIME;
    }

    public void setCHECK_SIGN_TIME(String CHECK_SIGN_TIME) {
        this.CHECK_SIGN_TIME = CHECK_SIGN_TIME;
    }

    public String getUNIT_NUMBER() {
        return UNIT_NUMBER;
    }

    public void setUNIT_NUMBER(String UNIT_NUMBER) {
        this.UNIT_NUMBER = UNIT_NUMBER;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getCHECK_UNIT2() {
        return CHECK_UNIT2;
    }

    public void setCHECK_UNIT2(String CHECK_UNIT2) {
        this.CHECK_UNIT2 = CHECK_UNIT2;
    }

    public String getCHECK_PEOPLE2() {
        return CHECK_PEOPLE2;
    }

    public void setCHECK_PEOPLE2(String CHECK_PEOPLE2) {
        this.CHECK_PEOPLE2 = CHECK_PEOPLE2;
    }

    public String getCARD_NUMBER2() {
        return CARD_NUMBER2;
    }

    public void setCARD_NUMBER2(String CARD_NUMBER2) {
        this.CARD_NUMBER2 = CARD_NUMBER2;
    }

    public String getCHECKED_REPRESENT_PEOPLE() {
        return CHECKED_REPRESENT_PEOPLE;
    }

    public void setCHECKED_REPRESENT_PEOPLE(String CHECKED_REPRESENT_PEOPLE) {
        this.CHECKED_REPRESENT_PEOPLE = CHECKED_REPRESENT_PEOPLE;
    }

    public String getCHECKED_SEX() {
        return CHECKED_SEX;
    }

    public void setCHECKED_SEX(String CHECKED_SEX) {
        this.CHECKED_SEX = CHECKED_SEX;
    }

    public String getCHECKED_REPRESENT_NUMBER() {
        return CHECKED_REPRESENT_NUMBER;
    }

    public void setCHECKED_REPRESENT_NUMBER(String CHECKED_REPRESENT_NUMBER) {
        this.CHECKED_REPRESENT_NUMBER = CHECKED_REPRESENT_NUMBER;
    }

    public String getCHECKED_REPRESENT_DUTY() {
        return CHECKED_REPRESENT_DUTY;
    }

    public void setCHECKED_REPRESENT_DUTY(String CHECKED_REPRESENT_DUTY) {
        this.CHECKED_REPRESENT_DUTY = CHECKED_REPRESENT_DUTY;
    }

    public String getCHECKED_REPRESENT_MOBILE() {
        return CHECKED_REPRESENT_MOBILE;
    }

    public void setCHECKED_REPRESENT_MOBILE(String CHECKED_REPRESENT_MOBILE) {
        this.CHECKED_REPRESENT_MOBILE = CHECKED_REPRESENT_MOBILE;
    }

    public String getWITNESS_PEOPLE() {
        return WITNESS_PEOPLE;
    }

    public void setWITNESS_PEOPLE(String WITNESS_PEOPLE) {
        this.WITNESS_PEOPLE = WITNESS_PEOPLE;
    }

    public String getWITNESS_SEX() {
        return WITNESS_SEX;
    }

    public void setWITNESS_SEX(String WITNESS_SEX) {
        this.WITNESS_SEX = WITNESS_SEX;
    }

    public String getWITNESS_NUMBER() {
        return WITNESS_NUMBER;
    }

    public void setWITNESS_NUMBER(String WITNESS_NUMBER) {
        this.WITNESS_NUMBER = WITNESS_NUMBER;
    }

    public String getEXPERT_NAME1() {
        return EXPERT_NAME1;
    }

    public void setEXPERT_NAME1(String EXPERT_NAME1) {
        this.EXPERT_NAME1 = EXPERT_NAME1;
    }

    public String getEXPERT_UNIT1() {
        return EXPERT_UNIT1;
    }

    public void setEXPERT_UNIT1(String EXPERT_UNIT1) {
        this.EXPERT_UNIT1 = EXPERT_UNIT1;
    }

    public String getEXPERT_NAME2() {
        return EXPERT_NAME2;
    }

    public void setEXPERT_NAME2(String EXPERT_NAME2) {
        this.EXPERT_NAME2 = EXPERT_NAME2;
    }

    public String getEXPERT_UNIT2() {
        return EXPERT_UNIT2;
    }

    public void setEXPERT_UNIT2(String EXPERT_UNIT2) {
        this.EXPERT_UNIT2 = EXPERT_UNIT2;
    }

    public String getEXPERT_SIGN() {
        return EXPERT_SIGN;
    }

    public void setEXPERT_SIGN(String EXPERT_SIGN) {
        this.EXPERT_SIGN = EXPERT_SIGN;
    }

    public String getEXPERT_SIGN_TIME() {
        return EXPERT_SIGN_TIME;
    }

    public void setEXPERT_SIGN_TIME(String EXPERT_SIGN_TIME) {
        this.EXPERT_SIGN_TIME = EXPERT_SIGN_TIME;
    }

    public String getWITNESS_SIGN() {
        return WITNESS_SIGN;
    }

    public void setWITNESS_SIGN(String WITNESS_SIGN) {
        this.WITNESS_SIGN = WITNESS_SIGN;
    }

    public String getWITNESS_SIGN_TIME() {
        return WITNESS_SIGN_TIME;
    }

    public void setWITNESS_SIGN_TIME(String WITNESS_SIGN_TIME) {
        this.WITNESS_SIGN_TIME = WITNESS_SIGN_TIME;
    }

    public String getNOTICE_PDF() {
        return NOTICE_PDF;
    }

    public void setNOTICE_PDF(String NOTICE_PDF) {
        this.NOTICE_PDF = NOTICE_PDF;
    }

    public String getNOTICE_QR_CODE() {
        return NOTICE_QR_CODE;
    }

    public void setNOTICE_QR_CODE(String NOTICE_QR_CODE) {
        this.NOTICE_QR_CODE = NOTICE_QR_CODE;
    }

    public String getNOTICE_CONTENT() {
        return NOTICE_CONTENT;
    }

    public void setNOTICE_CONTENT(String NOTICE_CONTENT) {
        this.NOTICE_CONTENT = NOTICE_CONTENT;
    }

    public String getNOTICE_WAY() {
        return NOTICE_WAY;
    }

    public void setNOTICE_WAY(String NOTICE_WAY) {
        this.NOTICE_WAY = NOTICE_WAY;
    }

    public String getNOTICE_CONTACTS() {
        return NOTICE_CONTACTS;
    }

    public void setNOTICE_CONTACTS(String NOTICE_CONTACTS) {
        this.NOTICE_CONTACTS = NOTICE_CONTACTS;
    }

    public String getNOTICE_PHONE() {
        return NOTICE_PHONE;
    }

    public void setNOTICE_PHONE(String NOTICE_PHONE) {
        this.NOTICE_PHONE = NOTICE_PHONE;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getNOTIFICATION_NUMBER() {
        return NOTIFICATION_NUMBER;
    }

    public void setNOTIFICATION_NUMBER(String NOTIFICATION_NUMBER) {
        this.NOTIFICATION_NUMBER = NOTIFICATION_NUMBER;
    }

    public String getAUDIT_AREA() {
        return AUDIT_AREA;
    }

    public void setAUDIT_AREA(String AUDIT_AREA) {
        this.AUDIT_AREA = AUDIT_AREA;
    }

    public String getAUDIT_RECORD() {
        return AUDIT_RECORD;
    }

    public void setAUDIT_RECORD(String AUDIT_RECORD) {
        this.AUDIT_RECORD = AUDIT_RECORD;
    }
}
