package common.reptile;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author MingRuiRen
 * @return
 * @descrption 爬虫2.0
 * @date 2018/9/20 14:38
 */
public class HttpTest3 {

    private static final String UTF_8 = "utf-8";

    /**
     * @param args
     * @return void
     * @descrption
     * @author MingRuiRen
     * @date 2018/7/10 14:31
     */
    public static void main(String[] args) throws IOException {

        String keyword = "疯狂金车";

        try {
            keyword = java.net.URLEncoder.encode(keyword, "UTF-8");
            System.out.println(keyword);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "http://v.baidu.com/v?ie=utf-8&word="+keyword;


        // 屏蔽HtmlUnit等系统 log
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
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
        System.out.println("html:"+document);

        webClient.close();

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
            CloseableHttpResponse responseGetSearch = null;
            CloseableHttpClient httpclient = HttpClients.createDefault();

            try {

                programName = URLEncoder.encode(programName, UTF_8);
//                url = "http://v.baidu.com/v?word="+programName+"&ct=301989888&rn=67&pn=0&db=0&s=0&fbl=800&ie=utf-8" ;
                url = "http://v.baidu.com/v?word=%E5%A6%82%E6%87%BF%E4%BC%A0&ct=301989888&rn=67&pn=0&db=0&s=0&fbl=800&ie=utf-8";
                HttpGet getMethodSearch = new HttpGet(url);

                responseGetSearch = httpclient.execute(getMethodSearch);
                HttpEntity entitySearch = responseGetSearch.getEntity();
                String contentSearch = EntityUtils.toString(entitySearch, UTF_8);


                WebClient webClient = new WebClient();
                webClient.getOptions().setJavaScriptEnabled(false);
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setUseInsecureSSL(false);


                //获取页面
                HtmlPage page = webClient.getPage(url);
                System.out.println(page.toString());

                //百度百科-版权
                List<String> list = printContent(null, null);

                getCellIntroduction(row, list);


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

    private static void getCellIntroduction(Row row, List<String> list) {

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
    private static List<String> printContent(Document doc, String s) {
        s = "info-wrap";
        s = "j-more more-list-wrap";
        s = "intro-item";
        s = "sites arrow-tip";
        List<String> list = new ArrayList<>();
        System.out.println(doc);
        Elements dt = doc.getElementsByClass(s).select("a");

        for (int i = 0; i < dt.size(); i++) {
            String key = dt.get(i).text();
            list.add(key);
        }
        return list;

    }


    private static void setCellValue0(Row row, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String key = list.get(i);
            row.getCell(i + 2).setCellValue(key);
        }


    }


}
