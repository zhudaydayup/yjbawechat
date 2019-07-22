package yjbd.yjbawechat.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yjbd.yjbawechat.Model.EnforceModel;

import java.util.List;
import java.util.Map;

/**
 * @Totle:DataDao
 * @ProjectName:yjbawechat
 * @author:社会码农
 * @data:2019/4/118:09
 */
@Repository
public interface DataDao extends JpaRepository<EnforceModel,String> {

    @Query(value = "select * from ZHIFA_SCENE_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> getSceneRecord(String recordId);

    @Query(value = "select * from ZHIFA_ITEM_RECORD where SERVICE_ID = ?1", nativeQuery = true)
    List<Map> getITEMRecord(String recordId);

    @Query(value = "select * from ZHIFA_PUNISH_MEASURE where SERVICE_ID = ?1", nativeQuery = true)
    List<Map>  getPUNISHRecord(String recordId);

    @Query(value = "select * from ZHIFA_ZELING_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> getZelingRecord(String recordId);

    @Query(value = "select * from ZHIFA_RECTIFICATION where SERVICE_ID = ?1", nativeQuery = true)
    Map<String, String> getFuChaRecord(String recordId);

    @Query(value = "select * from ZHIFA_EXPERT_SCENE_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> getEXPERTRecord(String recordId);

    @Query(value = "select * from ZHIFA_NOTICE_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> getZeJianRecord1(String recordId);

    @Query(value = "select * from ZHIFA_OWN_CHECK_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> getZeJianRecord2(String recordId);


    /**
     * 传送执法处罚数据给保通
     */
    @Query(value = "select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map PushZhiFaChuFaData1(String recordId);
    @Query(value = "select * from ZHIFA_EXPERT_PUNISH_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map PushZhiFaChuFaData2(String recordId);



    /**
     * 传送自检封装数据
     */
    @Query(value = "select * from ZHIFA_ZJ_ITEM_RECORD where SERVICE_ID = ?1", nativeQuery = true)
    List<Map> putZiJianDate(String recordId);

    @Query(value = "select * from ZHIFA_COMPANY_INFO_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> putZJRecord(String recordId);

    /**
     * 传送专家较大隐患信息
     */
    @Query(value = "select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> putExpertHidddenDangerDate(String recordId);

    @Query(value = "select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> putExpertDangerRecord(String recordId);




    @Query(value = "select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> getExSceneRecord(String recordId);


    @Query(value = "select * from ZHIFA_EXPERT_Zeling_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> getExZelingRecord(String recordId);

    @Query(value = "select * from ZHIFA_EXPERT_PUNISH_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map>  getExPUNISHRecord(String recordId);

    @Query(value = "select * from ZHIFA_EXPERT_SCENE_RECORD where RECORD_ID = ?1", nativeQuery = true)
    Map<String, String> getExSceneRecord2(String recordId);

    /**
     * 传送执法复查数据
     */
    @Query(value = "select * from ZHIFA_RECTIFICATION where SERVICE_ID = ?1", nativeQuery = true)
    List<Map> getRectificationData(String recordId);

    @Query(value = "select * from ZHIFA_RECTIFICATION where SERVICE_ID = ?1", nativeQuery = true)
    List<Map> getRectificationDataByRecordId(String recordId);

    @Query(value = "select * from ZHIFA_SCENE_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> getSceneDataByRecordId(String recordId);



    /**
     * 推送执法暂存数据
     */
    @Query(value = "select * from ZHIFA_SCENE_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> getTemporaryZhiFaTempName(String RECORD_ID);

    /**
     * 推送专家暂存数据
     */
    @Query(value = "select * from ZHIFA_EXPERT_SCENE_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> getgetTemporaryExTempName(String RECORD_ID);


    /**
     *传送专家检查合格数据
     */
    @Query(value = "select * from ZHIFA_EXPERT_SCENE_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> putExpertHeGeDate(String recordId);

    @Query(value = "select * from ZHIFA_EXPERT_ITEM_RECORD where SERVICE_ID = ?1", nativeQuery = true)
    List<Map>  putExpertHeGeDate1(String recordId);

    /**
     *传送九小场所责令限期整改数据
     * @param
     * @return
     */
    @Query(value = "select * from FIRE_CHECK_ITEM where CREATE_TIME = ?1", nativeQuery = true)
    List<Map> putFireFightingTimeLimitDate(String recordId);
    @Query(value = "select * from FIRE_TIME_LIMIT_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> putFireFightingTimeLimitDate1(String recordId);
    @Query(value = "select * from FIRE_CHECK_ITEM where CREATE_TIME  = ?1", nativeQuery = true)
    List<Map> putFireFightingTimeLimitDate3(String recordId);

    /**
     *传送九小场所现场改正数据
     */
    @Query(value = "select * from FIRE_RECTIFICATION_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> putTimeRectificationDate(String recordId);

    /**
     *传送九小场所重大隐患数据
     */
    @Query(value = "select * from FIRE_DANGER_RECORD where RECORD_ID = ?1", nativeQuery = true)
    List<Map> putFireHiddenDangerRecordDate(String recordId);

}
