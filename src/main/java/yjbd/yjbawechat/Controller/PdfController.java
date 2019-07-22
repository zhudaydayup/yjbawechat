package yjbd.yjbawechat.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjbd.yjbawechat.Service.PdfService;
import yjbd.yjbawechat.Util.MsgUtil;
import org.apache.commons.io.IOUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * @Description: $
 * @Param: $
 * @return: $
 * @Author: your name
 * @date: $
 */
@RestController
@RequestMapping(value="/pdf")
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @RequestMapping(value = "/create_notice_pdf")
    public String createNoticePdf(HttpSession session, HttpServletRequest request) {
        String recordId = request.getParameter("RecordId");
        try {
            return pdfService.createNoticePdf(recordId);
        } catch (Exception e) {
            e.printStackTrace();
            return MsgUtil.errorMsg(e.toString());
        }
    }

    @RequestMapping(value = "/create_record_pdf")
    public String createRecordPdf(HttpSession session, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        try {
            //String id="12";
            return pdfService.createRecordPdf(RecordId);//String id=request.getParameter("RecordId");
        }catch (Exception e){
            return MsgUtil.errorMsg(e.toString());
        }
    }




    @RequestMapping(value = "/create_expert_record_pdf")
    public String createExpertRecordPdf(HttpSession session, HttpServletRequest request) {
        String RecordId=request.getParameter("RecordId");
        try {
            //String id="12";
            return pdfService.createExpertRecordPdf(RecordId);//String id=request.getParameter("RecordId");
        }catch (Exception e){
            return MsgUtil.errorMsg(e.toString());
        }
    }
    @RequestMapping(value = "/getPdf")
    private void getPdf2(HttpServletResponse response, HttpServletRequest request) {
        try {
            String url=request.getParameter("url");
            String path="D:/zfbdzl/pdf/"+url;
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
