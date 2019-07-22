package yjbd.yjbawechat.Service;

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
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.HiddenDangerDao;
import yjbd.yjbawechat.Util.MsgUtil;
import yjbd.yjbawechat.Util.MsgUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Author: 随心的小新
 * @Date: 2019/5/7 16:27
 */
@Service
public class HiddenDangerService {
    @Resource
    HiddenDangerDao hiddenDangerDao;
    static final Logger logger = LoggerFactory.getLogger(HiddenDangerService.class);
    /**
     * 根据ID来获取专家名称
     */
    public String getExpertById(String recordId){
        String responseText = "";
        try {
            List<Map> mapList = hiddenDangerDao.getExpertById(recordId);
            responseText = JSON.toJSONString ( mapList);
            responseText =  "[" + responseText + "]";
        }catch (Exception e){
            logger.info ( e.toString () );
        }
        return responseText;
    }
    /**
     * 获取一般问题
     */
    public String GetExpertProblemAndMsgs(String RecordId){
        String responseText = "";
        try {
            List<Map> mapList = hiddenDangerDao.GetExpertProblemAndMsgs(RecordId );
            responseText = JSON.toJSONString ( mapList);
            responseText =  "[" + responseText + "]";
        }catch (Exception e){
            logger.info ( e.toString () );
        }
        return responseText;
    }
    /**
     * 获取重大隐患问题
     */
    public String getHiddenDangerById(){
        String responseText = "";
        try {
            List<Map> mapList = hiddenDangerDao.getHiddenDangerById( );
            responseText = JSON.toJSONString ( mapList);
            responseText =  "[" + responseText + "]";
        }catch (Exception e){
            logger.info ( e.toString () );
        }
        return responseText;
    }
    /**
     * 获取被检查单位信息
     */
    public String GetCheckByRecord(String RecordId) {
        String responseText="";
        try {
            List<Map> mapList=hiddenDangerDao.GetCheckByRecord(RecordId);
            responseText = JSON.toJSONString ( mapList);
            responseText =  "[" + responseText + "]";
        }catch (Exception e){
            logger.info ( e.toString () );
        }
        return responseText;
    }
    /**
     * 创建重大隐患表格信息
     */
    public String createDangerTable(String RECORD_ID,String CHECKED_START_TIME,String CHECKED_END_TIME,String CHECKED_UNIT,String UNIT_NUMBER,String ADDRESS,
                                    String REPRESENT_PEOPLE,String CHECKE_PLACE,String EXPERT_PEOPLLE,String DANGER_NAME,String CHECKE_DETAIL,
                                    String VIDEO_URL,String LOCATION_IMG,String OTHER_IMG,String PKID,String SECURITY_PEOPLE) {
        int count=0;
        String responseText="";
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        String RECORD_TIME = now.get(Calendar.YEAR)+"年"+month+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
        try {
            count = hiddenDangerDao.getTemporaryCreateDangerTableCount(RECORD_ID);
            if (count>0){
                int flag = hiddenDangerDao.createDangerTable1(CHECKED_START_TIME,CHECKED_END_TIME,CHECKED_UNIT, UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,CHECKE_PLACE,
                        EXPERT_PEOPLLE,DANGER_NAME,CHECKE_DETAIL,VIDEO_URL,LOCATION_IMG,OTHER_IMG,RECORD_TIME,PKID,SECURITY_PEOPLE,RECORD_ID);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }else {
                int flag = hiddenDangerDao.createDangerTable(RECORD_ID,CHECKED_START_TIME,CHECKED_END_TIME,CHECKED_UNIT, UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,CHECKE_PLACE,
                        EXPERT_PEOPLLE,DANGER_NAME,CHECKE_DETAIL,VIDEO_URL,LOCATION_IMG,OTHER_IMG,RECORD_TIME,PKID,SECURITY_PEOPLE);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
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
     * 缓存更新重大隐患表格信息
     */
    public String temporaryCreateDangerTable(String RECORD_ID,String CHECKED_START_TIME,String CHECKED_END_TIME,String CHECKED_UNIT,String UNIT_NUMBER,String ADDRESS,
                                             String REPRESENT_PEOPLE,String CHECKE_PLACE,String EXPERT_PEOPLLE,String DANGER_NAME,String CHECKE_DETAIL,
                                             String VIDEO_URL,String LOCATION_IMG,String OTHER_IMG,String PKID,String SECURITY_PEOPLE,String URL) {
        int count=0;
        String responseText="";
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        String RECORD_TIME = now.get(Calendar.YEAR)+"年"+month+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
        try {
            count = hiddenDangerDao.getTemporaryCreateDangerTableCount(RECORD_ID);
            if (count>0){
                int flag = hiddenDangerDao.temporaryCreateDangerTable(CHECKED_START_TIME,CHECKED_END_TIME,CHECKED_UNIT, UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,CHECKE_PLACE,
                        EXPERT_PEOPLLE,DANGER_NAME,CHECKE_DETAIL,VIDEO_URL,LOCATION_IMG,OTHER_IMG,RECORD_TIME,PKID,SECURITY_PEOPLE,URL,RECORD_ID);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }else {
                int flag = hiddenDangerDao.temporaryCreateDangerTable1(RECORD_ID,CHECKED_START_TIME,CHECKED_END_TIME,CHECKED_UNIT, UNIT_NUMBER,ADDRESS,REPRESENT_PEOPLE,CHECKE_PLACE,
                        EXPERT_PEOPLLE,DANGER_NAME,CHECKE_DETAIL,VIDEO_URL,LOCATION_IMG,OTHER_IMG,RECORD_TIME,PKID,SECURITY_PEOPLE,URL);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
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
     * 获取缓存更新重大隐患表格信息
     */
    public String getTemporaryCreateDangerTable(String RecordId) {
        String responseText="";
        List<Map> mapList=hiddenDangerDao.getTemporaryCreateDangerTable(RecordId);
        responseText = JSON.toJSONString ( mapList);
        return responseText;
    }
    /**
     * 上传专家签名
     */
    public String UpdateExpertSignature(String CHECKE_SIGNATURE, String RecordId) {
        String responseText="";
        try {
            int flag = hiddenDangerDao.UpdateExpertSignature(CHECKE_SIGNATURE, RecordId);
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
    public String UpdateInspectedSignature(String REPRESENR_SIGNATURE, String RecordId) {
        String responseText="";
        try {
            int flag = hiddenDangerDao.UpdateInspectedSignature(REPRESENR_SIGNATURE, RecordId);
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
    public String LookExHiddenDangerPdf(String recordId) {
        String responseText="";
        try {
            List<Map> list=hiddenDangerDao.LookExHiddenDangerPdf(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            logger.info ( e.toString () );
        }

        return responseText;
    }
    public static PdfPCell getPDFCell(String string, Font font,int HorizontalAlignment,int VerticalAlignment,boolean hasBorder)
    {
        //创建单元格对象，将内容与字体放入段落中作为单元格内容
        PdfPCell cell = new PdfPCell(new Paragraph(string,font));
        if(!hasBorder){
            cell.setBorder(0);
        }
        cell.setHorizontalAlignment(HorizontalAlignment);
        cell.setVerticalAlignment(VerticalAlignment);

        //设置最小单元格高度
        //cell.setMinimumHeight(25);
        return cell;
    }
    /**
     * 获取PDF页面
     * @param
     * @return
     * @throws IOException
     */
    public String createExDangerPdf(String RECORD_ID) throws IOException {
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
        List<Map> List=hiddenDangerDao.getPdfInfo(RECORD_ID);
        //从表格中拉取证件号信息
        List<Map>mapList=hiddenDangerDao.searchCareId(RECORD_ID);
        String UPID="";//自增id
        String AREA="";//检查单位所属辖区
        String CHECKE_UNIT="";
        String CHECK_START_TIME="";
        String DANGER_NAME="";
        String PROCESS_DECISION="";
        String CHECKE_SIGH="";
        String REPRESENR_SIGN="";
        String RECORD_TIME="";
        String EXPERT_PEOPLLE ="";
        String CHECKE_PLACE ="";
        if(List.get(0).get("RECORD_ID")!=null)  UPID = List.get(0).get("RECORD_ID").toString ();
        if(mapList.get(0).get("AREA")!=null)  AREA = mapList.get(0).get("AREA").toString ();
        if(mapList.get(0).get("CHECKED_START_TIME")!=null)  CHECK_START_TIME = mapList.get(0).get("CHECKED_START_TIME").toString ();
        if(List.get(0).get("CHECKED_UNIT")!=null) CHECKE_UNIT = List.get(0).get("CHECKED_UNIT").toString ();
        if(List.get(0).get("EXPERT_PEOPLLE")!=null)  EXPERT_PEOPLLE = List.get(0).get("EXPERT_PEOPLLE").toString ();
        if(List.get(0).get("CHECKE_DETAIL")!=null)  PROCESS_DECISION = List.get(0).get("CHECKE_DETAIL").toString ();
        if(List.get(0).get("EXPERT_SIGH")!=null)  CHECKE_SIGH = List.get(0).get("EXPERT_SIGH").toString ();
        if(List.get(0).get("REPRESENR_SIGN")!=null)  REPRESENR_SIGN = List.get(0).get("REPRESENR_SIGN").toString ();
        if(List.get(0).get("DANGER_NAME")!=null)  DANGER_NAME = List.get(0).get("DANGER_NAME").toString ();
        if(List.get(0).get("RECORD_TIME")!=null)  RECORD_TIME = List.get(0).get("RECORD_TIME").toString ();
        if(List.get(0).get("CHECKE_PLACE")!=null)  CHECKE_PLACE = List.get(0).get("CHECKE_PLACE").toString ();
        String[] SIGNATURE_PATH_ARRAY= CHECKE_SIGH.split("\\|");
        String[] EXPERT_PEOPLLE_ARRAY= EXPERT_PEOPLLE.split("\\|");
        String[] SIGNATURE2_PATH_ARRAY= REPRESENR_SIGN.split("\\|");
        String[] DANGER_NAME_ARRAY=DANGER_NAME.split("\\|");

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
            //主标题：安全生产行政执法文书现场检查记录
            Paragraph title=new Paragraph("",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
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
            Paragraph subTitle=new Paragraph("专家检查重大隐患文书",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);


            //应急检记
//            Paragraph subTitle2=new Paragraph("                     "+AREA+" ）宁玄公消即字[2019]（"+UPID+"）号",para);
//            subTitle2.setAlignment(Element.ALIGN_CENTER);
//            document.add(subTitle2);
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


            String []time=CHECK_START_TIME.split("\\-");
            //时间

            for(int i=0;i<EXPERT_PEOPLLE_ARRAY.length;i++){
                chunk = new Chunk(" 专家:",para);
                document.add(chunk);
                chunk = new Chunk ( EXPERT_PEOPLLE_ARRAY[i]+"、",para );
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
            }
            chunk = new Chunk("于",para);
            document.add(chunk);
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
            chunk = new Chunk("对你单位 ",para);
            document.add(chunk);
            chunk = new Chunk(""+CHECKE_PLACE,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("（场所）进行监督检查时,发现存在下列重大隐患: ",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);


            //违法违规行为
//            for(int i=1;i<2;i++){
//                Chunk checkUnit3 = new Chunk(""+i+""+CHECKE_DETAIL,para);
//                checkUnit3.setUnderline(0.1f, -1f);
//                document.add(checkUnit3);
//                document.add(Chunk.NEWLINE);
//            }

            for(int i=0;i<DANGER_NAME_ARRAY.length;i++){
                Chunk problem = new Chunk ( "         "+(i+1)+"." ,para);
                document.add(problem);
                chunk = new Chunk ( DANGER_NAME_ARRAY[i],para );
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }
            Chunk fy = new Chunk("         （此栏不够，可另附页）",para);
            document.add(fy);
            document.add(Chunk.NEWLINE);


            //处理决定
            chunk = new Chunk("具体问题",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            for(int i=1;i<2;i++){
                Chunk checkUnit4 = new Chunk("         "+PROCESS_DECISION,para);
                checkUnit4.setUnderline(0.1f, -1f);
                document.add(checkUnit4);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }


//            Chunk chunk5 = new Chunk("         你单位（场所）应当采取措施，确保消防安全。对消防安全违法行为，将依法予以处罚。",para);
//            document.add(chunk5);
//            document.add(Chunk.NEWLINE);


            PdfContentByte sign = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign.beginText();
            sign.setFontAndSize(bf, 12);
            sign.showTextAligned(PdfContentByte.ALIGN_LEFT,  "专家签名:", 25f, 320f, 0);
            sign.endText();
            //安全生产监管行政执法人员(签名)图片
            for(int i=0;i<SIGNATURE_PATH_ARRAY.length;i++){
                Image image = Image.getInstance(url+SIGNATURE_PATH_ARRAY[i]);
                image.setAbsolutePosition(230f, 300f-40*i);
                image.scaleAbsolute(100, 50);
                document.add(image);

//                PdfContentByte cb4 = writer.getDirectContent();
//                BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//                cb4.beginText();
//                cb4.setFontAndSize(bf2, 12);
//                cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER1_ARRAY[1], 350f, 320-40*i, 0);
//                cb4.endText();
            }
//            if(!CARD_NUMBER2.equals("")){
//                for(int i=0;i<SIGNATURE1_PATH_ARRAY.length;i++){
//                    Image image = Image.getInstance(url+SIGNATURE1_PATH_ARRAY[i]);
//                    image.setAbsolutePosition(230f, 250f-40*i);
//                    image.scaleAbsolute(100, 50);
//                    document.add(image);
//
//                    PdfContentByte cb4 = writer.getDirectContent();
//                    BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//                    cb4.beginText();
//                    cb4.setFontAndSize(bf2, 12);
//                    cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER2, 350f, 240-40*i, 0);
//                    cb4.endText();
//                }
//            }
            //被检查单位负责人
            PdfContentByte sign2 = writer.getDirectContent();
            BaseFont bf6= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign2.beginText();
            sign2.setFontAndSize(bf6, 12);
            sign2.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被检查单位（场所）负责人签名：", 25f, 220f, 0);
            sign2.endText();

            for(int i=0;i<SIGNATURE2_PATH_ARRAY.length;i++) {
                Image image = Image.getInstance ( url + SIGNATURE2_PATH_ARRAY[i] );
                image.setAbsolutePosition ( 230f, 200f - 40 * i );
                image.scaleAbsolute ( 100, 50 );
                document.add ( image );
            }




            //印章
            PdfContentByte cb = writer.getDirectContent();
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "", 320f, 90f, 0);
            cb.endText();

            if(AREA.equals("宁")){
                Image image = Image.getInstance ( "C:/zfbdmoban/images/zz.png" );
                image.setAbsolutePosition ( 400f, 40f );
                image.scaleAbsolute ( 140, 140 );
                document.add ( image );
            }



            //创建时间
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  RECORD_TIME, 460f, 60f, 0);
            cb.endText();

            document.newPage();
            Image image ;
            //地址
            paragraph=new Paragraph("",para);
            paragraph.setLeading(20f);
            document.add(paragraph);
            chunk = new Chunk("附件",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            PdfPCell cell;
//            for(String TEMP_ID:hashMap.keySet()){
//                String  TEMP_NAME=hashMap.get(TEMP_ID).toString();
                List<Map<String,Object>> tempList2=hiddenDangerDao.getItemRecordByIdAndTEMPIDAndState(RECORD_ID);
                paragraph = new Paragraph("重大隐患:",para);
                document.add(paragraph);

                //得到所有不合格图片的数组
                List<String> imgList2=new ArrayList<> ();
                for(int i=0;i<tempList2.size();i++){
                    if(tempList2.get(i).get("LOCATION_IMG")!=null){
                        String LOCATION_IMG=tempList2.get(i).get("LOCATION_IMG").toString();
                        String[] LOCATION_IMG_ARRAY=LOCATION_IMG.split("\\|");
                        for(int j=0;j<LOCATION_IMG_ARRAY.length;j++){
                            imgList2.add(LOCATION_IMG_ARRAY[j]);
                        }
                    }
                    if(tempList2.get(i).get("OTHER_IMG")!=null){
                        String OTHER_IMG=tempList2.get(i).get("OTHER_IMG").toString();
                        String[] OTHER_IMG_ARRAY=OTHER_IMG.split("\\|");
                        for(int j=0;j<OTHER_IMG_ARRAY.length;j++){
                            imgList2.add(OTHER_IMG_ARRAY[j]);
                        }
                    }
                }
                PdfPTable table=new PdfPTable (3);
                table.setWidthPercentage(100);
                table.getDefaultCell().setMinimumHeight(160f);
                table.setTotalWidth(new float[]{160,160,160});
                //显示图片
                for(int i=0;i<imgList2.size();i++){
                    image = Image.getInstance(url+imgList2.get(i));
                    image.setIndentationLeft(100f);
                    image.scaleAbsolute(150, 150);
                    cell=new PdfPCell(image);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(0);
                    cell.setMinimumHeight(160f);
                    table.addCell(cell);
                }
                if(imgList2.size()%3!=0){
                    table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                    table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                }
                //TODO
                document.add(table);
//            }


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
                BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 197, 197, hints);
                Path file=new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);

            }catch (Exception e){
                System.out.print(e);
                logger.info ( e.toString () );
                return MsgUtil.errorMsg(e.toString());
            }
            hiddenDangerDao.updatePdfAndQrcode(pdfName,qrCodeName,RECORD_ID);


        } catch (DocumentException e) {
            e.printStackTrace();
            logger.info ( e.getLocalizedMessage () );
            return MsgUtil.errorMsg(e.toString());
        } finally {
            document.close();
        }
        return MsgUtil.successMsg();
    }
//    public static PdfPCell getPDFCell(String string, Font font, int HorizontalAlignment, int VerticalAlignment)
//    {
//        //创建单元格对象，将内容与字体放入段落中作为单元格内容
//        PdfPCell cell=new PdfPCell(new Paragraph (string,font));
//
//        cell.setHorizontalAlignment(HorizontalAlignment);
//        cell.setVerticalAlignment(VerticalAlignment);
//
//        //设置最小单元格高度
//        //cell.setMinimumHeight(25);
//        return cell;
//    }

}
