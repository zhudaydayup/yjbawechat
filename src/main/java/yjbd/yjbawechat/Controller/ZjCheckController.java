package yjbd.yjbawechat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Model.EnforceModel;
import yjbd.yjbawechat.Service.ZjCheckService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/20 19:16
 */
@RestController
@RequestMapping("/ZjCheck")
public class ZjCheckController {
    @Autowired
    private ZjCheckService zjCheckService;




    /**
     * 提交自检企业信息
     */
    @RequestMapping("/PutZjCheckView")
    @ResponseBody
    public String PutZjCheckView(HttpServletRequest request, EnforceModel enforceModel){
        String responseText="";

        responseText = zjCheckService.PutZjCheckView(enforceModel);
        return responseText;
    }

    @RequestMapping(value = "/GetZjRecord")
    public String GetZjRecord(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = zjCheckService.GetZjRecord(RecordId);
        return responseText;
    }


    @RequestMapping(value = "/GetZjCheckMsgs")
    public String GetZjCheckMsgs(HttpServletRequest request) {
        String responseText="";
        String RecordId=request.getParameter("RecordId");
        responseText = zjCheckService.GetZjCheckMsgs(RecordId);
        return responseText;
    }


}
