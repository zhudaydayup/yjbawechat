package yjbd.yjbawechat.Dao.FireFighting;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 努力的小朱
 * @date 2019/4/24
 */
@Mapper
public interface FireIndexDao {
    @Select("select * from FIRE_CHECKED_CONTENT")
    List<Map> getFireChecked();
    @Insert("insert into FIRE_CHECK_ITEM (CHECKED_UNIT,UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,REPRESENT_MOBILE,RISK_POINT,CHECK_START_TIME,CHECK_PEOPLE,CARD_NUMBER,CHECK_UNIT,CHECK_ITEM,CHECK_DESC,CHECK_IMG,CHECK_VIDEO,CREATE_TIME,INSPECT_TYPE,REGION_ID) values (#{CHECKED_UNIT},#{UNIT_NUMBER},#{ADDRESS},#{REPRESENT_PEOPLE},#{REPRESENT_MOBILE},#{RISK_POINT},#{CHECK_START_TIME},#{CHECK_PEOPLE},#{CARD_NUMBER},#{CHECK_UNIT},#{CHECK_ITEM},#{CHECK_DESC},#{CHECK_IMG},#{CHECK_VIDEO},#{CREATE_TIME},#{INSPECT_TYPE},#{REGION_ID})")
    void insertCheckItem(@Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("UNIT_NUMBER") String UNIT_NUMBER, @Param("ADDRESS") String ADDRESS, @Param("REPRESENT_PEOPLE") String REPRESENT_PEOPLE, @Param("REPRESENT_MOBILE") String REPRESENT_MOBILE, @Param("RISK_POINT") String RISK_POINT, @Param("CHECK_START_TIME") String CHECK_START_TIME, @Param("CHECK_PEOPLE") String CHECK_PEOPLE, @Param("CARD_NUMBER") String CARD_NUMBER, @Param("CHECK_UNIT") String CHECK_UNIT, @Param("CHECK_ITEM") String CHECK_ITEM, @Param("CHECK_DESC") String CHECK_DESC,
                         @Param("CHECK_IMG") String CHECK_IMG, @Param("CHECK_VIDEO") String CHECK_VIDEO, @Param("CREATE_TIME") String CREATE_TIME, @Param("INSPECT_TYPE") String INSPECT_TYPE, @Param("REGION_ID") String REGION_ID);
    @Update("update FIRE_CHECK_ITEM set CHECKMAN_SIGN=#{CHECKMAN_SIGN} where CREATE_TIME=#{CREATE_TIME}")
    void updateCheckSign(@Param("CHECKMAN_SIGN") String CHECKMAN_SIGN, @Param("CREATE_TIME") String CREATE_TIME);
    @Update("update FIRE_CHECK_ITEM set CHECKEDMAN_SIGN=#{CHECKEDMAN_SIGN} where CREATE_TIME=#{CREATE_TIME}")
    void updateCheckedSign(@Param("CHECKEDMAN_SIGN") String CHECKEDMAN_SIGN, @Param("CREATE_TIME") String CREATE_TIME);
    @Select("select * from FIRE_CHECK_ITEM where CREATE_TIME=#{CREATE_TIME}")
    List<Map> getCheckInfo(@Param("CREATE_TIME") String CREATE_TIME);
    @Update("update FIRE_CHECK_ITEM set CHECK_PDF=#{CHECK_PDF},QR_CODE=#{QR_CODE} where CREATE_TIME=#{CREATE_TIME}")
    void updateCheckPdf(@Param("CHECK_PDF") String CHECK_PDF, @Param("QR_CODE") String QR_CODE, @Param("CREATE_TIME") String CREATE_TIME);
}
