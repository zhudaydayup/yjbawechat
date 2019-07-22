package yjbd.yjbawechat.Dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/5/7 16:33
 */
@Mapper
public interface HiddenDangerDao {
    /**
     * 根据ID来获取专家名称
     */
    @Select("SELECT * FROM ZHIFA_EXPERT_SCENE_RECORD WHERE RECORD_ID= #{recordId}")
    List<Map> getExpertById(@Param("recordId") String recordId);
    /**
     * 获取一般问题
     */
    @Select("SELECT * FROM ZHIFA_EXPERT_ITEM_RECORD WHERE CHECK_STATE='不合格'and SERVICE_ID= #{RecordId}" )
    List<Map> GetExpertProblemAndMsgs(@Param("RecordId") String RecordId);
    /**
     * 获取重大隐患问题
     */
    @Select("SELECT * FROM ZHIFA_EXPERT_MAJOR_DANGER " )
    List<Map> getHiddenDangerById();
    /**
     * 获取被检查单位信息
     */
    @Select("SELECT * FROM ZHIFA_EXPERT_SCENE_RECORD WHERE RECORD_ID=#{RecordId}" )
    List<Map> GetCheckByRecord(@Param("RecordId") String RecordId);
    /**
     * 创建重大隐患表格信息
     */
    @Update ( "update ZHIFA_EXPERT_DANGER_RECORD set CHECKED_START_TIME=#{CHECKED_START_TIME},CHECKED_END_TIME=#{CHECKED_END_TIME},CHECKED_UNIT=#{CHECKED_UNIT}," +
            " UNIT_NUMBER=#{UNIT_NUMBER},ADDRESS= #{ADDRESS},REPRESENT_PEOPLE=#{REPRESENT_PEOPLE},CHECKE_PLACE=#{CHECKE_PLACE},EXPERT_PEOPLLE=#{EXPERT_PEOPLLE}," +
            "DANGER_NAME=#{DANGER_NAME},CHECKE_DETAIL=#{CHECKE_DETAIL},VIDEO_URL=#{VIDEO_URL},LOCATION_IMG=#{LOCATION_IMG},OTHER_IMG=#{OTHER_IMG},RECORD_TIME=#{RECORD_TIME},PKID=#{PKID},SECURITY_PEOPLE=#{SECURITY_PEOPLE} where RECORD_ID = #{RECORD_ID}")
    int createDangerTable1( @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME,
                            @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("UNIT_NUMBER") String UNIT_NUMBER,
                            @Param("ADDRESS") String ADDRESS, @Param("REPRESENT_PEOPLE") String REPRESENT_PEOPLE,
                            @Param("CHECKE_PLACE") String CHECKE_PLACE, @Param("EXPERT_PEOPLLE") String EXPERT_PEOPLLE, @Param("DANGER_NAME") String DANGER_NAME,
                            @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,
                            @Param("LOCATION_IMG") String LOCATION_IMG, @Param("OTHER_IMG") String OTHER_IMG, @Param("RECORD_TIME") String RECORD_TIME, @Param("PKID") String PKID, @Param("SECURITY_PEOPLE") String SECURITY_PEOPLE, @Param("RECORD_ID") String RECORD_ID);

    @Insert( "insert into ZHIFA_EXPERT_DANGER_RECORD(RECORD_ID,CHECKED_START_TIME,CHECKED_END_TIME,CHECKED_UNIT, UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,CHECKE_PLACE,EXPERT_PEOPLLE,DANGER_NAME,CHECKE_DETAIL,VIDEO_URL,LOCATION_IMG,OTHER_IMG,RECORD_TIME,PKID,SECURITY_PEOPLE) " +
            "values (#{RECORD_ID},#{CHECKED_START_TIME}, #{CHECKED_END_TIME},#{CHECKED_UNIT}, #{UNIT_NUMBER}, #{ADDRESS}, #{REPRESENT_PEOPLE},#{CHECKE_PLACE},#{EXPERT_PEOPLLE},#{DANGER_NAME}, " +
            "#{CHECKE_DETAIL}, #{VIDEO_URL},#{LOCATION_IMG},#{OTHER_IMG},#{RECORD_TIME},#{PKID},#{SECURITY_PEOPLE})")
    int createDangerTable(@Param("RECORD_ID") String RECORD_ID, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME,
                          @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("UNIT_NUMBER") String UNIT_NUMBER,
                          @Param("ADDRESS") String ADDRESS, @Param("REPRESENT_PEOPLE") String REPRESENT_PEOPLE,
                          @Param("CHECKE_PLACE") String CHECKE_PLACE, @Param("EXPERT_PEOPLLE") String EXPERT_PEOPLLE, @Param("DANGER_NAME") String DANGER_NAME,
                          @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,
                          @Param("LOCATION_IMG") String LOCATION_IMG, @Param("OTHER_IMG") String OTHER_IMG, @Param("RECORD_TIME") String RECORD_TIME, @Param("PKID") String PKID, @Param("SECURITY_PEOPLE") String SECURITY_PEOPLE);
    /**
     * 缓存更新重大隐患表格信息
     */
    @Select("select count(*)  from  ZHIFA_EXPERT_DANGER_RECORD   Where  RECORD_ID=#{RECORD_ID}  ")
    int getTemporaryCreateDangerTableCount(@Param("RECORD_ID") String RECORD_ID );
    @Update ( "update ZHIFA_EXPERT_DANGER_RECORD set CHECKED_START_TIME=#{CHECKED_START_TIME},CHECKED_END_TIME=#{CHECKED_END_TIME},CHECKED_UNIT=#{CHECKED_UNIT}," +
            " UNIT_NUMBER=#{UNIT_NUMBER},ADDRESS= #{ADDRESS},REPRESENT_PEOPLE=#{REPRESENT_PEOPLE},CHECKE_PLACE=#{CHECKE_PLACE},EXPERT_PEOPLLE=#{EXPERT_PEOPLLE}," +
            "DANGER_NAME=#{DANGER_NAME},CHECKE_DETAIL=#{CHECKE_DETAIL},VIDEO_URL=#{VIDEO_URL},LOCATION_IMG=#{LOCATION_IMG},OTHER_IMG=#{OTHER_IMG},RECORD_TIME=#{RECORD_TIME},PKID=#{PKID},SECURITY_PEOPLE=#{SECURITY_PEOPLE},URL=#{URL} where RECORD_ID = #{RECORD_ID}")
    int temporaryCreateDangerTable( @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME,
                                    @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("UNIT_NUMBER") String UNIT_NUMBER,
                                    @Param("ADDRESS") String ADDRESS, @Param("REPRESENT_PEOPLE") String REPRESENT_PEOPLE,
                                    @Param("CHECKE_PLACE") String CHECKE_PLACE, @Param("EXPERT_PEOPLLE") String EXPERT_PEOPLLE, @Param("DANGER_NAME") String DANGER_NAME,
                                    @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,
                                    @Param("LOCATION_IMG") String LOCATION_IMG, @Param("OTHER_IMG") String OTHER_IMG, @Param("RECORD_TIME") String RECORD_TIME, @Param("PKID") String PKID, @Param("SECURITY_PEOPLE") String SECURITY_PEOPLE, @Param("URL") String URL,@Param("RECORD_ID") String RECORD_ID);
    @Insert( "insert into ZHIFA_EXPERT_DANGER_RECORD(RECORD_ID,CHECKED_START_TIME,CHECKED_END_TIME,CHECKED_UNIT, UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,CHECKE_PLACE,EXPERT_PEOPLLE,DANGER_NAME,CHECKE_DETAIL,VIDEO_URL,LOCATION_IMG,OTHER_IMG,RECORD_TIME,PKID,SECURITY_PEOPLE,URL) " +
            "values (#{RECORD_ID},#{CHECKED_START_TIME}, #{CHECKED_END_TIME},#{CHECKED_UNIT}, #{UNIT_NUMBER}, #{ADDRESS}, #{REPRESENT_PEOPLE},#{CHECKE_PLACE},#{EXPERT_PEOPLLE},#{DANGER_NAME}, " +
            "#{CHECKE_DETAIL}, #{VIDEO_URL},#{LOCATION_IMG},#{OTHER_IMG},#{RECORD_TIME},#{PKID},#{SECURITY_PEOPLE},#{URL})")
    int temporaryCreateDangerTable1(@Param("RECORD_ID") String RECORD_ID, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME,
                                    @Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("UNIT_NUMBER") String UNIT_NUMBER,
                                    @Param("ADDRESS") String ADDRESS, @Param("REPRESENT_PEOPLE") String REPRESENT_PEOPLE,
                                    @Param("CHECKE_PLACE") String CHECKE_PLACE, @Param("EXPERT_PEOPLLE") String EXPERT_PEOPLLE, @Param("DANGER_NAME") String DANGER_NAME,
                                    @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,
                                    @Param("LOCATION_IMG") String LOCATION_IMG, @Param("OTHER_IMG") String OTHER_IMG, @Param("RECORD_TIME") String RECORD_TIME, @Param("PKID") String PKID, @Param("SECURITY_PEOPLE") String SECURITY_PEOPLE, @Param("URL") String URL);
    /**
     * 获取缓存更新重大隐患表格信息
     */
    @Select("SELECT CHECKE_DETAIL FROM ZHIFA_EXPERT_DANGER_RECORD WHERE RECORD_ID=#{RecordId}" )
    List<Map> getTemporaryCreateDangerTable(@Param("RecordId") String RecordId);   /**
     * 上传专家的签名以及ID
     */
    @Update( "update ZHIFA_EXPERT_DANGER_RECORD set EXPERT_SIGH = #{CHECKE_SIGNATURE} where RECORD_ID = #{RecordId}")
    int UpdateExpertSignature(@Param("CHECKE_SIGNATURE") String CHECKE_SIGNATURE, @Param("RecordId") String RecordId);
    /**
     * 上传被执法单位的名称以及ID
     */
    @Update("update ZHIFA_EXPERT_DANGER_RECORD set REPRESENR_SIGN = #{REPRESENR_SIGNATURE} where RECORD_ID = #{RecordId}")
    int UpdateInspectedSignature(@Param("REPRESENR_SIGNATURE") String REPRESENR_SIGNATURE, @Param("RecordId") String RecordId);
    /**
     * 最后预览PDF页面
     */
    @Select("select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = #{recordId} ")
    List<Map> LookExHiddenDangerPdf(@Param("recordId") String recordId);
    /**
     * 从最初的表格中获取信息
     * @param
     * @param
     * @param
     */
    @Select("select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = #{RECORD_ID}  ")
    List<Map> getPdfInfo(@Param("RECORD_ID") String RECORD_ID);

    @Select("select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = #{RECORD_ID}  ")
    List<Map> searchCareId(@Param("RECORD_ID") String RECORD_ID);

    //获取重大隐患图片
    @Select("SELECT * FROM ZHIFA_EXPERT_DANGER_RECORD WHERE RECORD_ID= #{RECORD_ID} ")
    List<Map<String,Object>> getItemRecordByIdAndTEMPIDAndState(@Param("RECORD_ID") String RECORD_ID);


    @Update("update ZHIFA_EXPERT_DANGER_RECORD set PDF_PATH= #{PDF_PATH},QRCODE_PATH=#{QRCODE_PATH} where RECORD_ID= #{RECORD_ID}")
    void updatePdfAndQrcode(@Param("PDF_PATH") String PDF_PATH, @Param("QRCODE_PATH") String QRCODE_PATH, @Param("RECORD_ID") String RECORD_ID);






}

