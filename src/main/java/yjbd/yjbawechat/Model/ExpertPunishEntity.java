package yjbd.yjbawechat.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ExpertPunishEntity {
    @Id
    @GeneratedValue
    public String UPID;
    public String CHECKED_START_TIME;
    public String CHECKED_UNIT;
    public String PROCESS_DECISION;
    public String CHECKE_DETAIL;
    public String RECORD_TIME;
    public String RECORD_ID;
    public String SIGNATURE_PATH;
    public String SIGNATURE1_PATH;
    public String SIGNATURE2_PATH;
    public String PDF_PATH;
    public String QRCODE_PATH;




}
