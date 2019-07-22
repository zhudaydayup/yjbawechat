package yjbd.yjbawechat.Model.ma;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PunishMeasureEntity {
    @Id
    @GeneratedValue
    public String RECORD_ID;
    public String SERVICE_ID;
    public String PROCESS_DECISION;
    public String SIGNATURE_PATH;
    public String SIGNATURE1_PATH;
    public String SIGNATURE2_PATH;
    public String SIGNATURE3_PATH;
    public String PDF_PATH;
    public String QRCODE_PATH;

    //伟东
    public String CHECKE_UNIT;
    public String CHECKED_START_TIME;
    public String CHECKE_DETAIL;
    public String CHECKED_END_TIME;



}
