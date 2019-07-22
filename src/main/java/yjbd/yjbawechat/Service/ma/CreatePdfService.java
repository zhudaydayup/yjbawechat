package yjbd.yjbawechat.Service.ma;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.ma.PunishMeasureDao;
import yjbd.yjbawechat.Service.ZjCheckService;
import yjbd.yjbawechat.Util.MsgUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreatePdfService {
    @Autowired
    PunishMeasureDao punishMeasureDao;
    static final Logger logger = LoggerFactory.getLogger(CreatePdfService.class);

    /**
     * 生成执法现场处理措施处理决定书
     * @param RECORD_ID
     * @throws IOException
     */
    public String createPDF(String RECORD_ID) throws IOException {
        Document document = new Document(PageSize.A4);
        String pdfPath="D:/zfbdzl/pdf/xianchangchuli/";
        String qrCodePath="D:/zfbdzl/qrcode/xianchangchuli/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=xianchangchuli/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹
        //设置日期格式
        List<Map> List=punishMeasureDao.getPdfInfo(RECORD_ID);
        //从表格中拉取证件号信息
        List<Map>mapList=punishMeasureDao.searchCareId(RECORD_ID);
        String UPID="";//自增id
        String AREA="";//检查单位所属辖区
        String CARD_NUMBER1="";//证件号1
        String CARD_NUMBER2 ="";//证件号2
        String CHECKE_UNIT="";
        String CHECKED_START_TIME="";
        String CHECKE_DETAIL="";
        String PROCESS_DECISION="";
        String SIGNATURE_PATH="";
        String SIGNATURE1_PATH="";
        String SIGNATURE2_PATH="";
        String SIGNATURE3_PATH="";
        String RECORD_TIME="";
        String ZF_ON_SCENE_AREA="";//所属地区
        String ZF_ON_SCENE_RECORD="";//文书编号
        if(List.get(0).get("ZF_ON_SCENE_AREA")!=null)  ZF_ON_SCENE_AREA = List.get(0).get("ZF_ON_SCENE_AREA").toString ();
        if(List.get(0).get("ZF_ON_SCENE_RECORD")!=null)  ZF_ON_SCENE_RECORD = List.get(0).get("ZF_ON_SCENE_RECORD").toString ();
        if(List.get(0).get("RECORD_ID")!=null)  UPID = List.get(0).get("RECORD_ID").toString ();
        if(mapList.get(0).get("AREA")!=null)  AREA = mapList.get(0).get("AREA").toString ();
        if(mapList.get(0).get("CARD_NUMBER1")!=null)  CARD_NUMBER1 = mapList.get(0).get("CARD_NUMBER1").toString ();
        if(mapList.get(0).get("CARD_NUMBER2")!=null)  CARD_NUMBER2 = mapList.get(0).get("CARD_NUMBER2").toString ();
        if(List.get(0).get("CHECKE_UNIT")!=null) CHECKE_UNIT = List.get(0).get("CHECKE_UNIT").toString ();
        if(List.get(0).get("CHECKED_START_TIME")!=null)  CHECKED_START_TIME = List.get(0).get("CHECKED_START_TIME").toString ();
        if(List.get(0).get("CHECKE_DETAIL")!=null)  CHECKE_DETAIL = List.get(0).get("CHECKE_DETAIL").toString ();
        if(List.get(0).get("PROCESS_DECISION")!=null)  PROCESS_DECISION = List.get(0).get("PROCESS_DECISION").toString ();
        if(List.get(0).get("SIGNATURE_PATH")!=null)  SIGNATURE_PATH = List.get(0).get("SIGNATURE_PATH").toString ();
        if(List.get(0).get("SIGNATURE1_PATH")!=null)  SIGNATURE1_PATH = List.get(0).get("SIGNATURE1_PATH").toString ();
        if(List.get(0).get("SIGNATURE2_PATH")!=null)  SIGNATURE2_PATH = List.get(0).get("SIGNATURE2_PATH").toString ();
        if(List.get(0).get("SIGNATURE3_PATH")!=null)  SIGNATURE3_PATH = List.get(0).get("SIGNATURE3_PATH").toString ();
        if(List.get(0).get("RECORD_TIME")!=null)  RECORD_TIME = List.get(0).get("RECORD_TIME").toString ();
        String[] SIGNATURE_PATH_ARRAY= SIGNATURE_PATH.split("\\|");
        String[] SIGNATURE1_PATH_ARRAY= SIGNATURE1_PATH.split("\\|");
        String[] SIGNATURE2_PATH_ARRAY= SIGNATURE2_PATH.split("\\|");
        String[] SIGNATURE3_PATH_ARRAY= SIGNATURE3_PATH.split("\\|");
        String[] CHECKE_DETAIL_ARRAY1=CHECKE_DETAIL.split("\\|");

        try {
            File file1 =new File(pdfPath+date1);
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            File file2 =new File(qrCodePath+date1);
            if(!file2 .exists()  && !file2 .isDirectory()){
                file2 .mkdir();
            }
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(pdfPath+pdfName));
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            //加入document：
            Font titleFont = new Font(bfChinese, 20, Font.BOLD,BaseColor.BLACK);
            Font para = new Font(bfChinese, 12, Font.NORMAL,BaseColor.BLACK);
            Font lineFont=new Font(bfChinese,2, Font.NORMAL,BaseColor.BLACK);
            Paragraph paragraph;
            float leading=20f;
            //补全下划线至行尾
            LineSeparator lineSeparator=new LineSeparator();
            lineSeparator.setOffset(-1f);
            lineSeparator.setLineWidth(0.1F);
            Chunk chunk;
            document.addTitle("example of PDF");
            document.open();
//            //主标题：安全生产行政执法文书现场检查记录
//            Paragraph title=new Paragraph("安全生产行政执法文书",titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            //直线
//            Paragraph line = new Paragraph("",lineFont);
//            LineSeparator lineSeparator=new LineSeparator();
//            lineSeparator.setLineWidth(0.5F);
//            line.add(new Chunk(lineSeparator));
//            line.add("");
//            document.add(line);
//
//            //直线
//            Paragraph line2 = new Paragraph("",lineFont);
//            LineSeparator lineSeparator2=new LineSeparator();
//            lineSeparator2.setLineWidth(0.5F);
//            line2.add(new Chunk(lineSeparator2));
//            line2.add("");
//            document.add(line2);


            //副标题:现场检查记录
            Paragraph subTitle=new Paragraph("现场处理措施决定书",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);


            //应急检记
            Paragraph subTitle2=new Paragraph("（"+ZF_ON_SCENE_AREA+" ）应急现决[2019]（"+ZF_ON_SCENE_RECORD+"）号",para);
            subTitle2.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle2);
            //设置行距，该处会影响后面所有的chunk的行距
            paragraph=new Paragraph("",para);
            paragraph.setLeading(25f);
            document.add(paragraph);

            //被检查单位
            chunk = new Chunk("    "+CHECKE_UNIT+"："+"",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
//            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);


            String []time=CHECKED_START_TIME.split("\\-");
            //时间
            chunk = new Chunk("         本机关于",para);
            document.add(chunk);
            chunk = new Chunk(time[0],para);
            document.add(chunk);
            chunk = new Chunk("年",para);
            document.add(chunk);
            chunk = new Chunk(time[1],para);
            document.add(chunk);
            chunk = new Chunk("月",para);
            document.add(chunk);
            chunk = new Chunk(time[2],para);
            document.add(chunk);
            chunk = new Chunk("日",para);
            document.add(chunk);
            chunk = new Chunk("现场检查时,发现你单位有下列违法违规行为和事故隐患: ",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);


            //违法违规行为
//            for(int i=1;i<2;i++){
//                Chunk checkUnit3 = new Chunk(""+i+""+CHECKE_DETAIL,para);
//                checkUnit3.setUnderline(0.1f, -1f);
//                document.add(checkUnit3);
//                document.add(Chunk.NEWLINE);
//            }

            for(int i=0;i<CHECKE_DETAIL_ARRAY1.length;i++){
                Chunk problem = new Chunk ( "         "+(i+1)+"." ,para);
                document.add(problem);
                chunk = new Chunk ( CHECKE_DETAIL_ARRAY1[i],para );
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }


          /*  //法律条文
            Chunk chunk3 = new Chunk("         现场处理决定：",para);
            document.add(chunk3);
            document.add(Chunk.NEWLINE);*/


            //处理决定
            for(int i=1;i<2;i++){
                Chunk checkUnit4 = new Chunk("         "+PROCESS_DECISION,para);
               // checkUnit4.setUnderline(0.1f, -1f);
                document.add(checkUnit4);
                //document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }


            //法律条文

            Chunk fy = new Chunk("         （此栏不够，可另附页）",para);
            document.add(fy);
            document.add(Chunk.NEWLINE);

            Chunk chunk5 = new Chunk("         如果不服本决定，可以依法在60日内向南京市人民政府或者江苏省应急管理厅申请行政复议，或者在6个月内依法向南京铁路运输法院提起行政诉讼，但本决定不停止执行，法律另有规定的除外。",para);
            document.add(chunk5);
            document.add(Chunk.NEWLINE);


            PdfContentByte sign = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign.beginText();
            sign.setFontAndSize(bf, 12);
            sign.showTextAligned(PdfContentByte.ALIGN_LEFT,  "安全生产监管行政执法人员(签名):", 25f, 320f, 0);
            sign.endText();
            //安全生产监管行政执法人员(签名)图片
            for(int i=0;i<SIGNATURE_PATH_ARRAY.length;i++){
                Image image = Image.getInstance(url+SIGNATURE_PATH_ARRAY[i]);
                image.setAbsolutePosition(230f, 300f-40*i);
                image.scaleAbsolute(100, 50);
                document.add(image);

                PdfContentByte cb4 = writer.getDirectContent();
                BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
                cb4.beginText();
                cb4.setFontAndSize(bf2, 12);
                cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER1, 350f, 320-40*i, 0);
                cb4.endText();
            }
            if(!CARD_NUMBER2.equals("")){
                for(int i=0;i<SIGNATURE1_PATH_ARRAY.length;i++){
                    Image image = Image.getInstance(url+SIGNATURE1_PATH_ARRAY[i]);
                    image.setAbsolutePosition(230f, 250f-40*i);
                    image.scaleAbsolute(100, 50);
                    document.add(image);

                    PdfContentByte cb4 = writer.getDirectContent();
                    BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
                    cb4.beginText();
                    cb4.setFontAndSize(bf2, 12);
                    cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER2, 350f, 270-40*i, 0);
                    cb4.endText();
                }
            }



            //被检查单位负责人
            PdfContentByte sign2 = writer.getDirectContent();
            BaseFont bf6= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign2.beginText();
            sign2.setFontAndSize(bf6, 12);
            sign2.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被检查单位负责人（签名）：", 25f, 220f, 0);
            sign2.endText();

            for(int i=0;i<SIGNATURE2_PATH_ARRAY.length;i++) {
                Image image = Image.getInstance ( url + SIGNATURE2_PATH_ARRAY[i] );
                image.setAbsolutePosition ( 230f, 200f - 40 * i );
                image.scaleAbsolute ( 100, 50 );
                document.add ( image );
            }

            if (!SIGNATURE3_PATH.equals("")){
                //见证人
                PdfContentByte sign3 = writer.getDirectContent();
                BaseFont bf11= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
                sign3.beginText();
                sign3.setFontAndSize(bf11, 12);
                sign3.showTextAligned(PdfContentByte.ALIGN_LEFT,  "见证人（签名）：", 25f, 160f, 0);
                sign3.endText();
                for(int i=0;i<SIGNATURE3_PATH_ARRAY.length;i++) {
                    Image image = Image.getInstance ( url + SIGNATURE3_PATH_ARRAY[i] );
                    image.setAbsolutePosition ( 230f, 140f-40 * i );
                    image.scaleAbsolute ( 100, 50 );
                    document.add ( image );
                }
            }


            //印章
            PdfContentByte cb = writer.getDirectContent();
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "", 400f, 70f, 0);
            cb.endText();

            if(AREA.equals("宁")){
                Image image = Image.getInstance ( "C:/zfbdmoban/images/zz.png" );
                image.setAbsolutePosition ( 400f, 70f );
                image.scaleAbsolute ( 140, 140 );
                document.add ( image );
            }


            String inscribe="";//'宁','江北', '玄', '秦','建','鼓','栖','经开','雨','江','浦','六','溧','高'
            if (AREA.equals("宁")) {
                inscribe="";
            } else if (AREA.equals("江北")) {
                inscribe="南京市江北新区应急管理局";
            } else if (AREA.equals("玄")) {
                inscribe="南京市玄武区应急管理局";
            }else if (AREA.equals("秦")) {
                inscribe="南京市秦淮区应急管理局";
            }else if (AREA.equals("建")) {
                inscribe="南京市建邺区应急管理局";
            }else if (AREA.equals("鼓")) {
                inscribe="南京市鼓楼区应急管理局";
            }else if (AREA.equals("栖")) {
                inscribe="南京市栖霞区应急管理局";
            }else if (AREA.equals("经开")) {
                inscribe="南京市经济开发区应急管理局";
            }else if (AREA.equals("雨")) {
                inscribe="南京市雨花台区应急管理局";
            }else if (AREA.equals("江")) {
                inscribe="南京市江宁区应急管理局";
            }else if (AREA.equals("浦")) {
                inscribe="南京市浦口区应急管理局";
            }else if (AREA.equals("六")) {
                inscribe="南京市六合区应急管理局";
            }else if (AREA.equals("溧")) {
                inscribe="南京市溧水区应急管理局";
            }else if (AREA.equals("高")) {
                inscribe="南京市高淳区应急管理局";
            }
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  inscribe, 400f, 110f, 0);
            cb.endText();
            //创建时间
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  RECORD_TIME, 450f, 90f, 0);
            cb.endText();


            //预览二维码
            String content=pdfAbsolutePath+pdfName;
            String qrCodeName=date1+"/"+nowTime+ random+".png";
            try{
                Map<EncodeHintType, Object> hints = new HashMap<>();
                //编码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                //边框距
                hints.put(EncodeHintType.MARGIN, 0);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 197, 197, hints);
                Path file=new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);

            }catch (Exception e){
                System.out.print(e);
                logger.info ( e.toString () );
                return MsgUtil.errorMsg(e.toString());
            }
            punishMeasureDao.updatePdfAndQrcode(pdfName,qrCodeName,RECORD_ID);


        } catch (DocumentException e) {
            e.printStackTrace();
            logger.info ( e.getLocalizedMessage () );
            return MsgUtil.errorMsg(e.toString());
        } finally {
            document.close();
        }
        return MsgUtil.successMsg();
    }

    /**
     * 生成专家现场处理措施处理决定书
     * @param RECORD_ID
     * @throws IOException
     */
    public String CreateExPdf(String RECORD_ID) throws IOException {
        Document document = new Document(PageSize.A4);
        String pdfPath="D:/zfbdzl/pdf/expert_xianchangchuli/";
        String qrCodePath="D:/zfbdzl/qrcode/expert_xianchangchuli/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=expert_xianchangchuli/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹

        //从表格中拉取检查单位所属辖区信息
     /*   List<Map>mapList=punishMeasureDao.searchExCareId(RECORD_ID);
        String AREA="";//检查单位所属辖区
        if(mapList.get(0).get("AREA")!=null)  AREA = mapList.get(0).get("AREA").toString ();
*/

        List<Map> List=punishMeasureDao.getPunishPdfInfo(RECORD_ID);
        String UPID="";//自增id
        String CHECKED_UNIT="";
        String CHECKED_START_TIME="";
        String CHECKE_DETAIL="";
        String PROCESS_DECISION="";
        String SIGNATURE_PATH="";
        String SIGNATURE1_PATH="";
        String SIGNATURE2_PATH="";
        String RECORD_TIME="";
        if(List.get(0).get("UPID")!=null)  UPID = List.get(0).get("UPID").toString ();
        if(List.get(0).get("CHECKED_UNIT")!=null) CHECKED_UNIT = List.get(0).get("CHECKED_UNIT").toString ();
        if(List.get(0).get("CHECKED_START_TIME")!=null)  CHECKED_START_TIME = List.get(0).get("CHECKED_START_TIME").toString ();
        if(List.get(0).get("CHECKE_DETAIL")!=null)  CHECKE_DETAIL = List.get(0).get("CHECKE_DETAIL").toString ();
        if(List.get(0).get("PROCESS_DECISION")!=null)  PROCESS_DECISION = List.get(0).get("PROCESS_DECISION").toString ();
        if(List.get(0).get("SIGNATURE_PATH")!=null)  SIGNATURE_PATH = List.get(0).get("SIGNATURE_PATH").toString ();
        if(List.get(0).get("SIGNATURE1_PATH")!=null)  SIGNATURE1_PATH = List.get(0).get("SIGNATURE1_PATH").toString ();
        if(List.get(0).get("SIGNATURE2_PATH")!=null)  SIGNATURE2_PATH = List.get(0).get("SIGNATURE2_PATH").toString ();
        if(List.get(0).get("RECORD_TIME")!=null)  RECORD_TIME = List.get(0).get("RECORD_TIME").toString ();
        String[] SIGNATURE_PATH_ARRAY= SIGNATURE_PATH.split("\\|");
        String[] SIGNATURE1_PATH_ARRAY= SIGNATURE1_PATH.split("\\|");
        String[] SIGNATURE2_PATH_ARRAY= SIGNATURE2_PATH.split("\\|");
        String[] CHECKE_DETAIL_ARRAY1=CHECKE_DETAIL.split("\\|");

        try {
            File file1 =new File(pdfPath+date1);
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            File file2 =new File(qrCodePath+date1);
            if(!file2 .exists()  && !file2 .isDirectory()){
                file2 .mkdir();
            }
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(pdfPath+pdfName));
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            //加入document：
            Font titleFont = new Font(bfChinese, 20, Font.BOLD,BaseColor.BLACK);
            Font para = new Font(bfChinese, 12, Font.NORMAL,BaseColor.BLACK);
            Font lineFont=new Font(bfChinese,2, Font.NORMAL,BaseColor.BLACK);
            Paragraph paragraph;
            float leading=20f;
            //补全下划线至行尾
            LineSeparator lineSeparator=new LineSeparator();
            lineSeparator.setOffset(-1f);
            lineSeparator.setLineWidth(0.1F);
            Chunk chunk;
            document.addTitle("example of PDF");
            document.open();
//            //主标题：安全生产行政执法文书现场检查记录
//            Paragraph title=new Paragraph("安全生产行政执法文书",titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            //直线
//            Paragraph line = new Paragraph("",lineFont);
//            LineSeparator lineSeparator=new LineSeparator();
//            lineSeparator.setLineWidth(0.5F);
//            line.add(new Chunk(lineSeparator));
//            line.add("");
//            document.add(line);
//
//            //直线
//            Paragraph line2 = new Paragraph("",lineFont);
//            LineSeparator lineSeparator2=new LineSeparator();
//            lineSeparator2.setLineWidth(0.5F);
//            line2.add(new Chunk(lineSeparator2));
//            line2.add("");
//            document.add(line2);


            //副标题:现场检查记录
            Paragraph subTitle=new Paragraph("现场处理措施决定书",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);


            //应急检记
            //Paragraph subTitle2=new Paragraph("（"+AREA+" ）应急现决[2019]（"+UPID+"）号",para);
            Paragraph subTitle2=new Paragraph("（宁）应急现决[2019]（"+UPID+"）号",para);
            subTitle2.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle2);
            //设置行距，该处会影响后面所有的chunk的行距
            paragraph=new Paragraph("",para);
            paragraph.setLeading(25f);
            document.add(paragraph);

            //被检查单位
            chunk = new Chunk("    "+CHECKED_UNIT+"："+"",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
//            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);


            String []time=CHECKED_START_TIME.split("-");
            //时间
            chunk = new Chunk("         本机关于",para);
            document.add(chunk);
            chunk = new Chunk(time[0],para);
            document.add(chunk);
            chunk = new Chunk("年",para);
            document.add(chunk);
            chunk = new Chunk(time[1],para);
            document.add(chunk);
            chunk = new Chunk("月",para);
            document.add(chunk);



            chunk = new Chunk(time[2].split(" ")[0],para);
            document.add(chunk);
            chunk = new Chunk("日",para);
            document.add(chunk);
            chunk = new Chunk("现场检查时,发现你单位有下列违法违规行为和事故隐患: ",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);


            //违法违规行为
//            for(int i=1;i<2;i++){
//                Chunk checkUnit3 = new Chunk(""+i+""+CHECKE_DETAIL,para);
//                checkUnit3.setUnderline(0.1f, -1f);
//                document.add(checkUnit3);
//                document.add(Chunk.NEWLINE);
//            }

            for(int i=0;i<CHECKE_DETAIL_ARRAY1.length;i++){
                Chunk problem = new Chunk ( "         "+(i+1)+"." ,para);
                document.add(problem);
                chunk = new Chunk ( CHECKE_DETAIL_ARRAY1[i],para );
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }


          /*  //法律条文
            Chunk chunk3 = new Chunk("         现场处理决定：",para);
            document.add(chunk3);
            document.add(Chunk.NEWLINE);*/


            //处理决定
            for(int i=1;i<2;i++){
                Chunk checkUnit4 = new Chunk("         "+PROCESS_DECISION,para);
                // checkUnit4.setUnderline(0.1f, -1f);
                document.add(checkUnit4);
                //document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }


            //法律条文

            Chunk fy = new Chunk("         （此栏不够，可另附页）",para);
            document.add(fy);
            document.add(Chunk.NEWLINE);

            Chunk chunk5 = new Chunk("         如果不服本决定，可以依法在60日内向南京市人民政府或者江苏省应急管理厅申请行政复议，或者在6个月内依法向南京铁路运输法院提起行政诉讼，但本决定不停止执行，法律另有规定的除外。",para);
            document.add(chunk5);
            document.add(Chunk.NEWLINE);


            PdfContentByte sign = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign.beginText();
            sign.setFontAndSize(bf, 12);
            sign.showTextAligned(PdfContentByte.ALIGN_LEFT,  "检查人(签名):", 25f, 320f, 0);
            sign.endText();
            //检查人(签名)图片
            for(int i=0;i<SIGNATURE_PATH_ARRAY.length;i++){
                Image image = Image.getInstance(url+SIGNATURE_PATH_ARRAY[i]);
                image.setAbsolutePosition(230f, 300f-40*i);
                image.scaleAbsolute(100, 50);
                document.add(image);
            }


            //被检查单位负责人
            PdfContentByte sign2 = writer.getDirectContent();
            BaseFont bf6= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign2.beginText();
            sign2.setFontAndSize(bf6, 12);
            sign2.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被检查单位负责人（签名）：", 25f, 220f, 0);
            sign2.endText();

            for(int i=0;i<SIGNATURE1_PATH_ARRAY.length;i++) {
                Image image = Image.getInstance ( url + SIGNATURE1_PATH_ARRAY[i] );
                image.setAbsolutePosition ( 230f, 200f - 40 * i );
                image.scaleAbsolute ( 100, 50 );
                document.add ( image );
            }

            if (!SIGNATURE2_PATH.equals("")){
                //见证人
                PdfContentByte sign3 = writer.getDirectContent();
                BaseFont bf11= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
                sign3.beginText();
                sign3.setFontAndSize(bf11, 12);
                sign3.showTextAligned(PdfContentByte.ALIGN_LEFT,  "见证人（签名）：", 25f, 160f, 0);
                sign3.endText();
                for(int i=0;i<SIGNATURE2_PATH_ARRAY.length;i++) {
                    Image image = Image.getInstance ( url + SIGNATURE2_PATH_ARRAY[i] );
                    image.setAbsolutePosition ( 230f, 140f-40 * i );
                    image.scaleAbsolute ( 100, 50 );
                    document.add ( image );
                }
            }


            //印章
            PdfContentByte cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "", 400f, 70f, 0);
            cb.endText();

          /*  if(AREA.equals("宁")){
                Image image = Image.getInstance ( "C:/zfbdmoban/images/zz.png" );
                image.setAbsolutePosition ( 400f, 70f );
                image.scaleAbsolute ( 140, 140 );
                document.add ( image );
            }*/


            String inscribe="南京市应急管理局";//'宁','江北', '玄', '秦','建','鼓','栖','经开','雨','江','浦','六','溧','高'
            /*if (AREA.equals("宁")) {
                inscribe="";
            } else if (AREA.equals("江北")) {
                inscribe="南京市江北新区应急管理局";
            } else if (AREA.equals("玄")) {
                inscribe="南京市玄武区应急管理局";
            }else if (AREA.equals("秦")) {
                inscribe="南京市秦淮区应急管理局";
            }else if (AREA.equals("建")) {
                inscribe="南京市建邺区应急管理局";
            }else if (AREA.equals("鼓")) {
                inscribe="南京市鼓楼区应急管理局";
            }else if (AREA.equals("栖")) {
                inscribe="南京市栖霞区应急管理局";
            }else if (AREA.equals("经开")) {
                inscribe="南京市经济开发区应急管理局";
            }else if (AREA.equals("雨")) {
                inscribe="南京市雨花台区应急管理局";
            }else if (AREA.equals("江")) {
                inscribe="南京市江宁区应急管理局";
            }else if (AREA.equals("浦")) {
                inscribe="南京市浦口区应急管理局";
            }else if (AREA.equals("六")) {
                inscribe="南京市六合区应急管理局";
            }else if (AREA.equals("溧")) {
                inscribe="南京市溧水区应急管理局";
            }else if (AREA.equals("高")) {
                inscribe="南京市高淳区应急管理局";
            }*/
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  inscribe, 400f, 110f, 0);
            cb.endText();
            //创建时间
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  RECORD_TIME, 450f, 90f, 0);
            cb.endText();


            //预览二维码
            String content=pdfAbsolutePath+pdfName;
            String qrCodeName=date1+"/"+nowTime+ random+".png";
            try{
                Map<EncodeHintType, Object> hints = new HashMap<>();
                //编码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                //边框距
                hints.put(EncodeHintType.MARGIN, 0);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 197, 197, hints);
                Path file=new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);

            }catch (Exception e){
                System.out.print(e);
                logger.info ( e.toString () );
                return MsgUtil.errorMsg(e.toString());
            }
            punishMeasureDao.updateExPdfAndQrcode(pdfName,qrCodeName,RECORD_ID);


        } catch (DocumentException e) {
            e.printStackTrace();
            logger.info ( e.getLocalizedMessage () );
            return MsgUtil.errorMsg(e.toString());
        } finally {
            document.close();
        }
        return MsgUtil.successMsg();
    }

}
