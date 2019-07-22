package yjbd.yjbawechat.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.sun.prism.impl.BaseMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import yjbd.yjbawechat.Dao.PdfDal;
import yjbd.yjbawechat.Util.DateTimeUtils;
import yjbd.yjbawechat.Util.MsgUtil;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Description: $
 * @Param: $
 * @return: $
 * @Author: your name
 * @date: $
 */
@Service
public class PdfService  {
    @Resource
    PdfDal pdfDal;
    static final Logger logger = LoggerFactory.getLogger(PdfService.class);
    public PdfTemplate tpl;
    public BaseFont helv;


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
     * 执法记录文书
     * @param
     * @return
     * @throws IOException
     */
    public String createRecordPdf(String ID) throws IOException
    {
        Document document = new Document(PageSize.A4);
        String pdfPath="D:/zfbdzl/pdf/record/";
        String qrCodePath="D:/zfbdzl/qrcode/record/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=record/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹
        //设置日期格式
        List<Map<String,Object>> list=pdfDal.getById(ID);
        List<Map<String,Object>> itemRecordList=pdfDal.getItemRecordById(ID);
        HashMap<String,String> hashMap=new HashMap<>();
        for(int i=0;i<itemRecordList.size();i++){
            String  TEMP_ID=itemRecordList.get(i).get("TEMP_ID").toString();
            String  TEMP_NAME=itemRecordList.get(i).get("TEMP_NAME").toString();
            hashMap.put(TEMP_ID,TEMP_NAME);
        }
        List<Map<String,Object>> itemRecordList1=pdfDal.getItemRecord(ID);
        HashMap<String,String> hashMap1=new HashMap<>();
        for(int i=0;i<itemRecordList1.size();i++){
            String  TEMP_ID=itemRecordList1.get(i).get("TEMP_ID").toString();
            String  TEMP_NAME=itemRecordList1.get(i).get("TEMP_NAME").toString();
            hashMap1.put(TEMP_ID,TEMP_NAME);
        }


        String RECORD_ID=ID;//现场检查记录的自增id
        String CHECKED_UNIT="";//被检查单位
        String ADDRESS="";//被检单位地址
        String REPRESENT_PEOPLE="";//法定代表人
        String REPRESENT_MOBILE="";//法定代表人手机号码
        String CHECKED_LOCATION="";//检查场所
        String CHECKED_START_TIME="";//检查开始时间
        String CHECKED_END_TIME="";//检查结束时间
        String CHECK_UNIT1="";//检查单位1
        String CHECK_PEOPLE1="";//检查人员1
        String CARD_NUMBER1="";//证件号1
        String REPRESENT_SIGN="";//被检查代表签字图片
        String CHECK_SIGN="";//检查人员签字
        String CHECK2_SIGN="";//检查人员签字
        String CREATE_TIME="";//记录创建时间
        String RECORD_PDF="";//最终执法记录pdf
        String RECORD_QR_CODE="";//执法记录二维码地址
        String REPRESENT_SIGN_TIME="";///被检查代表签字时间
        String CHECK_SIGN_TIME="";//检查人员签字时间
        String UNIT_NUMBER="";///检查记录信息（从数据库获取的检查历史记录）
        String USER_ID="";//记录创建者的USER_ID
        String CHECK_UNIT2="";//检查单位2
        String CHECK_PEOPLE2="";//检查人员2
        String CARD_NUMBER2="";//证件号2
        String LEGAL_PEOPLE_NUMBER="";//法定人电话
        String CHECKED_REPRESENT_PEOPLE="";//被检查单位出现场负责人
        String CHECKED_SEX="";//被检查单位出现场负责人性别
        String CHECKED_REPRESENT_NUMBER="";//现场负责人证件
        String CHECKED_REPRESENT_DUTY="";//现场负责人职务
        String CHECKED_REPRESENT_MOBILE="";//现场负责人电话
        String WITNESS_PEOPLE="";//见证者姓名
        String WITNESS_SEX="";//见证者性别
        String WITNESS_NUMBER="";//见证者身份证
        String EXPERT_NAME1="";//专家1
        String EXPERT_UNIT1="";//专家1所在单位

        String EXPERT_SIGN="";//专家签字
        String EXPERT_SIGN_TIME="";//专家签字时间
        String WITNESS_SIGN="";//见证人签字
        String WITNESS_SIGN_TIME="";//见证人签字时间
        String AREA="";
        String AUDIT_AREA="";//检查记录文书的地址
        String AUDIT_RECORD="";//检查记录文书的编号
        if(list.get(0).get("AUDIT_AREA")!=null) AUDIT_AREA=list.get(0).get("AUDIT_AREA").toString();
        if(list.get(0).get("AUDIT_RECORD")!=null) AUDIT_RECORD=list.get(0).get("AUDIT_RECORD").toString();
        if(list.get(0).get("RECORD_ID")!=null) RECORD_ID=list.get(0).get("RECORD_ID").toString();
        if(list.get(0).get("CHECKED_UNIT")!=null) CHECKED_UNIT=list.get(0).get("CHECKED_UNIT").toString();
        if(list.get(0).get("ADDRESS")!=null) ADDRESS=list.get(0).get("ADDRESS").toString();
        if(list.get(0).get("REPRESENT_PEOPLE")!=null) REPRESENT_PEOPLE=list.get(0).get("REPRESENT_PEOPLE").toString();
        if(list.get(0).get("REPRESENT_MOBILE")!=null) REPRESENT_MOBILE=list.get(0).get("REPRESENT_MOBILE").toString();
        if(list.get(0).get("CHECKED_LOCATION")!=null) CHECKED_LOCATION=list.get(0).get("CHECKED_LOCATION").toString();
        if(list.get(0).get("CHECKED_START_TIME")!=null) CHECKED_START_TIME=list.get(0).get("CHECKED_START_TIME").toString();
        if(list.get(0).get("CHECKED_END_TIME")!=null) CHECKED_END_TIME=list.get(0).get("CHECKED_END_TIME").toString();
        if(list.get(0).get("CHECK_UNIT1")!=null) CHECK_UNIT1=list.get(0).get("CHECK_UNIT1").toString();
        if(list.get(0).get("CHECK_PEOPLE1")!=null) CHECK_PEOPLE1=list.get(0).get("CHECK_PEOPLE1").toString();
        if(list.get(0).get("CARD_NUMBER1")!=null) CARD_NUMBER1=list.get(0).get("CARD_NUMBER1").toString();
        if(list.get(0).get("REPRESENT_SIGN")!=null) REPRESENT_SIGN=list.get(0).get("REPRESENT_SIGN").toString();
        if(list.get(0).get("CHECK_SIGN")!=null) CHECK_SIGN=list.get(0).get("CHECK_SIGN").toString();
        if(list.get(0).get("CREATE_TIME")!=null) CREATE_TIME=list.get(0).get("CREATE_TIME").toString();
        if(list.get(0).get("RECORD_PDF")!=null) RECORD_PDF=list.get(0).get("RECORD_PDF").toString();
        if(list.get(0).get("REPRESENT_SIGN_TIME")!=null) REPRESENT_SIGN_TIME=list.get(0).get("REPRESENT_SIGN_TIME").toString();
        if(list.get(0).get("CHECK_SIGN_TIME")!=null) CHECK_SIGN_TIME=list.get(0).get("CHECK_SIGN_TIME").toString();
        if(list.get(0).get("UNIT_NUMBER")!=null) UNIT_NUMBER=list.get(0).get("UNIT_NUMBER").toString();
        if(list.get(0).get("USER_ID")!=null) USER_ID=list.get(0).get("USER_ID").toString();
        if(list.get(0).get("CHECK_UNIT2")!=null) CHECK_UNIT2=list.get(0).get("CHECK_UNIT2").toString();
        if(list.get(0).get("CHECK_PEOPLE2")!=null) CHECK_PEOPLE2=list.get(0).get("CHECK_PEOPLE2").toString();
        if(list.get(0).get("CARD_NUMBER2")!=null) CARD_NUMBER2=list.get(0).get("CARD_NUMBER2").toString();
        if(list.get(0).get("LEGAL_PEOPLE_NUMBER")!=null) LEGAL_PEOPLE_NUMBER=list.get(0).get("LEGAL_PEOPLE_NUMBER").toString();
        if(list.get(0).get("CHECKED_REPRESENT_PEOPLE")!=null) CHECKED_REPRESENT_PEOPLE=list.get(0).get("CHECKED_REPRESENT_PEOPLE").toString();
        if(list.get(0).get("CHECKED_SEX")!=null) CHECKED_SEX=list.get(0).get("CHECKED_SEX").toString();
        if(list.get(0).get("CHECKED_REPRESENT_NUMBER")!=null) CHECKED_REPRESENT_NUMBER=list.get(0).get("CHECKED_REPRESENT_NUMBER").toString();
        if(list.get(0).get("CHECKED_REPRESENT_DUTY")!=null) CHECKED_REPRESENT_DUTY=list.get(0).get("CHECKED_REPRESENT_DUTY").toString();
        if(list.get(0).get("CHECKED_REPRESENT_MOBILE")!=null) CHECKED_REPRESENT_MOBILE=list.get(0).get("CHECKED_REPRESENT_MOBILE").toString();
        if(list.get(0).get("WITNESS_PEOPLE")!=null) WITNESS_PEOPLE=list.get(0).get("WITNESS_PEOPLE").toString();
        if(list.get(0).get("WITNESS_SEX")!=null) WITNESS_SEX=list.get(0).get("WITNESS_SEX").toString();
        if(list.get(0).get("WITNESS_NUMBER")!=null) WITNESS_NUMBER=list.get(0).get("WITNESS_NUMBER").toString();
        if(list.get(0).get("EXPERT_NAME1")!=null) EXPERT_NAME1=list.get(0).get("EXPERT_NAME1").toString();
        if(list.get(0).get("EXPERT_UNIT1")!=null)EXPERT_UNIT1=list.get(0).get("EXPERT_UNIT1").toString();
        if(list.get(0).get("CHECK2_SIGN")!=null) CHECK2_SIGN=list.get(0).get("CHECK2_SIGN").toString();
        if(list.get(0).get("EXPERT_SIGN")!=null) EXPERT_SIGN=list.get(0).get("EXPERT_SIGN").toString();
        if(list.get(0).get("EXPERT_SIGN_TIME")!=null) EXPERT_SIGN_TIME=list.get(0).get("EXPERT_SIGN_TIME").toString();
        if(list.get(0).get("WITNESS_SIGN")!=null) WITNESS_SIGN=list.get(0).get("WITNESS_SIGN").toString();
        if(list.get(0).get("WITNESS_SIGN_TIME")!=null) WITNESS_SIGN_TIME=list.get(0).get("WITNESS_SIGN_TIME").toString();
        if(list.get(0).get("AREA")!=null) AREA=list.get(0).get("AREA").toString();



        try {
            File file1 =new File(pdfPath+date1);
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            File file2 =new File(qrCodePath+date1);
            if(!file2 .exists()  && !file2 .isDirectory()){
                file2 .mkdir();
            }

            //BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 20, Font.BOLD,BaseColor.BLACK);//加入document：
            Font para = new Font(bfChinese, 12, Font.NORMAL,BaseColor.BLACK);
            Font lineFont=new Font(bfChinese,2, Font.NORMAL,BaseColor.BLACK);
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(pdfPath+pdfName));
//            //设置页页事件监听器
//            Font pageNumFont = new Font(bfChinese,9,Font.NORMAL);
//            writer.setPageEvent(this);
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
//            line.add(new Chunk(lineSeparator));
//            line.add("");
//            document.add(line);
////
//            //直线
//            Paragraph line2 = new Paragraph("",lineFont);
//            LineSeparator lineSeparator2=new LineSeparator();
//            lineSeparator2.setLineWidth(0.5F);
//            line2.add(new Chunk(lineSeparator2));
//            line2.add("");
//            document.add(line2);
            //副标题:现场检查记录
            Paragraph subTitle=new Paragraph("现场检查记录",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);

            subTitle=new Paragraph("（"+AUDIT_AREA+"）应急检记〔"+nowTime.substring(0,4)+"〕("+AUDIT_RECORD+") 号",para);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);


            //time
            //设置行距，该处会影响后面所有的chunk的行距
            paragraph=new Paragraph("",para);
            paragraph.setLeading(25f);
            document.add(paragraph);

            chunk = new Chunk("检查时间：",para);
            document.add(chunk);
            String[] temp=CHECKED_START_TIME.split(" ");
            String[] temp1=temp[0].split("\\-");
            String[] temp2=temp[1].split("\\:");
            chunk = new Chunk("    "+temp1[0]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("年",para);
            document.add(chunk);
            chunk = new Chunk("    "+temp1[1]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("月",para);
            document.add(chunk);
            chunk = new Chunk("    "+temp1[2]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("日",para);
            document.add(chunk);
            chunk = new Chunk("    "+temp2[0]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("时",para);
            document.add(chunk);
            chunk = new Chunk("    "+temp2[1]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("分至",para);
            document.add(chunk);

            temp=CHECKED_END_TIME.split("T");
            temp1=temp[0].split("\\-");
            temp2=temp[1].split("\\:");
            chunk = new Chunk("    "+temp1[1]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("月",para);
            document.add(chunk);
            chunk = new Chunk("    "+temp1[2]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("日",para);
            document.add(chunk);
            chunk = new Chunk("    "+temp2[0]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("时",para);
            document.add(chunk);
            chunk = new Chunk("    "+temp2[1]+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("分",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
//
            //检查人、单位、证号
            chunk = new Chunk("检查人：",para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECK_PEOPLE1+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);

            chunk = new Chunk("单位：",para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECK_UNIT1+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);

            chunk = new Chunk("证号：",para);
            document.add(chunk);
            chunk = new Chunk("    "+CARD_NUMBER1+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);

            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            if (!CHECK_PEOPLE2.equals(""))
            {
                //检查人、单位、证号
                chunk = new Chunk("检查人：",para);
                document.add(chunk);
                chunk = new Chunk("    "+CHECK_PEOPLE2+"     ",para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                chunk = new Chunk("单位：",para);
                document.add(chunk);
                chunk = new Chunk("    "+CHECK_UNIT2+"     ",para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                chunk = new Chunk("证号：",para);
                document.add(chunk);
                chunk = new Chunk("    "+CARD_NUMBER2+"     ",para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }



            //被检查单位
            chunk = new Chunk("被检查单位：",para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECKED_UNIT+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //地址
            chunk = new Chunk("检查地址：",para);
            document.add(chunk);
            chunk = new Chunk("    "+ADDRESS,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);
            //法定代表人、电话
            chunk = new Chunk("法定代表人（负责人）：",para);
            document.add(chunk);
            chunk = new Chunk("          "+REPRESENT_PEOPLE+"           ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);

            chunk = new Chunk("联系电话：",para);
            document.add(chunk);
            chunk = new Chunk("      "+REPRESENT_MOBILE,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //被检查单位现场负责人、性别、身份证号
            chunk = new Chunk("被检查单位现场负责人：",para);
            document.add(chunk);
            chunk = new Chunk("   "+CHECKED_REPRESENT_PEOPLE+"   ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);

            chunk = new Chunk("性别：",para);
            document.add(chunk);
            chunk = new Chunk("   "+CHECKED_SEX+"   ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);

            chunk = new Chunk("身份证号：",para);
            document.add(chunk);
            chunk = new Chunk("   "+CHECKED_REPRESENT_NUMBER,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //职务、联系电话
            chunk = new Chunk("职务：",para);
            document.add(chunk);
            chunk = new Chunk(CHECKED_REPRESENT_DUTY+"  ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);

            chunk = new Chunk("联系电话：",para);
            document.add(chunk);
            chunk = new Chunk(CHECKED_REPRESENT_MOBILE,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            if (!WITNESS_PEOPLE.equals("")){
                //见证人、性别、身份证号
                chunk = new Chunk("见证人：",para);
                document.add(chunk);
                chunk = new Chunk(WITNESS_PEOPLE,para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                chunk = new Chunk("性别：",para);
                document.add(chunk);
                chunk = new Chunk(WITNESS_SEX,para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                chunk = new Chunk("身份证号：",para);
                document.add(chunk);
                chunk = new Chunk(WITNESS_NUMBER,para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }

            String[] expertName = EXPERT_NAME1.split("_");
            String[] expert = EXPERT_UNIT1.split("_");
            if (!EXPERT_NAME1.equals("")) {
                for(int i=0;i<expertName.length;i++){
                    chunk = new Chunk("检查专家：",para);
                    document.add(chunk);
                    chunk = new Chunk(expertName[i],para);
                    chunk.setUnderline(0.1f, -1f);
                    document.add(chunk);

                    chunk = new Chunk("   "+"；单位：",para);
                    document.add(chunk);
                    chunk = new Chunk(expert[i],para);
                    chunk.setUnderline(0.1f, -1f);
                    document.add(chunk);

                    document.add(new Chunk(lineSeparator));
                    document.add(Chunk.NEWLINE);
                }
            }



            //地址
            chunk = new Chunk("检查场所：",para);
            document.add(chunk);
            chunk = new Chunk(CHECKED_LOCATION,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);


            //事由和目的
            chunk = new Chunk("         事由和目的：",para);
            document.add(chunk);
            chunk = new Chunk("对你单位安全生产管理情况进行日常监督检查是否存在安全生产违法违规行为。",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);




            //过程和结果
            chunk = new Chunk("         过程和结果：",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);


            //用表格实现
            List<Map<String,Object>> qualifiedList=pdfDal.getItemRecordByServiceIdAndStateOrderByTempID(ID,"不合格");
            if (qualifiedList.size ()!=0){
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
            }


            //用表格实现
            List<Map<String,Object>> notQualifiedList=pdfDal.getItemRecordByServiceIdAndStateOrderByTempID(ID,"合格");
            if (notQualifiedList.size ()!=0){
                chunk = new Chunk("合格：",para);
                document.add(chunk);
                PdfPTable table=new PdfPTable(2);
                table.setWidthPercentage(90);
                table.setTotalWidth(new float[]{200,300});
                for(int i=0;i<notQualifiedList.size();i++){
                    String TEMP_NAME=notQualifiedList.get(i).get("TEMP_NAME").toString();
                    String CHECKE_DETAIL=notQualifiedList.get(i).get("CHECKE_DETAIL").toString();
                    table.addCell(getPDFCell(TEMP_NAME,para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,true));
                    table.addCell(getPDFCell(CHECKE_DETAIL,para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,true));
                }
                document.add(table);
            }

            document.add(Chunk.NEWLINE);
            //            //表格


            Image image2 ;

            PdfPTable table=new PdfPTable(4);
            table.setWidthPercentage(100);
            table.getDefaultCell().setMinimumHeight(60f);
            table.setTotalWidth(new float[]{180,60,60,200});


            //检查人员签名
            String[] CHECK_SIGN_ARRAY=CHECK_SIGN.split("\\|");
            table.addCell(getPDFCell("检查人员（签名）:",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            if(!CHECK_SIGN.equals("")){
                image2 = Image.getInstance(url+CHECK_SIGN_ARRAY[0]);
                image2.setIndentationLeft(100f);
                //Scale to new height and new width of image
                image2.scaleAbsolute(48, 48);
                PdfPCell cell=new PdfPCell(image2);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(0);
                cell.setMinimumHeight(60f);
                table.addCell(cell);
                if(CHECK_SIGN_ARRAY.length==2){
                    image2 = Image.getInstance(url+CHECK_SIGN_ARRAY[1]);
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
            }else {
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            }

            table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));

            //检查人员2签名
            String[] CHECK2_SIGN_ARRAY=CHECK2_SIGN.split("\\|");
            table.addCell(getPDFCell("检查人员2（签名）:",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            if(!CHECK_SIGN.equals("")){
                image2 = Image.getInstance(url+CHECK2_SIGN_ARRAY[0]);
                image2.setIndentationLeft(100f);
                //Scale to new height and new width of image
                image2.scaleAbsolute(48, 48);
                PdfPCell cell=new PdfPCell(image2);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(0);
                cell.setMinimumHeight(60f);
                table.addCell(cell);
                if(CHECK2_SIGN_ARRAY.length==2){
                    image2 = Image.getInstance(url+CHECK2_SIGN_ARRAY[1]);
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
            }else {
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            }
            table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));


            if (!EXPERT_SIGN.equals("")) {
                //专家签名
                table.addCell(getPDFCell("专家（签名）:", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
                if (!EXPERT_SIGN.equals("")) {
                    String[] EXPERT_SIGN_ARRAY = EXPERT_SIGN.split("\\|");
                    image2 = Image.getInstance(url + EXPERT_SIGN_ARRAY[0]);
                    image2.setIndentationLeft(100f);
                    image2.scaleAbsolute(48, 48);
                    PdfPCell cell = new PdfPCell(image2);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBorder(0);
                    cell.setMinimumHeight(60f);
                    table.addCell(cell);
                    if (EXPERT_SIGN_ARRAY.length == 2) {
                        image2 = Image.getInstance(url + EXPERT_SIGN_ARRAY[1]);
                        image2.setIndentationLeft(100f);
                        //Scale to new height and new width of image
                        image2.scaleAbsolute(48, 48);
                        cell = new PdfPCell(image2);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(0);
                        cell.setMinimumHeight(60f);
                        table.addCell(cell);
                    } else {
                        table.addCell(getPDFCell("", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
                    }
                } else {
                    table.addCell(getPDFCell("", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
                    table.addCell(getPDFCell("", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
                }
                table.addCell(getPDFCell("", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
            }

            //被检查单位现场负责人（签名）
            table.addCell(getPDFCell("被检查单位现场负责人（签名）:",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            if(!REPRESENT_SIGN.equals("")){
                image2 = Image.getInstance(url+REPRESENT_SIGN);
                image2.setIndentationLeft(100f);
                //Scale to new height and new width of image
                image2.scaleAbsolute(48, 48);
                PdfPCell cell=new PdfPCell(image2);
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


            if(!WITNESS_SIGN.equals("")){
                table.addCell(getPDFCell("见证人（签名）",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                if(!WITNESS_SIGN.equals("")){
                    image2 = Image.getInstance(url+WITNESS_SIGN);
                    image2.setIndentationLeft(100f);
                    //Scale to new height and new width of image
                    image2.scaleAbsolute(48, 48);
                    PdfPCell cell=new PdfPCell(image2);
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
            }
            document.add(table);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            paragraph=new Paragraph(CHECK_UNIT1,para);
            paragraph.setFirstLineIndent(370f);
            document.add(paragraph);

            //时间
            String year=nowTime.substring(0,4);
            String month=nowTime.substring(4,6);
            String day=nowTime.substring(6,8);
            paragraph=new Paragraph(year+"年"+month+"月"+day+"日",para);
            paragraph.setFirstLineIndent(370f);
            document.add(paragraph);

            //附件
            document.newPage();
            Image image ;
            paragraph=new Paragraph("",para);
            paragraph.setLeading(20f);
            document.add(paragraph);
            chunk = new Chunk("附件",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            for(String TEMP_ID:hashMap.keySet()){
                String  TEMP_NAME=hashMap.get(TEMP_ID).toString();
                List<Map<String,Object>> tempList2=pdfDal.getItemRecordByIdAndTEMPIDAndState(ID,TEMP_ID,"不合格");
                if (tempList2.size ()!=0){
                    paragraph = new Paragraph(TEMP_NAME+"（不合格）:",para);
                    document.add(paragraph);

                    //得到所有不合格图片的数组
                    List<String> imgList2=new ArrayList<>();
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
                        //Scale to new height and new width of image
                        image.scaleAbsolute(150, 150);
                        PdfPCell cell=new PdfPCell(image);
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

            }



            for(String TEMP_ID:hashMap1.keySet()) {
                String TEMP_NAME = hashMap1.get(TEMP_ID).toString();
                List<Map<String, Object>> tempList = pdfDal.getItemRecordByIdAndTEMPIDAndState(ID, TEMP_ID, "合格");
                if (tempList.size ()!=0){
                    paragraph = new Paragraph(TEMP_NAME + "（合格）:", para);
                    document.add(paragraph);
                    //得到所有合格图片的数组
                    List<String> imgList = new ArrayList<>();
                    for (int i = 0; i < tempList.size(); i++) {
                        if (tempList.get(i).get("LOCATION_IMG") != null) {
                            String LOCATION_IMG = tempList.get(i).get("LOCATION_IMG").toString();
                            String[] LOCATION_IMG_ARRAY = LOCATION_IMG.split("\\|");
                            for (int j = 0; j < LOCATION_IMG_ARRAY.length; j++) {
                                imgList.add(LOCATION_IMG_ARRAY[j]);
                            }
                        }
                        if (tempList.get(i).get("OTHER_IMG") != null) {
                            String OTHER_IMG = tempList.get(i).get("OTHER_IMG").toString();
                            String[] OTHER_IMG_ARRAY = OTHER_IMG.split("\\|");
                            for (int j = 0; j < OTHER_IMG_ARRAY.length; j++) {
                                imgList.add(OTHER_IMG_ARRAY[j]);
                            }
                        }
                    }
                    table = new PdfPTable(3);
                    table.setWidthPercentage(100);
                    table.getDefaultCell().setMinimumHeight(160f);
                    table.setTotalWidth(new float[]{160, 160, 160});
                    //显示图片
                    for (int i = 0; i < imgList.size(); i++) {
                        image = Image.getInstance(url + imgList.get(i));
                        image.setIndentationLeft(100f);
                        //Scale to new height and new width of image
                        image.scaleAbsolute(150, 150);
                        PdfPCell cell = new PdfPCell(image);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(0);
                        cell.setMinimumHeight(160f);
                        table.addCell(cell);
                    }
                    if (imgList.size() % 3 != 0) {
                        table.addCell(getPDFCell("", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
                        table.addCell(getPDFCell("", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
                    }
                    //TODO
                    document.add(table);
                }

            }



            String content=pdfAbsolutePath+pdfName;
            String qrCodeName=date1+"/"+nowTime+ random+".png";
            try{
                Map<EncodeHintType, Object> hints = new HashMap<>();
                //编码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                //边框距
                hints.put(EncodeHintType.MARGIN, 0);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
                Path file=new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);

            }catch (Exception e){
                System.out.print(e);
                logger.info ( e.toString () );
                return MsgUtil.errorMsg(e.toString());
            }
            pdfDal.updatePdfAndQrcode(pdfName,qrCodeName,ID);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info ( e.toString ());
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






    /**
     * 执法检查通知书
     * @param ID
     * @return
     * @throws IOException
     */
    public String createNoticePdf(String ID) throws IOException
    {
        Document document = new Document(PageSize.A4);
        String pdfPath ="D:/zfbdzl/pdf/notice/";
        String qrCodePath ="D:/zfbdzl/qrcode/notice/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=notice/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹
        //设置日期格式
        List<Map<String,Object>> list = pdfDal.getById(ID);

        String RECORD_ID = ID;  //现场检查记录的自增id
        String CHECKED_UNIT = "";//被检查单位
        String UNIT_NUMBER = "";//社会信用代码
        String REPRESENT_PEOPLE = "";//法定代表人
        String ADDRESS = "";//地址
        String CHECK_PEOPLE1 = "";//检查人1姓名
        String CHECK_UNIT1 = "";//检查人1单位
        String CARD_NUMBER1 ="";//证件号1
        String CHECK_PEOPLE2 = "";//检查人2姓名
        String CHECK_UNIT2 = "";//检查人2单位
        String CARD_NUMBER2 = "";//证件号2
        String EXPERT_NAME1 = "";//专家1姓名
        String EXPERT_UNIT1 = "";//专家1单位
        String NOTIFICATION_NUMBER = "";//通知书的编号
        String CHECKED_START_TIME = "";//检查开始时间
        String NOTICE_CONTENT = "";//检查内容
        String NOTICE_WAY = "";//检查方式
        String NOTICE_CONTACTS = "";//联系人
        String NOTICE_PHONE = "";//联系人电话
        String AREA = "";//检查单位所属地区
        String CREATE_TIME = DateTimeUtils.getNowDate();
        if(list.get(0).get("NOTIFICATION_NUMBER")!=null) NOTIFICATION_NUMBER = list.get(0).get("NOTIFICATION_NUMBER").toString();
        if(list.get(0).get("RECORD_ID")!=null) RECORD_ID = list.get(0).get("RECORD_ID").toString();
        if(list.get(0).get("CHECKED_UNIT")!=null) CHECKED_UNIT = list.get(0).get("CHECKED_UNIT").toString();
        if(list.get(0).get("UNIT_NUMBER")!=null) UNIT_NUMBER = list.get(0).get("UNIT_NUMBER").toString();
        if(list.get(0).get("REPRESENT_PEOPLE")!=null) REPRESENT_PEOPLE = list.get(0).get("REPRESENT_PEOPLE").toString();
        if(list.get(0).get("ADDRESS")!=null) ADDRESS = list.get(0).get("ADDRESS").toString();
        if(list.get(0).get("CHECK_PEOPLE1")!=null) CHECK_PEOPLE1 = list.get(0).get("CHECK_PEOPLE1").toString();
        if(list.get(0).get("CHECK_UNIT1")!=null) CHECK_UNIT1 = list.get(0).get("CHECK_UNIT1").toString();
        if(list.get(0).get("CARD_NUMBER1")!=null) CARD_NUMBER1 = list.get(0).get("CARD_NUMBER1").toString();

        if(list.get(0).get("CHECK_PEOPLE2")!=null) CHECK_PEOPLE2 = list.get(0).get("CHECK_PEOPLE2").toString();
        if(list.get(0).get("CHECK_UNIT2")!=null) CHECK_UNIT2 = list.get(0).get("CHECK_UNIT2").toString();
        if(list.get(0).get("CARD_NUMBER2")!=null) CARD_NUMBER2 = list.get(0).get("CARD_NUMBER2").toString();

        if(list.get(0).get("EXPERT_NAME1")!=null) EXPERT_NAME1 = list.get(0).get("EXPERT_NAME1").toString();
        if(list.get(0).get("EXPERT_UNIT1")!=null) EXPERT_UNIT1 = list.get(0).get("EXPERT_UNIT1").toString();

        if(list.get(0).get("CHECKED_START_TIME")!=null) CHECKED_START_TIME = list.get(0).get("CHECKED_START_TIME").toString();
        if(list.get(0).get("NOTICE_CONTENT")!=null) NOTICE_CONTENT = list.get(0).get("NOTICE_CONTENT").toString();
        if(list.get(0).get("NOTICE_WAY")!=null) NOTICE_WAY = list.get(0).get("NOTICE_WAY").toString();
        //if(list.get(0).get("CREATE_TIME")!=null) CREATE_TIME = list.get(0).get("CREATE_TIME").toString();
        if(list.get(0).get("NOTICE_CONTACTS")!=null) NOTICE_CONTACTS = list.get(0).get("NOTICE_CONTACTS").toString();
        if(list.get(0).get("NOTICE_PHONE")!=null) NOTICE_PHONE = list.get(0).get("NOTICE_PHONE").toString();
        if(list.get(0).get("AREA")!=null) AREA = list.get(0).get("AREA").toString();


        try {
            File file1 = new File(pdfPath+date1);
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            File file2 =new File(qrCodePath+date1);
            if(!file2 .exists()  && !file2 .isDirectory()){
                file2 .mkdir();
            }
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(pdfPath+pdfName));
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 20, Font.BOLD,BaseColor.BLACK);//加入document：
            Font para = new Font(bfChinese, 12, Font.NORMAL,BaseColor.BLACK);
            Font lineFont=new Font(bfChinese,2, Font.NORMAL,BaseColor.BLACK);
            document.addTitle("example of PDF");
            document.open();

            //主标题：安全生产行政执法文书现场检查记录
            Paragraph title=new Paragraph("执法监督检查通知书",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            //应急检记
            Paragraph subTitle2=new Paragraph("（" + AREA + "）应急检记[2019](" +NOTIFICATION_NUMBER + ")号",para);
            subTitle2.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle2);
            document.add(Chunk.NEWLINE);

            //设置行距，该处会影响后面所有的chunk的行距
            Paragraph paragraph=new Paragraph("",para);
            paragraph.setLeading(25f);
            document.add(paragraph);


            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            //补全下划线至行尾
            LineSeparator lineSeparator=new LineSeparator();
            lineSeparator.setOffset(-1f);
            lineSeparator.setLineWidth(0.1F);
            Chunk chunk;


            //被检查单位
            Chunk checkUnit = new Chunk("被检查单位：",para);
            document.add(checkUnit);
            Chunk checkUnit2 = new Chunk("    " + CHECKED_UNIT + "     ",para);
            checkUnit2.setUnderline(0.1f, -1f);
            document.add(checkUnit2);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //社会信用代码、法定代表人
            Chunk code = new Chunk("社会信用代码：",para);
            document.add(code);
            Chunk code2 = new Chunk(UNIT_NUMBER,para);
            code2.setUnderline(0.1f, -1f);
            document.add(code2);

            Chunk responsePeople = new Chunk(" ; 法定代表人：",para);
            document.add(responsePeople);
            Chunk responsePeople2 = new Chunk(REPRESENT_PEOPLE,para);
            responsePeople2.setUnderline(0.1f, -1f);
            document.add(responsePeople2);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //地址
            Chunk address = new Chunk("检查地址：",para);
            document.add(address);
            Chunk address2 = new Chunk(ADDRESS,para);
            address2.setUnderline(0.1f, -1f);
            document.add(address2);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //检查人姓名及单位及证件号
            Chunk checkPerson = new Chunk("检查人姓名、单位和证件号：",para);
            document.add(checkPerson);
            document.add(Chunk.NEWLINE);
            Chunk checkPerson1 = new Chunk("         "+CHECK_PEOPLE1,para);
            checkPerson1.setUnderline(0.1f, -1f);
            document.add(checkPerson1);

            Chunk checkPerson11 = new Chunk("、",para);
            checkPerson11.setUnderline(0.1f, -1f);
            document.add(checkPerson11);

            Chunk peopleAddress1 = new Chunk(CHECK_UNIT1,para);
            peopleAddress1.setUnderline(0.1f, -1f);
            document.add(peopleAddress1);

            Chunk peopleAddress11 = new Chunk("、",para);
            peopleAddress11.setUnderline(0.1f, -1f);
            document.add(peopleAddress11);

            Chunk card1 = new Chunk(CARD_NUMBER1,para);
            card1.setUnderline(0.1f, -1f);
            document.add(card1);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            if (!CHECK_PEOPLE2.equals(""))
            {
                Chunk checkPerson2 = new Chunk("        "+CHECK_PEOPLE2,para);
                checkPerson2.setUnderline(0.1f, -1f);
                document.add(checkPerson2);

                Chunk checkPerson21 = new Chunk("、",para);
                checkPerson21.setUnderline(0.1f, -1f);
                document.add(checkPerson21);

                Chunk peopleAddress2 = new Chunk(CHECK_UNIT1,para);
                peopleAddress2.setUnderline(0.1f, -1f);
                document.add(peopleAddress2);

                Chunk peopleAddress21 = new Chunk("、",para);
                peopleAddress21.setUnderline(0.1f, -1f);
                document.add(peopleAddress21);

                Chunk card2 = new Chunk(CARD_NUMBER2,para);
                card2.setUnderline(0.1f, -1f);
                document.add(card2);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }


            String[] expertName = EXPERT_NAME1.split("_");
            String[] expert = EXPERT_UNIT1.split("_");
            if (!EXPERT_NAME1.equals("")) {
                for(int i=0;i<expertName.length;i++){
                    chunk = new Chunk("检查专家：",para);
                    document.add(chunk);
                    chunk = new Chunk(expertName[i],para);
                    chunk.setUnderline(0.1f, -1f);
                    document.add(chunk);

                    chunk = new Chunk("   "+"；单位：",para);
                    document.add(chunk);
                    chunk = new Chunk(expert[i],para);
                    chunk.setUnderline(0.1f, -1f);
                    document.add(chunk);

                    document.add(Chunk.NEWLINE);
                }
            }



           /* if (!EXPERT_NAME1.equals("")){
                //专家及单位1
                Chunk expertPerson1 = new Chunk("专家：",para);
                document.add(expertPerson1);
                Chunk expertPerson11 = new Chunk(EXPERT_NAME1,para);
                expertPerson11.setUnderline(0.1f, -1f);
                document.add(expertPerson11);

                Chunk expertAddress1 = new Chunk("   "+"；单位：",para);
                document.add(expertAddress1);
                Chunk expertAddress11 = new Chunk(EXPERT_UNIT1,para);
                expertAddress11.setUnderline(0.1f, -1f);
                document.add(expertAddress11);
                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }*/



            Chunk chunk1 = new Chunk("         依据《中华人民共和国安全生产法》等法律法规，现定于",para);
            document.add(chunk1);
            String[] temp=CHECKED_START_TIME.split("T");
            String[] time=temp[0].split("\\-");
            chunk1 = new Chunk(time[0],para);
            document.add(chunk1);
            chunk1 = new Chunk("年",para);
            document.add(chunk1);
            chunk1 = new Chunk(time[1],para);
            document.add(chunk1);
            chunk1 = new Chunk("月",para);
            document.add(chunk1);
            chunk1 = new Chunk(time[2],para);
            document.add(chunk1);
            chunk1 = new Chunk("日",para);
            document.add(chunk1);


            Chunk chunk2 = new Chunk("对你单位依法实施日常监督管理检查，先将有关事项通知如下： ",para);
            document.add(chunk2);
            document.add(Chunk.NEWLINE);

            //检查内容
            Chunk chunk3 = new Chunk("         一、检查内容：",para);
            document.add(chunk3);
            Chunk content = new Chunk(NOTICE_CONTENT,para);
            content.setUnderline(0.1f, -1f);
            document.add(content);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //检查方式
            Chunk chunk4 = new Chunk("         二、检查方式：",para);
            document.add(chunk4);
            Chunk way = new Chunk(NOTICE_WAY, para);
            way.setUnderline(0.1f, -1f);
            document.add(way);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            //说明
            Chunk chunk5 = new Chunk("         请你单位予以配合和支持。拒绝接受检查或拒绝提供有关资料的，依法追究法律责任。",para);
            document.add(chunk5);
            document.add(Chunk.NEWLINE);

            //联系人、电话
            Chunk contacts = new Chunk("         联系人：", para);
            document.add(contacts);
            Chunk contacts1 = new Chunk(NOTICE_CONTACTS, para);
            contacts1.setUnderline(0.1f, -1f);
            document.add(contacts1);

            Chunk phone = new Chunk("；"+"电话：", para);
            document.add(phone);
            Chunk phone1 = new Chunk(NOTICE_PHONE);
            phone1.setUnderline(0.1f, -1f);
            document.add(phone1);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            //落款
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,  CHECK_UNIT1, 550f, 260f, 0);
            cb.endText();
            //创建时间
            PdfContentByte cb2 = writer.getDirectContent();
            BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb2.beginText();
            cb2.setFontAndSize(bf2, 12);
            cb2.showTextAligned(PdfContentByte.ALIGN_RIGHT,  CREATE_TIME, 550f, 240f, 0);
            cb2.endText();

            //预览二维码
            String QR=pdfAbsolutePath+pdfName;
            String qrCodeName=date1+"/"+nowTime+ random+".png";
            try{
                Map<EncodeHintType, Object> hints = new HashMap<>();
                //编码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                //边框距
                hints.put(EncodeHintType.MARGIN, 0);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bm = qrCodeWriter.encode(QR, BarcodeFormat.QR_CODE, 200, 200, hints);
                Path file = new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);
            }catch (Exception e){
                System.out.print(e);
                logger.info ( e.toString () );
                return MsgUtil.errorMsg(e.toString());
            }
            pdfDal.updateNoticePdfAndQrcode(pdfName,qrCodeName,ID);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtil.errorMsg(e.toString());
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
     * 专家检查文书
     * @param ID
     * @return
     * @throws IOException
     */
    public String createExpertRecordPdf(String ID) throws IOException
    {
        Document document = new Document(PageSize.A4);
        String pdfPath="D:/zfbdzl/pdf/expert_record/";
        String qrCodePath="D:/zfbdzl/qrcode/expert_record/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=expert_record/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹
        //设置日期格式
//
//       Map<String,Object> map=new HashMap<>();
//       map.put("","");
//       List<Map<String,Object>> list=new ArrayList<>();
//       list.add(map);
        List<Map<String,Object>> list=pdfDal.getEXById(ID);
        List<Map<String,Object>> itemRecordList=pdfDal.getEXItemRecordById(ID);
        HashMap<String,String> hashMap=new HashMap<>();
        for(int i=0;i<itemRecordList.size();i++){
            String  TEMP_ID=itemRecordList.get(i).get("TEMP_ID").toString();
            String  TEMP_NAME=itemRecordList.get(i).get("TEMP_NAME").toString();
            hashMap.put(TEMP_ID,TEMP_NAME);
        }
        List<Map<String,Object>> itemRecordList1=pdfDal.getEXItemRecordById1(ID);
        HashMap<String,String> hashMap1=new HashMap<>();
        for(int i=0;i<itemRecordList1.size();i++){
            String  TEMP_ID=itemRecordList1.get(i).get("TEMP_ID").toString();
            String  TEMP_NAME=itemRecordList1.get(i).get("TEMP_NAME").toString();
            hashMap1.put(TEMP_ID,TEMP_NAME);
        }
//
//
//
//
//

        String CHECKED_UNIT="";//被检查单位
        String UNIT_NUMBER="";//社会信用代码
        String ADDRESS="";//被检单位地址
        String REPRESENT_PEOPLE="";//法定代表人
        String REPRESENT_MOBILE="";//法定代表人手机号码

        String CHECKED_LOCATION="";//检查场所
        String CHECKED_START_TIME="";//检查开始时间
        String CHECKED_END_TIME="";//检查结束时间
        String EXPERT_NAME1="";//专家1
        String EXPERT_UNIT1="";//专家1所在单位
        String EXPERT_NAME2="";///专家2
        String EXPERT_UNIT2="";//专家2所在单位
        String EXPERT_SIGN="";//专家签字
        String CREATE_TIME="";
        String WITNESS_SIGN="";//见证人签字



        if(list.get(0).get("CHECKED_UNIT")!=null) CHECKED_UNIT=list.get(0).get("CHECKED_UNIT").toString();
        if(list.get(0).get("UNIT_NUMBER")!=null) UNIT_NUMBER=list.get(0).get("UNIT_NUMBER").toString();

        if(list.get(0).get("ADDRESS")!=null) ADDRESS=list.get(0).get("ADDRESS").toString();
        if(list.get(0).get("REPRESENT_PEOPLE")!=null) REPRESENT_PEOPLE=list.get(0).get("REPRESENT_PEOPLE").toString();
        if(list.get(0).get("REPRESENT_MOBILE")!=null) REPRESENT_MOBILE=list.get(0).get("REPRESENT_MOBILE").toString();

        if(list.get(0).get("CHECKED_LOCATION")!=null) CHECKED_LOCATION=list.get(0).get("CHECKED_LOCATION").toString();
        if(list.get(0).get("CHECKED_START_TIME")!=null) CHECKED_START_TIME=list.get(0).get("CHECKED_START_TIME").toString();
        if(list.get(0).get("CREATE_TIME")!=null) CREATE_TIME=list.get(0).get("CREATE_TIME").toString();
        if(list.get(0).get("CHECKED_END_TIME")!=null) CHECKED_END_TIME=list.get(0).get("CHECKED_END_TIME").toString();

        if(list.get(0).get("EXPERT_NAME1")!=null) EXPERT_NAME1=list.get(0).get("EXPERT_NAME1").toString();
        if(list.get(0).get("EXPERT_UNIT1")!=null)EXPERT_UNIT1=list.get(0).get("EXPERT_UNIT1").toString();
        if(list.get(0).get("EXPERT_NAME2")!=null) EXPERT_NAME2=list.get(0).get("EXPERT_NAME2").toString();
        if(list.get(0).get("EXPERT_UNIT2")!=null) EXPERT_UNIT2=list.get(0).get("EXPERT_UNIT2").toString();
        if(list.get(0).get("EXPERT_SIGN")!=null) EXPERT_SIGN=list.get(0).get("EXPERT_SIGN").toString();

        if(list.get(0).get("WITNESS_SIGN")!=null) WITNESS_SIGN=list.get(0).get("WITNESS_SIGN").toString();




        try {
            File file1 =new File(pdfPath+date1);
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            File file2 =new File(qrCodePath+date1);
            if(!file2 .exists()  && !file2 .isDirectory()){
                file2 .mkdir();
            }

            //BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 20, Font.BOLD,BaseColor.BLACK);//加入document：
            Font smallTitle = new Font(bfChinese, 16, Font.NORMAL,BaseColor.GRAY);
            Font para = new Font(bfChinese, 12, Font.NORMAL,BaseColor.BLACK);
            Font lineFont=new Font(bfChinese,2, Font.NORMAL,BaseColor.BLACK);
            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(pdfPath+pdfName));
//            //设置页页事件监听器
//            Font pageNumFont = new Font(bfChinese,9,Font.NORMAL);
//            writer.setPageEvent(this);
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
//            line.add(new Chunk(lineSeparator));
//            line.add("");
//            document.add(line);
////
//            //直线
//            Paragraph line2 = new Paragraph("",lineFont);
//            LineSeparator lineSeparator2=new LineSeparator();
//            lineSeparator2.setLineWidth(0.5F);
//            line2.add(new Chunk(lineSeparator2));
//            line2.add("");
//            document.add(line2);

            //主标题
            Paragraph title=new Paragraph(""+CHECKED_UNIT,titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            //副标题:现场检查记录
            Paragraph subTitle=new Paragraph("专家安全检查记录",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);

            /*subTitle=new Paragraph("（"+AREA+"）应急检记〔"+nowTime.substring(0,4)+"〕"+ID+"号",para);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);*/


            //time
            //设置行距，该处会影响后面所有的chunk的行距
            paragraph=new Paragraph("",para);
            paragraph.setLeading(20f);
            document.add(paragraph);



            chunk = new Chunk("检查开始时间：",para);
            document.add(chunk);
            String[] temp=CREATE_TIME.split(" ");
            chunk = new Chunk("    "+temp[0]+" "+temp[1],para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            chunk = new Chunk("检查结束时间：",para);
            document.add(chunk);
            String[] temp1=CHECKED_END_TIME.split(" ");
            chunk = new Chunk("    "+temp1[0]+" "+temp1[1]+":08",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(Chunk.NEWLINE);


            //被检查单位
            chunk = new Chunk("被检查单位：",para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECKED_UNIT+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //检查专家
            chunk = new Chunk("检查专家：",para);
            document.add(chunk);
            String[] expertName = EXPERT_NAME1.split("_");
            for(int i=0;i<expertName.length;i++){
                chunk = new Chunk("          "+expertName[i]+"           ",para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);
            }
            document.add(Chunk.NEWLINE);

            //被检查单位
            chunk = new Chunk("检查场所：",para);
            document.add(chunk);
            chunk = new Chunk("    "+CHECKED_LOCATION+"     ",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);


            //检查单位地址
            chunk = new Chunk("检查单位地址：",para);
            document.add(chunk);
            chunk = new Chunk(ADDRESS,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            //检查情况
            chunk = new Chunk("检查情况：",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            //用表格实现
            List<Map<String,Object>> qualifiedList=pdfDal.getEXItemRecordByServiceIdAndStateOrderByTempID(ID,"不合格");
            if (qualifiedList.size ()!=0){
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
            }


            //用表格实现
            List<Map<String,Object>> notQualifiedList=pdfDal.getEXItemRecordByServiceIdAndStateOrderByTempID(ID,"合格");
            if (notQualifiedList.size ()!=0){
                chunk = new Chunk("合格：",para);
                document.add(chunk);
                PdfPTable table=new PdfPTable(2);
                table.setWidthPercentage(90);
                table.setTotalWidth(new float[]{200,300});
                for(int i=0;i<notQualifiedList.size();i++){
                    String TEMP_NAME=notQualifiedList.get(i).get("TEMP_NAME").toString();
                    String CHECKE_DETAIL=notQualifiedList.get(i).get("CHECKE_DETAIL").toString();
                    table.addCell(getPDFCell(TEMP_NAME,para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,true));
                    table.addCell(getPDFCell(CHECKE_DETAIL,para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,true));
                }
                document.add(table);
            }
            document.add(Chunk.NEWLINE);
            //            //表格
            Image image2 ;

            PdfPTable table=new PdfPTable(4);
            table.setWidthPercentage(100);
            table.getDefaultCell().setMinimumHeight(60f);
            table.setTotalWidth(new float[]{180,60,60,200});

            //专家签名
            table.addCell(getPDFCell("专家（签名）:",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            if(!EXPERT_SIGN.equals("")){
                String[] EXPERT_SIGN_ARRAY=EXPERT_SIGN.split("\\|");
                image2 = Image.getInstance(url+EXPERT_SIGN_ARRAY[0]);
                image2.setIndentationLeft(100f);
                //Scale to new height and new width of image
                image2.scaleAbsolute(48, 48);
                PdfPCell cell=new PdfPCell(image2);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(0);
                cell.setMinimumHeight(60f);
                table.addCell(cell);
                if(EXPERT_SIGN_ARRAY.length==2){
                    image2 = Image.getInstance(url+EXPERT_SIGN_ARRAY[1]);
                    image2.setIndentationLeft(100f);
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
            }else {
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            }
            table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));




            table.addCell(getPDFCell("被检查单位（签名）",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            if(!WITNESS_SIGN.equals("")){
                image2 = Image.getInstance(url+WITNESS_SIGN);
                image2.setIndentationLeft(100f);
                //Scale to new height and new width of image
                image2.scaleAbsolute(48, 48);
                PdfPCell cell=new PdfPCell(image2);
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
                List<Map<String,Object>> tempList=pdfDal.getEXItemRecordByIdAndTEMPIDAndState(ID,TEMP_ID,"合格");
                if (tempList.size ()!=0){
                    paragraph = new Paragraph(TEMP_NAME+"（合格）:",para);
                    document.add(paragraph);


                    //得到所有合格图片的数组
                    List<String> imgList=new ArrayList<>();
                    for(int i=0;i<tempList.size();i++){
                        if(tempList.get(i).get("LOCATION_IMG")!=null){
                            String LOCATION_IMG=tempList.get(i).get("LOCATION_IMG").toString();
                            String[] LOCATION_IMG_ARRAY=LOCATION_IMG.split("\\|");
                            for(int j=0;j<LOCATION_IMG_ARRAY.length;j++){
                                imgList.add(LOCATION_IMG_ARRAY[j]);
                            }
                        }
                        if(tempList.get(i).get("OTHER_IMG")!=null){
                            String OTHER_IMG=tempList.get(i).get("OTHER_IMG").toString();
                            String[] OTHER_IMG_ARRAY=OTHER_IMG.split("\\|");
                            for(int j=0;j<OTHER_IMG_ARRAY.length;j++){
                                imgList.add(OTHER_IMG_ARRAY[j]);
                            }
                        }
                    }
                    table=new PdfPTable(3);
                    table.setWidthPercentage(100);
                    table.getDefaultCell().setMinimumHeight(160f);
                    table.setTotalWidth(new float[]{160,160,160});
                    //显示图片
                    for(int i=0;i<imgList.size();i++){
                        image = Image.getInstance(url+imgList.get(i));
                        image.setIndentationLeft(100f);
                        //Scale to new height and new width of image
                        image.scaleAbsolute(150, 150);
                        PdfPCell cell=new PdfPCell(image);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(0);
                        cell.setMinimumHeight(160f);
                        table.addCell(cell);
                    }
                    if(imgList.size()%3!=0){
                        table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                        table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
                    }
                    //TODO
                    document.add(table);
                }

            }

            for(String TEMP_ID:hashMap1.keySet()) {
                String TEMP_NAME = hashMap1.get(TEMP_ID).toString();
                List<Map<String, Object>> tempList2 = pdfDal.getEXItemRecordByIdAndTEMPIDAndState(ID, TEMP_ID, "不合格");
                if (tempList2.size ()!=0){
                    paragraph = new Paragraph(TEMP_NAME + "（不合格）:", para);
                    document.add(paragraph);
                    //得到所有不合格图片的数组
                    List<String> imgList2 = new ArrayList<>();
                    for (int i = 0; i < tempList2.size(); i++) {
                        if (tempList2.get(i).get("LOCATION_IMG") != null) {
                            String LOCATION_IMG = tempList2.get(i).get("LOCATION_IMG").toString();
                            String[] LOCATION_IMG_ARRAY = LOCATION_IMG.split("\\|");
                            for (int j = 0; j < LOCATION_IMG_ARRAY.length; j++) {
                                imgList2.add(LOCATION_IMG_ARRAY[j]);
                            }
                        }
                        if (tempList2.get(i).get("OTHER_IMG") != null) {
                            String OTHER_IMG = tempList2.get(i).get("OTHER_IMG").toString();
                            String[] OTHER_IMG_ARRAY = OTHER_IMG.split("\\|");
                            for (int j = 0; j < OTHER_IMG_ARRAY.length; j++) {
                                imgList2.add(OTHER_IMG_ARRAY[j]);
                            }
                        }
                    }
                    table = new PdfPTable(3);
                    table.setWidthPercentage(100);
                    table.getDefaultCell().setMinimumHeight(160f);
                    table.setTotalWidth(new float[]{160, 160, 160});
                    //显示图片
                    for (int i = 0; i < imgList2.size(); i++) {
                        image = Image.getInstance(url + imgList2.get(i));
                        image.setIndentationLeft(100f);
                        //Scale to new height and new width of image
                        image.scaleAbsolute(150, 150);
                        PdfPCell cell = new PdfPCell(image);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setBorder(0);
                        cell.setMinimumHeight(160f);
                        table.addCell(cell);
                    }
                    if (imgList2.size() % 3 != 0) {
                        table.addCell(getPDFCell("", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
                        table.addCell(getPDFCell("", para, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, false));
                    }
                    //TODO
                    document.add(table);
                }

            }



//            //预览二维码
            String content=pdfAbsolutePath+pdfName;
            String qrCodeName=date1+"/"+nowTime+ random+".png";
            try{
                Map<EncodeHintType, Object> hints = new HashMap<>();
                //编码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                //边框距
                hints.put(EncodeHintType.MARGIN, 0);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
                Path file=new File(qrCodePath+qrCodeName).toPath();
                MatrixToImageWriter.writeToPath(bm, "png", file);

            }catch (Exception e){
                System.out.print(e);
                logger.info ( e.toString () );
                return MsgUtil.errorMsg(e.toString());
            }
            pdfDal.updateEXPdfAndQrcode(pdfName,qrCodeName,ID);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info ( e.toString () );
            return MsgUtil.errorMsg(e.toString());
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
