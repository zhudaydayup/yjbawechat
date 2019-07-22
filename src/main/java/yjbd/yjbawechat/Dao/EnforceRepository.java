package yjbd.yjbawechat.Dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yjbd.yjbawechat.Model.EnforceModel;

import java.util.List;
import java.util.Map;

@Repository
public interface EnforceRepository extends JpaRepository<EnforceModel,Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into ZHIFA_SCENE_RECORD(CHECKED_UNIT, UNIT_NUMBER, REPRESENT_PEOPLE, REPRESENT_MOBILE, ADDRESS, CHECK_PEOPLE1, CARD_NUMBER1, CHECK_UNIT1, CHECK_PEOPLE2, CARD_NUMBER2, CHECK_UNIT2, EXPERT_NAME1, EXPERT_UNIT1, CHECKED_START_TIME, NOTICE_CONTENT, NOTICE_WAY, NOTICE_CONTACTS, NOTICE_PHONE, USER_ID, AREA, CREATE_TIME,ONE_ID,TWO_ID,NOTIFICATION_NUMBER) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16,?17,?18,?19,?20,?21,?22,?23,?24)", nativeQuery = true)
    int createEnforce(String CHECKED_UNIT, String UNIT_NUMBER, String REPRESENT_PEOPLE, String REPRESENT_MOBILE, String ADDRESS, String CHECK_PEOPLE1, String CARD_NUMBER1, String CHECK_UNIT1, String CHECK_PEOPLE2, String CARD_NUMBER2, String CHECK_UNIT2, String EXPERT_NAME1, String EXPERT_UNIT1, String CHECKED_START_TIME, String NOTICE_CONTENT, String NOTICE_WAY, String NOTICE_CONTACTS, String NOTICE_PHONE, String USER_ID, String AREA, String CREATE_TIME,String ONE_ID,String TWO_ID,String NOTIFICATION_NUMBER);

    @Query(value = "select * from (select * from ZHIFA_SCENE_RECORD where USER_ID = ?1 order by CREATE_TIME desc) where rownum=1", nativeQuery = true)
    List<EnforceModel> getEnforceByUserId(String userId);
    /**
     * 现场检查记录缓存
     * @param
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_SCENE_RECORD set CHECKED_UNIT=?1, ADDRESS=?2, REPRESENT_PEOPLE=?3, REPRESENT_MOBILE=?4, CHECKED_LOCATION=?5, CHECKED_START_TIME=?6, CHECKED_END_TIME=?7, CHECK_UNIT1=?8, CHECK_PEOPLE1=?9, CARD_NUMBER1=?10, UNIT_NUMBER=?11, CHECK_UNIT2=?12, CHECK_PEOPLE2=?13, CARD_NUMBER2=?14, CHECKED_REPRESENT_PEOPLE=?15, CHECKED_SEX=?16,CHECKED_REPRESENT_NUMBER=?17,CHECKED_REPRESENT_DUTY=?18,CHECKED_REPRESENT_MOBILE=?19,WITNESS_PEOPLE=?20,WITNESS_SEX=?21,WITNESS_NUMBER=?22,EXPERT_NAME1=?23 ,EXPERT_UNIT1=?24,EXPERT_NAME2=?25,EXPERT_UNIT2=?26,CREATE_TIME=?27, AREA=?28,URL=?29 where RECORD_ID = ?30", nativeQuery = true)
    int temporaryUpdateIdRecord(String checked_unit, String address, String represent_people, String represent_mobile, String checked_location, String checked_start_time, String checked_end_time, String check_unit1, String check_people1, String card_number1, String unit_number, String check_unit2, String check_people2, String card_number2, String checked_represent_people, String checked_sex, String checked_represent_number, String checked_represent_duty, String checked_represent_mobile, String witness_people, String witness_sex, String witness_number, String expert_name1, String expert_unit1, String expert_name2, String expert_unit2, String createTime, String area, String URL,String recordId);
    /**
     * 获取现场检查记录缓存数据
     * @param
     * @return
     */
    @Query(value = "select * from ZHIFA_SCENE_RECORD where RECORD_ID = ?1 ", nativeQuery = true)
    List<EnforceModel> gettemporaryUpdateIdRecord(String recordId);

    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_SCENE_RECORD set CHECKED_UNIT=?1, ADDRESS=?2, REPRESENT_PEOPLE=?3, REPRESENT_MOBILE=?4, CHECKED_LOCATION=?5, CHECKED_START_TIME=?6, CHECKED_END_TIME=?7, CHECK_UNIT1=?8, CHECK_PEOPLE1=?9, CARD_NUMBER1=?10, UNIT_NUMBER=?11, CHECK_UNIT2=?12, CHECK_PEOPLE2=?13, CARD_NUMBER2=?14, CHECKED_REPRESENT_PEOPLE=?15, CHECKED_SEX=?16,CHECKED_REPRESENT_NUMBER=?17,CHECKED_REPRESENT_DUTY=?18,CHECKED_REPRESENT_MOBILE=?19,WITNESS_PEOPLE=?20,WITNESS_SEX=?21,WITNESS_NUMBER=?22,EXPERT_NAME1=?23 ,EXPERT_UNIT1=?24,CREATE_TIME=?25, AREA=?26 ,AUDIT_AREA=?27,AUDIT_RECORD=?28 where RECORD_ID = ?29", nativeQuery = true)
    int UpdateIdRecord(String checked_unit, String address, String represent_people, String represent_mobile, String checked_location, String checked_start_time, String checked_end_time, String check_unit1, String check_people1, String card_number1, String unit_number, String check_unit2, String check_people2, String card_number2, String checked_represent_people, String checked_sex, String checked_represent_number, String checked_represent_duty, String checked_represent_mobile, String witness_people, String witness_sex, String witness_number, String expert_name1, String expert_unit1, String createTime, String area,String AUDIT_AREA,String AUDIT_RECORD, String recordId);

    @Query(value = "select * from ZHIFA_SCENE_RECORD where CREATE_TIME = ?1 ", nativeQuery = true)
    List<EnforceModel> getEnforceId(String creatTime);
    /**
     *更新检查人1签名
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_SCENE_RECORD set CHECK_SIGN = ?1, CHECK_SIGN_TIME=?3  where RECORD_ID = ?2", nativeQuery = true)
    int UpdateCheckSigh(String signName, String eventId, String signTime);

    /**
     *更新检查人2签名
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_SCENE_RECORD set CHECK2_SIGN = ?1 where RECORD_ID = ?2", nativeQuery = true)
    int UpdateCheckPeopleSign(String signName, String eventId);

    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_SCENE_RECORD set REPRESENT_SIGN = ?1,REPRESENT_SIGN_TIME=?3 where RECORD_ID = ?2", nativeQuery = true)
    int UpdateRepresentSign(String signName, String eventId, String signTime);

    @Query(value = "select * from ZHIFA_SCENE_RECORD where RECORD_ID = ?1 ", nativeQuery = true)
    List<EnforceModel> getIdRecord(String recordId);

    @Query(value = "select * from ZHIFA_ITEM_RECORD where SERVICE_ID = ?1 ", nativeQuery = true)
    List<Map> getIdCheckMsgs(String recordId);

    @Query(value = "select * from ZHIFA_SCENE_RECORD where RECORD_ID = ?1 ", nativeQuery = true)
    List<Map> getName(String recordId);

    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_SCENE_RECORD set EXPERT_SIGN = ?1,EXPERT_SIGN_TIME=?3 where RECORD_ID = ?2", nativeQuery = true)
    int UpdateExpertSign(String signName, String eventId, String signTime);
    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_SCENE_RECORD set WITNESS_SIGN = ?1,WITNESS_SIGN_TIME=?3 where RECORD_ID = ?2", nativeQuery = true)
    int UpdateWitnessSign(String signName, String eventId, String signTime);
}
