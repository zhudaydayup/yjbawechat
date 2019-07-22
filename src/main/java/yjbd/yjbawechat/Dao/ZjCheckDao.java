package yjbd.yjbawechat.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yjbd.yjbawechat.Model.EnforceModel;

import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/20 19:12
 */
@Repository
public interface ZjCheckDao extends JpaRepository<EnforceModel,Integer> {
    @Transactional
    @Modifying
    @Query(value = "update ZHIFA_ZJ_ITEM_RECORD set CHECKE_UNIT=?1,   CHECKED_LOCATION=?2,  CHECKED_END_TIME=?3,CHECK_PEOPLE=?4,UNIT_NUMBER=?5,CREATE_TIME=?6  where SERVICE_ID = ?7", nativeQuery = true)
    int PutZjCheckView(String checked_unit, String checked_location, String checked_end_time, String CHECK_PEOPLE1, String unit_number, String createTime, String recordId);

    @Query(value = "select * from ZHIFA_COMPANY_INFO_RECORD where RECORD_ID = ?1 ", nativeQuery = true)
    List<Map> GetZjRecord(String recordId);

    @Query(value = "select * from ZHIFA_ZJ_ITEM_RECORD where SERVICE_ID = ?1 ", nativeQuery = true)
    List<Map> GetZjCheckMsgs(String recordId);
}
