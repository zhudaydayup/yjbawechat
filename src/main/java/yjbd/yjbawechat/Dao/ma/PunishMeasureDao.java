package yjbd.yjbawechat.Dao.ma;


import org.apache.ibatis.annotations.*;
import yjbd.yjbawechat.Model.ExpertPunishEntity;
import yjbd.yjbawechat.Model.ma.PunishMeasureEntity;

import java.util.List;
import java.util.Map;

@Mapper
public interface PunishMeasureDao {


    /**  查询产品类别 */
    @Select("select * from ZHIFA_SCENE_RECORD where RECORD_ID = #{RECORD_ID}  ")
    List<Map> getPunishMeasureInfo1(@Param("RECORD_ID") String RECORD_ID);

    @Select("select * from ZHIFA_ITEM_RECORD  WHERE CHECK_STATE='不合格' and SERVICE_ID = #{RECORD_ID}  ")
    List<PunishMeasureEntity> getPunishMeasureInfo(@Param("RECORD_ID") String RECORD_ID);

    @Select("select * from ZHIFA_ZELING_RECORD  WHERE RECORD_ID = #{RECORD_ID}  ")
    List<PunishMeasureEntity> getZhenggaiMeasureInfo(@Param("RECORD_ID") String RECORD_ID);

    /**
     * 政府执法现场处理措施决定
     * @param
     */
    @Select("select count(*)  from  ZHIFA_PUNISH_MEASURE  Where  SERVICE_ID =#{RECORD_ID}  ")
    int getTemporaryPunishMeasureInfoCount(@Param("RECORD_ID") String RECORD_ID );
    @Update("update ZHIFA_PUNISH_MEASURE set PROCESS_DECISION=#{PROCESS_DECISION},CHECKE_DETAIL=#{CHECKE_DETAIL},CHECKE_UNIT=#{CHECKE_UNIT},CHECKED_START_TIME=#{CHECKED_START_TIME},RECORD_TIME=#{RECORD_TIME},ZF_ON_SCENE_AREA=#{ZF_ON_SCENE_AREA},ZF_ON_SCENE_RECORD=#{ZF_ON_SCENE_RECORD} WHERE SERVICE_ID=#{RECORD_ID}" )
    void setPunishMeasureInfo1( @Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("RECORD_TIME") String RECORD_TIME, @Param("ZF_ON_SCENE_AREA") String ZF_ON_SCENE_AREA, @Param("ZF_ON_SCENE_RECORD") String ZF_ON_SCENE_RECORD,@Param("RECORD_ID") String RECORD_ID);
    @Insert("insert into ZHIFA_PUNISH_MEASURE (SERVICE_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME,RECORD_TIME,ZF_ON_SCENE_AREA,ZF_ON_SCENE_RECORD) values (#{RECORD_ID},#{PROCESS_DECISION},#{CHECKE_DETAIL},#{CHECKE_UNIT},#{CHECKED_START_TIME},#{RECORD_TIME},#{ZF_ON_SCENE_AREA},#{ZF_ON_SCENE_RECORD})" )
    void setPunishMeasureInfo(@Param("RECORD_ID") String RECORD_ID, @Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("RECORD_TIME") String RECORD_TIME, @Param("ZF_ON_SCENE_AREA") String ZF_ON_SCENE_AREA, @Param("ZF_ON_SCENE_RECORD") String ZF_ON_SCENE_RECORD);

    @Update("update ZHIFA_PUNISH_MEASURE set SIGNATURE_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_PUNISH_MEASURE set SIGNATURE1_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign1(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_PUNISH_MEASURE set SIGNATURE2_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign2(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_PUNISH_MEASURE set SIGNATURE3_PATH=#{SIGNATURE_PATH} where SERVICE_ID=#{RECORD_ID}")
    void UpdateCheckSign3(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Select("select * from ZHIFA_PUNISH_MEASURE where SERVICE_ID = #{RECORD_ID}  ")
    List<Map> getPdfInfo(@Param("RECORD_ID") String RECORD_ID);

    /**
     * 从最初的表格中获取信息
     * @param PDF_PATH
     * @param QRCODE_PATH
     * @param RECORD_ID
     */
    @Select("select * from ZHIFA_SCENE_RECORD where RECORD_ID = #{RECORD_ID}  ")
    List<Map> searchCareId(@Param("RECORD_ID") String RECORD_ID);

    @Update("update ZHIFA_PUNISH_MEASURE set PDF_PATH= #{PDF_PATH},QRCODE_PATH=#{QRCODE_PATH} where SERVICE_ID= #{RECORD_ID}")
    void updatePdfAndQrcode(@Param("PDF_PATH") String PDF_PATH, @Param("QRCODE_PATH") String QRCODE_PATH, @Param("RECORD_ID") String RECORD_ID);


    @Select("select * from ZHIFA_PUNISH_MEASURE where SERVICE_ID = #{RECORD_ID}")
    List<PunishMeasureEntity> getIdRecord(@Param("RECORD_ID") String RECORD_ID);



    /**
     * 记录专家现场处理措施信息
     */
    @Insert("insert into ZHIFA_EXPERT_PUNISH_RECORD (RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKED_UNIT,CHECKED_START_TIME,RECORD_TIME) values (#{RECORD_ID},#{PROCESS_DECISION},#{CHECKE_DETAIL},#{CHECKED_UNIT},#{CHECKED_START_TIME},#{RECORD_TIME})" )
    void setExPunishMeasureInfo(@Param("RECORD_ID") String RECORD_ID, @Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("RECORD_TIME") String RECORD_TIME);
    @Update("update ZHIFA_EXPERT_PUNISH_RECORD set PROCESS_DECISION=#{PROCESS_DECISION},CHECKE_DETAIL=#{CHECKE_DETAIL},CHECKED_UNIT=#{CHECKED_UNIT},CHECKED_START_TIME=#{CHECKED_START_TIME},RECORD_TIME=#{RECORD_TIME} where RECORD_ID=#{RECORD_ID}" )
    void setExPunishMeasureInfo1(@Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("RECORD_TIME") String RECORD_TIME, @Param("RECORD_ID") String RECORD_ID);

    /**
     * 缓存记录专家现场处理措施信息
     */
    @Select("select count(*)  from  ZHIFA_EXPERT_PUNISH_RECORD   Where  RECORD_ID=#{RECORD_ID}  ")
    int getTemporarySetExPunishMeasureInfoCount(@Param("RECORD_ID") String RECORD_ID );
    @Update("update ZHIFA_EXPERT_PUNISH_RECORD set PROCESS_DECISION=#{PROCESS_DECISION},CHECKE_DETAIL=#{CHECKE_DETAIL},CHECKED_UNIT=#{CHECKED_UNIT},CHECKED_START_TIME=#{CHECKED_START_TIME},RECORD_TIME=#{RECORD_TIME},URL=#{URL} where RECORD_ID=#{RECORD_ID}" )
    int temporarySetExPunishMeasureInfo(@Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("RECORD_TIME") String RECORD_TIME, @Param("URL") String URL,@Param("RECORD_ID") String RECORD_ID);
    @Insert("insert into ZHIFA_EXPERT_PUNISH_RECORD (RECORD_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKED_UNIT,CHECKED_START_TIME,RECORD_TIME,URL) values (#{RECORD_ID},#{PROCESS_DECISION},#{CHECKE_DETAIL},#{CHECKED_UNIT},#{CHECKED_START_TIME},#{RECORD_TIME},#{URL})" )
    int temporarySetExPunishMeasureInfo1(@Param("RECORD_ID") String RECORD_ID, @Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("RECORD_TIME") String RECORD_TIME, @Param("URL") String URL);

    /**
     *获取缓存记录专家现场处理措施信息
     */
    @Select("SELECT PROCESS_DECISION FROM ZHIFA_EXPERT_PUNISH_RECORD WHERE RECORD_ID=#{RecordId}" )
    List<Map> getTemporarySetExPunishMeasureInfo(@Param("RecordId") String RecordId);
    /**
     * 更新现场处理措施的执法人签名、被检查单位负责人签名、见证人签名
     */
    @Update("update ZHIFA_EXPERT_PUNISH_RECORD set SIGNATURE_PATH=#{SIGNATURE_PATH} where RECORD_ID=#{RECORD_ID}")
    void UpdatePunishSign(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_EXPERT_PUNISH_RECORD set SIGNATURE1_PATH=#{SIGNATURE_PATH} where RECORD_ID=#{RECORD_ID}")
    void UpdatePunishSign1(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_EXPERT_PUNISH_RECORD set SIGNATURE2_PATH=#{SIGNATURE_PATH} where RECORD_ID=#{RECORD_ID}")
    void UpdatePunishSign2(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    /**
     * 获取现场处理措施PDF需要信息
     */
    @Select("select * from ZHIFA_EXPERT_PUNISH_RECORD where RECORD_ID = #{RECORD_ID}")
    List<Map> getPunishPdfInfo(@Param("RECORD_ID") String RECORD_ID);

    @Select("select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = #{RECORD_ID}  ")
    List<Map> searchExCareId(@Param("RECORD_ID") String RECORD_ID);

    @Update("update ZHIFA_EXPERT_PUNISH_RECORD set PDF_PATH= #{PDF_PATH},QRCODE_PATH=#{QRCODE_PATH} where RECORD_ID= #{RECORD_ID}")
    void updateExPdfAndQrcode(@Param("PDF_PATH") String PDF_PATH, @Param("QRCODE_PATH") String QRCODE_PATH, @Param("RECORD_ID") String RECORD_ID);

    @Select("select * from ZHIFA_EXPERT_PUNISH_RECORD where RECORD_ID = #{RECORD_ID}")
    List<ExpertPunishEntity> getExRecord(@Param("RECORD_ID") String RECORD_ID);




    /**
     * 更新责令整改的执法人签名、被检查单位负责人签名、见证人签名
     */
    @Update("update ZHIFA_EXPERT_ZELING_RECORD set SIGNATURE3_PATH=#{SIGNATURE_PATH} where RECORD_ID=#{RECORD_ID}")
    void UpdateZeLingSign3(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_EXPERT_ZELING_RECORD set SIGNATURE4_PATH=#{SIGNATURE_PATH} where RECORD_ID=#{RECORD_ID}")
    void UpdateZeLingSign4(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);

    @Update("update ZHIFA_EXPERT_ZELING_RECORD set SIGNATURE5_PATH=#{SIGNATURE_PATH} where RECORD_ID=#{RECORD_ID}")
    void UpdateZeLingSign5(@Param("RECORD_ID") String RECORD_ID, @Param("SIGNATURE_PATH") String SIGNATURE_PATH);
    /**
     * 缓存政府执法现场处理措施决定
     * @param
     */
    @Update("update ZHIFA_PUNISH_MEASURE set PROCESS_DECISION=#{PROCESS_DECISION},CHECKE_DETAIL=#{CHECKE_DETAIL},CHECKE_UNIT=#{CHECKE_UNIT},CHECKED_START_TIME=#{CHECKED_START_TIME},RECORD_TIME=#{RECORD_TIME},URL=#{URL},ZF_ON_SCENE_AREA=#{ZF_ON_SCENE_AREA},ZF_ON_SCENE_RECORD=#{ZF_ON_SCENE_RECORD} WHERE SERVICE_ID=#{RECORD_ID}" )
    int temporaryPunishMeasureInfoTable( @Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("RECORD_TIME") String RECORD_TIME, @Param("URL") String URL, @Param("ZF_ON_SCENE_AREA") String ZF_ON_SCENE_AREA, @Param("ZF_ON_SCENE_RECORD") String ZF_ON_SCENE_RECORD,@Param("RECORD_ID") String RECORD_ID);
    @Insert("insert into ZHIFA_PUNISH_MEASURE (SERVICE_ID,PROCESS_DECISION,CHECKE_DETAIL,CHECKE_UNIT,CHECKED_START_TIME,RECORD_TIME,URL,ZF_ON_SCENE_AREA,ZF_ON_SCENE_RECORD) values (#{RECORD_ID},#{PROCESS_DECISION},#{CHECKE_DETAIL},#{CHECKE_UNIT},#{CHECKED_START_TIME},#{RECORD_TIME},#{URL},#{ZF_ON_SCENE_AREA},#{ZF_ON_SCENE_RECORD})" )
    int temporaryPunishMeasureInfo(@Param("RECORD_ID") String RECORD_ID, @Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("RECORD_TIME") String RECORD_TIME, @Param("URL") String URL, @Param("ZF_ON_SCENE_AREA") String ZF_ON_SCENE_AREA, @Param("ZF_ON_SCENE_RECORD") String ZF_ON_SCENE_RECORD);
    /**
     * 获取缓存政府执法现场处理措施决定
     * @param
     */
    @Select("SELECT * FROM ZHIFA_PUNISH_MEASURE WHERE SERVICE_ID=#{RecordId}" )
    List<Map> getTemporaryPunishMeasureInfo(@Param("RecordId") String RecordId);

}
