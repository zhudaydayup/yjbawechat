package yjbd.yjbawechat.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yjbd.yjbawechat.Model.exEnforceModel;

import java.util.List;
import java.util.Map;

@Repository
public interface ExEnforceRepository extends JpaRepository<exEnforceModel,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_EXPERT_SCENE_RECORD set CHECKED_UNIT=?1, ADDRESS=?2, REPRESENT_PEOPLE=?3, REPRESENT_MOBILE=?4, CHECKED_LOCATION=?5, CHECKED_START_TIME=?6, CHECKED_END_TIME=?7,  UNIT_NUMBER=?8, EXPERT_NAME1=?9 ,EXPERT_UNIT1=?10,EXPERT_NAME2=?11,EXPERT_UNIT2=?12 where RECORD_ID = ?13", nativeQuery = true)
    int UpdateIdRecord(String checked_unit, String address, String represent_people, String represent_mobile, String checked_location, String checked_start_time, String checked_end_time, String unit_number, String expert_name1, String expert_unit1, String expert_name2, String expert_unit2, String recordId);

    /**
     * 更新检查记录页面缓存
     * @param
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_EXPERT_SCENE_RECORD set CHECKED_UNIT=?1, ADDRESS=?2, REPRESENT_PEOPLE=?3, REPRESENT_MOBILE=?4, CHECKED_LOCATION=?5, CHECKED_START_TIME=?6, CHECKED_END_TIME=?7, UNIT_NUMBER=?8, EXPERT_NAME1=?9, EXPERT_UNIT1=?10, CREATE_TIME=?11, URL=?12 where RECORD_ID = ?13", nativeQuery = true)
    int temporaryExUpdateIdRecord(String checked_unit, String address, String represent_people, String represent_mobile, String checked_location, String checked_start_time, String checked_end_time, String unit_number, String expert_name1, String expert_unit1, String createTime,String url, String recordId);
    /**
     * 获取更新检查记录页面缓存
     * @param
     * @return
     */
    @Query(value = "select * from ZHIFA_EXPERT_SCENE_RECORD where RECORD_ID = ?1 ", nativeQuery = true)
    List<Map> getTemporaryExUpdateIdRecord(String recordId);

    @Query(value = "select CREATE_TIME from ZHIFA_EXPERT_SCENE_RECORD where RECORD_ID = ?1 ", nativeQuery = true)
    String GetTime(String recordId);

    @Query(value = "select * from ZHIFA_EXPERT_SCENE_RECORD where RECORD_ID = ?1 ", nativeQuery = true)
    List<exEnforceModel> getIdRecord(String recordId);

    @Query(value = "select * from ZHIFA_EXPERT_ITEM_RECORD where SERVICE_ID = ?1 ", nativeQuery = true)
    List<Map> getIdCheckMsgs(String recordId);

    @Query(value = "select * from ZHIFA_EXPERT_DANGER_RECORD where RECORD_ID = ?1 ", nativeQuery = true)
    List<Map> getDangerRecord(String recordId);



    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_EXPERT_SCENE_RECORD set EXPERT_SIGN = ?1,EXPERT_SIGN_TIME=?3 where RECORD_ID = ?2", nativeQuery = true)
    int UpdateExpertSign(String signName, String eventId, String signTime);


    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_EXPERT_SCENE_RECORD set WITNESS_SIGN = ?1,WITNESS_SIGN_TIME=?3 where RECORD_ID = ?2", nativeQuery = true)
    int UpdateWitnessSign(String signName, String eventId, String signTime);
}
