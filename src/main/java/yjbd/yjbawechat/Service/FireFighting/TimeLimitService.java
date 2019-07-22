package yjbd.yjbawechat.Service.FireFighting;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.FireFighting.TimeLimitDao;
import yjbd.yjbawechat.Dao.FireFighting.TimelyRectificationDao;
import yjbd.yjbawechat.Util.MsgUtil;
import yjbd.yjbawechat.Util.MsgUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/25 17:13
 */
@Service
public class TimeLimitService {
    @Resource
    TimeLimitDao timeLimitDao;
    @Autowired
    TimelyRectificationDao timelyRectificationDao;
    static final Logger logger = LoggerFactory.getLogger(TimeLimitService.class);
    /**
     * 获取被执法单位名称
     */
    public String getEnforcerById(String recordId){
        String responseText = "";
        try {
            recordId = URLDecoder.decode(recordId, "UTF-8");
            List<Map> mapList = timelyRectificationDao.getMessageById(recordId);
            responseText = JSON.toJSONString ( mapList);
            responseText =  "[" + responseText + "]";
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
        }

        return responseText;
    }
    /**
     * 获取存在的问题列表
     */
    public String getProblemsById(String recordId){
        String responseText = "";
        try {
            recordId = URLDecoder.decode(recordId, "UTF-8");
            List<Map> mapList = timeLimitDao.getProblemsById(recordId);
            responseText = JSON.toJSONString ( mapList);
            responseText =  "[" + responseText + "]";
        }catch (Exception e){
            logger.info ( e.toString () );
        }

        return responseText;
    }
    /**
     * 获取PDF预览信息
     */
    public String createTimeLimitTable(String TIME_IDS,String EXECUTE_PEOPLE2,String CARD_NUMBER2,String CHECKE_UNIT, String EXECUTE_PEOPLE,String CARD_NUMBER,String CHECKE_DETAIL ,String RECORD_ID) {
        String responseText="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=df.format(new Date()).toString();
        try {
            int flag = timeLimitDao.createTimeLimitTable(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_DETAIL,RECORD_ID,createTime);
            if (flag > 0) {
                responseText = MsgUtils.successMsg("recordId",RECORD_ID);
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }
        return responseText;
    }
    /**
     * 上传执法者签名
     */
    public String UpdateEnforceSigh(String CHECKE_SIGH, String RecordId) {
        String responseText="";

        try {
            int flag = timeLimitDao.UpdateEnforceSigh(CHECKE_SIGH, RecordId);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }

        return responseText;
    }
    /**
     * 上传被执法单位签名
     */
    public String UpdateInspectorSigh(String REPRESENR_SIGN, String RecordId) {
        String responseText="";
        try {
            int flag = timeLimitDao.UpdateInspectorSigh(REPRESENR_SIGN, RecordId);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }

        return responseText;
    }
    /**
     * 最后生成PDF页面以及二维码图片
     */
    public String getTimeLimitRecord(String recordId) {
        String responseText="";
        try {
            List<Map> list=timeLimitDao.getTimeLimitRecord(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
            return MsgUtils.errorMsg("数值异常，无法上传");
        }
        return responseText;

    }
    /**
     * 获取PDF页面
     * @param ID
     * @return
     * @throws IOException
     */
    public String createTimeLimitPdf (String ID,String ID1) throws IOException {
        Document document = new Document( PageSize.A4);
        String pdfPath="D:/zfbdzl/pdf/zeling/";
        String qrCodePath="D:/zfbdzl/qrcode/zeling/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=zeling/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date ());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹
        //设置日期格式
        List<Map<String,Object>> list=timeLimitDao.getThreeById(ID);
        String CHECKE_DETAIL ="";//获取问题
        if(list.get(0).get("CHECKE_DETAIL")!=null) CHECKE_DETAIL=list.get(0).get("CHECKE_DETAIL").toString();
        String[] CHECKE_DETAIL_ARRAY = CHECKE_DETAIL.split ( "\\|" );
//        String CHECKED_START_TIME="";//检查开始时间
//        if(list.get(0).get("CHECKE_PROBLEM_IDS")!=null) CHECKE_PROBLEM_IDS=list.get(0).get("CHECKE_PROBLEM_IDS").toString();
//        String[] PROBLEM_ID=CHECKE_PROBLEM_IDS.split("\\|");
//        try {
//            for (int i=0;i<PROBLEM_ID.length;i++){
//                String problemId = PROBLEM_ID[i];
//                List<Map>mapList = timeLimitDao.searchProblemById(problemId);
//                if(mapList.get(0).get("CHECKE_DETAIL")!=null)
//                    CHECKE_DETAIL += mapList.get(0).get("CHECKE_DETAIL").toString()+"|";
//                if(mapList.get(0).get("CHECKED_END_TIME")!=null) CHECKED_START_TIME=mapList.get(0).get("CHECKED_END_TIME").toString();
//            }
//        }catch (Exception e){
//            System.out.println ( e.toString () );
//            e.printStackTrace();
//        }
        //从表格中拉取证件号信息
        List<Map>mapList2=timeLimitDao.searchCareId(ID);
        String AREA="";//检查单位所属辖区
        if(mapList2.get(0).get("AREA")!=null)  AREA = mapList2.get(0).get("AREA").toString ();
        String CHECK_UNIT="";//检查单位
        if(mapList2.get(0).get("CHECK_UNIT")!=null)  CHECK_UNIT = mapList2.get(0).get("CHECK_UNIT").toString ();
        String CHECK_START_TIME="";//检查开始时间
        if(mapList2.get(0).get("CHECK_START_TIME")!=null)  CHECK_START_TIME = mapList2.get(0).get("CHECK_START_TIME").toString ();

        String RECORD_ID=ID;  //现场检查记录的自增id
        String CHECKE_UNIT="";//被检查单位
        String TIME_IDS="";   //问题的整改期限
        if(list.get(0).get("TIME_IDS")!=null) TIME_IDS=list.get(0).get("TIME_IDS").toString();
        String[] TIME_IDSL_ARRAY = TIME_IDS.split ( "\\|" );
        String UPID= ID1;//责令整改自增id
        String CHECKED_END_TIME="";//检查结束时间
        String CARD_NUMBER="";//证件号
        String CARD_NUMBER2="";//证件号2
        String REPRESENR_SIGN="";//被检查代表签字图片
        String CHECKE_SIGH="";//检查人员签字图片
        String WITNESS_SIGH="";//见证者签名图片
        String ENFORCE_PAPER="";//最终执法文书
        String OTHER_IMG="";//其他图片
        String SELECTED_MODEL="";
        String NOT_SELECTED_MODEL="";
        if(list.get(0).get("RECORD_ID")!=null) RECORD_ID=list.get(0).get("RECORD_ID").toString();
        if(list.get(0).get("CHECKE_UNIT")!=null) CHECKE_UNIT=list.get(0).get("CHECKE_UNIT").toString();
        if(list.get(0).get("CHECKED_END_TIME")!=null) CHECKED_END_TIME=list.get(0).get("CHECKED_END_TIME").toString();
        if(mapList2.get(0).get("CARD_NUMBER")!=null)CARD_NUMBER=mapList2.get(0).get("CARD_NUMBER").toString();
        if(list.get(0).get("CARD_NUMBER2")!=null)CARD_NUMBER2=list.get(0).get("CARD_NUMBER2").toString();
        if(list.get(0).get("REPRESENR_SIGN")!=null) REPRESENR_SIGN=list.get(0).get("REPRESENR_SIGN").toString();
        if(list.get(0).get("CHECKE_SIGH")!=null) CHECKE_SIGH=list.get(0).get("CHECKE_SIGH").toString();
        if(list.get(0).get("ENFORCE_PAPER")!=null) ENFORCE_PAPER=list.get(0).get("ENFORCE_PAPER").toString();
        if(list.get(0).get("OTHER_IMG")!=null) OTHER_IMG=list.get(0).get("OTHER_IMG").toString();
        if(list.get(0).get("SELECTED_MODEL")!=null) SELECTED_MODEL=list.get(0).get("SELECTED_MODEL").toString();
        if(list.get(0).get("NOT_SELECTED_MODEL")!=null) NOT_SELECTED_MODEL=list.get(0).get("NOT_SELECTED_MODEL").toString();
        if(list.get(0).get("WITNESS_SIGH")!=null) WITNESS_SIGH=list.get(0).get("WITNESS_SIGH").toString();
        String[] CARD_NUMBER1_ARRAY= CARD_NUMBER.split("\\__");
        String[] REPRESENR_SIGN_ARRAY= REPRESENR_SIGN.split("\\|");
        String[] CHECKE_SIGH_ARRAY= CHECKE_SIGH.split("\\|");
        String[] JIANZHENG_SIGH_ARRAY= WITNESS_SIGH.split("\\|");
        String[] selectedArray=SELECTED_MODEL.split("\\|");
        String[] unselectedArray=NOT_SELECTED_MODEL.split("\\|");

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
            //BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 20, Font.BOLD,BaseColor.BLACK);//加入document：
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
            //主标题：安全生产行政执法文书现场检查记录
            String Temp = "";
            if (CHECK_UNIT.contains("江北")) {
                Temp = "江北";
            }
            if (CHECK_UNIT.contains("玄武")) {
                Temp = "玄武";
            }
            if (CHECK_UNIT.contains("秦淮")) {
                Temp = "秦淮";
            }
            if (CHECK_UNIT.contains("建邺")) {
                Temp = "建邺";
            }
            if (CHECK_UNIT.contains("鼓楼")) {
                Temp = "鼓楼";
            }
            if (CHECK_UNIT.contains("栖霞")) {
                Temp = "栖霞";
            }
            if (CHECK_UNIT.contains("经济开发")) {
                Temp = "经济开发";
            }
            if (CHECK_UNIT.contains("雨花台")) {
                Temp = "雨花台";
            }
            if (CHECK_UNIT.contains("江宁")) {
                Temp = "江宁";
            }
            if (CHECK_UNIT.contains("浦口")) {
                Temp = "浦口";
            }
            if (CHECK_UNIT.contains("六合")) {
                Temp = "六合";
            }
            if (CHECK_UNIT.contains("溧水")) {
                Temp = "溧水";
            }
            if (CHECK_UNIT.contains("高淳")) {
                Temp = "高淳";
            }
            Paragraph title = new Paragraph("南京市公安消防支队"+Temp+"区大队",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            /*//直线
            Paragraph line = new Paragraph("",lineFont);
            line.add(new Chunk(lineSeparator));
            line.add("");
            document.add(line);

            //直线
            Paragraph line2 = new Paragraph("",lineFont);
            LineSeparator lineSeparator2=new LineSeparator();
            lineSeparator2.setLineWidth(0.5F);
            line2.add(new Chunk(lineSeparator2));
            line2.add("");
            document.add(line2);*/
            //副标题:现场检查记录
            Paragraph subTitle=new Paragraph("责令限期改正通知书",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
//            //应急检记
            Paragraph subTitle2=new Paragraph("                                                                                    （宁玄）公消限字[2019]("+UPID+")号",para);
            subTitle2.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle2);
            //time
            //设置行距，该处会影响后面所有的chunk的行距
            paragraph=new Paragraph("",para);
            paragraph.setLeading(25f);
            document.add(paragraph);

            //被检查单位
            chunk = new Chunk("",para);
            document.add(chunk);
            chunk = new Chunk(CHECKE_UNIT+"："+"",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
//            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);


            //事由和目的
            /*chunk = new Chunk("         经查，你单位存在下列问题：",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            for (int i = 0;i<CHECKE_DETAIL_ARRAY.length;i++){
                chunk = new Chunk("         "+(i+1)+"." ,para);
                document.add(chunk);
                chunk = new Chunk(CHECKE_DETAIL_ARRAY[i],para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }
            chunk = new Chunk("         (此栏不够，可另附页)。",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);*/



            //段落1
            chunk = new Chunk("        根据《中华人民共和国消防法》第五十三条的规定，我",para);
            document.add(chunk);
            chunk = new Chunk(""+CHECK_UNIT,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("于",para);
            document.add(chunk);
            String []time=CHECK_START_TIME.split("\\-");//时间
            chunk = new Chunk(time[0],para);
            document.add(chunk);
            chunk = new Chunk("年",para);
            document.add(chunk);
            chunk = new Chunk(time[1],para);
            document.add(chunk);
            chunk = new Chunk("月",para);
            document.add(chunk);
            String []time1=time[2].split ( "\\ " );
            chunk = new Chunk(time1[0],para);
            document.add(chunk);
            chunk = new Chunk("日",para);
            document.add(chunk);
//            chunk = new Chunk(""+CHECK_START_TIME,para);
//            chunk.setUnderline(0.1f, -1f);
//            document.add(chunk);
            chunk = new Chunk("对你单位（场所）进行消防监督检查，发现存在下列",para);
            document.add(chunk);
            chunk = new Chunk(""+CHECKE_DETAIL_ARRAY.length,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("项消防安全违法行为：",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            for (int i = 0;i<CHECKE_DETAIL_ARRAY.length;i++){
                chunk = new Chunk("         "+(i+1)+"." ,para);
                document.add(chunk);
                chunk = new Chunk(CHECKE_DETAIL_ARRAY[i],para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }

//            //问题和整改时间
//            for (int i = 0;i<CHECKE_DETAIL_ARRAY.length;i++){
//                chunk = new Chunk("         "+(i+1)+"." ,para);
//                document.add(chunk);
//                chunk = new Chunk(CHECKE_DETAIL_ARRAY[i],para);
//                chunk.setUnderline(0.1f, -1f);
//                document.add(chunk);
//
//                chunk = new Chunk("  "+"整改完成时限：",para);
//                document.add(chunk);
//
//                chunk = new Chunk(TIME_IDSL_ARRAY[i],para);
//                chunk.setUnderline(0.1f, -1f);
//                document.add(chunk);
//
//                document.add(new Chunk(lineSeparator));
//                document.add(Chunk.NEWLINE);
//            }
            //段落2
            chunk = new Chunk("具体问题：",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            for (int i = 0;i<CHECKE_DETAIL_ARRAY.length;i++){
                chunk = new Chunk("        对上述第",para);
                document.add(chunk);
                chunk = new Chunk(""+(i+1)+"" ,para);
//                document.add(chunk);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
                chunk = new Chunk(""+"项问题",para);
                document.add(chunk);
                chunk = new Chunk("，责令你单位（场所）于",para);
                document.add(chunk);
                chunk = new Chunk ( TIME_IDSL_ARRAY[i],para );
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
//                document.add(new Chunk(lineSeparator));
                chunk = new Chunk("前改正。",para);
                document.add(chunk);
                document.add(Chunk.NEWLINE);
            }
//            document.add(Chunk.NEWLINE);

            //段落3
            chunk = new Chunk("        改正期间，你单位（场所）应当采取措施，确保消防安全。对消防安全违反行为，将依法予以处罚。",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            if (!WITNESS_SIGH.equals("")){
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "", 25f, 200f, 0);
                cb.endText();
            }


            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被检查单位（场所）签收：", 25f, 250f, 0);
            cb.endText();

            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "执法人（签名）：", 25f, 300f, 0);
            cb.endText();

//            cb = writer.getDirectContent();
//            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            cb.beginText();
//            cb.setFontAndSize(bf, 12);
//            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号：", 320f, 300f, 0);
//            cb.endText();
//
//            cb = writer.getDirectContent();
//            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            cb.beginText();
//            cb.setFontAndSize(bf, 12);
//            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  CARD_NUMBER1_ARRAY[1], 360f, 300f, 0);
//            cb.endText();

            if(!CARD_NUMBER2.equals("")){

                cb = writer.getDirectContent();
                bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  CARD_NUMBER2, 450f, 300f, 0);
                cb.endText();
            }

            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "", 320f, 150f, 0);
            cb.endText();
            //时间
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  CHECK_START_TIME, 470f, 90f, 0);
            cb.endText();

            if(AREA.equals("宁")){
                //印章图片
                Image image = Image.getInstance ( "C:/zfbdmoban/images/zz.png" );
                image.setAbsolutePosition ( 400f, 70f );
                image.scaleAbsolute ( 140, 140 );
                document.add ( image );

            }

            //检查人员签名图片
            for(int i=0;i<CHECKE_SIGH_ARRAY.length;i++){
                Image image1 = Image.getInstance(url+CHECKE_SIGH_ARRAY[i]);
                image1.setAbsolutePosition(240f+105*i, 280f);
                //Scale to new height and new width of image
                image1.scaleAbsolute(48, 48);
                //Add to document
                document.add(image1);
            }

            //专家签名图片
//            for(int i=0;i<2;i++){
//                Image image1 = Image.getInstance(url+"1554599827797.png");
//                //Fixed Positioning
//                image1.setAbsolutePosition(400f+105*i, 150f);
//                //Scale to new height and new width of image
//                image1.scaleAbsolute(48, 48);
//                //Add to document
//                document.add(image1);
//            }
            //被检查人单位现场负责人签名图片
            for(int i=0;i<REPRESENR_SIGN_ARRAY.length;i++){
                Image image1 = Image.getInstance(url+REPRESENR_SIGN_ARRAY[i]);
                //Fixed Positioning
                image1.setAbsolutePosition(220f+105*i, 230f);
                //Scale to new height and new width of image
                image1.scaleAbsolute(48, 48);
                //Add to document
                document.add(image1);
            }

            if (!WITNESS_SIGH.equals("")){
                //见证人签名图片
                for(int i=0;i<JIANZHENG_SIGH_ARRAY.length;i++){
                    Image image1 = Image.getInstance(url+JIANZHENG_SIGH_ARRAY[i]);
                    //Fixed Positioning
                    image1.setAbsolutePosition(140f+105*i, 180f);
                    //Scale to new height and new width of image
                    image1.scaleAbsolute(48, 48);
                    //Add to document
                    document.add(image1);
                }
            }

            document.newPage();
            //地址
            paragraph=new Paragraph("",para);
            paragraph.setLeading(20f);
            document.add(paragraph);

//            chunk = new Chunk("附件",para);
//            document.add(chunk);
//            document.add(Chunk.NEWLINE);

            //表格
            PdfPTable table=new PdfPTable(2);
            table.setTotalWidth(new float[]{100,500});
            for(int i=0;i<selectedArray.length;i++){
                table.addCell(getPDFCell("√",para,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                table.addCell(getPDFCell(selectedArray[i],para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE));
            }
            for(int i=0;i<unselectedArray.length;i++){
                table.addCell(getPDFCell("",para, Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                table.addCell(getPDFCell(unselectedArray[i],para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE));
            }

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
                BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 199, 199, hints);
                Path file=new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);

            }catch (Exception e){
                System.out.println ( e.toString () );
                e.printStackTrace();
                logger.info ( e.toString () );
            }
            timeLimitDao.updatePdfAndQrcode(pdfName,qrCodeName,ID);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtil.errorMsg(e.toString());
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtil.errorMsg(e.toString());
        } finally {
            document.close();
        }
        return MsgUtil.successMsg();
    }
    public static PdfPCell getPDFCell(String string, Font font, int HorizontalAlignment, int VerticalAlignment)
    {
        //创建单元格对象，将内容与字体放入段落中作为单元格内容
        PdfPCell cell=new PdfPCell(new Paragraph(string,font));

        cell.setHorizontalAlignment(HorizontalAlignment);
        cell.setVerticalAlignment(VerticalAlignment);

        //设置最小单元格高度
        //cell.setMinimumHeight(25);
        return cell;
    }

}
