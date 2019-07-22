package yjbd.yjbawechat.Util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yjbd.yjbawechat.Enum.PathEnum;
import yjbd.yjbawechat.Service.EnforceService;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static yjbd.yjbawechat.Service.PdfService.getPDFCell;

public class CreatePdfUtil {
    static final Logger logger = LoggerFactory.getLogger(CreatePdfUtil.class);
    public static String createPdf(String folderName,String ID,String CHECKED_UNIT,String CHECKE_UNIT,String CHECK_PEOPLE,String CHECK_START_TIME,String CHECK_ITEM,String CHECK_DESC,String CHECKMAN_SIGN,String CHECKEDMAN_SIGN,String CHECK_IMG){
        Document document = new Document(PageSize.A4);
        //创建书写器将文档写入指定磁盘
        String path = PathEnum.PDF_PATH.getPath();
        //根据时间戳创建新的文件名
        String fileName = System.currentTimeMillis() + new Random().nextInt(1000) + ".pdf";
        //创建文件路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String createTime = sdf.format(new Date());
        String filePath = path + "/" + folderName+ "/" +createTime;
        String url = filePath+ "/" +fileName;
        try {
            File folder = new File(filePath);
            //判断文件夹是否存在，如不存在，创建
            if(!folder.exists()){
                folder.mkdirs();
            }
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 20, Font.BOLD, BaseColor.BLACK);//加入document：
            Font subTitleFont = new Font(bfChinese, 16, Font.NORMAL,BaseColor.BLACK);
            Font para = new Font(bfChinese, 12, Font.NORMAL,BaseColor.BLACK);
            Font lineFont=new Font(bfChinese,2, Font.NORMAL,BaseColor.BLACK);
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(url));
            document.open();
            String Temp = "";
            if (CHECKE_UNIT.contains("江北")) {
                Temp = "江北";
            }
            if (CHECKE_UNIT.contains("玄武")) {
                Temp = "玄武";
            }
            if (CHECKE_UNIT.contains("秦淮")) {
                Temp = "秦淮";
            }
            if (CHECKE_UNIT.contains("建邺")) {
                Temp = "建邺";
            }
            if (CHECKE_UNIT.contains("鼓楼")) {
                Temp = "鼓楼";
            }
            if (CHECKE_UNIT.contains("栖霞")) {
                Temp = "栖霞";
            }
            if (CHECKE_UNIT.contains("经济开发")) {
                Temp = "经济开发";
            }
            if (CHECKE_UNIT.contains("雨花台")) {
                Temp = "雨花台";
            }
            if (CHECKE_UNIT.contains("江宁")) {
                Temp = "江宁";
            }
            if (CHECKE_UNIT.contains("浦口")) {
                Temp = "浦口";
            }
            if (CHECKE_UNIT.contains("六合")) {
                Temp = "六合";
            }
            if (CHECKE_UNIT.contains("溧水")) {
                Temp = "溧水";
            }
            if (CHECKE_UNIT.contains("高淳")) {
                Temp = "高淳";
            }
            Paragraph title = new Paragraph("南京市公安消防支队"+Temp+"区大队",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            Chapter chapter1 = new Chapter(title,1);
            chapter1.setNumberDepth(0);
            Paragraph subTitle = new Paragraph("消防安全检查通知书",subTitleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            chapter1.add(subTitle);
            Paragraph number = new Paragraph("宁玄公消限字[2019]第"+ID+"号  ",para);
            number.setAlignment(Element.ALIGN_RIGHT);
            chapter1.add(number);
            document.add(chapter1);

            Paragraph checkedUnit = new Paragraph("        "+CHECKED_UNIT+":",para);
            checkedUnit.setLeading(25f);
            document.add(checkedUnit);

            Paragraph content = new Paragraph();
            content.setLeading(25f);
            String check = "";
            if(CHECK_PEOPLE.contains("__")){
                String[] arr=CHECK_PEOPLE.split("__");
                for (int i=0;i<arr.length-1;i++){
                    check += arr[i]+"，";
                }
                check += arr[arr.length-1];
            }
            else {
                check = CHECK_PEOPLE;
            }
            Chunk text = new Chunk("        "+"根据《中华人民共和国消防法》第五十三条的规定，我",para);
            content.add(text);
            text = new Chunk(check,para);
            text.setUnderline(0.1f, -1f);
            content.add(text);
            String[] time = CHECK_START_TIME.split("-");
            String day = time[2].split(" ")[0];
            text = new Chunk("于",para);
            content.add(text);
            text = new Chunk(time[0],para);
            text.setUnderline(0.1f, -1f);
            content.add(text);
            text = new Chunk("年",para);
            content.add(text);
            text = new Chunk(time[1],para);
            text.setUnderline(0.1f, -1f);
            content.add(text);
            text = new Chunk("月",para);
            content.add(text);
            text = new Chunk(day,para);
            text.setUnderline(0.1f, -1f);
            content.add(text);
            text = new Chunk("日对你单位（场所）进行消防监督检查，发现存在下列消防安全违法行为：",para);
            content.add(text);
            document.add(content);

            List<String> checkItem = new ArrayList<String>();
            if(CHECK_ITEM.contains("__")){
                String[] arr=CHECK_ITEM.split("__");
                for(int i = 0;i<arr.length-1;i++){
                    checkItem.add(arr[i]+"；");
                }
                checkItem.add(arr[arr.length-1]+"。");
            }
            else {
                checkItem.add(CHECK_ITEM);
            }
            for(int i=0;i<checkItem.size();i++){
                Paragraph text1 = new Paragraph("        "+(i+1)+"、"+checkItem.get(i),para);
                text1.setLeading(25f);
                document.add(text1);
            }
            //具体问题
            Paragraph content1 = new Paragraph("具体问题：",para);
            content1.setLeading(25f);
            document.add(content1);
            Paragraph content2 = new Paragraph("        "+CHECK_DESC,para);
            content2.setLeading(25f);
            document.add(content2);
            Paragraph content3 = new Paragraph(" ",para);
            content3.setLeading(25f);
            document.add(content3);
            Paragraph content4 = new Paragraph(" ",para);
            content4.setLeading(25f);
            document.add(content4);
            //检查人签名
            PdfContentByte sign = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign.beginText();
            sign.setFontAndSize(bf, 12);
            sign.showTextAligned(PdfContentByte.ALIGN_LEFT,  "检查人签名:", 25f, 320f, 0);
            sign.endText();
            //安全生产监管行政执法人员(签名)图片
            Image image = Image.getInstance (PathEnum.IMAGES_PATH.getPath()+"/sign"+CHECKMAN_SIGN) ;
            image.setAlignment( Image.TEXTWRAP);
            image.setAbsolutePosition ( 230f, 300f );
            image.scaleAbsolute ( 100, 50 );
            document.add(image);
            //被检查单位负责人
            PdfContentByte sign2 = writer.getDirectContent();
            BaseFont bf6= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            sign2.beginText();
            sign2.setFontAndSize(bf6, 12);
            sign2.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被检查单位（场所）负责人签名：", 25f, 220f, 0);
            sign2.endText();
            Image image1 = Image.getInstance (PathEnum.IMAGES_PATH.getPath()+"/sign"+CHECKEDMAN_SIGN) ;
            image1.setAlignment( Image.TEXTWRAP);
            image1.setAbsolutePosition ( 230f, 200f);
            image1.scaleAbsolute ( 100, 50 );
            document.add(image1);

            //印章
            PdfContentByte cb = writer.getDirectContent();
//            cb = writer.getDirectContent();
//            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            cb.beginText();
//            cb.setFontAndSize(bf, 12);
//            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "", 320f, 90f, 0);
//            cb.endText();
//
//            if(AREA.equals("宁")){
//                Image image = Image.getInstance ( "C:/zfbdmoban/images/zz.png" );
//                image.setAbsolutePosition ( 400f, 40f );
//                image.scaleAbsolute ( 140, 140 );
//                document.add ( image );
//            }



            //创建时间
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            Date date=new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String now=df.format(date);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  now, 460f, 60f, 0);
            cb.endText();

            //附件

            if(!"".equals(CHECK_IMG)){
                document.newPage();
                Paragraph annex1 = new Paragraph("附件：",para);
                document.add(annex1);
                annex1.setLeading(25f);
                Paragraph annex = new Paragraph(" ",para);
                annex.setLeading(25f);
                if(CHECK_IMG.contains("|")){
                    String[] arr=CHECK_IMG.split("\\|");
                    for(int i = 0;i<arr.length;i++){
                        Image annexImg = Image.getInstance(PathEnum.IMAGES_PATH.getPath()+"/"+arr[i]);
                        annexImg.setIndentationLeft(100f);
                        annexImg.scaleAbsolute(150, 150);
                        annex.add(annexImg);
                    }
                    document.add(annex);
                }
                else {
                    Image annexImg = Image.getInstance(PathEnum.IMAGES_PATH.getPath()+"/"+CHECK_IMG);
                    annexImg.setIndentationLeft(100f);
                    annexImg.scaleAbsolute(150, 150);
                    annex.add(annexImg);
                    document.add(annex);
                }
            }
            //预览二维码
            String qrCodeName=System.currentTimeMillis() + new Random().nextInt(1000)+".png";
            Map<EncodeHintType, Object> hints = new HashMap<>();
            //编码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //边框距
            hints.put(EncodeHintType.MARGIN, 0);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bm = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);
            File folder1 = new File(PathEnum.QRCORD_PATH.getPath()+"/fireFighting/"+createTime);
            //判断文件夹是否存在，如不存在，创建
            if(!folder1.exists()){
                folder1.mkdirs();
            }
            Path filePath1=new File(PathEnum.QRCORD_PATH.getPath()+"/fireFighting/"+createTime+qrCodeName).toPath();
            MatrixToImageWriter.writeToPath(bm, "png", filePath1);
            document.close();
            return folderName+ "/" +createTime+ "/" +fileName+"_"+"fireFighting/"+createTime+qrCodeName;
        } catch (Exception e){
            e.printStackTrace();
            logger.info ( e.toString () );
            document.close();
            return "";
        }
    }
    public static String createExpertPdf(String folderName,String ID,String CHECKED_UNIT,String CHECKED_START_TIME,List<Map> list,String DEAL_VIEW,String EXPERT_SIGN,String CHECKEDMAN_SIGN){
        Document document = new Document(PageSize.A4);
        //创建书写器将文档写入指定磁盘
        String path = PathEnum.PDF_PATH.getPath();
        //根据时间戳创建新的文件名
        String fileName = System.currentTimeMillis() + new Random().nextInt(1000) + ".pdf";
        //创建文件路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String createTime = sdf.format(new Date());
        String filePath = path + "/" + folderName+ "/" +createTime;
        String url = filePath+ "/" +fileName;
        try {
            File folder = new File(filePath);
            //判断文件夹是否存在，如不存在，创建
            if(!folder.exists()){
                folder.mkdirs();
            }
            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(bfChinese, 20, Font.BOLD, BaseColor.BLACK);//加入document：
            Font subTitleFont = new Font(bfChinese, 16, Font.NORMAL,BaseColor.BLACK);
            Font para = new Font(bfChinese, 12, Font.NORMAL,BaseColor.BLACK);
            Font lineFont=new Font(bfChinese,2, Font.NORMAL,BaseColor.BLACK);
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(url));
            document.open();
            Paragraph title = new Paragraph("现场处理措施决定书",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            Chapter chapter1 = new Chapter(title,1);
            chapter1.setNumberDepth(0);
            Date date=new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String now=df.format(date);
            String[] time= now.split("-");
            Paragraph number = new Paragraph("（宁）应急现决["+time[0]+"]第"+ID+"号  ",para);
            number.setAlignment(Element.ALIGN_RIGHT);
            chapter1.add(number);
            document.add(chapter1);

            Paragraph checkedUnit = new Paragraph("        "+CHECKED_UNIT+":",para);
            checkedUnit.setLeading(25f);
            document.add(checkedUnit);

            Paragraph content = new Paragraph();
            content.setLeading(25f);
            Chunk text = new Chunk("        "+"本机关于",para);
            content.add(text);
            String[] time1 = CHECKED_START_TIME.split("-");
            String day = time1[2].split(" ")[0];
            text = new Chunk(time1[0],para);
            text.setUnderline(0.1f, -1f);
            content.add(text);
            text = new Chunk("年",para);
            content.add(text);
            text = new Chunk(time1[1],para);
            text.setUnderline(0.1f, -1f);
            content.add(text);
            text = new Chunk("月",para);
            content.add(text);
            text = new Chunk(day,para);
            text.setUnderline(0.1f, -1f);
            content.add(text);
            text = new Chunk("日现场检查时，发现你单位有下列违法违规行为和事故隐患：",para);
            content.add(text);
            document.add(content);

            for(int i=0;i<list.size();i++){
                if(list.get(i).get("TEMP_NAME")!=null){
                    Paragraph text1 = new Paragraph("        "+(i+1)+"、"+list.get(i).get("TEMP_NAME")+"："+list.get(i).get("CHECKE_DETAIL"),para);
                    text1.setLeading(25f);
                    document.add(text1);
                }
            }

            //具体问题
            Paragraph content1 = new Paragraph("        "+DEAL_VIEW,para);
            content1.setLeading(25f);
            document.add(content1);
            Paragraph content3 = new Paragraph(" ",para);
            content3.setLeading(25f);
            document.add(content3);
            Paragraph content4 = new Paragraph(" ",para);
            content4.setLeading(25f);
            document.add(content4);
            //专家签名
            PdfContentByte sign = writer.getDirectContent();
            BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            sign.beginText();
//            sign.setFontAndSize(bf, 12);
//            sign.showTextAligned(PdfContentByte.ALIGN_LEFT,  "专家签名:", 25f, 320f, 0);
//            sign.endText();
//            //安全生产监管行政执法人员(签名)图片
//            Image image = Image.getInstance (PathEnum.IMAGES_PATH.getPath()+"/expertSign"+EXPERT_SIGN) ;
//            image.setAlignment( Image.TEXTWRAP);
//            image.setAbsolutePosition ( 230f, 300f );
//            image.scaleAbsolute ( 100, 50 );
//            document.add(image);
//            //被检查单位负责人
//            PdfContentByte sign2 = writer.getDirectContent();
//            BaseFont bf6= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            sign2.beginText();
//            sign2.setFontAndSize(bf6, 12);
//            sign2.showTextAligned(PdfContentByte.ALIGN_LEFT,  "被检查单位（场所）负责人签名：", 25f, 220f, 0);
//            sign2.endText();
//            Image image1 = Image.getInstance (PathEnum.IMAGES_PATH.getPath()+"/exCheckedSign"+CHECKEDMAN_SIGN) ;
//            image1.setAlignment( Image.TEXTWRAP);
//            image1.setAbsolutePosition ( 230f, 200f);
//            image1.scaleAbsolute ( 100, 50 );
//            document.add(image1);

            Image image2 ;

            PdfPTable table = new PdfPTable ( 4 );
            table.setWidthPercentage(100);
            table.getDefaultCell().setMinimumHeight(60f);
            table.setTotalWidth(new float[]{180,60,60,200});





            //专家签名
            table.addCell(getPDFCell("专家（签名）:",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            PdfPCell cell;
            if(!EXPERT_SIGN.equals("")) {
//                String[] EXPERT_SIGN_ARRAY=EXPERT_SIGN.split("\\|");
                image2 = Image.getInstance ( PathEnum.IMAGES_PATH.getPath()+"/expertSign"+EXPERT_SIGN );
                image2.setIndentationLeft ( 100f );
                //Scale to new height and new width of image
                image2.scaleAbsolute ( 48, 48 );
                cell = new PdfPCell ( image2 );
                cell.setHorizontalAlignment ( Element.ALIGN_CENTER );
                cell.setVerticalAlignment ( Element.ALIGN_MIDDLE );
                cell.setBorder ( 0 );
                cell.setMinimumHeight ( 60f );
                table.addCell ( cell );
            }else {
                table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            }
            table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            table.addCell(getPDFCell("",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
//            document.add(table);
            table.addCell(getPDFCell("被检查单位（签名）",para,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,false));
            if(!CHECKEDMAN_SIGN.equals("")){
                image2 = Image.getInstance(PathEnum.IMAGES_PATH.getPath()+"/exCheckedSign"+CHECKEDMAN_SIGN);
                image2.setIndentationLeft(100f);
                //Scale to new height and new width of image
                image2.scaleAbsolute(48, 48);
                cell=new PdfPCell (image2);
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

            //印章
            PdfContentByte
                    cb = writer.getDirectContent();
//            cb = writer.getDirectContent();
//            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
//            cb.beginText();
//            cb.setFontAndSize(bf, 12);
//            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "", 320f, 90f, 0);
//            cb.endText();
//
//            if(AREA.equals("宁")){
//                Image image = Image.getInstance ( "C:/zfbdmoban/images/zz.png" );
//                image.setAbsolutePosition ( 400f, 40f );
//                image.scaleAbsolute ( 140, 140 );
//                document.add ( image );
//            }


            //检查部门
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  "应急管理局", 460f, 85f, 0);
            cb.endText();

            //创建时间
            cb = writer.getDirectContent();
            bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT,  now, 460f, 60f, 0);
            cb.endText();

            //附件
//            document.newPage();
//            Paragraph annex1 = new Paragraph("附件：",para);
//            document.add(annex1);
//            annex1.setLeading(25f);
//            Paragraph annex = new Paragraph(" ",para);
//            annex.setLeading(25f);
//            if(!"".equals(CHECK_IMG)){
//                if(CHECK_IMG.contains("__")){
//                    String[] arr=CHECK_IMG.split("__");
//                    for(int i = 0;i<arr.length;i++){
//                        Image annexImg = Image.getInstance(PathEnum.IMAGES_PATH.getPath()+"/"+arr[i]);
//                        annexImg.setIndentationLeft(100f);
//                        annexImg.scaleAbsolute(150, 150);
//                        annex.add(annexImg);
//                    }
//                    document.add(annex);
//                }
//                else {
//                    Image annexImg = Image.getInstance(PathEnum.IMAGES_PATH.getPath()+"/"+CHECK_IMG);
//                    annexImg.setIndentationLeft(100f);
//                    annexImg.scaleAbsolute(150, 150);
//                    annex.add(annexImg);
//                    document.add(annex);
//                }
//            }
            //预览二维码
            String qrCodeName=System.currentTimeMillis() + new Random().nextInt(1000)+".png";
            Map<EncodeHintType, Object> hints = new HashMap<>();
            //编码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //边框距
            hints.put(EncodeHintType.MARGIN, 0);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bm = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);
            File folder1 = new File(PathEnum.QRCORD_PATH.getPath()+"/expert_notice/"+createTime);
            //判断文件夹是否存在，如不存在，创建
            if(!folder1.exists()){
                folder1.mkdirs();
            }
            Path filePath1=new File(PathEnum.QRCORD_PATH.getPath()+"/expert_notice/"+createTime+qrCodeName).toPath();
            MatrixToImageWriter.writeToPath(bm, "png", filePath1);
            document.close();
            return folderName+ "/" +createTime+ "/" +fileName+"__"+"expert_notice/"+createTime+qrCodeName;
        } catch (Exception e){
            e.printStackTrace();
            logger.info ( e.toString () );
            document.close();
            return "";
        }
    }
}
