package common.reptile;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 111
 */
public class HttpTest {

    private static final String UTF_8 = "utf-8";

   /**
    * @param args
    * @return void
    * @descrption
    * @author MingRuiRen
    * @date 2018/7/10 14:31
    */
   public static void main(String[] args) {


        long startTime = System.currentTimeMillis();
        File f = new File("C:\\Users\\47077\\Desktop\\临时需求\\湖北IPTV片单+行云-底量（芒果，补信息） (1).xls");
        File fout = new File("C:\\Users\\47077\\Desktop\\临时需求\\湖北IPTV片单+行云-底量（芒果，补信息） (2).xls");
        HSSFWorkbook xssfWorkbook;

        try {

            InputStream inputStream = new FileInputStream(f);
            xssfWorkbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet;

            //遍历输出所有sheet
//            for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
//                if (i == 5) {
//                    break;
//                }
//                sheet = xssfWorkbook.getSheetAt(i);
//                setSheetCellValuie(i, sheet);
//            }

//        输出指定索引sheet
            int sheetIndex = 4;
            sheet = xssfWorkbook.getSheetAt(sheetIndex);
            setSheetCellValuie(sheetIndex, sheet);

            FileOutputStream excelFileOutPutStream = new FileOutputStream(fout);
            xssfWorkbook.write(excelFileOutPutStream);
            excelFileOutPutStream.flush();
            excelFileOutPutStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime)/1000/60 + "min");

    }

    /**
     * @param sheetIndex
     * @param sheet1
     * @return void
     * 设置单个sheet值
     * @author MingRuiRen
     * @date 2018/6/16 22:23
     */
    private static void setSheetCellValuie(int sheetIndex, HSSFSheet sheet1) {
        int i = 0;
        String programName = "";
        String url = "";

        for (Row row : sheet1) {
            i++;
            if (i == 1) {
                continue;
            }
            programName = row.getCell(1).getStringCellValue().replace("-HD", "");
            System.out.println("sheet" + sheetIndex + "  row " + i + " " + programName);
            CloseableHttpResponse responseGetSearch = null;
            CloseableHttpClient httpclient = HttpClients.createDefault();

            try {

                url = "https://baike.baidu.com/item/" + URLEncoder.encode(programName, UTF_8);
                HttpGet getMethodSearch = new HttpGet(url);
                responseGetSearch = httpclient.execute(getMethodSearch);
                HttpEntity entitySearch = responseGetSearch.getEntity();
                String contentSearch = EntityUtils.toString(entitySearch, UTF_8);

                Document doc = Jsoup.parse(contentSearch);
                String left = "basicInfo-block basicInfo-left";
                String right = "basicInfo-block basicInfo-right";

                Map<String, Object> map = new HashMap<>();
                //百度百科-演职表-左
                Map<String, Object> mapleft = printContent(doc, left);
                //百度百科-上映情况-右
                Map<String, Object> mapright = printContent(doc, right);
                map.putAll(mapleft);
                map.putAll(mapright);

                //简介在第几列
                int cellIntroduction = getCellIntroduction(sheetIndex, row, map);

                //百度百科-剧情简介
                printIntroduction(doc, row, cellIntroduction);


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != httpclient) {
                        httpclient.close();
                    }
                    if (null != responseGetSearch) {
                        responseGetSearch.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            if (i == 30) {
//                break;
//            }

        }
    }

    private static int getCellIntroduction(int sheetIndex, Row row, Map<String, Object> map) {
        int cellIntroduction;
        switch (sheetIndex) {
            case 0:
                cellIntroduction = 14;
                setCellValue0(row, map);
                break;
            case 1:
                cellIntroduction = 17;
                setCellValue1(row, map);
                break;
            case 2:
                cellIntroduction = 15;
                setCellValue2(row, map);
                break;
            case 3:
                cellIntroduction = 15;
                setCellValue2(row, map);
                break;
            case 4:
                cellIntroduction = 15;
                setCellValue2(row, map);
                break;
            default:
                cellIntroduction = 15;
                setCellValue2(row, map);
                break;

        }
        return cellIntroduction;
    }


    /**
     * 打印简介
     *
     * @param doc
     */
    private static void printIntroduction(Document doc, Row row, int cellIntroduction) {
        Elements elements5 = doc.select(".main-content").select(".para").select("div");
        String introduction = "";
        for (int i = 0; i < elements5.size(); i++) {
            introduction += elements5.get(i).text();
            row.getCell(cellIntroduction).setCellValue(introduction);
        }
    }

    /**
     * @param doc
     * @param s
     * @return void
     * 打印基本信息
     * @author MingRuiRen
     * @date 2018/6/15 22:40
     */
    private static Map<String, Object> printContent(Document doc, String s) {
        Map<String, Object> map = new HashMap<>();
        Elements dt = doc.getElementsByClass(s).select("dt");
        Elements dd = doc.getElementsByClass(s).select("dd");
        String key;
        String value;
        for (int i = 0; i < dt.size(); i++) {
            key = dt.get(i).text().replaceAll("\\s*", "");
            value = dd.get(i).text();
            map.put(key, value);
        }
        return map;

    }

    /**
     * 电影
     *
     * @param row
     * @param map
     */
    private static void setCellValue0(Row row, Map<String, Object> map) {
        //获取类型
        String columnIndex2 = (String) map.get("类型");
        //获取时长
        String columnIndex3 = (String) map.get("每集长度");
        if (null == columnIndex3) {
            columnIndex3 = (String) map.get("片长");
        }
        String columnIndex4 = (String) map.get("高/高(可转4K)/标/4K");
        String columnIndex5 = (String) map.get("标签分类");
        String columnIndex6 = (String) map.get("院线上映时间");
        if (null == columnIndex6) {
            columnIndex6 = (String) map.get("上映时间");
        }
        String columnIndex7 = (String) map.get("出品方");
        if (null == columnIndex7) {
            columnIndex7 = (String) map.get("出品公司");
        }
        String columnIndex8 = (String) map.get("年份");
        String columnIndex9 = (String) map.get("制片地区");
        String columnIndex10 = (String) map.get("语言");
        String columnIndex11 = (String) map.get("导演");
        String columnIndex12 = (String) map.get("编剧");
        String columnIndex13 = (String) map.get("主演");
        row.getCell(2).setCellValue(columnIndex2);
        row.getCell(3).setCellValue(columnIndex3);
        row.getCell(4).setCellValue(columnIndex4);
        row.getCell(5).setCellValue(columnIndex5);
        row.getCell(6).setCellValue(columnIndex6);
        row.getCell(7).setCellValue(columnIndex7);
//        row.getCell(8).setCellValue(columnIndex8);
        row.getCell(9).setCellValue(columnIndex9);
        row.getCell(10).setCellValue(columnIndex10);
        row.getCell(11).setCellValue(columnIndex11);
        row.getCell(12).setCellValue(columnIndex12);
        row.getCell(13).setCellValue(columnIndex13);
    }

    /**
     * 电视剧
     *
     * @param row
     * @param map
     */
    private static void setCellValue1(Row row, Map<String, Object> map) {
        //获取类型
        String columnIndex2 = (String) map.get("类型");
        String columnIndex3 = (String) map.get("集数");
        String columnIndex4 = (String) map.get("每集长度");
        if (null == columnIndex4) {
            columnIndex4 = (String) map.get("片长");
        }
        String columnIndex5 = (String) map.get("总时长");
        String columnIndex6 = (String) map.get("高/高(可转4K)/标/4K");
        String columnIndex7 = (String) map.get("标签分类");
        String columnIndex8 = (String) map.get("院线上映时间");
        if (null == columnIndex8) {
            columnIndex8 = (String) map.get("上映时间");
        }
        String columnIndex9 = (String) map.get("播出平台");
        String columnIndex10 = (String) map.get("出品方");
        if (null == columnIndex10) {
            columnIndex10 = (String) map.get("出品公司");
        }
        String columnIndex11 = (String) map.get("年份");
        String columnIndex12 = (String) map.get("制片地区");
        String columnIndex13 = (String) map.get("语言");
        String columnIndex14 = (String) map.get("导演");
        String columnIndex15 = (String) map.get("编剧");
        String columnIndex16 = (String) map.get("主演");
        row.getCell(2).setCellValue(columnIndex2);
        row.getCell(3).setCellValue(columnIndex3);
        row.getCell(4).setCellValue(columnIndex4);
        row.getCell(5).setCellValue(columnIndex5);
        row.getCell(6).setCellValue(columnIndex6);
        row.getCell(7).setCellValue(columnIndex7);
        row.getCell(8).setCellValue(columnIndex8);
        row.getCell(9).setCellValue(columnIndex9);
        row.getCell(10).setCellValue(columnIndex10);
//        row.getCell(11).setCellValue(columnIndex11);
        row.getCell(12).setCellValue(columnIndex12);
        row.getCell(13).setCellValue(columnIndex13);
        row.getCell(14).setCellValue(columnIndex14);
        row.getCell(15).setCellValue(columnIndex15);
        row.getCell(16).setCellValue(columnIndex16);
    }

    /**
     * 少儿、纪实、综艺
     *
     * @param row
     * @param map
     */
    private static void setCellValue2(Row row, Map<String, Object> map) {
        //获取类型
        String columnIndex2 = (String) map.get("类型");
        String columnIndex3 = (String) map.get("集数");
        String columnIndex4 = (String) map.get("每集长度");
        if (null == columnIndex4) {
            columnIndex4 = (String) map.get("片长");
        }
        String columnIndex5 = (String) map.get("总时长");
        String columnIndex6 = (String) map.get("高/高(可转4K)/标/4K");
        String columnIndex7 = (String) map.get("标签分类");
//        String columnIndex8 = (String) map.get("院线上映时间");
//        if (null == columnIndex8) {
//            columnIndex8 = (String) map.get("上映时间");
//        }
//        String columnIndex9 = (String) map.get("播出平台");
        String columnIndex8 = (String) map.get("出品方");
        if (null == columnIndex8) {
            columnIndex8 = (String) map.get("出品公司");
        }
        String columnIndex9 = (String) map.get("年份");
        String columnIndex10 = (String) map.get("制片地区");
        String columnIndex11 = (String) map.get("语言");
        String columnIndex12 = (String) map.get("导演");
        String columnIndex13 = (String) map.get("编剧");
        String columnIndex14 = (String) map.get("主演");
        row.getCell(2).setCellValue(columnIndex2);
        row.getCell(3).setCellValue(columnIndex3);
        row.getCell(4).setCellValue(columnIndex4);
        row.getCell(5).setCellValue(columnIndex5);
        row.getCell(6).setCellValue(columnIndex6);
        row.getCell(7).setCellValue(columnIndex7);
        row.getCell(8).setCellValue(columnIndex8);
//        row.getCell(9).setCellValue(columnIndex9);
        row.getCell(10).setCellValue(columnIndex10);
        row.getCell(11).setCellValue(columnIndex11);
        row.getCell(12).setCellValue(columnIndex12);
        row.getCell(13).setCellValue(columnIndex13);
        row.getCell(14).setCellValue(columnIndex14);

    }


}
