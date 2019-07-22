package yjbd.yjbawechat.Service.FireFighting;

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
import yjbd.yjbawechat.Dao.FireFighting.ReexamineDao;
import yjbd.yjbawechat.Util.MsgUtil;
import yjbd.yjbawechat.Util.MsgUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Author: 随心的小新
 * @Date: 2019/5/28 16:24
 */
@Service
public class ReexamineService {
    @Autowired
    ReexamineDao reexamineDao;
    static final Logger logger = LoggerFactory.getLogger(ReexamineService.class);
    /**
     * 根据ID来查询数据
     */
    public List<Map> getReexamineDate(String RECORD_ID){
        return reexamineDao.getReexamineDate(RECORD_ID);
    }
    /**
     * 存入自己复查信息的表格
     * @param
     */
    public void setReexamineInfo(String RECORD_ID,String CHECKE_UNIT,String CHECKED_END_TIME,String CHECKE_OPINION){
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        String RECORD_TIME = now.get(Calendar.YEAR)+"年"+month+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
        reexamineDao.setReexamineInfo(RECORD_ID,CHECKE_UNIT,CHECKED_END_TIME,CHECKE_OPINION,RECORD_TIME);
    }
    public String updateExpertSign(String CHECKE_SIGNATURE, String RecordId) {
        String responseText="";
        try {
            int flag = reexamineDao.updateExpertSign(CHECKE_SIGNATURE, RecordId);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;
    }
    /**
     * 上传被执法单位签名
     */
    public String updateInspectedSign(String REPRESENR_SIGNATURE, String RecordId) {
        String responseText="";
        try {
            int flag = reexamineDao.updateInspectedSign(REPRESENR_SIGNATURE, RecordId);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            logger.info ( e.toString () );
        }
        return responseText;
    }
    /**
     * 最后预览PDF页面以及二维码图片
     */
    public String LookReexaminePdf(String recordId) {
        String responseText="";
        try {
            List<Map> list=reexamineDao.LookReexaminePdf(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            logger.info ( e.toString () );
        }
        return responseText;
    }
    /**
     * 生成整改意见复查文书表格
     * @param RECORD_ID
     * @return
     * @throws IOException
     */
    public String createReexaminePdf(String RECORD_ID) throws IOException {
        Document document = new Document( PageSize.A4);
        String pdfPath="D:/zfbdzl/pdf/firefighting_fucha/";
        String qrCodePath="D:/zfbdzl/qrcode/firefighting_fucha/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=firefighting_fucha/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date ());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹
        //设置日期格式
        List<Map> List = reexamineDao.getReexaminePdfInfo(RECORD_ID);

        String UPID="";//复查自增id
        if(List.get(0).get("RECORD_ID")!=null)  UPID = List.get(0).get("RECORD_ID").toString ();
        String AREA="宁";//检查单位所属辖区
        String CHECKE_UNIT="";
        String CHECKED_END_TIME="";
        String CHECKE_OPINION="";
        String SIGNATURE4_PATH="";
        String SIGNATURE5_PATH="";
        String RECORD_TIME="";
        if(List.get(0).get("CHECKE_UNIT")!=null)  CHECKE_UNIT = List.get(0).get("CHECKE_UNIT").toString ();
        if(List.get(0).get("CHECKED_END_TIME")!=null)  CHECKED_END_TIME = List.get(0).get("CHECKED_END_TIME").toString ();
        if(List.get(0).get("CHECKE_OPINION")!=null)  CHECKE_OPINION = List.get(0).get("CHECKE_OPINION").toString ();
        if(List.get(0).get("CHECKE_SIGNATURE")!=null)  SIGNATURE4_PATH = List.get(0).get("CHECKE_SIGNATURE").toString ();
        if(List.get(0).get("REPRESENR_SIGNATURE")!=null)  SIGNATURE5_PATH = List.get(0).get("REPRESENR_SIGNATURE").toString ();
        if(List.get(0).get("RECORD_TIME")!=null)  RECORD_TIME = List.get(0).get("RECORD_TIME").toString ();
        String[] SIGNATURE4_PATH_ARRAY= SIGNATURE4_PATH.split("\\|");
        String[] SIGNATURE5_PATH_ARRAY= SIGNATURE5_PATH.split("\\|");
        try {
            File file1 =new File(pdfPath+date1);
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            File file2 =new File(qrCodePath+date1);
            if(!file2 .exists()  && !file2 .isDirectory()){
                file2 .mkdir();
            }
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream (pdfPath+pdfName));
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
//            //直线1
//            Paragraph line = new Paragraph("",lineFont);
//            LineSeparator lineSeparator=new LineSeparator();
//            lineSeparator.setLineWidth(0.5F);
//            line.add(new Chunk(lineSeparator));
//            line.add("");
//            document.add(line);
//
//            //直线2
//            Paragraph line2 = new Paragraph("",lineFont);
//            LineSeparator lineSeparator2=new LineSeparator();
//            lineSeparator2.setLineWidth(0.5F);
//            line2.add(new Chunk(lineSeparator2));
//            line2.add("");
//            document.add(line2);


            //副标题:现场检查记录
            Paragraph subTitle=new Paragraph("整改复查意见书",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);


            //应急检记
            Paragraph subTitle2=new Paragraph("（"+AREA+" ）应急复查[2019]（"+UPID+"）号",para);
            subTitle2.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle2);
            //设置行距，该处会影响后面所有的chunk的行距
            paragraph=new Paragraph("",para);
            paragraph.setLeading(20f);
            document.add(paragraph);

            //被检查单位
            chunk = new Chunk("",para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECKE_UNIT+"有限公司："+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
//            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);


            //时间
            Chunk chunk1 = new Chunk("         本机关于"+CHECKED_END_TIME+"作出了责令限期整改书[("+AREA+" )应急字责改[2019] ("+UPID+")号]，经对你单位整改情况进行复查，提出如下意见:",para);
            document.add(chunk1);
            document.add(Chunk.NEWLINE);

            //修改意见
            chunk = new Chunk ( "         "+CHECKE_OPINION,para );
            // document.add ( chunk );
            chunk.setUnderline ( 0.1f,-1f );
            document.add ( chunk );
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            chunk = new Chunk ( "",para );
            document.add ( chunk );
            chunk.setUnderline ( 0.1f,-1f );
            document.add ( chunk );
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);
            chunk = new Chunk ( "",para );
            document.add ( chunk );
            chunk.setUnderline ( 0.1f,-1f );
            document.add ( chunk );
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);
            chunk = new Chunk ( "",para );
            document.add ( chunk );
            chunk.setUnderline ( 0.1f,-1f );
            document.add ( chunk );
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //被复查单位负责人（签名）：
            PdfContentByte sign4 = writer.getDirectContent();
            BaseFont bf4= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign4.beginText();
            sign4.setFontAndSize(bf4, 12);
            sign4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被复查单位负责人（签名）：", 25f, 360f, 0);
            sign4.endText();
            for(int i=0;i<SIGNATURE4_PATH_ARRAY.length;i++){
                Image image1 = Image.getInstance(url+SIGNATURE4_PATH_ARRAY[i]);
                image1.setAbsolutePosition(230f+105*i, 340f);
                image1.scaleAbsolute(48, 48);
                document.add(image1);
            }

//            Image image4 = Image.getInstance(url+SIGNATURE4_PATH);
//            image4.setAbsolutePosition(230f, 340f);
//            image4.scaleAbsolute(100, 50);
//            document.add(image4);

//            PdfContentByte cb4 = writer.getDirectContent();
//            BaseFont bf41= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            cb4.beginText();
//            cb4.setFontAndSize(bf41, 12);
//            cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+, 450f, 360f, 0);
//            cb4.endText();



            //安全生产监管行政执法人员(签名):
            PdfContentByte sign5 = writer.getDirectContent();
            BaseFont bf5= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign5.beginText();
            sign5.setFontAndSize(bf5, 12);
            sign5.showTextAligned(PdfContentByte.ALIGN_LEFT,  "安全生产监管行政执法人员(签名):", 25f, 300f, 0);
            sign5.endText();
            for(int i=0;i<SIGNATURE5_PATH_ARRAY.length;i++){
                Image image = Image.getInstance(url+SIGNATURE5_PATH_ARRAY[i]);
                image.setAbsolutePosition(230f, 290f-40*i);
                image.scaleAbsolute(48, 48);
                document.add(image);

//                PdfContentByte cb4 = writer.getDirectContent();
//                BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//                cb4.beginText();
//                cb4.setFontAndSize(bf2, 12);
//                cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER1, 350f, 300-40*i, 0);
//                cb4.endText();
            }
//            for(int i=0;i<SIGNATURE6_PATH_ARRAY.length;i++){
//                Image image = Image.getInstance(url+SIGNATURE6_PATH_ARRAY[i]);
//                image.setAbsolutePosition(230f, 250f-40*i);
//                image.scaleAbsolute(48, 48);
//                document.add(image);
//
////                PdfContentByte cb4 = writer.getDirectContent();
////                BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
////                cb4.beginText();
////                cb4.setFontAndSize(bf2, 12);
////                cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER2, 350f, 250-40*i, 0);
////                cb4.endText();
//            }
//
////            for(int i=0;i<2;i++){
////                if(i==1) {SIGNATURE5_PATH=SIGNATURE6_PATH; }
////                Image image5 = Image.getInstance(url+SIGNATURE5_PATH);
////                image5.setAbsolutePosition(230f, 290f-40*i);
////                image5.scaleAbsolute(100, 50);
////                document.add(image5);
////
////                PdfContentByte cb40 = writer.getDirectContent();
////                BaseFont bf20= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
////                cb40.beginText();
////                cb40.setFontAndSize(bf20, 12);
////                cb40.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER1, 400f, 280f-40*i, 0);
////                cb40.endText();
////
////            }
//
//            //专家1(签名):
//            PdfContentByte sign7 = writer.getDirectContent();
//            BaseFont bf7= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            sign7.beginText();
//            sign7.setFontAndSize(bf7, 12);
//            sign7.showTextAligned(PdfContentByte.ALIGN_LEFT,  "专家(签名):", 25f, 200f, 0);
//            sign7.endText();
//            for(int i=0;i<SIGNATURE7_PATH_ARRAY.length;i++){
//                Image image1 = Image.getInstance(url+SIGNATURE7_PATH_ARRAY[i]);
//                image1.setAbsolutePosition(230f+105*i, 180f);
//                image1.scaleAbsolute(48, 48);
//                document.add(image1);
//            }

            //专家2(签名):
//            PdfContentByte sign8 = writer.getDirectContent();
//            BaseFont bf8= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            sign8.beginText();
//            sign8.setFontAndSize(bf8, 12);
//            sign8.showTextAligned(PdfContentByte.ALIGN_LEFT,  "专家(签名):", 25f, 160f, 0);
//            sign8.endText();
//            for(int i=0;i<SIGNATURE8_PATH_ARRAY.length;i++){
//                Image image1 = Image.getInstance(url+SIGNATURE8_PATH_ARRAY[i]);
//                image1.setAbsolutePosition(230f+105*i, 140f);
//                image1.scaleAbsolute(48, 48);
//                document.add(image1);
//            }


            //见证人(签名):
//            PdfContentByte sign9 = writer.getDirectContent();
//            BaseFont bf9= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            sign9.beginText();
//            sign9.setFontAndSize(bf9, 12);
//            sign9.showTextAligned(PdfContentByte.ALIGN_LEFT,  "见证人(签名):", 25f, 120f, 0);
//            sign9.endText();
//            for(int i=0;i<SIGNATURE9_PATH_ARRAY.length;i++){
//                Image image1 = Image.getInstance(url+SIGNATURE9_PATH_ARRAY[i]);
//                image1.setAbsolutePosition(230f+105*i, 100f);
//                image1.scaleAbsolute(48, 48);
//                document.add(image1);
//            }

            String inscribe="";//'宁','江北', '玄', '秦','建','鼓','栖','经开','雨','江','浦','六','溧','高'
            if (AREA.equals("宁")) {
                inscribe="南京市应急管理局";
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
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  inscribe, 360f, 70f, 0);
            cb.endText();

            //印章
          /*  PdfContentByte yz= writer.getDirectContent();
            BaseFont bfy= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            yz.beginText();
            yz.setFontAndSize(bfy, 12);
            yz.showTextAligned(PdfContentByte.ALIGN_LEFT,  "应急管理部门（印章）", 350f, 80f, 0);
            yz.endText();
            document.add(Chunk.NEWLINE);*/

            if(AREA.equals("宁")){
                //印章图片
                Image image = Image.getInstance ( "C:/zfbdmoban/images/zz.png" );
                image.setAbsolutePosition ( 370f, 30f );
                image.scaleAbsolute ( 140, 140 );
                document.add ( image );

            }

            //创建时间
            PdfContentByte createTime = writer.getDirectContent();
            BaseFont bfc= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            createTime.beginText();
            createTime.setFontAndSize(bfc, 12);
            createTime.showTextAligned(PdfContentByte.ALIGN_LEFT,  RECORD_TIME, 420f, 50f, 0);
            createTime.endText();


            //预览二维码
            String content=pdfAbsolutePath+pdfName;
            String qrCodeName=date1+"/"+nowTime+ random+".png";
            try{
                Map<EncodeHintType, Object> hints = new HashMap<> ();
                //编码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                //边框距
                hints.put(EncodeHintType.MARGIN, 0);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 198, 198, hints);
                Path file=new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);

            }catch (Exception e){
                System.out.print(e);
                logger.info ( e.toString () );
                return MsgUtil.errorMsg(e.toString());
            }
            reexamineDao.updatePdfAndQrcode(pdfName,qrCodeName,RECORD_ID);
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtil.errorMsg(e.toString());
        } finally {
            document.close();
        }
        return MsgUtil.successMsg();
    }
}
