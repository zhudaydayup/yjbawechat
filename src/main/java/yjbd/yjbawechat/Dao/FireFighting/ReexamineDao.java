package yjbd.yjbawechat.Dao.FireFighting;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/5/28 16:33
 */
@Mapper
public interface ReexamineDao {
    /**
     * 根据ID来查询数据
     */
    @Select("select * from FIRE_TIME_LIMIT_RECORD where RECORD_ID = #{RECORD_ID}  ")
    List<Map> getReexamineDate(@Param("RECORD_ID") String RECORD_ID);
    /**
     * 存入自己复查信息的表格
     * @param
     */
    @Insert("insert into FIRE_REEXAMINE_RECORD (RECORD_ID,CHECKE_UNIT,CHECKED_END_TIME,CHECKE_OPINION,RECORD_TIME) values (#{RECORD_ID},#{CHECKE_UNIT},#{CHECKED_END_TIME},#{CHECKE_OPINION},#{RECORD_TIME})" )
    void setReexamineInfo(@Param("RECORD_ID") String RECORD_ID, @Param("CHECKE_UNIT") String CHECKE_UNIT, @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("CHECKE_OPINION") String CHECKE_OPINION, @Param("RECORD_TIME") String RECORD_TIME);
    /**
     * 上传专家的签名以及ID
     */
    @Update( "update FIRE_REEXAMINE_RECORD set CHECKE_SIGNATURE = #{CHECKE_SIGNATURE} where RECORD_ID = #{RecordId}")
    int updateExpertSign(@Param("CHECKE_SIGNATURE") String CHECKE_SIGNATURE, @Param("RecordId") String RecordId);
    /**
     * 上传被执法单位的名称以及ID
     */
    @Update("update FIRE_REEXAMINE_RECORD set REPRESENR_SIGNATURE = #{REPRESENR_SIGNATURE} where RECORD_ID = #{RecordId}")
    int updateInspectedSign(@Param("REPRESENR_SIGNATURE") String REPRESENR_SIGNATURE, @Param("RecordId") String RecordId);
    /**
     * 最后预览PDF页面
     */
    @Select("select * from FIRE_REEXAMINE_RECORD where RECORD_ID = #{recordId} ")
    List<Map> LookReexaminePdf(@Param("recordId") String recordId);
    /**
     * 从最初的表格中获取信息
     * @param
     * @param
     * @param
     */
    @Select("select * from FIRE_REEXAMINE_RECORD where RECORD_ID = #{RECORD_ID}  ")
    List<Map> getReexaminePdfInfo(@Param("RECORD_ID") String RECORD_ID);
    @Update("update FIRE_REEXAMINE_RECORD set PDF_PATH= #{PDF_PATH},QRCODE_PATH=#{QRCODE_PATH} where RECORD_ID= #{RECORD_ID}")
    void updatePdfAndQrcode(@Param("PDF_PATH") String PDF_PATH, @Param("QRCODE_PATH") String QRCODE_PATH, @Param("RECORD_ID") String RECORD_ID);
}
