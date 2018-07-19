/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: TestC2xml
 * Author:   MingRuiRen
 * Date:     2018/7/19 10:27
 * Description: 测试生成c2xml
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.c2xml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.util.c2xml.adi.*;
import common.util.c2xml.entity.C2Metadata;
import common.util.c2xml.entity.MappingBean;
import common.util.c2xml.entity.Movie;
import common.util.c2xml.entity.Program;
import common.util.c2xml.service.IBeantoXmlService;
import common.util.c2xml.service.impl.JaxbServiceImpl;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试生成c2xml〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
public class TestC2xml {

    private IBeantoXmlService service = new JaxbServiceImpl();


    public static void main(String[] args) {


    }

    @Test
    public void testBeantoXml() throws JAXBException, UnsupportedEncodingException {

        //元数据
        Program program = new Program(53140, "AAA", "d148d5a4727e4b0189245867b1927879");
        Movie movie =new Movie(111,"m2","m3","m4");
        C2Metadata<Program> programc2Metadata=new C2Metadata( "Program","DELETE",program);
        C2Metadata<Program> moviec2Metadata=new C2Metadata( "movie","regist",movie);

        MappingBean mappingBean = new MappingBean("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        MappingBean mappingBean2 = new MappingBean("2", "2", "3", "4", "5", "6", "7", "8", "9", "10");


        //key-value  元数据-对应xml模板
        Map<Object,Object> objecstMap =new HashMap<>();
        Map<Object,Object> mappingsMap =new HashMap<>();

        objecstMap.put(programc2Metadata,getTemplateByType(program));
        objecstMap.put(moviec2Metadata,getTemplateByType(movie));

        mappingsMap.put(mappingBean,getTemplateByType(mappingBean));
        mappingsMap.put(mappingBean2,getTemplateByType(mappingBean));


        C2ADI adi = new C2ADI();
        C2Objects c2Objects = service.getObjects( objecstMap);
        C2Mappings mappings = service.getMappings(mappingsMap);

        //objects节点
        adi.setObjects(c2Objects);
        //mappings节点
        adi.setMappings(mappings);

        String xmlStr = service.beanToXml(adi, C2ADI.class);
        System.out.println(xmlStr);
    }

    /**
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @descrption 根据元素据类型获取模板
     * @author MingRuiRen
     * @date 2018/7/19 15:13
     */
    private Map<String, Object> getTemplateByType(Object object) {
        Map<String, Object> map = new HashMap<>();
        if (object instanceof Program) {
            //Program
            String jsonMap = "{\"name\":\"name\",\"code\":\"code1\"}";
            JSONObject jsonObject = JSON.parseObject(jsonMap);
            map = JSON.parseObject(jsonObject.toJSONString());
        } else if (object instanceof MappingBean) {
            //c2mapping...
            String jsonMap = "{\"Type\":\"Type\",\"Sequence\":\"Sequence\"}";
            JSONObject jsonObject = JSON.parseObject(jsonMap);
            map = JSON.parseObject(jsonObject.toJSONString());
        } else if (object instanceof Movie) {
            //c2mapping...
            String jsonMap = "{\"movieType\":\"movieType\",\"movieSequence\":\"movieSequence\"}";
            JSONObject jsonObject = JSON.parseObject(jsonMap);
            map = JSON.parseObject(jsonObject.toJSONString());
        }
        return map;
    }


}