package yjbd.yjbawechat.Controller.Expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yjbd.yjbawechat.Service.expert.ExpertService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/expertData")
public class ExpertController {

    @Autowired
    private ExpertService expertService;
    @RequestMapping("/getExpertData")
    @ResponseBody
    public Map<String,Object> getExpertData(HttpServletRequest request){
        String RECORD_ID = request.getParameter("RecordId");
        List<Map> list = expertService.getExpertInfo(RECORD_ID);
        Map<String,Object> map = new HashMap<>(16);
        map.put("CHECKED_UNIT",list.get(0).get("CHECKED_UNIT").toString());
        return map;
    }
}
