package yjbd.yjbawechat.Dao.ma;


import org.apache.ibatis.annotations.*;
import yjbd.yjbawechat.Model.ma.RectificationEntity;

import java.util.List;
import java.util.Map;

@Mapper
public interface RectificationDao {
    @Insert("insert into ZHIFA_RECTIFICATION (SERVICE_ID,CHECKE_UNIT,CHECKED_START_TIME,CHECKE_OPINION,RECORD_TIME) values (#{RECORD_ID},#{CHECKE_UNIT},#{CHECKED_START_TIME},#{CHECKE_OPINION},#{RECORD_TIME})" )
    void setRectificationInfo(@Param("RECORD_ID") String RECORD_ID, @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKE_OPINION") String CHECKE_OPINION, @Param("RECORD_TIME") String RECORD_TIME);

    @Update("update ZHIFA_RECTIFICATION set SIGNATURE4_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign4(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_RECTIFICATION set SIGNATURE5_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign5(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_RECTIFICATION set SIGNATURE6_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign6(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_RECTIFICATION set SIGNATURE7_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign7(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_RECTIFICATION set SIGNATURE8_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign8(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_RECTIFICATION set SIGNATURE9_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign9(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);


    @Select("select * from ZHIFA_RECTIFICATION where SERVICE_ID = #{RECORD_ID}  ")
    List<Map> getPdfInfo(@Param("RECORD_ID") String RECORD_ID);

    /**
     * 从最初的表格中获取信息
     * @param
     * @param
     * @param
     */
    @Select("select * from ZHIFA_SCENE_RECORD where RECORD_ID = #{RECORD_ID}  ")
    List<Map> searchCareId(@Param("RECORD_ID") String RECORD_ID);
    @Update("update ZHIFA_RECTIFICATION set PDF_PATH= #{PDF_PATH},QRCODE_PATH=#{QRCODE_PATH} where SERVICE_ID= #{RECORD_ID}")
    void updatePdfAndQrcode(@Param("PDF_PATH") String PDF_PATH, @Param("QRCODE_PATH") String QRCODE_PATH, @Param("RECORD_ID") String RECORD_ID);


    @Select("select * from ZHIFA_RECTIFICATION where SERVICE_ID = #{RECORD_ID}")
    List<RectificationEntity> getIdRecord(@Param("RECORD_ID") String RECORD_ID);
}
