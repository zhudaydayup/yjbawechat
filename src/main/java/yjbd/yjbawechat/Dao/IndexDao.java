package yjbd.yjbawechat.Dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface IndexDao {

    @Insert("insert into ZHIFA_ONE (ONE_ID,ONE_NAME,ONE_IMG) values ('10','map',#{formattedAddress})")
    int InsertMap(@Param("formattedAddress") String formattedAddress);

    /*
     * 查询首页显示内容
     * parse 无
     */
    @Select("select * from ZHIFA_ONE order by cast(ONE_ID as integer) ASC")
    List<Map> checkOneItemServiceDao();

    /*
     * 查询专家是否有检查记录
     * parse  userId
     */
    @Select("select count(*) from ZHIFA_EXPERT_SCENE_RECORD Where USER_ID = #{userId} and UNIT_NUMBER = #{social_credit_code} ")
    int  checkExPreRecordByUserId(@Param("userId") String userId,@Param("social_credit_code") String social_credit_code);

    /*
     * 查询最近一条检查记录
     * parse  userId            and  rownum<2
     *///select  RECORD_ID  from (select * from  ZHIFA_EXPERT_SCENE_RECORD  Where  UNIT_NUMBER=#{social_credit_code} order by CREATE_TIME desc )where rownum<2
    @Select(" select * from (select * from ZHIFA_EXPERT_SCENE_RECORD Where  USER_ID = #{userId} and UNIT_NUMBER = #{social_credit_code} order by CREATE_TIME desc) where rownum<2 ")
    List<Map> checkExPreRecordNewListByUserId(@Param("userId") String userId,@Param("social_credit_code") String social_credit_code);


    /*
     * 查询第二级内容by oneId and twoId
     * parse  oneId
     */
    @Select("select * from ZHIFA_TWO Where ONE_ID = #{oneId} order by cast(TWO_ID as integer) ASC")
    List<Map> checkTwoItemServiceDao(@Param("oneId") String oneId);

    /*
     * 查询是否有检查记录
     * parse  userId
     */
    @Select("select count(*) from ZHIFA_SCENE_RECORD Where USER_ID = #{userId} and UNIT_NUMBER = #{social_credit_code}")
    int  checkIsHaveRecordServiceDao(@Param("userId") String userId,@Param("social_credit_code") String social_credit_code);

    /*
     * 查询最近一条检查记录
     * parse  userId            and  rownum<2
     */
    @Select(" select * from (select * from ZHIFA_SCENE_RECORD Where  USER_ID = #{userId} and UNIT_NUMBER = #{social_credit_code} order by CHECKED_START_TIME desc) where rownum<2 ")
    List<Map> checkNewLastRecordServiceDao(@Param("userId") String userId,@Param("social_credit_code") String social_credit_code);

    /*
     * 查询第二级内容by oneId and twoId
     * parse  oneId
     */
    @Select("select * from ZHIFA_TWO Where ONE_ID = #{oneId} ${condition} order by cast(TWO_ID as integer) ASC")
    List<Map> checkTwoItemServiceDaos(@Param("oneId") String oneId,@Param("condition") String condition);

    /*
     * 查询第三级级内容by oneId and twoId
     * parse  oneId , twoId
     */
    @Select("select * from ZHIFA_THREE Where ONE_ID = #{oneId} and TWO_ID=#{twoId} order by cast(THREE_ID as integer) ASC")
    List<Map> checkThreeItemServiceDao(@Param("oneId") String oneId , @Param("twoId") String twoId);



    /* 执法流程
     * 查询第四级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    @Select("select * from ZHIFA_FOUR Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} order by cast(FOUR_ID as integer) ASC")
    List<Map> checkFourItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId);
    /*
     * 根据RecordId查询社会信用代码
     * parse  RecordId
     */
    @Select("select  UNIT_NUMBER  from  ZHIFA_SCENE_RECORD  Where  RECORD_ID=#{RecordId} ")
    String checkSocialCreditCodeByRecordId(@Param("RecordId") String RecordId );
    /*
     * 根据社会信用代码和模板id查询该模板当月执法检查记录数
     * parse  RecordId
     */
    @Select("select count(*)  from  ZHIFA_ITEM_RECORD   Where  CHECKE_UNIT=#{social_credit_code} and TEMP_ID=#{TEMP_ID} and to_char(sysdate,'yyyy-mm')= to_char(to_date(CHECKED_END_TIME,'yyyy/mm/dd hh:mi:ss'),'yyyy-mm') ")
    int checkRecordCountBySocialCreditCodeAndTempId(@Param("social_credit_code") String social_credit_code,@Param("TEMP_ID") String TEMP_ID );






    /* 自检流程
     * 查询第四级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    @Select("select * from ZHIFA_FOUR Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} order by cast(FOUR_ID as integer) ASC")
    List<Map> zjCheckFourItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId);
    /*
     * 根据自检RecordId查询社会信用代码
     * parse  RecordId
     */
    @Select("select  UNIT_NUMBER  from  ZHIFA_COMPANY_INFO_RECORD  Where  RECORD_ID=#{RecordId} ")
    String zjCheckSocialCreditCodeByRecordId(@Param("RecordId") String RecordId );
    /*
     * 根据自检社会信用代码和模板id查询该模板当月执法检查记录数
     * parse  RecordId
     */
    @Select("select count(*)  from  ZHIFA_ZJ_ITEM_RECORD   Where  UNIT_NUMBER=#{UNIT_NUMBER} and TEMP_ID=#{TEMP_ID} and to_char(sysdate,'yyyy-mm')= to_char(to_date(CHECKED_END_TIME,'yyyy/mm/dd HH24:mi:ss'),'yyyy-mm') ")
    int zjCheckRecordCountBySocialCreditCodeAndTempId(@Param("UNIT_NUMBER") String UNIT_NUMBER,@Param("TEMP_ID") String TEMP_ID );


    /*
     * 查询专家第四级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    @Select("select * from ZHIFA_FOUR Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} order by cast(FOUR_ID as integer) ASC")
    List<Map> exCheckFourItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId);
    /*
     * 根据专家RecordId查询社会信用代码
     * parse  RecordId
     *///select * from (select * from ZHIFA_EXPERT_SCENE_RECORD Where  USER_ID = #{userId} order by CHECKED_START_TIME desc) where rownum<2
    @Select("select  UNIT_NUMBER  from  ZHIFA_EXPERT_SCENE_RECORD  Where  RECORD_ID=#{RecordId}")
    String exCheckSocialCreditCodeByRecordId(@Param("RecordId") String RecordId );
    /*
     * 根据专家社会信用代码和模板id查询该模板当月执法检查记录数
     * parse  RecordId
     */
    @Select("select count(*)  from  ZHIFA_EXPERT_ITEM_RECORD   Where  UNIT_NUMBER=#{social_credit_code} and TEMP_ID=#{TEMP_ID} and to_char(sysdate,'yyyy-mm')= to_char(to_date(CREATE_TIME,'yyyy-mm-dd HH24:mi:ss'),'yyyy-mm')")
    int exCheckRecordCountBySocialCreditCodeAndTempId(@Param("social_credit_code") String social_credit_code,@Param("TEMP_ID") String TEMP_ID );

    /* 执法流程
     * 查询第五级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId, fourId
     */
    @Select("select * from ZHIFA_FIVE Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} and FOUR_ID=#{fourId} order by cast(FIVE_ID as integer) ASC")
    List<Map> checkFiveItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId ,@Param("fourId") String fourId);


    /*
     * 查询专家第五级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    @Select("select * from ZHIFA_FIVE Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} and FOUR_ID=#{fourId} order by cast(FIVE_ID as integer) ASC")
    List<Map> exCheckFiveItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId,@Param("fourId") String fourId);
    /*
     * 根据专家第五级RecordId查询社会信用代码
     * parse  RecordId
     *///select * from (select * from ZHIFA_EXPERT_SCENE_RECORD Where  USER_ID = #{userId} order by CHECKED_START_TIME desc) where rownum<2
    @Select("select  UNIT_NUMBER  from  ZHIFA_EXPERT_SCENE_RECORD  Where  RECORD_ID=#{RecordId}")
    String exCheckFiveSocialCreditCodeByRecordId(@Param("RecordId") String RecordId );
    /*
     * 根据专家第五级社会信用代码和模板id查询该模板当月执法检查记录数
     * parse  RecordId
     */
    @Select("select count(*)  from  ZHIFA_EXPERT_ITEM_RECORD   Where  UNIT_NUMBER=#{social_credit_code} and TEMP_ID=#{TEMP_ID} and to_char(sysdate,'yyyy-mm')= to_char(to_date(CREATE_TIME,'yyyy-mm-dd HH24:mi:ss'),'yyyy-mm')")
    int exCheckFiveRecordCountBySocialCreditCodeAndTempId(@Param("social_credit_code") String social_credit_code,@Param("TEMP_ID") String TEMP_ID );

    /* 自检流程
     * 查询第五级内容by oneId and twoId and threeId and fourId
     * parse  oneId , twoId ,threeId, fourId
     */
    @Select("select * from ZHIFA_FIVE Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} and FOUR_ID=#{fourId} order by cast(FIVE_ID as integer) ASC")
    List<Map> ZjcheckFiveItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId ,@Param("fourId") String fourId);


    /* 执法流程
     * 查询第六级内容by oneId and twoId and threeId and fourId and fiveId
     * parse  oneId , twoId ,threeId, fourId,fiveId
     */
    @Select("select * from ZHIFA_Six Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} and FOUR_ID=#{fourId} and FIVE_ID=#{fiveId}order by cast(SIX_ID as integer) ASC")
    List<Map> checkSixItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId ,@Param("fourId") String fourId,@Param("fiveId") String fiveId);


    /*
     * 查询专家第六级内容by oneId and twoId and threeId
     * parse  oneId , twoId ,threeId
     */
    @Select("select * from ZHIFA_Six Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} and FOUR_ID=#{fourId} and FIVE_ID=#{fiveId}order by cast(SIX_ID as integer) ASC")
    List<Map> exCheckSixItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId,@Param("fourId") String fourId,@Param("fiveId") String fiveId);
    /*
     * 根据专家第六级RecordId查询社会信用代码
     * parse  RecordId
     *///select * from (select * from ZHIFA_EXPERT_SCENE_RECORD Where  USER_ID = #{userId} order by CHECKED_START_TIME desc) where rownum<2
    @Select("select  UNIT_NUMBER  from  ZHIFA_EXPERT_SCENE_RECORD  Where  RECORD_ID=#{RecordId}")
    String exCheckSixSocialCreditCodeByRecordId(@Param("RecordId") String RecordId );
    /*
     * 根据专家第六级社会信用代码和模板id查询该模板当月执法检查记录数
     * parse  RecordId
     */
    @Select("select count(*)  from  ZHIFA_EXPERT_ITEM_RECORD   Where  UNIT_NUMBER=#{social_credit_code} and TEMP_ID=#{TEMP_ID} and to_char(sysdate,'yyyy-mm')= to_char(to_date(CREATE_TIME,'yyyy-mm-dd HH24:mi:ss'),'yyyy-mm')")
    int exCheckSixRecordCountBySocialCreditCodeAndTempId(@Param("social_credit_code") String social_credit_code,@Param("TEMP_ID") String TEMP_ID );

    /* 自检流程
     * 查询第六级内容by oneId and twoId and threeId and fourId and fiveId
     * parse  oneId , twoId ,threeId, fourId,fiveId
     */
    @Select("select * from ZHIFA_Six Where ONE_ID = #{oneId} and TWO_ID=#{twoId} and THREE_ID=#{threeId} and FOUR_ID=#{fourId} and FIVE_ID=#{fiveId}order by cast(SIX_ID as integer) ASC")
    List<Map> ZjcheckSixItemServiceeDao(@Param("oneId") String oneId ,@Param("twoId") String twoId ,@Param("threeId") String threeId ,@Param("fourId") String fourId,@Param("fiveId") String fiveId);



    /*
     * 关键字查询模板
     * parse  keyWord
     */
    @Select("select * from ZHIFA_FOUR where FOUR_NAME like #{keyWord} order by ONE_ID")
    List<Map> checkTempByKeyWordServiceDao(@Param("keyWord") String keyWord);


    /*
     * 查询所有的模板
     * parse
     */
    @Select("select * from ZHIFA_FOUR where FOUR_IMG!='1' order by cast(ONE_ID as integer),cast(TWO_ID as integer),cast(THREE_ID as integer),cast(FOUR_ID as integer)")
    List<Map> checkAllTempServiceDao1();
    @Select("select * from ZHIFA_FIVE order by cast(ONE_ID as integer),cast(TWO_ID as integer),cast(THREE_ID as integer),cast(FOUR_ID as integer),cast(FIVE_ID as integer)")
    List<Map> checkAllTempServiceDao2();



    /*
     * 创建检查
     * parse
     */
//    @Select("select count(*)  from  ZHIFA_ITEM_RECORD   Where  SERVICE_ID=#{SERVICE_ID}  AND TEMP_ID=#{TEMP_ID}")
//    int getGreatPaperInfoCount(@Param("SERVICE_ID") String SERVICE_ID,@Param("TEMP_ID") String TEMP_ID );
//
//    @Update( "update ZHIFA_ITEM_RECORD set TEMP_PDF=#{TEMP_PDF},TEMP_NAME= #{TEMP_NAME},USER_ID= #{USER_ID},CHECK_STATE= #{CHECK_STATE}, CHECKED_START_TIME#{CHECKED_START_TIME}," +
//            " CHECKED_END_TIME #{CHECKED_END_TIME},   " +
//            "CHECKE_DETAIL=#{CHECKE_DETAIL},VIDEO_URL=#{VIDEO_URL},LOCATION_IMG=#{LOCATION_IMG},OTHER_IMG=#{OTHER_IMG},CREATE_TIME=#{creaTime},CHECKE_UNIT=( select UNIT_NUMBER from ZHIFA_SCENE_RECORD where  RECORD_ID=#{SERVICE_ID}) where TEMP_ID=#{TEMP_ID} and SERVICE_ID=#{SERVICE_ID} ")
//    void createPaperInfo1(@Param("TEMP_PDF") String TEMP_PDF, @Param("TEMP_NAME") String TEMP_NAME,@Param("USER_ID") String USER_ID,@Param("CHECK_STATE") String CHECK_STATE, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,@Param("LOCATION_IMG") String LOCATION_IMG,@Param("OTHER_IMG") String OTHER_IMG, @Param("creaTime") String creaTime,@Param("TEMP_ID") String TEMP_ID,@Param("SERVICE_ID") String SERVICE_ID);
    @Insert( "insert into ZHIFA_ITEM_RECORD (  TEMP_ID,TEMP_PDF,TEMP_NAME,   USER_ID,CHECK_STATE,SERVICE_ID,    CHECKED_START_TIME, CHECKED_END_TIME,   CHECKE_DETAIL,     VIDEO_URL,LOCATION_IMG, OTHER_IMG,CREATE_TIME,CHECKE_UNIT) values (#{TEMP_ID}, #{TEMP_PDF}, #{TEMP_NAME},      #{USER_ID}, #{CHECK_STATE}, #{SERVICE_ID},        #{CHECKED_START_TIME}, #{CHECKED_END_TIME},         #{CHECKE_DETAIL},             #{VIDEO_URL},#{LOCATION_IMG},#{OTHER_IMG},#{creaTime} ,( select UNIT_NUMBER from ZHIFA_SCENE_RECORD where  RECORD_ID=#{SERVICE_ID})  )")
    void createPaperInfo(@Param("TEMP_ID") String TEMP_ID, @Param("TEMP_PDF") String TEMP_PDF, @Param("TEMP_NAME") String TEMP_NAME,@Param("USER_ID") String USER_ID,@Param("CHECK_STATE") String CHECK_STATE,@Param("SERVICE_ID") String SERVICE_ID, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,@Param("LOCATION_IMG") String LOCATION_IMG,@Param("OTHER_IMG") String OTHER_IMG, @Param("creaTime") String creaTime);



    /*
     * 创建检查(企业内查)
     * parse
     */
    @Insert( "insert into ZHIFA_ZJ_ITEM_RECORD (  TEMP_ID,TEMP_PDF,TEMP_NAME,   USER_ID,CHECK_STATE,SERVICE_ID,    CHECKED_START_TIME, CHECKED_END_TIME,   CHECKE_DETAIL,     VIDEO_URL,LOCATION_IMG, OTHER_IMG,CREATE_TIME,UNIT_NUMBER) values (#{TEMP_ID}, #{TEMP_PDF}, #{TEMP_NAME},      #{USER_ID}, #{CHECK_STATE}, #{SERVICE_ID},        #{CHECKED_START_TIME}, #{CHECKED_END_TIME},         #{CHECKE_DETAIL},             #{VIDEO_URL},#{LOCATION_IMG},#{OTHER_IMG},#{creaTime},#{UNIT_NUMBER})")
    void zjCreatePaperInfo(@Param("TEMP_ID") String TEMP_ID, @Param("TEMP_PDF") String TEMP_PDF, @Param("TEMP_NAME") String TEMP_NAME,@Param("USER_ID") String USER_ID,@Param("CHECK_STATE") String CHECK_STATE,@Param("SERVICE_ID") String SERVICE_ID, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,@Param("LOCATION_IMG") String LOCATION_IMG,@Param("OTHER_IMG") String OTHER_IMG, @Param("creaTime") String creaTime, @Param("UNIT_NUMBER") String UNIT_NUMBER);


    /*
     * 创建企业内查信息 生成record_id
     * parse
     */
    @Insert( "insert into ZHIFA_COMPANY_INFO_RECORD (  RECORD_ID,CHECKED_UNIT,ADDRESS, REPRESENT_PEOPLE,USER_ID,CREATE_TIME,UNIT_NUMBER) values (#{RecordId}, #{CHECKED_UNIT}, #{ADDRESS}, #{REPRESENT_PEOPLE},#{USER_ID}, #{createTime},#{UNIT_NUMBER})")
    void zjCreateEnforceCompanyDal(@Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("ADDRESS") String ADDRESS,  @Param("REPRESENT_PEOPLE") String REPRESENT_PEOPLE , @Param("USER_ID") String USER_ID,  @Param("createTime") String createTime, @Param("RecordId") String RecordId,@Param("UNIT_NUMBER")String UNIT_NUMBER);


    /*
     * 创建检查(专家检查)
     * parse
     */
//    @Update( "update  ZHIFA_EXPERT_ITEM_RECORD set UNIT_NUMBER=#{UNIT_NUMBER},TEMP_PDF=#{TEMP_PDF},TEMP_NAME=#{TEMP_NAME}, USER_ID= #{USER_ID},CHECK_STATE= #{CHECK_STATE}, CHECKED_START_TIME= #{CHECKED_START_TIME}, CHECKED_END_TIME= #{CHECKED_END_TIME}, CHECKE_DETAIL= #{CHECKE_DETAIL}, VIDEO_URL= #{VIDEO_URL},LOCATION_IMG=#{LOCATION_IMG}, OTHER_IMG=#{OTHER_IMG},CREATE_TIME=#{creaTime} where SERVICE_ID=#{SERVICE_ID} and TEMP_ID=#{TEMP_ID}")
//    void exCreateEnforce( @Param("UNIT_NUMBER") String UNIT_NUMBER,@Param("TEMP_PDF") String TEMP_PDF,
//                          @Param("TEMP_NAME") String TEMP_NAME,@Param("USER_ID") String USER_ID,@Param("CHECK_STATE") String CHECK_STATE,
//                          @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("CHECKE_DETAIL") String CHECKE_DETAIL,
//                          @Param("VIDEO_URL") String VIDEO_URL,@Param("LOCATION_IMG") String LOCATION_IMG,@Param("OTHER_IMG") String OTHER_IMG, @Param("creaTime") String creaTime, @Param("SERVICE_ID") String SERVICE_ID,@Param("TEMP_ID") String TEMP_ID);
    @Insert( "insert into ZHIFA_EXPERT_ITEM_RECORD (  TEMP_ID,TEMP_PDF,TEMP_NAME,   USER_ID,CHECK_STATE,SERVICE_ID,    CHECKED_START_TIME, CHECKED_END_TIME,   CHECKE_DETAIL,     VIDEO_URL,LOCATION_IMG, OTHER_IMG,CREATE_TIME,UNIT_NUMBER) values (#{TEMP_ID}, #{TEMP_PDF}, #{TEMP_NAME},      #{USER_ID}, #{CHECK_STATE}, #{SERVICE_ID},        #{CHECKED_START_TIME}, #{CHECKED_END_TIME},         #{CHECKE_DETAIL},             #{VIDEO_URL},#{LOCATION_IMG},#{OTHER_IMG},#{creaTime},#{UNIT_NUMBER})")
    void exCreatePaperInfo(@Param("TEMP_ID") String TEMP_ID, @Param("TEMP_PDF") String TEMP_PDF, @Param("TEMP_NAME") String TEMP_NAME,@Param("USER_ID") String USER_ID,@Param("CHECK_STATE") String CHECK_STATE,@Param("SERVICE_ID") String SERVICE_ID, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,@Param("LOCATION_IMG") String LOCATION_IMG,@Param("OTHER_IMG") String OTHER_IMG, @Param("creaTime") String creaTime, @Param("UNIT_NUMBER") String UNIT_NUMBER);


    /**
     * 专家检查暂存更新第四级目录
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     * @param
     */
    @Select("select count(*)  from  ZHIFA_EXPERT_ITEM_RECORD   Where  SERVICE_ID=#{SERVICE_ID}  AND TEMP_ID=#{TEMP_ID}")
    int checkRecordIdCount(@Param("SERVICE_ID") String SERVICE_ID,@Param("TEMP_ID") String TEMP_ID );

    @Update( "update  ZHIFA_EXPERT_ITEM_RECORD set TEMP_PDF=#{TEMP_PDF},TEMP_NAME=#{TEMP_NAME}, USER_ID= #{USER_ID},CHECK_STATE= #{CHECK_STATE}, CHECKED_START_TIME= #{CHECKED_START_TIME}, CHECKED_END_TIME= #{CHECKED_END_TIME}, CHECKE_DETAIL= #{CHECKE_DETAIL}, VIDEO_URL= #{VIDEO_URL},LOCATION_IMG=#{LOCATION_IMG}, OTHER_IMG=#{OTHER_IMG},CREATE_TIME=#{creaTime},URL=#{URL} where SERVICE_ID=#{SERVICE_ID}and TEMP_ID=#{TEMP_ID}")
    void temporaryExCreateEnforce(@Param("TEMP_PDF") String TEMP_PDF,
                                  @Param("TEMP_NAME") String TEMP_NAME,@Param("USER_ID") String USER_ID,@Param("CHECK_STATE") String CHECK_STATE,
                                  @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("CHECKE_DETAIL") String CHECKE_DETAIL,
                                  @Param("VIDEO_URL") String VIDEO_URL,@Param("LOCATION_IMG") String LOCATION_IMG,@Param("OTHER_IMG") String OTHER_IMG, @Param("creaTime") String creaTime, @Param("URL") String URL,@Param("SERVICE_ID") String SERVICE_ID,@Param("TEMP_ID") String TEMP_ID );
    @Insert( "insert into ZHIFA_EXPERT_ITEM_RECORD (  TEMP_ID,TEMP_PDF,TEMP_NAME,   USER_ID,CHECK_STATE,SERVICE_ID,    CHECKED_START_TIME, CHECKED_END_TIME,   CHECKE_DETAIL,     VIDEO_URL,LOCATION_IMG, OTHER_IMG,CREATE_TIME,URL) values (#{TEMP_ID}, #{TEMP_PDF}, #{TEMP_NAME},      #{USER_ID}, #{CHECK_STATE}, #{SERVICE_ID},        #{CHECKED_START_TIME}, #{CHECKED_END_TIME},         #{CHECKE_DETAIL},             #{VIDEO_URL},#{LOCATION_IMG},#{OTHER_IMG},#{creaTime},#{URL})")
    void temporaryExCreateEnforce1(@Param("TEMP_ID") String TEMP_ID, @Param("TEMP_PDF") String TEMP_PDF, @Param("TEMP_NAME") String TEMP_NAME,@Param("USER_ID") String USER_ID,@Param("CHECK_STATE") String CHECK_STATE,@Param("SERVICE_ID") String SERVICE_ID, @Param("CHECKED_START_TIME") String CHECKED_START_TIME, @Param("CHECKED_END_TIME") String CHECKED_END_TIME, @Param("CHECKE_DETAIL") String CHECKE_DETAIL, @Param("VIDEO_URL") String VIDEO_URL,@Param("LOCATION_IMG") String LOCATION_IMG,@Param("OTHER_IMG") String OTHER_IMG, @Param("creaTime") String creaTime,@Param("URL") String URL);

    /*
     * 创建专家检查信息 生成record_id
     * parse
     */
    @Insert( "insert into ZHIFA_EXPERT_SCENE_RECORD (  RECORD_ID,CHECKED_UNIT,ADDRESS, REPRESENT_PEOPLE,USER_ID,CREATE_TIME,UNIT_NUMBER) values (#{RecordId}, #{CHECKED_UNIT}, #{ADDRESS}, #{REPRESENT_PEOPLE},#{USER_ID}, #{createTime},#{UNIT_NUMBER})")
    void exCreateEnforceCompanyDal(@Param("CHECKED_UNIT") String CHECKED_UNIT, @Param("ADDRESS") String ADDRESS,  @Param("REPRESENT_PEOPLE") String REPRESENT_PEOPLE , @Param("USER_ID") String USER_ID,  @Param("createTime") String createTime, @Param("RecordId") String RecordId,@Param("UNIT_NUMBER")String UNIT_NUMBER);


}
