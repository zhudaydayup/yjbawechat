package yjbd.yjbawechat.Util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Totle:MsgUtils
 * @ProjectName:knowledgebase_rear
 * @author:社会码农
 * @data:2018/12/2517:13
 */
public class MsgUtils {

    public static String successMsg() {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("backFlag", "ok");
    return jsonObject.toJSONString();

    }

    public static String successMsg(String type,String content) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("backFlag", "ok");
        jsonObject.put(type,content);
        return jsonObject.toJSONString();

    }

    public static String successMsg(String type1,String content1,String type2,String content2) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("backFlag", "ok");
        jsonObject.put(type1,content1);
        jsonObject.put(type2,content2);
        return jsonObject.toJSONString();

    }

    public static String successMsg(String type, List list) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("backFlag", "ok");
        JSONArray jsonArray = new JSONArray();
        for(int i = 0;i < list.size(); i++) {

            jsonArray.add(list.get(i));

        }
        jsonObject.put(type,jsonArray);
        return jsonObject.toJSONString();

    }

    public static String successMsg(String type1, List list1, String type2, List list2) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("backFlag", "ok");
        JSONArray jsonArray1 = new JSONArray();
        for (int i = 0; i < list1.size(); i++) {
            jsonArray1.add(list1.get(i));
        }
        jsonObject.put(type1, jsonArray1);
        JSONArray jsonArray2 = new JSONArray();
        for (int j = 0; j < list1.size(); j++) {
            jsonArray2.add(list2.get(j));
        }
        jsonObject.put(type2, jsonArray2);
        return jsonObject.toJSONString();

    }

    public static String successMsg(String type1, String content1, String type2, List list1, String type3, List list2) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("backFlag", "ok");
        jsonObject.put(type1, content1);
        JSONArray jsonArray1 = new JSONArray();
        for (int i = 0; i < list1.size(); i++) {
            jsonArray1.add(list1.get(i));
        }
        jsonObject.put(type2, jsonArray1);
        JSONArray jsonArray2 = new JSONArray();
        for (int j = 0; j < list1.size(); j++) {
            jsonArray2.add(list2.get(j));
        }
        jsonObject.put(type3, jsonArray2);
        return jsonObject.toJSONString();

    }
    /**
     * @param type1  键1的名称
     * @param content1 键1的值
     * @param type2   键2的名称
     * @param list2   键2的list数组
     * @return
    {
    "msg": "ok",
    type1:content1
    "info": [{
    "cellNo": "1",
    "id": 1193,
    "mac": "a84314318498493174",
    "number": "123"
    }, {
    "cellNo": "1",
    "id": 1194,
    "mac": "a84314318498493174",
    "number": "124"
    }, {
    "cellNo": "1",
    "id": 1195,
    "mac": "12",
    "number": "125",
    "registerTime": "2018-11-15 17:13:15"
    }]
    }
     */
    public static String successMsg(String type1,String content1,String type2, List list2) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("backFlag","ok");
        jsonObject.put(type1,content1);
        JSONArray jsonArray=new JSONArray();
        for(int i = 0; i < list2.size(); i++) {

            jsonArray.add(list2.get(i));

        }
        jsonObject.put(type2,jsonArray);
        return jsonObject.toJSONString();
    }

    public static String errorMsg() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("backFlag", "error");
        return jsonObject.toJSONString();

    }

    public static String errorMsg(String failInfo) {

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("backFlag","error");
        jsonObject.put("failInfo",failInfo);
        return jsonObject.toJSONString();

    }

}
