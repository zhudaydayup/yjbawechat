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
import yjbd.yjbawechat.Dao.PdfThreeDal;
import yjbd.yjbawechat.Util.MsgUtil;
import yjbd.yjbawechat.Util.MsgUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 随心的小新
 * @Date: 2019/4/7 9:22
 */
@Service
public class PdfThreeService {
    @Resource
    PdfThreeDal pdfThreeDal;
    static final Logger logger = LoggerFactory.getLogger(PdfThreeService.class);
    /**
     * 获取被执法单位名称
     */
    public String getZhiFaRenById(String zeLingId){
        String responseText = "";
        try {
            List<Map> mapList = pdfThreeDal.getZhiFaRenById(zeLingId);

//        String CHECKED_UNIT = mapList.get ( 0 ).get ( "CHECKED_UNIT" ).toString ();//被执法单位
            responseText = JSON.toJSONString ( mapList);
            responseText =  "[" + responseText + "]";
        }catch (Exception e){
            logger.info ( e.toString () );
        }

        return responseText;
    }

    /**
     * 获取存在的问题列表
     */
    public String getProblemById(String zeLingId){
        String responseText = "";
        try {
            List<Map> mapList = pdfThreeDal.getProblemById(zeLingId);
//        for (int i = 0;i<mapList.size ();i++)
//        {
//            if (i == mapList.size ()-1){
//                String CHECKE_DETAIL = mapList.get ( i ).get ( "CHECKE_DETAIL" ).toString ();
//                responseText = JSON.toJSONString ( CHECKE_DETAIL);
//            }
//        }
//        String CHECKE_DETAIL = mapList.get ( 0 ).get ( "CHECK_STATE" ).toString ();
//        if (CHECK_STATE.equals ( "不合格" ))
//            String CHECKE_DETAIL = mapList.get ( 0 ).get ( "CHECKE_DETAIL" ).toString ();
//        break
//        String CHECKE_DETAIL = mapList.get ( i ).get ( "CHECKE_DETAIL" ).toString ();
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
    public String createZeLingBiao(String TIME_IDS,String EXECUTE_PEOPLE2,String CARD_NUMBER2,String CHECKE_UNIT, String EXECUTE_PEOPLE,String CARD_NUMBER,String CHECKE_PROBLEM_IDS ,String CHECKED_END_TIME,String RECORD_ID,String ZF_ORDER_DEADLINE_AREA,String ZF_ORDER_DEADLINE_RECORD) {
        String responseText="";
        int count = 0;


        try {
            count= pdfThreeDal.getCreatePaperInfoCount(RECORD_ID);
            if (count>0){
                int flag = pdfThreeDal.createPaperInfo1(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,ZF_ORDER_DEADLINE_AREA,ZF_ORDER_DEADLINE_RECORD,RECORD_ID);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }else {
                int flag = pdfThreeDal.createPaperInfo(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID,ZF_ORDER_DEADLINE_AREA,ZF_ORDER_DEADLINE_RECORD);
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
     * 上传执法者1签名
     */
    public String UpdateCheck(String CHECKE_SIGH, String RecordId) {
        String responseText="";

        try {
            int flag = pdfThreeDal.UpdateCheck(CHECKE_SIGH, RecordId);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            logger.info ( e.toString () );
            responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
        return responseText;
    }

    /**
     * 上传执法者2签名
     */
    public String UpdateCheckTwoSign(String CHECKETWO_SIGH, String RecordId) {
        String responseText="";
        try {
            int flag = pdfThreeDal.UpdateCheckTwoSign(CHECKETWO_SIGH, RecordId);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            logger.info ( e.toString () );
            responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }
        return responseText;
    }

    /**
     * 上传被执法单位签名
     */
    public String UpdateRepresent(String REPRESENR_SIGN, String RecordId) {
        String responseText="";
        try {
            int flag = pdfThreeDal.UpdateRepresent(REPRESENR_SIGN, RecordId);
            if (flag > 0) {
                responseText = MsgUtils.successMsg();
            }
            else {
                responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
            }
        }catch (Exception e){
            logger.info ( e.toString ());
            responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }

        return responseText;
    }

    /**
     * 上传见证人签名
     */
    public String UpdateJianZhengRen(String WITNESS_SIGH, String RecordId) {
        String responseText="";
        try {
            int flag = pdfThreeDal.UpdateJianZhengRen(WITNESS_SIGH, RecordId);
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
            List<Map> list=pdfThreeDal.getIdRecord(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            logger.info ( e.toString () );
            responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
        }

        return responseText;

    }



    /**
     * 记录专家责令整改信息
     */
    public String setExZeLingInfo(String TIME_IDS,String EXECUTE_PEOPLE2,String CARD_NUMBER2,String CHECKED_UNIT, String EXECUTE_PEOPLE,String CARD_NUMBER,String CHECKE_PROBLEM_IDS ,String CHECKED_END_TIME,String RECORD_ID) {
        String responseText="";
        int count=0;
        try {
            count=pdfThreeDal.checkTemporaryRecordIdCount(RECORD_ID);
            if (count>0){
                int flag = pdfThreeDal.setExZeLingInfo1(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKED_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }else {
                int flag = pdfThreeDal.setExZeLingInfo(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKED_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID);
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
     * 缓存更新记录专家责令整改信息
     */
    public String temporarySetExZeLingInfo(String TIME_IDS,String EXECUTE_PEOPLE2,String CARD_NUMBER2,String CHECKED_UNIT, String EXECUTE_PEOPLE,String CARD_NUMBER,String CHECKE_PROBLEM_IDS ,String CHECKED_END_TIME,String URL,String RECORD_ID) {
        String responseText="";
        int count=0;
        try {
            count=pdfThreeDal.checkTemporaryRecordIdCount(RECORD_ID);
            if (count>0){
                int flag = pdfThreeDal.temporarySetExZeLingInfo(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKED_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,URL,RECORD_ID);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }else {
                int flag = pdfThreeDal.temporarySetExZeLingInfo1(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKED_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,RECORD_ID,URL);
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
     * 缓存更新记录专家责令整改信息
     */
    public String getTemporarySetExZeLingInfo(String RecordId) {
        String responseText="";
        List<Map> mapList=pdfThreeDal.getTemporarySetExZeLingInfo(RecordId);
        responseText = JSON.toJSONString ( mapList);
        return responseText;
    }


    public String getExRecord(String recordId) {
        String responseText="";
        try {
            List<Map> list=pdfThreeDal.getExRecord(recordId);
            responseText = MsgUtils.successMsg("RecordInfo", list);
        }catch (Exception e){
            e.printStackTrace ();
            logger.info ( e.toString () );
        }

        return responseText;

    }
    /**
     * 缓存政府执法责令限期表格信息
     */
    public String createTemporaryZeLingBiao(String TIME_IDS,String EXECUTE_PEOPLE2,String CARD_NUMBER2,String CHECKE_UNIT, String EXECUTE_PEOPLE,String CARD_NUMBER,String CHECKE_PROBLEM_IDS ,String CHECKED_END_TIME,String URL,String RECORD_ID) {
        String responseText="";
        int count = 0;
        try {
            count= pdfThreeDal.getCreatePaperInfoCount(RECORD_ID);
            if (count>0){
                int flag = pdfThreeDal.createTemporaryZeLingBiao1(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,URL,RECORD_ID);
                if (flag > 0) {
                    responseText = MsgUtils.successMsg("recordId",RECORD_ID);
                }
                else {
                    responseText = MsgUtils.errorMsg("网络繁忙，请稍后再试...");
                }
            }else {
                int flag = pdfThreeDal.createTemporaryZeLingBiao(TIME_IDS,EXECUTE_PEOPLE2, CARD_NUMBER2,CHECKE_UNIT, EXECUTE_PEOPLE, CARD_NUMBER, CHECKE_PROBLEM_IDS,CHECKED_END_TIME,URL,RECORD_ID);
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
     * 专家责令整改PDF
     */
    public String createExZeLingPdf (String ID) throws IOException {
        Document document = new Document( PageSize.A4);
        String pdfPath="D:/zfbdzl/pdf/expert_zeling/";
        String qrCodePath="D:/zfbdzl/qrcode/expert_zeling/";
        String url="D:/zfbdzl/images/";
        String pdfAbsolutePath="http://zfxc.njyjgl.cn/yjbd2/pdf/getPdf?url=expert_zeling/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date1=sdf.format(new Date ());
        int random=(int)(Math.random()*10000);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        // new Date()为获取当前系统时间
        String nowTime=df.format(new Date());
        String pdfName = date1+"/"+nowTime+ random+".pdf";//包含时间的文件夹
        //设置日期格式
        List<Map<String,Object>> list=pdfThreeDal.getExZeLingById(ID);

        String CHECKE_PROBLEM_IDS= "";//整改隐患


        if(list.get(0).get("CHECKE_PROBLEM_IDS")!=null) CHECKE_PROBLEM_IDS=list.get(0).get("CHECKE_PROBLEM_IDS").toString();
        String[] PROBLEM_ID=CHECKE_PROBLEM_IDS.split("\\|");

      /*  //从表格中拉取证件号信息
        List<Map>mapList2=pdfThreeDal.searchCareId(ID);
        String AREA="";//检查单位所属辖区
        if(mapList2.get(0).get("AREA")!=null)  AREA = mapList2.get(0).get("AREA").toString ();
*/
        String CHECKED_UNIT="";//被检查单位

        String TIME_IDS="";   //问题的整改期限
        if(list.get(0).get("TIME_IDS")!=null) TIME_IDS=list.get(0).get("TIME_IDS").toString();
        String[] TIME_IDSL_ARRAY = TIME_IDS.split ( "\\|" );

        String UPID="";//责令整改自增id
        if(list.get(0).get("UPID")!=null) UPID=list.get(0).get("UPID").toString();


        String CHECKED_END_TIME="";//检查结束时间


        String CHECKE_SIGH="";//检查人员签字图片
        String REPRESENR_SIGN="";//被检查代表签字图片
        String WITNESS_SIGH="";//见证者签名图片



        if(list.get(0).get("CHECKED_UNIT")!=null) CHECKED_UNIT=list.get(0).get("CHECKED_UNIT").toString();

        if(list.get(0).get("CHECKED_END_TIME")!=null) CHECKED_END_TIME=list.get(0).get("CHECKED_END_TIME").toString();



        if(list.get(0).get("SIGNATURE4_PATH")!=null) REPRESENR_SIGN=list.get(0).get("SIGNATURE4_PATH").toString();
        if(list.get(0).get("SIGNATURE3_PATH")!=null) CHECKE_SIGH=list.get(0).get("SIGNATURE3_PATH").toString();

        if(list.get(0).get("SIGNATURE5_PATH")!=null) WITNESS_SIGH=list.get(0).get("SIGNATURE5_PATH").toString();


        String[] REPRESENR_SIGN_ARRAY= REPRESENR_SIGN.split("\\|");
        String[] CHECKE_SIGH_ARRAY= CHECKE_SIGH.split("\\|");
        String[] JIANZHENG_SIGH_ARRAY= WITNESS_SIGH.split("\\|");

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

            //副标题:现场检查记录
            Paragraph subTitle=new Paragraph("责令限期整改指令书",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            //应急检记
            //Paragraph subTitle2=new Paragraph("（"+AREA+" ）应急责改[2019]("+UPID+")号",para);
            Paragraph subTitle2=new Paragraph("（宁）应急责改[2019]("+UPID+")号",para);
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
            chunk = new Chunk(CHECKED_UNIT+"有限公司："+"",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
//            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);



            //段落1
            chunk = new Chunk("        现责令你单位对下述：",para);
            document.add(chunk);
            chunk = new Chunk(""+PROBLEM_ID.length,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("项问题按",para);
            document.add(chunk);

            chunk = new Chunk("下述规定的时间期限整改完毕，达到有关法律法规规章和标准规定的要求。由此造成事故的，依法追究有关人员的责任。",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);


            /*//问题和整改时间
            chunk = new Chunk("  "+"整改完成时限：",para);
            document.add(chunk);

            chunk = new Chunk(MODIFY_TIME,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);

            chunk = new Chunk("  "+"整改隐患：",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);
            for (int i = 0;i<PROBLEM_ID.length;i++){
                chunk = new Chunk("         "+(i+1)+"." ,para);
                document.add(chunk);
                chunk = new Chunk(PROBLEM_ID[i],para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }*/
            //问题和整改时间
            for (int i = 0;i<PROBLEM_ID.length;i++){
                chunk = new Chunk("         "+(i+1)+"." ,para);
                document.add(chunk);
                chunk = new Chunk(PROBLEM_ID[i],para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                chunk = new Chunk("  "+"整改完成时限：",para);
                document.add(chunk);

                chunk = new Chunk(TIME_IDSL_ARRAY[i],para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }

            //段落2
            chunk = new Chunk("        整改期间，你单位应当采取措施，确保安全生产。对安全生产违法行为，将依法予以行政处罚。",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);

            //段落3
            chunk = new Chunk("        如果不服从本指令，可以依法在60日内向南京市人民政府或者江苏省应急管理厅申请行政复议，或者在6个月内依法向",para);
            document.add(chunk);
            chunk = new Chunk("南京铁路运输法院",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("提起行政诉讼，但本指令不停止执行，法律另有规定的除外。",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);

            PdfContentByte cb = writer.getDirectContent();

            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            if (!WITNESS_SIGH.equals("")){
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "见证人（签名）：", 25f, 200f, 0);
                cb.endText();
            }


            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被检查单位现场负责人（签名）：", 25f, 250f, 0);
            cb.endText();

            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "检查人（签名）：", 25f, 300f, 0);
            cb.endText();


            String inscribe="南京市应急管理局";//'宁','江北', '玄', '秦','建','鼓','栖','经开','雨','江','浦','六','溧','高'
        /*    if (AREA.equals("宁")) {
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
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  inscribe, 390f, 110f, 0);
            cb.endText();
            //时间
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  CHECKED_END_TIME, 470f, 90f, 0);
            cb.endText();

           /* if(AREA.equals("宁")){
                //印章图片
                Image image = Image.getInstance ( "C:/zfbdmoban/images/zz.png" );
                image.setAbsolutePosition ( 400f, 70f );
                image.scaleAbsolute ( 140, 140 );
                document.add ( image );

            }*/

            //检查人员签名图片
            for(int i=0;i<CHECKE_SIGH_ARRAY.length;i++){
                Image image1 = Image.getInstance(url+CHECKE_SIGH_ARRAY[i]);
                image1.setAbsolutePosition(220f+105*i, 280f);
                //Scale to new height and new width of image
                image1.scaleAbsolute(48, 48);
                //Add to document
                document.add(image1);
            }

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
            pdfThreeDal.updateExPdfAndQrcode(pdfName,qrCodeName,ID);
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
     * 获取PDF页面
     * @param ID
     * @return
     * @throws IOException
     */
    public String createThreePDF (String ID) throws IOException {
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
        List<Map<String,Object>> list=pdfThreeDal.getThreeById(ID);

        String CHECKE_PROBLEM_IDS= "";//获取问题的唯一标识ID
        String CHECKE_DETAIL ="";//获取问题
        String CHECKED_START_TIME="";//检查开始时间
        if(list.get(0).get("CHECKE_PROBLEM_IDS")!=null) CHECKE_PROBLEM_IDS=list.get(0).get("CHECKE_PROBLEM_IDS").toString();
        String[] PROBLEM_ID=CHECKE_PROBLEM_IDS.split("\\|");
        try {
            for (int i=0;i<PROBLEM_ID.length;i++){
                String problemId = PROBLEM_ID[i];
                List<Map>mapList = pdfThreeDal.searchProblemById(problemId);
                if(mapList.get(0).get("CHECKE_DETAIL")!=null)
                CHECKE_DETAIL += mapList.get(0).get("CHECKE_DETAIL").toString()+"|";
                if(mapList.get(0).get("CHECKED_END_TIME")!=null) CHECKED_START_TIME=mapList.get(0).get("CHECKED_END_TIME").toString();
            }
        }catch (Exception e){
            System.out.println ( e.toString () );
            e.printStackTrace();
        }
        //从表格中拉取证件号信息
        List<Map>mapList2=pdfThreeDal.searchCareId(ID);
        String AREA="";//检查单位所属辖区
        if(mapList2.get(0).get("AREA")!=null)  AREA = mapList2.get(0).get("AREA").toString ();
        String[] CHECKE_DETAIL_ARRAY = CHECKE_DETAIL.split ( "\\|" );
        String RECORD_ID=ID;  //现场检查记录的自增id
        String CHECKE_UNIT="";//被检查单位

        String TIME_IDS="";   //问题的整改期限
        if(list.get(0).get("TIME_IDS")!=null) TIME_IDS=list.get(0).get("TIME_IDS").toString();
        String[] TIME_IDSL_ARRAY = TIME_IDS.split ( "\\|" );

        String UPID="";//责令整改自增id
        if(list.get(0).get("UPID")!=null) UPID=list.get(0).get("UPID").toString();

//        String CHECKE_PROBLEM="";//问题
//        String REPRESENT_PEOPLE="";//代表人
//        String REPRESENT_JOB="";//代表人职务
//        String MOBILE="";//手机号码
//        String CHECKED_LOCATION="";//检查场所
//        String EXECUTE_UNIT="";//执法单位
//        String EXECUTE_PEOPLE="";//执法人员
//        String CHECKE_DETAIL="";//检查情况
//        String CREATE_TIME="";//记录创建时间
//        String LOCATION_IMG="";//现场图片
        String CHECKED_END_TIME="";//检查结束时间
        String CARD_NUMBER1="";//证件号1
        String CARD_NUMBER2 ="";//证件号2
        String REPRESENR_SIGN="";//被检查代表签字图片
        String CHECKE_SIGH="";//检查人员签字图片
        String CHECKETWO_SIGH="";//检查人员2签字图片
        String WITNESS_SIGH="";//见证者签名图片
        String ENFORCE_PAPER="";//最终执法文书
        String OTHER_IMG="";//其他图片
        String SELECTED_MODEL="";
        String NOT_SELECTED_MODEL="";
        String ZF_ORDER_DEADLINE_AREA="";//所属地区
        String ZF_ORDER_DEADLINE_RECORD="";//文书编号
        if(list.get(0).get("ZF_ORDER_DEADLINE_AREA")!=null) ZF_ORDER_DEADLINE_AREA=list.get(0).get("ZF_ORDER_DEADLINE_AREA").toString();
        if(list.get(0).get("ZF_ORDER_DEADLINE_RECORD")!=null) ZF_ORDER_DEADLINE_RECORD=list.get(0).get("ZF_ORDER_DEADLINE_RECORD").toString();
        if(list.get(0).get("RECORD_ID")!=null) RECORD_ID=list.get(0).get("RECORD_ID").toString();
        if(list.get(0).get("CHECKE_UNIT")!=null) CHECKE_UNIT=list.get(0).get("CHECKE_UNIT").toString();
//        if(list.get(0).get("CHECKE_PROBLEM")!=null) CHECKE_PROBLEM=list.get(0).get("CHECKE_PROBLEM").toString();
//        if(list.get(0).get("REPRESENT_PEOPLE")!=null) REPRESENT_PEOPLE=list.get(0).get("REPRESENT_PEOPLE").toString();
//        if(list.get(0).get("REPRESENT_JOB")!=null) REPRESENT_JOB=list.get(0).get("REPRESENT_JOB").toString();
//        if(list.get(0).get("MOBILE")!=null) MOBILE=list.get(0).get("MOBILE").toString();
//        if(list.get(0).get("CHECKED_LOCATION")!=null) CHECKED_LOCATION=list.get(0).get("CHECKED_LOCATION").toString();

        if(list.get(0).get("CHECKED_END_TIME")!=null) CHECKED_END_TIME=list.get(0).get("CHECKED_END_TIME").toString();
//        if(list.get(0).get("EXECUTE_UNIT")!=null) EXECUTE_UNIT=list.get(0).get("EXECUTE_UNIT").toString();
//        if(list.get(0).get("EXECUTE_PEOPLE")!=null) EXECUTE_PEOPLE=list.get(0).get("EXECUTE_PEOPLE").toString();
        if(mapList2.get(0).get("CARD_NUMBER1")!=null) CARD_NUMBER1=mapList2.get(0).get("CARD_NUMBER1").toString();
        if(mapList2.get(0).get("CARD_NUMBER2")!=null) CARD_NUMBER2=mapList2.get(0).get("CARD_NUMBER2").toString();
//        if(list.get(0).get("CHECKE_DETAIL")!=null) CHECKE_DETAIL=list.get(0).get("CHECKE_DETAIL").toString();
        if(list.get(0).get("REPRESENR_SIGN")!=null) REPRESENR_SIGN=list.get(0).get("REPRESENR_SIGN").toString();
        if(list.get(0).get("CHECKE_SIGH")!=null) CHECKE_SIGH=list.get(0).get("CHECKE_SIGH").toString();
        if(list.get(0).get("CHECKETWO_SIGH")!=null) CHECKETWO_SIGH=list.get(0).get("CHECKETWO_SIGH").toString();
//        if(list.get(0).get("CREATE_TIME")!=null) CREATE_TIME=list.get(0).get("CREATE_TIME").toString();
//        if(list.get(0).get("LOCATION_IMG")!=null) LOCATION_IMG=list.get(0).get("LOCATION_IMG").toString();
        if(list.get(0).get("ENFORCE_PAPER")!=null) ENFORCE_PAPER=list.get(0).get("ENFORCE_PAPER").toString();
        if(list.get(0).get("OTHER_IMG")!=null) OTHER_IMG=list.get(0).get("OTHER_IMG").toString();
        if(list.get(0).get("SELECTED_MODEL")!=null) SELECTED_MODEL=list.get(0).get("SELECTED_MODEL").toString();
        if(list.get(0).get("NOT_SELECTED_MODEL")!=null) NOT_SELECTED_MODEL=list.get(0).get("NOT_SELECTED_MODEL").toString();
        if(list.get(0).get("WITNESS_SIGH")!=null) WITNESS_SIGH=list.get(0).get("WITNESS_SIGH").toString();

// String[] LOCATION_IMG_ARRAY= LOCATION_IMG.split("\\|");
        String[] OTHER_IMG_ARRAY= OTHER_IMG.split("\\|");
        String[] REPRESENR_SIGN_ARRAY= REPRESENR_SIGN.split("\\|");
        String[] CHECKE_SIGH_ARRAY= CHECKE_SIGH.split("\\|");
        String[] CHECKE_TWO_SIGH_ARRAY= CHECKETWO_SIGH.split("\\|");
        String[] JIANZHENG_SIGH_ARRAY= WITNESS_SIGH.split("\\|");
        String[] selectedArray=SELECTED_MODEL.split("\\|");
        String[] unselectedArray=NOT_SELECTED_MODEL.split("\\|");
//        String[] problem=CHECKE_PROBLEM.split("\\|");
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
          /*  //主标题：安全生产行政执法文书现场检查记录
            Paragraph title=new Paragraph("安全生产行政执法文书",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            //直线
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
            Paragraph subTitle=new Paragraph("责令限期整改指令书",titleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
//            //应急检记
            Paragraph subTitle2=new Paragraph("（"+ZF_ORDER_DEADLINE_AREA+" ）应急责改[2019]("+ZF_ORDER_DEADLINE_RECORD+")号",para);
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
            chunk = new Chunk(CHECKE_UNIT+"有限公司："+"",para);
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
            chunk = new Chunk("        现责令你单位对下述：",para);
            document.add(chunk);
            chunk = new Chunk(""+CHECKE_DETAIL_ARRAY.length,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("项问题按",para);
            document.add(chunk);
          /*  chunk = new Chunk(CHECKED_END_TIME,para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);*/
            chunk = new Chunk("下述规定的时间期限整改完毕，达到有关法律法规规章和标准规定的要求。由此造成事故的，依法追究有关人员的责任。",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);


            //问题和整改时间
            for (int i = 0;i<CHECKE_DETAIL_ARRAY.length;i++){
                chunk = new Chunk("         "+(i+1)+"." ,para);
                document.add(chunk);
                chunk = new Chunk(CHECKE_DETAIL_ARRAY[i],para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                chunk = new Chunk("  "+"整改完成时限：",para);
                document.add(chunk);

                chunk = new Chunk(TIME_IDSL_ARRAY[i],para);
                chunk.setUnderline(0.1f, -1f);
                document.add(chunk);

                document.add(new Chunk(lineSeparator));
                document.add(Chunk.NEWLINE);
            }


            //段落2
            chunk = new Chunk("        整改期间，你单位应当采取措施，确保安全生产。对安全生产违法行为，将依法予以行政处罚。",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);

            //段落3
            chunk = new Chunk("        如果不服从本指令，可以依法在60日内向南京市人民政府或者江苏省应急管理厅申请行政复议，或者在6个月内依法向",para);
            document.add(chunk);
            chunk = new Chunk("南京铁路运输法院",para);
            chunk.setUnderline(0.1f, -1f);
            document.add(chunk);
            chunk = new Chunk("提起行政诉讼，但本指令不停止执行，法律另有规定的除外。",para);
            document.add(chunk);
            document.add(Chunk.NEWLINE);

            PdfContentByte cb = writer.getDirectContent();

            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            if (!WITNESS_SIGH.equals("")){
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "见证人（签名）：", 25f, 150f, 0);
                cb.endText();
            }


            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被检查单位现场负责人（签名）：", 25f, 200f, 0);
            cb.endText();

            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "检查人1（签名）：", 25f, 300f, 0);
            cb.endText();

            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "检查人2（签名）：", 25f, 250f, 0);
            cb.endText();

            /*cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号：", 320f, 300f, 0);
            cb.endText();

            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  CARD_NUMBER, 360f, 300f, 0);
            cb.endText();

            if(!CARD_NUMBER2.equals("")){

                cb = writer.getDirectContent();
                bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  CARD_NUMBER2, 450f, 300f, 0);
                cb.endText();
            }*/
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
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  inscribe, 390f, 110f, 0);
            cb.endText();
            //时间
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  CHECKED_START_TIME, 470f, 90f, 0);
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
                image1.setAbsolutePosition(220f+105*i, 290f);
                //Scale to new height and new width of image
                image1.scaleAbsolute(48, 48);
                //Add to document
                document.add(image1);

                PdfContentByte cb4 = writer.getDirectContent();
                BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
                cb4.beginText();
                cb4.setFontAndSize(bf2, 12);
                cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER1, 350f, 300-40*i, 0);
                cb4.endText();
            }

            for(int i=0;i<CHECKE_TWO_SIGH_ARRAY.length;i++){
                Image image1 = Image.getInstance(url+CHECKE_TWO_SIGH_ARRAY[i]);
                image1.setAbsolutePosition(220f+105*i, 240f);
                //Scale to new height and new width of image
                image1.scaleAbsolute(48, 48);
                //Add to document
                document.add(image1);

                PdfContentByte cb4 = writer.getDirectContent();
                BaseFont bf2= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
                cb4.beginText();
                cb4.setFontAndSize(bf2, 12);
                cb4.showTextAligned(PdfContentByte.ALIGN_LEFT,  "证号:"+CARD_NUMBER2, 350f, 250-40*i, 0);
                cb4.endText();
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
                image1.setAbsolutePosition(220f+105*i, 180f);
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
                    image1.setAbsolutePosition(140f+105*i, 150f);
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



            //表格
            PdfPTable table=new PdfPTable(2);
            table.setTotalWidth(new float[]{100,500});
            for(int i=0;i<selectedArray.length;i++){
                table.addCell(getPDFCell("√",para,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
                table.addCell(getPDFCell(selectedArray[i],para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE));
            }
            for(int i=0;i<unselectedArray.length;i++){
                table.addCell(getPDFCell("",para,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE));
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
            pdfThreeDal.updatePdfAndQrcode(pdfName,qrCodeName,ID);
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
