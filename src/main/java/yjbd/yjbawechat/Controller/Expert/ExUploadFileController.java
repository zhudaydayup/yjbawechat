package yjbd.yjbawechat.Controller.Expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import yjbd.yjbawechat.Enum.PathEnum;
import yjbd.yjbawechat.Service.expert.ExpertService;
import yjbd.yjbawechat.Util.UploadFileUtil;
import yjbd.yjbawechat.VO.ResultVO;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/expertUpload")
@Controller
public class ExUploadFileController {
    @Autowired
    private ExpertService expertService;
    //上传专家签名
    @PostMapping("/uploadExpertSign")
    @ResponseBody
    public Map<String,Object> uploadCheckSign(@RequestParam("file")MultipartFile file,
                                              @RequestParam("RecordId")String RecordId){
        String path = PathEnum.IMAGES_PATH.getPath()+"/"+"expertSign";
        ResultVO<String> resultVO = UploadFileUtil.uploadFile(file,path);
        ResultVO result = expertService.updateExpertSign(resultVO.getData(),RecordId);
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
                                                @RequestParam("RecordId")String RecordId){
        String path = PathEnum.IMAGES_PATH.getPath()+"/"+"exCheckedSign";
        ResultVO<String> resultVO = UploadFileUtil.uploadFile(file,path);
        ResultVO result = expertService.updateCheckedSign(resultVO.getData(),RecordId);
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


