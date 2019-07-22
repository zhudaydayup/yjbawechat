package yjbd.yjbawechat.Dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/12 17:01
 */
@Mapper
public interface NoticeViewDao {

    /**
     * 通过id来获取开始时间
     */
    @Select("SELECT * FROM ZHIFA_COMPANY_INFO_RECORD WHERE RECORD_ID= #{RECORD_ID}")
    List<Map> getGreatTimeById(@Param("RECORD_ID") String RECORD_ID);
    /**
     * 获取自检表格信息
     */
    @Insert( "insert into ZHIFA_NOTICE_RECORD(CHECKE_UNIT, EXECUTE_PEOPLE,CHECKED_START_TIME,CHECKE_PROBLEM_IDS,CHECKED_END_TIME,NOTICE_DETAIL,RECORD_ID) values (#{CHECKE_UNIT}, #{EXECUTE_PEOPLE}, #{CHECKED_START_TIME}, #{CHECKE_PROBLEM_IDS},#{CHECKED_END_TIME},#{NOTICE_DETAIL},#{RECORD_ID})")
    int createNoticeBiao(@Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("EXECUTE_PEOPLE") String EXECUTE_PEOPLE,
                         @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKE_PROBLEM_IDS") String CHECKE_PROBLEM_IDS,
                         @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("NOTICE_DETAIL") String NOTICE_DETAIL, @Param("RECORD_ID") String RECORD_ID
    );
    /**
     * 上传执法者的名称以及ID
     */
    @Update( "update ZHIFA_ZJ_ITEM_RECORD set CHECKE_SIGH = #{CHECKE_SIGH} where SERVICE_ID = #{RecordId}")
    int UpdateCheck1(@Param("CHECKE_SIGH") String CHECKE_SIGH, @Param("RecordId") String RecordId);
    /**
     * 上传被执法单位的名称以及ID
     */
    @Update("update ZHIFA_ZJ_ITEM_RECORD set NOTICE_SIGH = #{NOTICE_SIGH} where SERVICE_ID = #{RecordId}")
    int UpdateRepresent1(@Param("NOTICE_SIGH") String NOTICE_SIGH, @Param("RecordId") String RecordId);
    /**
     * 获取PDF模板
     * @param
     * @return
     */
    @Select("select * from ZHIFA_ZJ_ITEM_RECORD where SERVICE_ID = #{RECORD_ID}  ")
    List<Map> getPdfInfo1(@Param("RECORD_ID") String RECORD_ID);
    /**
     * 通过唯一标识ID来获取对应问题
     * @param
     * @param
     * @param
     */
    @Select("SELECT * FROM ZHIFA_ZJ_ITEM_RECORD WHERE  CHECK_STATE= #{check_state}" )
    List<Map> searchProblemById1(@Param("check_state") String check_state);

    @Select("SELECT * FROM ZHIFA_ZJ_ITEM_RECORD WHERE CHECK_STATE='不合格' and SERVICE_ID= #{RECORD_ID}")
    List<Map<String,Object>> getItemRecordById(@Param("RECORD_ID") String RECORD_ID);

    @Select("SELECT * FROM ZHIFA_ZJ_ITEM_RECORD WHERE CHECK_STATE='合格' and SERVICE_ID= #{RECORD_ID}")
    List<Map<String,Object>> getItemRecordById1(@Param("RECORD_ID") String RECORD_ID);

    /**
     *
     */
    @Select("select * from ZHIFA_ZJ_ITEM_RECORD where SERVICE_ID=#{RECORD_ID} and CHECK_STATE=#{CHECK_STATE} ORDER BY TEMP_ID")
    List<Map<String,Object>> getItemRecordByServiceIdAndStateOrderByTempID(@Param("RECORD_ID") String RECORD_ID, @Param("CHECK_STATE") String CHECK_STATE);

    /**
     *
     */
    @Select("SELECT * FROM ZHIFA_ZJ_ITEM_RECORD WHERE SERVICE_ID= #{RECORD_ID} AND TEMP_ID= #{TEMP_ID} AND CHECK_STATE= #{CHECK_STATE}")
    List<Map<String,Object>> getItemRecordByIdAndTEMPIDAndState(@Param("RECORD_ID") String RECORD_ID, @Param("TEMP_ID") String TEMP_ID, @Param("CHECK_STATE") String CHECK_STATE);
    /**
     * 从最初的表格中获取信息
     * @param
     * @param
     * @param
     */
//    @Select("select * from ZHIFA_SCENE_RECORD where RECORD_ID = #{RECORD_ID}  ")
//    List<Map> searchCareId1(@Param("RECORD_ID") String RECORD_ID);

    @Update("update ZHIFA_ZJ_ITEM_RECORD set PDF_PATH= #{PDF_PATH},QRCODE_PATH=#{QRCODE_PATH} where SERVICE_ID= #{RECORD_ID}")
    void updatePdfAndQrcode(@Param("PDF_PATH") String PDF_PATH, @Param("QRCODE_PATH") String QRCODE_PATH, @Param("RECORD_ID") String RECORD_ID);

    /**
     * 最后生成PDF页面
     */
    @Select("select * from ZHIFA_ZJ_ITEM_RECORD where SERVICE_ID = #{recordId} ")
    List<Map> getIdRecord(@Param("recordId") String recordId);
}
