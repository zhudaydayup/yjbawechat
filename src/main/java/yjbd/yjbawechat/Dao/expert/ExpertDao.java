package yjbd.yjbawechat.Dao.expert;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpertDao {
    @Select("select * from ZHIFA_EXPERT_SCENE_RECORD where RECORD_ID=#{RECORD_ID}")
    List<Map> getExpertInfo(@Param("RECORD_ID") String RECORD_ID);
    @Select("select * from ZHIFA_EXPERT_ITEM_RECORD where SERVICE_ID=#{SERVICE_ID} and CHECK_STATE=#{CHECK_STATE}")
    List<Map> getExpertCheckInfo(@Param("SERVICE_ID") String SERVICE_ID, @Param("CHECK_STATE") String CHECK_STATE);
    @Select("select * from ZHIFA_EXPERT_RECTIFICATION where SERVICE_ID=#{SERVICE_ID}")
    List<Map> getExpertRecordInfo(@Param("SERVICE_ID") String SERVICE_ID);


    @Update ("update  ZHIFA_EXPERT_RECTIFICATION set CHECKED_START_TIME=#{CHECKED_START_TIME} where SERVICE_ID=#{SERVICE_ID}")
    void insertRectification1(@Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("SERVICE_ID") String SERVICE_ID);
    @Insert("insert into ZHIFA_EXPERT_RECTIFICATION (SERVICE_ID,CHECKED_START_TIME) values (#{SERVICE_ID},#{CHECKED_START_TIME})")
    void insertRectification(@Param("SERVICE_ID") String SERVICE_ID, @Param("CHECKED_START_TIME") String CHECKED_START_TIME);

    /**
     * 缓存更新一般隐患页面
     * @param
     * @param
     */
    @Select("select count(*)  from  ZHIFA_EXPERT_RECTIFICATION   Where  SERVICE_ID=#{SERVICE_ID}  ")
    int checkTemporaryRecordIdCount(@Param("SERVICE_ID") String SERVICE_ID );
    @Update("update ZHIFA_EXPERT_RECTIFICATION set DEAL_VIEW=#{PROCESS_DECISION},URL=#{URL} where SERVICE_ID=#{RECORD_ID}")
    int temporaryGeneralHiddenDanger(@Param("PROCESS_DECISION") String PROCESS_DECISION, @Param("URL") String URL, @Param("RECORD_ID") String RECORD_ID);



    /**
     *
     * @param
     * @param
     */
    @Update("update ZHIFA_EXPERT_RECTIFICATION set EXPERT_SIGN=#{EXPERT_SIGN} where SERVICE_ID=#{SERVICE_ID}")
    void updateExpertSign(@Param("EXPERT_SIGN") String EXPERT_SIGN, @Param("SERVICE_ID") String SERVICE_ID);
    @Update("update ZHIFA_EXPERT_RECTIFICATION set CHECKEDMAN_SIGN=#{CHECKEDMAN_SIGN} where SERVICE_ID=#{SERVICE_ID}")
    void updateCheckedSign(@Param("CHECKEDMAN_SIGN") String CHECKEDMAN_SIGN, @Param("SERVICE_ID") String SERVICE_ID);
    @Update("update ZHIFA_EXPERT_RECTIFICATION set DEAL_VIEW=#{DEAL_VIEW} where SERVICE_ID=#{SERVICE_ID}")
    void updateDealView(@Param("DEAL_VIEW") String DEAL_VIEW, @Param("SERVICE_ID") String SERVICE_ID);
    @Update("update ZHIFA_EXPERT_RECTIFICATION set PDF_PATH=#{PDF_PATH},QRCODE_PATH=#{QRCODE_PATH} where SERVICE_ID=#{SERVICE_ID}")
    void updateCheckPdf(@Param("PDF_PATH") String PDF_PATH, @Param("QRCODE_PATH") String QRCODE_PATH, @Param("SERVICE_ID") String SERVICE_ID);
    /**
     * 获取缓存一般隐患现场整改表格信息
     * @param
     * @return
     */
    @Select("SELECT * FROM ZHIFA_EXPERT_RECTIFICATION WHERE SERVICE_ID=#{RecordId}" )
    List<Map> getTemporaryGeneralHiddenDanger(@Param("RecordId") String RecordId);
}
