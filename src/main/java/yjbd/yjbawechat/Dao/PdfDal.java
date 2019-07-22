package yjbd.yjbawechat.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface PdfDal {
    @Select("SELECT * FROM ZHIFA_SCENE_RECORD WHERE RECORD_ID= #{id}")
    List<Map<String,Object>> getById( @Param("id") String id);

    @Select("SELECT * FROM ZHIFA_ITEM_RECORD WHERE CHECK_STATE='不合格' and SERVICE_ID= #{id}")
    List<Map<String,Object>> getItemRecordById( @Param("id") String id);

    @Select("SELECT * FROM ZHIFA_ITEM_RECORD WHERE CHECK_STATE='合格' and SERVICE_ID= #{id}")
    List<Map<String,Object>> getItemRecord( @Param("id") String id);



    @Select("SELECT * FROM ZHIFA_ITEM_RECORD WHERE SERVICE_ID= #{id} AND TEMP_ID= #{TEMP_ID}")
    List<Map<String,Object>> getItemRecordByIdAndTEMPID( @Param("id") String id, @Param("TEMP_ID")String TEMP_ID);

    @Select("SELECT * FROM ZHIFA_ITEM_RECORD WHERE SERVICE_ID= #{id} AND TEMP_ID= #{TEMP_ID} AND CHECK_STATE= #{CHECK_STATE}")
    List<Map<String,Object>> getItemRecordByIdAndTEMPIDAndState( @Param("id") String id, @Param("TEMP_ID")String TEMP_ID,@Param("CHECK_STATE")String CHECK_STATE);

    @Select("select * from ZHIFA_ITEM_RECORD where SERVICE_ID=#{id} and CHECK_STATE=#{CHECK_STATE} ORDER BY TEMP_ID")
    List<Map<String,Object>> getItemRecordByServiceIdAndStateOrderByTempID( @Param("id") String id, @Param("CHECK_STATE")String CHECK_STATE);

    @Update("update ZHIFA_SCENE_RECORD set RECORD_PDF= #{RECORD_PDF},RECORD_QR_CODE=#{RECORD_QR_CODE} where RECORD_ID= #{id}")
    void updatePdfAndQrcode(@Param("RECORD_PDF") String RECORD_PDF, @Param("RECORD_QR_CODE") String RECORD_QR_CODE,@Param("id") String id);

    @Update("update ZHIFA_SCENE_RECORD set NOTICE_PDF= #{NOTICE_PDF},NOTICE_QR_CODE=#{NOTICE_QR_CODE} where RECORD_ID= #{id}")
    void updateNoticePdfAndQrcode(@Param("NOTICE_PDF") String NOTICE_PDF, @Param("NOTICE_QR_CODE") String NOTICE_QR_CODE, @Param("id") String id);




    @Select("SELECT * FROM ZHIFA_EXPERT_SCENE_RECORD WHERE RECORD_ID= #{id}")
    List<Map<String,Object>> getEXById( @Param("id") String id);

    @Select("SELECT * FROM ZHIFA_EXPERT_ITEM_RECORD WHERE CHECK_STATE='合格' and SERVICE_ID= #{id}")
    List<Map<String,Object>> getEXItemRecordById( @Param("id") String id);

    @Select("SELECT * FROM ZHIFA_EXPERT_ITEM_RECORD WHERE CHECK_STATE='不合格' and SERVICE_ID= #{id}")
    List<Map<String,Object>> getEXItemRecordById1( @Param("id") String id);



    @Select("select * from ZHIFA_EXPERT_ITEM_RECORD where SERVICE_ID=#{id} and CHECK_STATE=#{CHECK_STATE} ORDER BY TEMP_ID")
    List<Map<String,Object>> getEXItemRecordByServiceIdAndStateOrderByTempID( @Param("id") String id, @Param("CHECK_STATE")String CHECK_STATE);


    @Select("SELECT * FROM ZHIFA_EXPERT_ITEM_RECORD WHERE SERVICE_ID= #{id} AND TEMP_ID= #{TEMP_ID} AND CHECK_STATE= #{CHECK_STATE}")
    List<Map<String,Object>> getEXItemRecordByIdAndTEMPIDAndState( @Param("id") String id, @Param("TEMP_ID")String TEMP_ID,@Param("CHECK_STATE")String CHECK_STATE);

    @Update("update ZHIFA_EXPERT_SCENE_RECORD set RECORD_PDF= #{RECORD_PDF},RECORD_QR_CODE=#{RECORD_QR_CODE} where RECORD_ID= #{id}")
    void updateEXPdfAndQrcode(@Param("RECORD_PDF") String RECORD_PDF, @Param("RECORD_QR_CODE") String RECORD_QR_CODE,@Param("id") String id);

}
