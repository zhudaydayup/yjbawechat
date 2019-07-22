package yjbd.yjbawechat.Controller.ma;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yjbd.yjbawechat.Service.ma.CreatePdfService;
import yjbd.yjbawechat.Service.ma.CreatePdfService1;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = "/CreatePdf")
public class CreatePdfController {

    @Autowired
    CreatePdfService createPdfService;

    @Autowired
    CreatePdfService1 createPdfService1;

    /**
     * 生成现场处理措施处理决定书
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "/CreatePdf")
    @ResponseBody
    public void CreatePdf(HttpServletRequest request) throws IOException {
        String RECORD_ID=request.getParameter("RECORD_ID");
        createPdfService.createPDF(RECORD_ID);
    }

    /**
     * 生成整改意见复查文书表格
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "/CreatePdf1")
    @ResponseBody
    public void CreatePdf1(HttpServletRequest request) throws IOException {
        String RECORD_ID=request.getParameter("RECORD_ID");
        createPdfService1.createPDF1(RECORD_ID);
    }


    /**
     * 生成专家现场处理措施处理决定书
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "/CreateExPdf")
    @ResponseBody
    public void CreateExPdf(HttpServletRequest request) throws IOException {
        String RECORD_ID=request.getParameter("RECORD_ID");
        createPdfService.CreateExPdf(RECORD_ID);
    }


}
