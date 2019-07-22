package yjbd.yjbawechat.Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @Totle:HttpUtil
 * @ProjectName:yjbawechat
 * @author:社会码农
 * @data:2019/4/1515:53
 */
public class HttpUtil {

    /**
     * 获取数据
     * HttpSend方法
     * @param url
     * @return
     */
    public static String sendGet(String url) {

        String result = "";
        BufferedReader in = null;

        try{
            String charset = java.nio.charset.StandardCharsets.UTF_8.name();
            //String query = String.format("param1=%s",URLEncoder.encode(param1, charset));

            //若是两个请求参数，使用param1=%s&param2=%s
            //String query = String.format("wd=%s&param2=%s",URLEncoder.encode(param1, charset),URLEncoder.encode(param2, charset));

            String request = url;

            //打开和URL之间的连接
            URLConnection connection = new URL(request).openConnection();
            connection.setRequestProperty("Authorization","Basic ZW1fc291cmNlOlBAc3N3MHJkU2t5bmo");
            /* begin获取响应码 */
            HttpURLConnection httpUrlConnection = (HttpURLConnection)connection;
            httpUrlConnection.setConnectTimeout(300000);
            httpUrlConnection.setReadTimeout(300000);
            httpUrlConnection.connect();
            //获取响应码 =200
            int resCode = httpUrlConnection.getResponseCode();
            //获取响应消息 =OK
            String message = httpUrlConnection.getResponseMessage();

            System.out.println("getResponseCode resCode ="+ resCode);
            System.out.println("getResponseMessage message ="+ message);
            /* end获取响应码 */

            /* begin获取响应headers*/
            System.out.println("响应头：" + result);
            for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                System.out.println(header.getKey() + "=" + header.getValue());
            }
            /* end获取响应headers*/

            /* begin获取响应内容 /
            if (resCode == httpUrlConnection.getResponseCode()) {
                int contentLength = httpUrlConnection.getContentLength();
                System.out.println("contentLength--->" + contentLength);
                if(contentLength > 0){
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        result += "\n" + inputLine;
                    System.out.println("响应内容：" + result);
                }
            }
            /* end获取响应内容 */

            /*
            //设置通用的请求属性
            connection.setRequestProperty("Accept-Charset", charset);
            //建立实际的连接
            connection.connect();
            //获取响应头部
            Map<String,List<String>> map = connection.getHeaderFields();
            System.out.println("\n显示响应Header信息...\n");
            //遍历所有的响应头字段并输出
            //方式一、
            for (String key : map.keySet()) {
                System.out.println(key + " : " + map.get(key));
            }
            //方式二、
            for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                System.out.println(header.getKey() + "=" + header.getValue());
            }
            */
            //打印response body
            //方式一、定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                result += inputLine;
            System.out.println("result===" + result);
            /*
            //方式二、使用Scanner
            System.out.println("响应内容：");
            InputStream response = connection.getInputStream();

            try(Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println(responseBody);
            }*/

            //解析响应json
//            JSONObject json = JSONObject.parseObject(result/*"待解析的json字符串"*/);
//            System.out.println(JSONObject.toJSONString(json, true));
        }catch (Exception e){
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }// 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 传送其他数据
     * HttpPost方法
     * @param myUrl,myData
     * @return
     */
    public static String httpPost(String myUrl,Map<String,String> myData){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(myUrl);
            // 打开和URL之间的连接
            // URLConnection conn = realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Authorization","Basic ZW1fc291cmNlOlBAc3N3MHJkU2t5bmo");
             conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(JSONObject.toJSONString(myData));
            System.out.println(JSONObject.toJSONString(myData));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 传送专家现场处理、责令整改、结束数据
     * HttpPost方法
     * @param myUrl,myData
     * @return
     */
    public static String httpPost2(String myUrl,Map<String,String> myData){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(myUrl);
            // 打开和URL之间的连接
            // URLConnection conn = realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Authorization","Basic ZW1fc291cmNlOlBAc3N3MHJkU2t5bmo");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print( "["+JSONObject.toJSONString(myData)+"]" );
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }





    /**
     * 传送执法处罚数据给保通
     * HttpPost方法
     * @param myUrl,myData
     * @return
     */
    public static String httpPost3(String myUrl,Map<String,String> myData){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(myUrl);
            // 打开和URL之间的连接
            // URLConnection conn = realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            //conn.setRequestProperty("accept", "*/*");
            //conn.setRequestProperty("connection", "Keep-Alive");
            //conn.setRequestProperty("Authorization","Basic ZW1fc291cmNlOlBAc3N3MHJkU2t5bmo");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Connection","close");
            //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(JSONObject.toJSONString(myData));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }









}
