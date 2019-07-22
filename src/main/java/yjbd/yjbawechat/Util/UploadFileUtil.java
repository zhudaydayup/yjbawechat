package yjbd.yjbawechat.Util;

import org.springframework.web.multipart.MultipartFile;
import yjbd.yjbawechat.VO.ResultVO;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Demo class
 * @descript path为文件夹名
 * @author 努力吧小朱
 * @date 2019/4/28
 */
public class UploadFileUtil {
    public static ResultVO uploadFile(MultipartFile file,  String folderName){

        //判断文件是否存在
        if(file.isEmpty()){
            return ResultVO.fail("文件为空",null);
        }
        String fileName = file.getOriginalFilename();
        //文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
        //根据时间戳创建新的文件名
        fileName = System.currentTimeMillis() + new Random().nextInt(1000) + suffixName;
        //创建文件路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String createTime = sdf.format(new Date());
        String filePath = folderName + "/" + createTime;
        //获取文件夹路径
        try {
            File folder = new File(filePath);
            //判断文件夹是否存在，如不存在，创建
            if(!folder.exists()){
                folder.mkdirs();
            }

            file.transferTo(new File(folder,fileName));
        }
        catch (IOException e){
            e.printStackTrace();
            return ResultVO.fail("上传失败",e.getMessage());
        }
        return ResultVO.success("上传成功","" +
                "/"+createTime+"/"+fileName);
    }
}
