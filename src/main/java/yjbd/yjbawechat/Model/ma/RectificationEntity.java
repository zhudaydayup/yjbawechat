package yjbd.yjbawechat.Model.ma;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class RectificationEntity {
    @Id
    @GeneratedValue
    public String RECORD_ID;
    public String CHECKE_UNIT;
    public String CHECKE_OPINION;
    public String CHECKED_START_TIME;
    public String SIGNATURE_PATH;
    public String SIGNATURE4_PATH;
    public String SIGNATURE5_PATH;
    public String SIGNATURE6_PATH;
    public String SIGNATURE7_PATH;
    public String SIGNATURE8_PATH;
    public String SIGNATURE9_PATH;
    public String PDF_PATH;
    public String QRCODE_PATH;







}
