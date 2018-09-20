package common.reptile;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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

/**
 * @return
 * @descrption 爬虫2.0
 * @author MingRuiRen
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

       //获取页面
       String    url = "http://v.baidu.com/";
       // HtmlUnit 模拟浏览器
       WebClient webClient = new WebClient(BrowserVersion.CHROME);
       webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
       webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
       webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
       webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
       webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
       HtmlPage page = webClient.getPage(url);
       webClient.waitForBackgroundJavaScript(5 * 1000);               // 等待js后台执行30秒

       HtmlInput htmlInput = page.getHtmlElementById("new-bdvSearchInput");
       htmlInput.setValueAttribute("特种保镖");
       HtmlInput btn = page.getHtmlElementById("new-bdvSearchBtn");
       HtmlPage page2=btn.click();


       Document doc=Jsoup.parse( page2.asXml());
       System.out.println(doc.toString());



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

                programName= URLEncoder.encode(programName, UTF_8);
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

               getCellIntroduction( row, list);



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

    private static void getCellIntroduction( Row row,List<String> list) {

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
        s="info-wrap";
        s="j-more more-list-wrap";
        s="intro-item";
        s="sites arrow-tip";
        List<String> list =new ArrayList<>();
        System.out.println(doc);
        Elements dt = doc.getElementsByClass(s).select("a");

        for (int i = 0; i < dt.size(); i++) {
            String key = dt.get(i).text();
            list.add(key);
        }
        return list;

    }


    private static void setCellValue0(Row row, List<String> list) {
       for(int i=0;i<list.size();i++){
           String key =list.get(i);
           row.getCell(i+2).setCellValue(key);
       }


    }




}
