package yjbd.yjbawechat.Controller.FireFighting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import yjbd.yjbawechat.Service.FireFighting.FireIndexService;

import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author 努力的小朱
 * @date 2019/4/24
 */
@Controller
@RequestMapping("/FireFighting")
public class FireFightingPageController {
    @Autowired
    private FireIndexService fireIndexService;
    //添加检察人员页面
    @RequestMapping(value = "/index")
    public String getIndex(Map<String,Object> map){
        List<Map> checkedItem = fireIndexService.getAddItem();
        map.put("checkedItem",checkedItem);
        return "/FireFighting/checkIndex";
    }

    //检察人签名
    @RequestMapping(value = "/checkSignPage")
    public String getCheckSignPage(){
        return "/FireFighting/checkSignPage";
    }
    //被检查单位负责人签名
    @RequestMapping(value = "/checkedSignPage")
    public String getCheckedSignPage(){
        return "/FireFighting/checkedSignPage";
    }
    //预览检查记录、二维码指示
    @RequestMapping(value = "/previewIndex")
    public String getPreviewIndex(){
        return "/FireFighting/previewIndex";
    }
    //pdf预览
    @RequestMapping(value = "/pdfView")
    public String getPdfView(){

        return "/FireFighting/pdfView";
    }
    //二维码预览
    @RequestMapping(value = "/qrView")
    public String getQrView(){
        return "/FireFighting/qrView";
    }
}
