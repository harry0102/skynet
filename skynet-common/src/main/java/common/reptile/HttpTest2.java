package common.reptile;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.media.sound.SoftTuning;
import org.apache.commons.logging.LogFactory;
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
import java.util.*;
import java.util.logging.Level;

/**
 * @author MingRuiRen
 * @return
 * @descrption 爬虫2.0
 * @date 2018/9/20 14:38
 */
public class HttpTest2 {

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
        File f = new File("D:\\附件三基础电影电视剧片单.xls");
        File fout = new File("D:\\附件三基础电影电视剧片单3.xls");
        HSSFWorkbook xssfWorkbook;

        try {

            InputStream inputStream = new FileInputStream(f);
            xssfWorkbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet;

//            遍历输出所有sheet
//            for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
//                if (i == 5) {
//                    break;
//                }
//                sheet = xssfWorkbook.getSheetAt(i);
//                setSheetCellValuie(i, sheet);
//            }

//        输出指定索引sheet
            int sheetIndex = 0;
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
        System.out.println("程序运行时间：" + (endTime - startTime) / 1000 / 60 + "min");

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
        String keyword = "";
        String url = "";

        for (Row row : sheet1) {
            i++;
            if (i == 1) {
                continue;
            }
            keyword = row.getCell(1).getStringCellValue();
            System.out.println("sheet" + sheetIndex + "  row " + i + " " + keyword);


            try {
                keyword = java.net.URLEncoder.encode(keyword, "UTF-8");
                System.out.println(keyword);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            url = "http://v.baidu.com/v?ie=utf-8&word=" + keyword;


            // 屏蔽HtmlUnit等系统 log
            LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

            //构造一个webClient 模拟Chrome 浏览器
            WebClient webClient = new WebClient(BrowserVersion.CHROME);

            // 启用JS解释器，默认为true
            webClient.getOptions().setJavaScriptEnabled(true);
            // 禁用css支持
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setCssEnabled(false);
            // js运行错误时，是否抛出异常
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            // 设置连接超时时间
            webClient.getOptions().setTimeout(5000);
            HtmlPage rootPage = null;
            try {
                rootPage = webClient.getPage(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 等待js后台执行5秒
            webClient.waitForBackgroundJavaScript(5000);
            String html = rootPage.asXml();
            Document document = Jsoup.parse(html);

            String copyright = "sites arrow-tip";

            //百度视频-版权
            Set<String> list = printContent(document, copyright);

            getCellIntroduction(row, list);


            if (i == 10) {
                break;
            }

        }
    }

    private static void getCellIntroduction(Row row, Set<String> list) {

        setCellValue0(row, list);

    }


    /**
     * @param doc
     * @param s
     * @return void
     * 打印基本信息
     * @author MingRuiRen
     * @date 2018/6/15 22:40
     */
    private static Set<String> printContent(Document doc, String s) {

        Set<String> list = new HashSet<>();
        Elements dt = doc.getElementsByClass(s).select("a");

        for (int i = 0; i < dt.size(); i++) {
            String key = dt.get(i).text();
            list.add(key);
        }
        return list;

    }


    private static void setCellValue0(Row row, Set<String> list) {
        int i = 1;
        for (String str : list) {
            i++;
            row.getCell(i).setCellValue(str);
        }
    }


}
