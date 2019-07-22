package yjbd.yjbawechat.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
//发布
@RequestMapping("/upLoderPic")
//本地调试
//@RequestMapping("/signupwechat/upLoderPic")

public class upLoderPic {

//    @ResponseBody
//    @RequestMapping("/uploadImg")
//    public String uploadPicture(@RequestParam(value="file",required=false)MultipartFile file, HttpServletRequest request, HttpServletResponse response){
//
//      //  public String  uploadPicture( HttpServletRequest request, HttpServletResponse response){
//        //ResponseResult result = new ResponseResult();
//        Map<String, String> myData = new HashMap<>();
//        String result="";
//
////      Map<String, Object> map = new HashMap<String, Object>();
//        File targetFile=null;
//        String url="";//返回存储路径
//        int code=1;
//        System.out.println(file);
//        String fileName=file.getOriginalFilename();//获取文件名加后缀
//        if(fileName!=null&&fileName!=""){
//            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
//
//            String path = "C:/signUpImages/" ; //文件存储位置
//
//            // String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
//            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
//            fileName=new Date().getTime()+new Random().nextInt(1000)+fileF; //新的文件名
//
//            //先判断文件是否存在
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            String fileAdd = sdf.format(new Date());
//            //获取文件夹路径
//            File file1 =new File(path+"/"+fileAdd);
//            //如果文件夹不存在则创建
//            if(!file1 .exists()  && !file1 .isDirectory()){
//                file1 .mkdir();
//            }
//            //将图片存入文件夹
//            targetFile = new File(file1, fileName);
//            try {
//                //将上传的文件写到服务器上指定的文件。
//                file.transferTo(targetFile);
//                url=fileAdd+"/"+fileName;
//                myData.put("url", url);
//                myData.put("error", "0");
//                result= JSONObject.toJSONString(myData);
//            } catch (Exception e) {
//                e.printStackTrace();
//                myData.put("failinfo", e.toString());
//                myData.put("error", "1");
//                result= JSONObject.toJSONString(myData);
//                // result.setMessage("系统异常，图片上传失败");
//            }
//        }
//        return result;
//    }

    //自定义传名图片页面

    @RequestMapping("/imgtest")
    public String imgtest(){return "/ImgTeat/imgtest"; }

    @RequestMapping("/uploadtest")
    public String uploadtest(){return "/ImgTeat/uploadtest"; }



    @RequestMapping("/uploadImg1")
    @ResponseBody
    public String uploadImg(@RequestParam(value="file",required=false)MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        System.out.println("123456");
        //String filename=request.getParameter("file");
        //System.out.println(filename);
        //public String  uploadPicture( HttpServletRequest request, HttpServletResponse response){
        //ResponseResult result = new ResponseResult();
        Map<String, String> myData = new HashMap<>();
        String result="";

//      Map<String, Object> map = new HashMap<String, Object>();
        File targetFile=null;
        String url="";//返回存储路径
        int code=1;
        System.out.println(file);
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        if(fileName!=null&&fileName!=""){
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径

            String path = "D:/zfbdzl/images/" ; //文件存储位置

            // String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+new Random().nextInt(1000)+fileF; //新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url=fileAdd+"/"+fileName;
                myData.put("url", url);
                myData.put("error", "0");
                result= '['+ JSONObject.toJSONString(myData)+']';
            } catch (Exception e) {
                e.printStackTrace();
                myData.put("failinfo", e.toString());
                myData.put("error", "1");
                result= '['+ JSONObject.toJSONString(myData)+']';
                // result.setMessage("系统异常，图片上传失败");
            }
        }
        return result;
    }

    //视屏上传
    @RequestMapping("/uploadVideo")
    @ResponseBody
    public String uploadVideo(@RequestParam(value="file",required=false)MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        System.out.println("123456");
        Map<String, String> myData = new HashMap<>();
        String result="";
//      Map<String, Object> map = new HashMap<String, Object>();
        File targetFile=null;
        String url="";//返回存储路径
        int code=1;
        System.out.println(file);
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        if(fileName!=null&&fileName!=""){
            String path = "D:/zfbdzl/video/" ; //文件存储位置
            // String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+new Random().nextInt(1000)+fileF; //新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url=fileAdd+"/"+fileName;
                myData.put("url", url);
                myData.put("error", "0");
                result= '['+ JSONObject.toJSONString(myData)+']';
            } catch (Exception e) {
                e.printStackTrace();
                myData.put("failinfo", e.toString());
                myData.put("error", "1");
                result= '['+ JSONObject.toJSONString(myData)+']';
            }
        }
        return result;
    }

}
