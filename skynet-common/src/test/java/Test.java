/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: Test
 * Author:   MingRuiRen
 * Date:     2018/7/17 15:42
 * Description: test
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import common.entity.Program;
import jdk.internal.util.xml.impl.Input;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sun.security.util.AuthResources_it;

import java.io.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈test〉
 *
 * @author MingRuiRen
 * @create 2018/7/17
 * @since 1.0.0
 */
public class Test {
    public static void main(String[] args) {
        String xmlStr ="\n" +
                "<Object ElementType=\"{0}\" ID=\"{1}\" Action=\"{2}\" Code=\"{3}\">\n" +
                "  <Property Name=\"Name\">人鱼校花</Property>\n" +
                "  <Property Name=\"OrderNumber\"/>\n" +
                "  <Property Name=\"OriginalName\"/>\n" +
                "  <Property Name=\"SortName\"/>\n" +
                "  <Property Name=\"SearchName\"/>\n" +
                "  <Property Name=\"ActorDisplay\">李玉,闪花</Property>\n" +
                "  <Property Name=\"WriterDisplay\">李易峰</Property>\n" +
                "  <Property Name=\"OriginalCountry\">中国</Property>\n" +
                "  <Property Name=\"Language\">中文</Property>\n" +
                "  <Property Name=\"ReleaseYear\">2018</Property>\n" +
                "  <Property Name=\"OrgAirDate\"/>\n" +
                "  <Property Name=\"LicensingWindowStart\">20180715000000</Property>\n" +
                "  <Property Name=\"LicensingWindowEnd\">20191231000000</Property>\n" +
                "  <Property Name=\"DisplayAsNew\"/>\n" +
                "  <Property Name=\"DisplayAsLastChance\"/>\n" +
                "  <Property Name=\"Macrovision\"/>\n" +
                "  <Property Name=\"Description\">是滴是滴所多所多所</Property>\n" +
                "  <Property Name=\"PriceTaxIn\">0</Property>\n" +
                "  <Property Name=\"Status\">1</Property>\n" +
                "  <Property Name=\"SourceType\">1</Property>\n" +
                "  <Property Name=\"SeriesFlag\">0</Property>\n" +
                "  <Property Name=\"Kpeople\"/>\n" +
                "  <Property Name=\"Director\">李易峰</Property>\n" +
                "  <Property Name=\"ScriptWriter\">杨样</Property>\n" +
                "  <Property Name=\"Compere\"/>\n" +
                "  <Property Name=\"Guest\"/>\n" +
                "  <Property Name=\"Reporter\"/>\n" +
                "  <Property Name=\"OPIncharge\"/>\n" +
                "  <Property Name=\"CopyRight\"/>\n" +
                "  <Property Name=\"Duration\">0</Property>\n" +
                "  <Property Name=\"Type\">电影</Property>\n" +
                "  <Property Name=\"Tags\">古装</Property>\n" +
                "  <Property Name=\"ContentProvider\"/>\n" +
                "</Object>\n";

        try {

            Program program=new Program("AAA","BBB","CCC");
            Map<String,Object> map =new HashMap<>();
            map.put("name",program.getName());
            map.put("OrderNumber",program.getOrderNumber());
            map.put("OriginalName",program.getOriginalName());

            //元数据字段顺序
            String[] columnNames=new String[]{"name","name","name","name"};
            String[] values=new String[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                values[i]= (String) map.get(columnNames[i]);
            }
            xmlStr= MessageFormat.format(xmlStr,values);



            Element root = DocumentHelper.createElement("ADI");
            root.addAttribute("StaffID","48").addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            Element objects = root.addElement("Objects");

            Document doc= DocumentHelper.parseText(xmlStr);
            Element ePromgram=doc.getRootElement();
            objects.add(ePromgram);

            Document document = DocumentHelper.createDocument(root);
            String documentStr = document.asXML();

            OutputStream os=new FileOutputStream(new File("C:\\Users\\mingruiren\\Desktop\\xml\\aaa.xml"));
            PrintWriter writer=new PrintWriter(os);
            writer.write(documentStr);
            writer.flush();
            writer.close();

            System.out.println(documentStr);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}