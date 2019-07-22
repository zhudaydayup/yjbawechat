package yjbd.yjbawechat.Dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/7 9:22
 */
@Mapper
public interface PdfThreeDal {
    /**
     * 获取被执法单位名称
     */
    @Select("SELECT * FROM ZHIFA_SCENE_RECORD WHERE RECORD_ID= #{zeLingId}")
    List<Map> getZhiFaRenById(@Param("zeLingId") String zeLingId);

    /**
     * 获取存在的问题列表
     */
    @Select("SELECT * FROM ZHIFA_ITEM_RECORD WHERE CHECK_STATE='不合格' and SERVICE_ID= #{zeLingId}" )
    List<Map> getProblemById(@Param("zeLingId") String zeLingId);

    /**
     * 获取PDF预览信息
     */
    @Select("select count(*)  from  ZHIFA_ZELING_RECORD   Where  RECORD_ID=#{RECORD_ID}  ")
    int getCreatePaperInfoCount(@Param("RECORD_ID") String RECORD_ID );
    @Update( "update ZHIFA_ZELING_RECORD set TIME_IDS = #{TIME_IDS},EXECUTE_PEOPLE2=#{EXECUTE_PEOPLE2} ,CARD_NUMBER2=#{CARD_NUMBER2},CHECKE_UNIT=#{CHECKE_UNIT},EXECUTE_PEOPLE=#{EXECUTE_PEOPLE},CARD_NUMBER=#{CARD_NUMBER},CHECKE_PROBLEM_IDS=#{CHECKE_PROBLEM_IDS},CHECKED_END_TIME=#{CHECKED_END_TIME},ZF_ORDER_DEADLINE_AREA=#{ZF_ORDER_DEADLINE_AREA},ZF_ORDER_DEADLINE_RECORD=#{ZF_ORDER_DEADLINE_RECORD} where RECORD_ID = #{RECORD_ID}")
    int createPaperInfo1(@Param("TIME_IDS") String TIME_IDS,
                         @Param("EXECUTE_PEOPLE2") String EXECUTE_PEOPLE2,@Param("CARD_NUMBER2") String CARD_NUMBER2,
                         @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                         @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM,
                         @Param("CHECKED_END_TIME") String CHECKED_END_TIME,@Param("ZF_ORDER_DEADLINE_AREA") String ZF_ORDER_DEADLINE_AREA, @Param("ZF_ORDER_DEADLINE_RECORD") String ZF_ORDER_DEADLINE_RECORD,  @Param("RECORD_ID") String RECORD_ID);
    @Insert ( "insert into ZHIFA_ZELING_RECORD(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID,ZF_ORDER_DEADLINE_AREA,ZF_ORDER_DEADLINE_RECORD) values (#{TIME_IDS},#{EXECUTE_PEOPLE2}, #{CARD_NUMBER2},#{CHECKE_UNIT}, #{EXECUTE_PEOPLE}, #{CARD_NUMBER}, #{CHECKE_PROBLEM_IDS},#{CHECKED_END_TIME},#{RECORD_ID},#{ZF_ORDER_DEADLINE_AREA},#{ZF_ORDER_DEADLINE_RECORD})")
    int createPaperInfo( @Param("TIME_IDS") String TIME_IDS,
                         @Param("EXECUTE_PEOPLE2") String EXECUTE_PEOPLE2,@Param("CARD_NUMBER2") String CARD_NUMBER2,
                         @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                         @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM,
                         @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("RECORD_ID") String RECORD_ID,@Param("ZF_ORDER_DEADLINE_AREA") String ZF_ORDER_DEADLINE_AREA, @Param("ZF_ORDER_DEADLINE_RECORD") String ZF_ORDER_DEADLINE_RECORD);


    /**
     * 上传执法者1的名称以及ID
     */
    @Update( "update ZHIFA_ZELING_RECORD set CHECKE_SIGH = #{CHECKE_SIGH} where RECORD_ID = #{RecordId}")
    int UpdateCheck(@Param("CHECKE_SIGH") String CHECKE_SIGH, @Param("RecordId") String RecordId);

    /**
     * 上传执法者2的名称以及ID
     */
    @Update( "update ZHIFA_ZELING_RECORD set CHECKETWO_SIGH = #{CHECKETWO_SIGH} where RECORD_ID = #{RecordId}")
    int UpdateCheckTwoSign(@Param("CHECKETWO_SIGH") String CHECKETWO_SIGH, @Param("RecordId") String RecordId);

    /**
     * 上传被执法单位的名称以及ID
     */
    @Update("update ZHIFA_ZELING_RECORD set REPRESENR_SIGN = #{REPRESENR_SIGN} where RECORD_ID = #{RecordId}")
    int UpdateRepresent(@Param("REPRESENR_SIGN") String REPRESENR_SIGN, @Param("RecordId") String RecordId);

    /**
     * 上传见证人签名的名称以及ID
     */
    @Update("update ZHIFA_ZELING_RECORD set WITNESS_SIGH = #{WITNESS_SIGH} where RECORD_ID = #{RecordId}")
    int UpdateJianZhengRen(@Param("WITNESS_SIGH") String WITNESS_SIGH, @Param("RecordId") String RecordId);

    /**
     * 最后生成PDF页面
     */
    @Select("select * from ZHIFA_ZELING_RECORD where RECORD_ID = #{recordId} ")
    List<Map> getIdRecord(@Param("recordId") String recordId);

    /**
     * 获取PDF模板
     * @param
     * @return
     */
    @Select("SELECT * FROM ZHIFA_ZELING_RECORD WHERE RECORD_ID= #{ID}")
    List<Map<String,Object>> getThreeById(@Param("ID") String ID);

    /**
     * 通过唯一标识ID来获取对应问题
     * @param
     * @param
     * @param
     */
    @Select("SELECT * FROM ZHIFA_ITEM_RECORD WHERE  CREATE_TIME= #{problemId}" )
    List<Map> searchProblemById(@Param("problemId") String problemId);

    /**
     * 从最初的表格中获取信息
     * @param
     * @param
     * @param
     */
    @Select("select * from ZHIFA_SCENE_RECORD where RECORD_ID = #{ID}  ")
    List<Map> searchCareId(@Param("ID") String ID);

    @Update("update ZHIFA_ZELING_RECORD set ENFORCE_PAPER= #{pdfName},QR_CODE_URL=#{qrCodeName} where RECORD_ID= #{id}")
    void updatePdfAndQrcode(@Param("pdfName") String pdfName, @Param("qrCodeName") String qrCodeName, @Param("id") String id);



    /**
     * 记录专家责令整改信息
     */
    @Insert ( "insert into ZHIFA_EXPERT_ZELING_RECORD(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKED_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID) values (#{TIME_IDS},#{EXECUTE_PEOPLE2}, #{CARD_NUMBER2},#{CHECKED_UNIT}, #{EXECUTE_PEOPLE}, #{CARD_NUMBER}, #{CHECKE_PROBLEM_IDS},#{CHECKED_END_TIME},#{RECORD_ID})")
    int setExZeLingInfo( @Param("TIME_IDS") String TIME_IDS,
                         @Param("EXECUTE_PEOPLE2") String EXECUTE_PEOPLE2,@Param("CARD_NUMBER2") String CARD_NUMBER2,
                         @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                         @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM,
                         @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("RECORD_ID") String RECORD_ID);
    @Update ( "update ZHIFA_EXPERT_ZELING_RECORD set TIME_IDS=#{TIME_IDS},EXECUTE_PEOPLE2=#{EXECUTE_PEOPLE2},CARD_NUMBER2=#{CARD_NUMBER2},CHECKED_UNIT=#{CHECKED_UNIT}, EXECUTE_PEOPLE=#{EXECUTE_PEOPLE},CARD_NUMBER=#{CARD_NUMBER},CHECKE_PROBLEM_IDS=#{CHECKE_PROBLEM_IDS},CHECKED_END_TIME=#{CHECKED_END_TIME} where RECORD_ID= #{RECORD_ID}")
    int setExZeLingInfo1( @Param("TIME_IDS") String TIME_IDS,
                          @Param("EXECUTE_PEOPLE2") String EXECUTE_PEOPLE2,@Param("CARD_NUMBER2") String CARD_NUMBER2,
                          @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                          @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM,
                          @Param("CHECKED_END_TIME") String CHECKED_END_TIME,@Param("RECORD_ID") String RECORD_ID);

    /**
     *更新缓存
     */
    @Select("select count(*)  from  ZHIFA_EXPERT_ZELING_RECORD   Where  RECORD_ID=#{RECORD_ID}  ")
    int checkTemporaryRecordIdCount(@Param("RECORD_ID") String RECORD_ID );
    @Update ( "update ZHIFA_EXPERT_ZELING_RECORD set TIME_IDS=#{TIME_IDS},EXECUTE_PEOPLE2=#{EXECUTE_PEOPLE2},CARD_NUMBER2=#{CARD_NUMBER2},CHECKED_UNIT=#{CHECKED_UNIT}, EXECUTE_PEOPLE=#{EXECUTE_PEOPLE},CARD_NUMBER=#{CARD_NUMBER},CHECKE_PROBLEM_IDS=#{CHECKE_PROBLEM_IDS},CHECKED_END_TIME=#{CHECKED_END_TIME},URL=#{URL} where RECORD_ID= #{RECORD_ID}")
    int temporarySetExZeLingInfo( @Param("TIME_IDS") String TIME_IDS,
                                  @Param("EXECUTE_PEOPLE2") String EXECUTE_PEOPLE2,@Param("CARD_NUMBER2") String CARD_NUMBER2,
                                  @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                                  @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM,
                                  @Param("CHECKED_END_TIME") String CHECKED_END_TIME,@Param("URL") String URL, @Param("RECORD_ID") String RECORD_ID);
    @Insert ( "insert into ZHIFA_EXPERT_ZELING_RECORD(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKED_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID,URL) values (#{TIME_IDS},#{EXECUTE_PEOPLE2}, #{CARD_NUMBER2},#{CHECKED_UNIT}, #{EXECUTE_PEOPLE}, #{CARD_NUMBER}, #{CHECKE_PROBLEM_IDS},#{CHECKED_END_TIME},#{RECORD_ID},#{URL})")
    int temporarySetExZeLingInfo1( @Param("TIME_IDS") String TIME_IDS,
                                   @Param("EXECUTE_PEOPLE2") String EXECUTE_PEOPLE2,@Param("CARD_NUMBER2") String CARD_NUMBER2,
                                   @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                                   @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM,
                                   @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("RECORD_ID") String RECORD_ID, @Param("URL") String URL);
    /**
     * 缓存更新记录专家责令整改信息
     */
    @Select("SELECT * FROM ZHIFA_EXPERT_ZELING_RECORD WHERE RECORD_ID=#{RecordId}" )
    List<Map> getTemporarySetExZeLingInfo(@Param("RecordId") String RecordId);

    /**
     * 缓存政府执法责令限期表格信息
     */
    @Update( "update ZHIFA_ZELING_RECORD set TIME_IDS = #{TIME_IDS},EXECUTE_PEOPLE2=#{EXECUTE_PEOPLE2} ,CARD_NUMBER2=#{CARD_NUMBER2},CHECKE_UNIT=#{CHECKE_UNIT},EXECUTE_PEOPLE=#{EXECUTE_PEOPLE},CARD_NUMBER=#{CARD_NUMBER},CHECKE_PROBLEM_IDS=#{CHECKE_PROBLEM_IDS},CHECKED_END_TIME=#{CHECKED_END_TIME},URL=#{URL} where RECORD_ID = #{RECORD_ID}")
    int createTemporaryZeLingBiao1(@Param("TIME_IDS") String TIME_IDS,
                                   @Param("EXECUTE_PEOPLE2") String EXECUTE_PEOPLE2,@Param("CARD_NUMBER2") String CARD_NUMBER2,
                                   @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                                   @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM,
                                   @Param("CHECKED_END_TIME") String CHECKED_END_TIME,@Param("URL") String URL,@Param("RECORD_ID") String RECORD_ID);
    @Insert ( "insert into ZHIFA_ZELING_RECORD(TIME_IDS,EXECUTE_PEOPLE2,CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE,CARD_NUMBER,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,URL,RECORD_ID) values (#{TIME_IDS},#{EXECUTE_PEOPLE2}, #{CARD_NUMBER2},#{CHECKE_UNIT}, #{EXECUTE_PEOPLE}, #{CARD_NUMBER}, #{CHECKE_PROBLEM_IDS},#{CHECKED_END_TIME},#{URL},#{RECORD_ID})")
    int createTemporaryZeLingBiao( @Param("TIME_IDS") String TIME_IDS,
                                   @Param("EXECUTE_PEOPLE2") String EXECUTE_PEOPLE2,@Param("CARD_NUMBER2") String CARD_NUMBER2,
                                   @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                                   @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM,
                                   @Param("CHECKED_END_TIME") String CHECKED_END_TIME,@Param("URL") String URL, @Param("RECORD_ID") String RECORD_ID);

    /**
     * 专家责令整改信息
     */
    @Select("SELECT * FROM ZHIFA_EXPERT_ZELING_RECORD WHERE RECORD_ID= #{RECORD_ID}")
    List<Map<String,Object>> getExZeLingById(@Param("RECORD_ID") String RECORD_ID);


    @Update("update ZHIFA_EXPERT_ZELING_RECORD set ENFORCE_PAPER= #{pdfName},QR_CODE_URL=#{qrCodeName} where RECORD_ID= #{id}")
    void updateExPdfAndQrcode(@Param("pdfName") String pdfName, @Param("qrCodeName") String qrCodeName, @Param("id") String id);

    @Select("select * from ZHIFA_EXPERT_ZELING_RECORD where RECORD_ID = #{recordId} ")
    List<Map> getExRecord(@Param("recordId") String recordId);


}
