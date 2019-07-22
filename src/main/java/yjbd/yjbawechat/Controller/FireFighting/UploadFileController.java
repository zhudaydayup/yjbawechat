package yjbd.yjbawechat.Controller.FireFighting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import yjbd.yjbawechat.Enum.PathEnum;
import yjbd.yjbawechat.Service.FireFighting.FireIndexService;
import yjbd.yjbawechat.Util.UploadFileUtil;
import yjbd.yjbawechat.VO.ResultVO;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/upload")
@Controller
public class UploadFileController {
    @Autowired
    private FireIndexService fireIndexService;
    //上传图片
    @PostMapping("/uploadPic")
    @ResponseBody
    public Map<String,Object> uploadPic(@RequestParam("file")MultipartFile file){
        String path = PathEnum.IMAGES_PATH.getPath()+"/"+"firefighting";
        ResultVO<String> resultVO = UploadFileUtil.uploadFile(file,path);
        Map<String,Object> map = new HashMap<>(16);
        map.put("msg",resultVO.getMsg());
        map.put("url","firefighting"+resultVO.getData());
        return map;
    }
    //上传视频
    @PostMapping("/uploadVideo")
    @ResponseBody
    public Map<String,Object> uploadVideo(@RequestParam("file")MultipartFile file){
        String path = PathEnum.VIDEO_PATH.getPath()+"/"+"firefighting";
        ResultVO<String> resultVO = UploadFileUtil.uploadFile(file,path);
        Map<String,Object> map = new HashMap<>(16);
        map.put("msg",resultVO.getMsg());
        map.put("url","../firefighting"+resultVO.getData());
        return map;
    }
    //上传检察人签名
    @PostMapping("/uploadCheckSign")
    @ResponseBody
    public Map<String,Object> uploadCheckSign(@RequestParam("file")MultipartFile file,
                                              @RequestParam("CREATE_TIME")String CREATE_TIME){
        String path = PathEnum.IMAGES_PATH.getPath()+"/"+"sign";
        ResultVO<String> resultVO = UploadFileUtil.uploadFile(file,path);
        ResultVO result = fireIndexService.updateCheckSign(resultVO.getData(),CREATE_TIME);
        Map<String,Object> map = new HashMap<>(16);
        if(result.getMsg()=="提交成功"){
            map.put("saveMsg",resultVO.getMsg());
        }
        else {
            map.put("saveMsg",result.getMsg());
        }
        return map;
    }
    //上传被检察单位负责人签名
    @PostMapping("/uploadCheckedSign")
    @ResponseBody
    public Map<String,Object> uploadCheckedSign(@RequestParam("file")MultipartFile file,
                                                @RequestParam("CREATE_TIME")String CREATE_TIME){
        String path = PathEnum.IMAGES_PATH.getPath()+"/"+"sign";
        ResultVO<String> resultVO = UploadFileUtil.uploadFile(file,path);
        ResultVO result = fireIndexService.updateCheckedSign(resultVO.getData(),CREATE_TIME);
        Map<String,Object> map = new HashMap<>(16);
        if(result.getMsg()=="提交成功"){
            map.put("saveMsg",resultVO.getMsg());
        }
        else {
            map.put("saveMsg",result.getMsg());
        }
        return map;
    }
}


