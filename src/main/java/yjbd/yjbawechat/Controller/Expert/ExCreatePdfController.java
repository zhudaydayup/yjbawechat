package yjbd.yjbawechat.Controller.Expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yjbd.yjbawechat.Service.expert.ExpertService;
import yjbd.yjbawechat.Util.CreatePdfUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/createExpertPdf")
public class ExCreatePdfController {
    @Autowired
    private ExpertService expertService;
    @RequestMapping("/createGeneralDangerPdf")
    @ResponseBody
    public Map<String,Object> createGeneralDangerPdf(HttpServletRequest request){
        String RecordId = request.getParameter("RecordId");
        List<Map> list = expertService.getExpertInfo(RecordId);
        String CHECK_STATE = "不合格";
        List<Map> list2 = expertService.getExpertRecordInfo(RecordId);
        String folderName = "expert_notice";
        String ID = list2.get(0).get("ID").toString();
        String CHECKED_UNIT = list.get(0).get("CHECKED_UNIT").toString();
        String CHECKED_START_TIME = list.get(0).get("CHECKED_START_TIME").toString();
        List<Map> checkItem = expertService.getExpertCheckInfo(RecordId,CHECK_STATE);
        String DEAL_VIEW = list2.get(0).get("DEAL_VIEW").toString();
        String EXPERT_SIGN = list2.get(0).get("EXPERT_SIGN").toString();
        String CHECKEDMAN_SIGN = list2.get(0).get("CHECKEDMAN_SIGN").toString();
        String path = CreatePdfUtil.createExpertPdf(folderName,ID,CHECKED_UNIT,CHECKED_START_TIME,checkItem,DEAL_VIEW,EXPERT_SIGN,CHECKEDMAN_SIGN);
        Map<String,Object> map = new HashMap<>(16);
        String[] arr = path.split("__");
        expertService.updateCheckPdf(arr[0],arr[1],RecordId);
        map.put("url",path);
        return map;
    }
}
