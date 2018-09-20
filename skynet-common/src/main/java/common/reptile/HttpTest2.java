package common.reptile;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.media.sound.SoftTuning;
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
        String programName = "";
        String url = "";

        for (Row row : sheet1) {
            i++;
            if (i == 1) {
                continue;
            }
            programName = row.getCell(1).getStringCellValue();
            System.out.println("sheet" + sheetIndex + "  row " + i + " " + programName);


            try {

                //获取页面
                url = "http://v.baidu.com/";
                // HtmlUnit 模拟浏览器
                WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
                webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
                webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
                webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
                webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
                webClient.getOptions().setTimeout(5 * 1000);                   // 设置连接超时时间
                HtmlPage page = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(5* 1000);               // 等待js后台执行30秒

                HtmlInput htmlInput = page.getHtmlElementById("new-bdvSearchInput");
                htmlInput.setValueAttribute(programName);
                HtmlInput btn = page.getHtmlElementById("new-bdvSearchBtn");
                HtmlPage page2 = btn.click();

                Document doc = Jsoup.parse(page2.asXml());

                String copyright = "sites arrow-tip";

                //百度视频-版权
                Set<String> list = printContent(doc, copyright);

                getCellIntroduction(row, list);


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (i == 10) {
                break;
            }

        }
    }

    private static void getCellIntroduction(Row row,Set<String>  list) {

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


    private static void setCellValue0(Row row, Set<String>  list) {
        int i=1;
        for (String str : list) {
            i++;
            row.getCell(i).setCellValue(str);
        }
    }


}
