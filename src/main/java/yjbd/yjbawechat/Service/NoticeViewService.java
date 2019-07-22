package yjbd.yjbawechat.Service;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.NoticeViewDao;
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
 * @Date: 2019/4/12 17:00
 */
@Service
public class NoticeViewService {
    @Autowired
    NoticeViewDao noticeViewDao;
    static final org.slf4j.Logger logger = LoggerFactory.getLogger(NoticeViewService.class);

    /**
     * 通过id来获取开始时间
     */
    public String getGreatTimeById(String RECORD_ID){
        String responseText = "";
        try {
            List<Map> mapList = noticeViewDao.getGreatTimeById(RECORD_ID);
            responseText = JSON.toJSONString ( mapList);
            responseText =  "[" + responseText + "]";
        }catch (Exception e){
            logger.info ( e.toString () );
        }
        return responseText;
    }
    /**
     * 获取整改自检表格信息
     */
    public String createNoticeBiao(String CHECKE_UNIT, String EXECUTE_PEOPLE,String CHECKED_START_TIME,String CHECKE_PROBLEM_IDS ,String CHECKED_END_TIME,String NOTICE_DETAIL,String RECORD_ID) {
        String responseText="";
//        String createTime = DateTimeUtils.getNowTime();
//        int sb=(int)(Math.random ()*10000);
//        String record_id=createTime+ sb;
        try {
            int flag = noticeViewDao.createNoticeBiao(CHECKE_UNIT, EXECUTE_PEOPLE, CHECKED_START_TIME, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,NOTICE_DETAIL,RECORD_ID);
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
     * 上传整改责任人签名
     */
    public String UpdateCheck1(String CHECKE_SIGH, String RecordId) {
        String responseText="";
        try {
            int flag = noticeViewDao.UpdateCheck1(CHECKE_SIGH, RecordId);
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
    public String UpdateRepresent1(String NOTICE_SIGH, String RecordId) {
        String responseText="";
        try {
            int flag = noticeViewDao.UpdateRepresent1(NOTICE_SIGH, RecordId);
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
     * 最后生成PDF页面
     */
    public String getIdRecord(String recordId) {
        String responseText="";
        try {
            List<Map> list=noticeViewDao.getIdRecord(recordId);
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
    public String createNoticePdf(String RECORD_ID) throws IOException {
        Document document = new Document( PageSize.A4);
        String pdfPath="D:/zfbdzl/pdf/zijian/";
        String qrCodePath="D:/zfbdzl/qrcode/zijian/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=zijian/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date ());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹
        //设置日期格式
        List<Map> List=noticeViewDao.getPdfInfo1(RECORD_ID);
        //从表格中拉取证件号信息

        String CHECK_PEOPLE="";//检查人
        String CHECKE_UNIT="";
        String CHECKED_START_TIME="";
        String CHECKED_END_TIME="";
        String CHECKED_LOCATION="";
        String CHECK_STATE="";
        String HEGE_DETAIL_ID="";
        String BUHEGE_DETAIL_ID="";
        String HEGE_LOCATION_IMG="";
        String BUHEGE_LOCATION_IMG="";
        String CHECKE_SIGH="";
        String NOTICE_SIGH="";

        for (int i =0;i<List.size ();i++){
            if(List.get(i).get("CHECK_STATE")!=null)  CHECK_STATE += List.get(i).get("CHECK_STATE").toString ()+"|";//问题ID
        }
        String[] CHECK_STATE_ID=CHECK_STATE.split("\\|");
        try {
            for (int i=0;i<CHECK_STATE_ID.length;i++){
                String check_state = CHECK_STATE_ID[i];
                if (check_state.equals ( "合格" )){
                    List<Map>mapList1 = noticeViewDao.searchProblemById1(check_state);
                    if(mapList1.get(0).get("CHECKE_DETAIL")!=null)
                        HEGE_DETAIL_ID += mapList1.get(0).get("CHECKE_DETAIL").toString()+"|";
                    if(mapList1.get(0).get("LOCATION_IMG")!=null)
                        HEGE_LOCATION_IMG += mapList1.get(0).get("LOCATION_IMG").toString()+"|";
                }else {
                    List<Map>mapList2 = noticeViewDao.searchProblemById1(check_state);
                    if(mapList2.get(0).get("CHECKE_DETAIL")!=null)
                        BUHEGE_DETAIL_ID += mapList2.get(0).get("CHECKE_DETAIL").toString()+"|";
                    if(mapList2.get(0).get("LOCATION_IMG")!=null)
                        BUHEGE_LOCATION_IMG += mapList2.get(0).get("LOCATION_IMG").toString()+"|";
                }
            }

        }catch (Exception e){
            System.out.println ( e.toString () );
            e.printStackTrace();
        }
        for (int i =0;i<List.size ();i++){
            if(List.get(i).get("CHECKE_SIGH")!=null)  CHECKE_SIGH += List.get(i).get("CHECKE_SIGH").toString ()+"|";//检查人签名数组
        }
        List<Map<String,Object>> itemRecordList=noticeViewDao.getItemRecordById(RECORD_ID);
        HashMap<String,String> hashMap=new HashMap<>();
        for(int i=0;i<itemRecordList.size();i++){
            String  TEMP_ID=itemRecordList.get(i).get("TEMP_ID").toString();
            String  TEMP_NAME=itemRecordList.get(i).get("TEMP_NAME").toString();
            hashMap.put(TEMP_ID,TEMP_NAME);
        }
        List<Map<String,Object>> itemRecordList1=noticeViewDao.getItemRecordById1(RECORD_ID);
        HashMap<String,String> hashMap1=new HashMap<>();
        for(int i=0;i<itemRecordList1.size();i++){
            String  TEMP_ID=itemRecordList1.get(i).get("TEMP_ID").toString();
            String  TEMP_NAME=itemRecordList1.get(i).get("TEMP_NAME").toString();
            hashMap1.put(TEMP_ID,TEMP_NAME);
        }


        if(List.get(0).get("CHECK_PEOPLE")!=null)  CHECK_PEOPLE = List.get(0).get("CHECK_PEOPLE").toString ();
        if(List.get(0).get("CHECKE_UNIT")!=null) CHECKE_UNIT = List.get(0).get("CHECKE_UNIT").toString ();
        if(List.get(0).get("CHECKED_START_TIME")!=null)  CHECKED_START_TIME = List.get(0).get("CREATE_TIME").toString ();
        if(List.get(0).get("CHECKED_LOCATION")!=null)  CHECKED_LOCATION = List.get(0).get("CHECKED_LOCATION").toString ();

        if(List.get(0).get("NOTICE_SIGH")!=null)  NOTICE_SIGH = List.get(0).get("NOTICE_SIGH").toString ();
        if(List.get(0).get("CHECKED_END_TIME")!=null)  CHECKED_END_TIME = List.get(0).get("CHECKED_END_TIME").toString ();

        String[] CHECK_PEOPLE_ARRAY= CHECK_PEOPLE.split("\\|");
        String[] HEGE_DETAIL_ARRAY1 = HEGE_DETAIL_ID.split ( "\\|" );
        String[] BUHEGE_DETAIL_ARRAY=BUHEGE_DETAIL_ID.split("\\|");

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

            Paragraph title=new Paragraph(""+CHECKE_UNIT,titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            //副标题:现场检查记录
            Paragraph subTitle=new Paragraph("企业现场检查记录",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);


            //应急检记
//            Paragraph subTitle2=new Paragraph("（"+AREA+" ）应急现决[2019]（"+RECORD_ID+"）号",para);
//            subTitle2.setAlignment(Element.ALIGN_CENTER);
//            document.add(subTitle2);
            //设置行距，该处会影响后面所有的chunk的行距
            paragraph=new Paragraph("",para);
            paragraph.setLeading(20f);
            document.add(paragraph);

            //检查时间/截止时间
            chunk = new Chunk ( "检查开始时间："  ,para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECKED_START_TIME+"",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);
            chunk = new Chunk ( "检查截止时间："  ,para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECKED_END_TIME+"",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //被检查单位
            chunk = new Chunk ( "检查单位：" ,para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECKE_UNIT+"",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //检查人
            for(int i=0;i<CHECK_PEOPLE_ARRAY.length;i++){
                chunk = new Chunk ( "检查人员"+(i+1)+"：" ,para);
                document.add(chunk);
                chunk = new Chunk ( "    "+CHECK_PEOPLE_ARRAY[i],para );
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }


            //检查场所
            chunk = new Chunk ( "检查场所：" ,para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECKED_LOCATION+"",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //检查情况
            chunk = new Chunk ( "检查情况：",para );
            document.add(chunk);
            document.add(Chunk.NEWLINE);

            List<Map<String,Object>> qualifiedList=noticeViewDao.getItemRecordByServiceIdAndStateOrderByTempID(RECORD_ID,"不合格");
            chunk = new Chunk("不合格：",para);
            document.add(chunk);
            PdfPTable table=new PdfPTable(2);
            table.setWidthPercentage(90);
            table.setTotalWidth(new float[]{200,300});
            PdfPCell cell;
            for(int i=0;i<qualifiedList.size();i++){
                String TEMP_NAME=qualifiedList.get(i).get("TEMP_NAME").toString();
                String CHECKE_DETAIL=qualifiedList.get(i).get("CHECKE_DETAIL").toString();
                table.addCell(getPDFCell(TEMP_NAME,para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,true));
                table.addCell(getPDFCell(CHECKE_DETAIL,para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,true));
            }
            document.add(table);

            //用表格实现
            List<Map<String,Object>> notQualifiedList=noticeViewDao.getItemRecordByServiceIdAndStateOrderByTempID(RECORD_ID,"合格");
            chunk = new Chunk("合格：",para);
            document.add(chunk);
            table=new PdfPTable(2);
            table.setWidthPercentage(90);
            table.setTotalWidth(new float[]{200,300});
            for(int i=0;i<notQualifiedList.size();i++){
                String TEMP_NAME=notQualifiedList.get(i).get("TEMP_NAME").toString();
                String CHECKE_DETAIL=notQualifiedList.get(i).get("CHECKE_DETAIL").toString();
                table.addCell(getPDFCell(TEMP_NAME,para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,true));
                table.addCell(getPDFCell(CHECKE_DETAIL,para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,true));
            }
            document.add(table);
            document.add(Chunk.NEWLINE);


            Image image2 ;
            Image image3 ;

            table=new PdfPTable(4);
            table.setWidthPercentage(100);
            table.getDefaultCell().setMinimumHeight(60f);
            table.setTotalWidth(new float[]{180,60,60,200});


            //检查人员签名
            String[] CHECK_SIGN_ARRAY=CHECKE_SIGH.split("\\|");
            table.addCell(getPDFCell("检查人员（签名）:",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            if(!CHECKE_SIGH.equals("")){
                image2 = Image.getInstance(url+CHECK_SIGN_ARRAY[0]);
                image2.setIndentationLeft(100f);
                //Scale to new height and new width of image
                image2.scaleAbsolute(48, 48);
                cell=new PdfPCell(image2);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(0);
                cell.setMinimumHeight(60f);
                table.addCell(cell);
                if(CHECK_SIGN_ARRAY.length==2){
                    image3 = Image.getInstance(url+CHECK_SIGN_ARRAY[1]);
                    image3.setIndentationLeft(120f);
                    //Scale to new height and new width of image
                    image3.scaleAbsolute(60, 60);
                    cell=new PdfPCell(image3);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(0);
                    cell.setMinimumHeight(60f);
                    table.addCell(cell);
                }else {
                    table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                }
            }else {
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            }
            table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));


            table.addCell(getPDFCell("被检查人员（签名）",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            if(!NOTICE_SIGH.equals("")){
                image2 = Image.getInstance(url+NOTICE_SIGH);
                image2.setIndentationLeft(100f);
                //Scale to new height and new width of image
                image2.scaleAbsolute(48, 48);
                cell=new PdfPCell(image2);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(0);
                cell.setMinimumHeight(60f);
                table.addCell(cell);
            }else {
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            }
            table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            document.add(table);



            String year=nowTime.substring(0,4);
            String month=nowTime.substring(4,6);
            String day=nowTime.substring(6,8);
            paragraph=new Paragraph(year+"年"+month+"月"+day+"日",para);
            paragraph.setFirstLineIndent(400f);
            document.add(paragraph);
            document.newPage();
            Image image ;
            //地址
            paragraph=new Paragraph("",para);
            paragraph.setLeading(20f);
            document.add(paragraph);

            chunk = new Chunk("附件",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);

            for(String TEMP_ID:hashMap.keySet()){
                String  TEMP_NAME=hashMap.get(TEMP_ID).toString();
                List<Map<String,Object>> tempList2=noticeViewDao.getItemRecordByIdAndTEMPIDAndState(RECORD_ID,TEMP_ID,"不合格");
                paragraph = new Paragraph(TEMP_NAME+"（不合格）:",para);
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
                table=new PdfPTable(3);
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
            }

            for(String TEMP_ID:hashMap1.keySet()) {
                String TEMP_NAME = hashMap1.get ( TEMP_ID ).toString ();
                List<Map<String, Object>> tempList = noticeViewDao.getItemRecordByIdAndTEMPIDAndState ( RECORD_ID, TEMP_ID, "合格" );
                paragraph = new Paragraph ( TEMP_NAME + "（合格）:", para );
                document.add ( paragraph );
                //得到所有合格图片的数组
                List<String> imgList = new ArrayList<> ();
                for (int i = 0; i < tempList.size (); i++) {
                    if (tempList.get ( i ).get ( "LOCATION_IMG" ) != null) {
                        String LOCATION_IMG = tempList.get ( i ).get ( "LOCATION_IMG" ).toString ();
                        String[] LOCATION_IMG_ARRAY = LOCATION_IMG.split ( "\\|" );
                        for (int j = 0; j < LOCATION_IMG_ARRAY.length; j++) {
                            imgList.add ( LOCATION_IMG_ARRAY[j] );
                        }
                    }
                    if (tempList.get ( i ).get ( "OTHER_IMG" ) != null) {
                        String OTHER_IMG = tempList.get ( i ).get ( "OTHER_IMG" ).toString ();
                        String[] OTHER_IMG_ARRAY = OTHER_IMG.split ( "\\|" );
                        for (int j = 0; j < OTHER_IMG_ARRAY.length; j++) {
                            imgList.add ( OTHER_IMG_ARRAY[j] );
                        }
                    }
                }
                table = new PdfPTable ( 3 );
                table.setWidthPercentage ( 100 );
                table.getDefaultCell ().setMinimumHeight ( 160f );
                table.setTotalWidth ( new float[]{160, 160, 160} );
                //显示图片
                for (int i = 0; i < imgList.size (); i++) {
                    image = Image.getInstance ( url + imgList.get ( i ) );
                    image.setIndentationLeft ( 100f );
                    //Scale to new height and new width of image
                    image.scaleAbsolute ( 150, 150 );
                    cell = new PdfPCell ( image );
                    cell.setHorizontalAlignment ( Element.ALIGN_CENTER );
                    cell.setVerticalAlignment ( Element.ALIGN_MIDDLE );
                    cell.setBorder ( 0 );
                    cell.setMinimumHeight ( 160f );
                    table.addCell ( cell );
                }
                if (imgList.size () % 3 != 0) {
                    table.addCell ( getPDFCell ( "", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false ) );
                    table.addCell ( getPDFCell ( "", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false ) );
                }
                //TODO
                document.add ( table );
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
                BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 201, 200, hints);
                Path file=new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);

            }catch (Exception e){
                System.out.print(e);
                logger.info ( e.toString () );
                return MsgUtil.errorMsg(e.toString());
            }
            noticeViewDao.updatePdfAndQrcode(pdfName,qrCodeName,RECORD_ID);


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
