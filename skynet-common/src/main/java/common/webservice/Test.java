/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: Test
 * Author:   MingRuiRen
 * Date:     2018/10/25 20:25
 * Description: test
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.webservice;

/**
 * 〈一句话功能简述〉<br> 
 * 〈test〉
 *
 * @author MingRuiRen
 * @create 2018/10/25
 * @since 1.0.0
 */
public class Test {

    public static void main(String[] args) {

        String param="<request>\n" +
                "\t\t\t\t\t<zcjgdm>zjhrkjgfyxgs</zcjgdm>\n" +
                "\t\t\t\t\t<zcjgmc>浙江和仁</zcjgmc>\n" +
                "\t\t\t\t\t<bdjgdm>zjhrkjgfyxgs</bdjgdm>\n" +
                "\t\t\t\t\t<bdjgmc>浙江和仁科技股份有限公司</bdjgmc>\n" +
                "\t\t\t\t\t<bdyydm>app</bdyydm>\n" +
                "\t\t\t\t\t<bdyymc>app</bdyymc>\n" +
                "\t\t\t\t\t<jkdm>ET_ETJBX001<dm>\n" +
                "\t\t\t\t\t<jkmc>儿童基本情况信息<mc>\n" +
                "\t\t\t\t\t<jrlx>app</jrlx>\n" +
                "\t\t\t\t\t<username>zjhr</username>\n" +
                "\t\t\t\t\t<password>111111</password>\n" +
                "\t\t\t\t\t<reqMessage>\n" +
                "\t\t\t\t\t\t\t<REQUEST>\n" +
                "\t\t\t\t\t\t\t\t<DMCSDQU>510811</DMCSDQU>\n" +
                "\t\t\t\t\t\t\t\t<CSRQKS>2014-04-16</CSRQKS>\n" +
                "\t\t\t\t\t\t\t\t<CSRQJS>2014-04-21</CSRQJS>\n" +
                "\t\t\t\t\t\t\t</REQUEST>\n" +
                "\t\t\t\t\t</reqMessage>\n" +
                "\t\t\t\t</request>";
        EntranceService service_service= new EntranceService_Service().getEntranceServiceImplPort();
        String  result= service_service.excute(param);

        System.out.println(result);


    }

}