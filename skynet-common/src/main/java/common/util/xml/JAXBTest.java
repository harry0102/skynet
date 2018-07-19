package common.util.xml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import common.util.xml.c2.adi.*;
import common.util.xml.c2.adi.Object;
import common.util.xml.c2.value.Category;
import common.util.xml.c2.value.Picture;
import common.util.xml.c2.value.Program;
import common.util.xml.c2.value.Series;
import common.util.xml.jaxbc2.ADIUtil;
import common.util.xml.jaxbc2.AssembleXml;
import common.util.xml.jaxbc2.EnumUtil;
import common.util.xml.jaxbc2.FieldUtil;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hubert
 * @date 2018/5/31
 * @description JAXBTest
 */
public class JAXBTest {


    /**
     * 采用JAXB解析xml
     * LinkedHashMap 有序
     * HashMap 无效
     */
    @Test
    public void test1() {
        Map<String, String> map = new LinkedHashMap<>(10);
        for (int i = 0; i < 10; i++) {
            map.put("key" + (i + 1), "value" + (i + 1));
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ";" + entry.getValue());
        }

    }

    private void programToXml(Objects objects, ObjectFactory factory) {

        Object object1 = factory.createObject();
        object1.setElementType("Program");
        object1.setAction("REGIST");
        object1.setCode("aa53766d68784972ab11997726538954");
        object1.setID("1717");

        Property property = factory.createProperty();
        Property property2 = factory.createProperty();
        property.setName("Name");
        property.setContent("人鱼校花");
        property2.setName("ActorDisplay");
        property2.setContent("李玉,闪花");

        object1.getProperty().add(property);
        object1.getProperty().add(property2);
        objects.getObject().add(object1);
    }

    private void movieToXml(Objects objects, ObjectFactory factory) {

        Object object2 = factory.createObject();
        object2.setElementType("Movie");
        object2.setAction("REGIST");
        object2.setCode("aa53766d68784972ab11997726538954");
        object2.setID("1717");

        Property property3 = factory.createProperty();
        Property property4 = factory.createProperty();
        property3.setName("Type");
        property3.setContent("1");
        property4.setName("FileURL");
        property4.setContent("ftp://vstorero:vsropukka@192.168.4.64/2018/07/16/DD95E12138CC4FE9BFFC08E8DF81FAAB.mp4");

        object2.getProperty().add(property3);
        object2.getProperty().add(property4);
        objects.getObject().add(object2);
    }

    private void mappingToXml(Mappings mappings, ObjectFactory factory) {

        Mapping mapping = factory.createMapping();
        mapping.setParentType("Program");
        mapping.setParentCode("d148d5a4727e4b0189245867b1927879");

        Property property = factory.createProperty();
        Property property2 = factory.createProperty();
        property.setName("Sequence");
        property.setContent("99");
        property2.setName("Type");
        property2.setContent("1");

        mapping.getProperty().add(property);
        mapping.getProperty().add(property2);
        mappings.getMapping().add(mapping);
    }

    /**
     * @param objects 节点对象
     * @param factory 初始化对象
     * @param map     对应的内容，series,program,movie,picture,category,cast,castmap，对象
     * @param type    对应类型，series,program,movie,picture,category,cast,castmap，字符串
     * @param action  reject,update,delete
     */
    private void beanToXml(Objects objects, ObjectFactory factory, Map<String, String> map, String type, String action) {

        Object object_map = factory.createObject();
        //循环一
        if (map != null && map.size() > 0) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = map.get(key);
                Property property_map = factory.createProperty();
                property_map.setName(key);
                property_map.setContent(value);
                object_map.getProperty().add(property_map);
            }
        }
        //循环二
        try {

            if (EnumUtil.Content_Type.Series.getValue().equals(type)) {


            }
            //内容类型，默认属性填充值
            object_map.setElementType(type);
            object_map.setCode(type + "707e6b3a46a94748bd074587c5d40811");
            object_map.setID(type + "51326");
            object_map.setAction("REGIST");

            //动态属性填充值
            for (Map.Entry<String, String> entry : map.entrySet()) {

                Property property_map = factory.createProperty();
                property_map.setName(entry.getKey());
                property_map.setContent(entry.getValue());
                object_map.getProperty().add(property_map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        objects.getObject().add(object_map);
    }

    /**
     * 组装ADI报文
     *
     * @return ADI
     */
    private ADI adiToXml() {

        ObjectFactory factory = new ObjectFactory();
        ADI adi = factory.createADI();
        adi.setPriority("5");
        adi.setStaffID("48");

        Objects objects = factory.createObjects();

        //组装program
        programToXml(objects, factory);

        //组装movie
        movieToXml(objects, factory);

        adi.setObjects(objects);

        Mappings mappings = factory.createMappings();

        //组装mapping
        mappingToXml(mappings, factory);

        adi.setMappings(mappings);

        return adi;
    }

    /**
     * 采用JAXB生成xml
     */
    @Test
    public void test2() {

        try {

//			JAXBContext jaxbContext = jaxbContextMap.get(ADI.class.getName());
//			if(jaxbContext == null){
//				// 如果每次都调用JAXBContext.newInstance方法，会导致性能急剧下降
//				jaxbContext = JAXBContext.newInstance(ADI.class);
//				jaxbContextMap.put(ADI.class.getName(), jaxbContext);
//			}
            JAXBContext jaxbContext = JAXBContext.newInstance(ADI.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            // 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //编码格式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            // 是否省略xm头声明信息
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            // 不进行转义字符的处理Lambda表达式
            //marshaller.setProperty(CharacterEscapeHandler.class.getName(), (CharacterEscapeHandler) (ch, start, length, isAttVal, writer) -> writer.write(ch, start, length));

            ADI adi = adiToXml();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            marshaller.marshal(adi, out);
            String xmlContent = out.toString("utf-8");

//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			XMLOutputFactory xof = XMLOutputFactory.newInstance();
//			XMLStreamWriter streamWriter = xof.createXMLStreamWriter(out);
//			//添加<![CDATA[2014-03-12 09:47:19]]>标签
//			CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter( streamWriter );
//			marshaller.marshal(adi, cdataStreamWriter);
//			xmlContent = out.toString("utf-8");
//					//new String(out.toByteArray(), "utf-8").trim();
//			cdataStreamWriter.flush();
//			cdataStreamWriter.close();

//			StringWriter writer = new StringWriter();
//			marshaller.marshal (adi, writer);
//			xmlContent = writer.toString ();

            if (xmlContent.contains("xsi:")) {
                xmlContent = xmlContent.replaceAll("xsi:", "");
            }
            System.out.println(xmlContent);
        } catch (Exception e) {
            System.err.println(e + e.getMessage());
        }
    }

    /**
     * 需解决的问题：
     * 1.将模版保存并解析 -- 解决
     * 2.为解析的模版赋值 -- 解决
     * 3.判断C2生成节点，如需要几类object，是否需要mapping，可以传入类型，
     * 例如，all,则表示全节点，只需要传入数据即可，
     * （series\program\movie\picture\categray\cast\catmap均属于数据节点，调用object即可实现，有三个数据节点，调用三遍方法即可）
     * mapping，则只发送mapping关系
     */
    @Test
    public void test3() {

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(ADI.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            // 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //编码格式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            Map<String, String> map = new HashMap<>(30);

            ADI adi = adiToXml();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            marshaller.marshal(adi, out);
            String xmlContent = out.toString("utf-8");
            System.out.println(xmlContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test4() {
        //模版,对象属性做key,别称做value,若考虑一个对象属性可以赋值刚给多个C2对象，则将别称(不能重复)做key,对象属性做value
        String jsonMap = "{" +
                "\"Series\":{\"name\":\"Name\",\"code\":\"Code\",\"status\":\"status\",\"actorDisplay\":\"ActorDisplay\"," + "\"description\":\"Description\",\"director\":\"Director\"}," +
                "\"Program\":{\"Name\":\"name\",\"Code\":\"Code\",\"Status\":\"status\"}," +
                "\"Movie\":{\"Code\":\"code\",\"FileURL\":\"fileurl\"}," +
                "\"Picture\":{\"code\":\"Code\",\"fileurl\":\"FileURL\",\"description\":\"Description\"}" +
                "}";
        JSONObject jsonObject = JSON.parseObject(jsonMap);
        if (jsonObject.get(EnumUtil.Content_Type.Series.getValue()) != null) {
            System.out.println(jsonObject.get(EnumUtil.Content_Type.Series.getValue()));
        }
        //模版对象
        Map mapSeries = JSON.parseObject(jsonObject.get(EnumUtil.Content_Type.Series.getValue()).toString());
        Map mapPicture = JSON.parseObject(jsonObject.get(EnumUtil.Content_Type.Picture.getValue()).toString());
        //System.out.println(mapSeries);
        //System.out.println(mapPicture);

        //数据对象
        Series series = new Series();
        series.setActorDisplay("111,222");
        series.setCode("series123456");
        series.setName("神盾局 第一季");
        series.setDescription("特工剧，科幻");
        series.setDirector("xxxx");
        series.setReleaseyear("2018");

        FieldUtil fieldUtil = new FieldUtil();
        //数据对象,对象转map
        Map<String, java.lang.Object> map_object = null;
        try {
            map_object = fieldUtil.objectToMap(series);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Map<String, String> map_series_data = new LinkedHashMap<>(30);
        fieldUtil.initDataMap(map_object, mapSeries, map_series_data);

        //System.out.println("series_map:"+map_series_data);
//		for (Map.Entry<String,String> entry: map_series_data.entrySet()) {
//			System.out.println(entry.getKey() + ";" + entry.getValue());
//		}
        Picture picture = new Picture();
        picture.setCode("pic123456");
        picture.setFileurl("ftp://123@123/11x.jpg");
        picture.setDescription("海报");
        picture.setType(1);
        picture.setSequence(99);

        FieldUtil fieldUtil_pic = new FieldUtil();
        Map<String, java.lang.Object> map_pic = fieldUtil_pic.getFiledsInfo(picture);
        System.out.println(map_pic);

        Map<String, String> map_pic_data = new HashMap<>(30);
        for (java.lang.Object mapData : mapPicture.entrySet()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) mapData;
            //System.out.println(entry.getKey());
            if (map_pic.containsKey(entry.getKey())) {
                map_pic_data.put(entry.getValue(), String.valueOf(map_pic.get(entry.getKey())));
            }
        }
        //System.out.println("map_pic_data:"+map_pic_data);
        Map<String, String> map_mapping_data = new HashMap<>(30);
        map_mapping_data.put("ParentCode", picture.getCode());
        map_mapping_data.put("ParentType", EnumUtil.Content_Type.Picture.getValue());
        map_mapping_data.put("ElementCode", series.getCode());
        map_mapping_data.put("ElementType", EnumUtil.Content_Type.Series.getValue());
        map_mapping_data.put("Action", EnumUtil.ACTION.REJIST.getValue());
        //如果有含有picture类型才有以下赋值
        if (picture.getType() != null) {
            map_mapping_data.put("Type", String.valueOf(picture.getType()));
            map_mapping_data.put("Sequence", String.valueOf(picture.getSequence()));
        }


        ADIUtil adiUtil = new ADIUtil();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ADI.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            // 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //编码格式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            String action = "REJIST";
            ObjectFactory objectFactory = new ObjectFactory();
            ADI adi = objectFactory.createADI();
            Objects objects = objectFactory.createObjects();
            Mappings mappings = objectFactory.createMappings();
            //组装series
            adiUtil.adiToXml(map_series_data, action, EnumUtil.Content_Type.Series.getValue(), adi, objectFactory, objects);

            //组装picture1
            adiUtil.adiToXml(map_pic_data, action, EnumUtil.Content_Type.Picture.getValue(), adi, objectFactory, objects);

            //组装picture2
            adiUtil.adiToXml(map_pic_data, action, EnumUtil.Content_Type.Picture.getValue(), adi, objectFactory, objects);

            //组装mapping1
            adiUtil.adiToXml(map_mapping_data, action, EnumUtil.ADI_TYPE.Mapping.getValue(), adi, objectFactory, mappings);

			/* 组装mapping2 */
            adiUtil.adiToXml(map_mapping_data, action, EnumUtil.ADI_TYPE.Mapping.getValue(), adi, objectFactory, mappings);

            String xmlContent = "";


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            marshaller.marshal(adi, out);
            xmlContent = out.toString("utf-8");
            if (xmlContent.contains("xsi:")) {
                xmlContent = xmlContent.replaceAll("xsi:", "");
            }
            System.out.println(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test5() {
        XStream xStream = new XStream();
        //调用toXML 将对象转成字符串
        String xmlContent = xStream.toXML("");
        System.out.println(xmlContent);
    }

    @Test
    public void test6() {

		/*封装的方法，仅用于生成xml，rejist,update,delete根据需要传入，需要传入的Propertyg值由上层逻辑控制*/
        String jsonMap = "{" +
                "\"Series\":{\"name\":\"Name1\",\"code\":\"Code\",\"status\":\"status\",\"actorDisplay\":\"ActorDisplay\",\"description\":\"Description\",\"director\":\"Director\"}," +
                "\"Program\":{\"Name\":\"name\",\"Code\":\"Code\",\"Status\":\"status\"}," +
                "\"Movie\":{\"Code\":\"code\",\"FileURL\":\"fileurl\"}," +
                "\"Picture\":{\"code\":\"Code\",\"fileurl\":\"FileURL\",\"description\":\"Description\"}," +
                "\"Category\":{\"code\":\"Code\",\"name\":\"Name\",\"description\":\"Description\",\"parentcode\":\"ParentCode\",\"sequence\":\"Sequence\"}" +
                "}";


        //数据对象
        Series series = new Series();
        series.setActorDisplay("111,222");
        series.setCode("series123456");
        series.setName("神盾局 第一季");
        series.setDescription("特工剧，科幻");
        series.setDirector("xxxx");
        series.setReleaseyear("2018");

        Picture picture = new Picture();
        picture.setCode("pic123456");
        picture.setFileurl("ftp://123@123/11x.jpg");
        picture.setDescription("海报");
        picture.setType(1);
        picture.setSequence(99);

        Picture picture2 = new Picture();
        picture2.setCode("pic2123456");
        picture2.setFileurl("ftp://22123@123/11x.jpg");
        picture2.setDescription("剧照");
        picture2.setType(2);
        picture2.setSequence(90);

        Program program = new Program();
        program.setSeriesCode("11111111111111111111");

        Category category = new Category();
        category.setName("精彩影视");
        category.setCode("category123");
        category.setDescription("栏目描述");
        category.setParentcode("root_0");
        category.setSequence(99);

        AssembleXml assembleXml = new AssembleXml();

        Map<String, java.lang.Object> objectMap = new HashMap<>(10);
        objectMap.put("Series", series);
        objectMap.put("Picture1", picture);
        objectMap.put("Picture2", picture2);

        Map<String, java.lang.Object> objectMap2 = new HashMap<>(10);

        //objectMap2.put("Series1|Element",series);
        objectMap2.put("Series|Element", series);
        objectMap2.put("Category|Parent", category);

        String xml = assembleXml.installADI(objectMap, EnumUtil.ACTION.REJIST.getValue(), jsonMap, EnumUtil.ADI_TYPE.Series_Picture.getValue(), null, null);
        System.out.println(xml);

        String parentType = EnumUtil.Content_Type.Category.getValue();
        String elementType = EnumUtil.Content_Type.Series.getValue();
        //仅发mapping关系，如栏目与剧集或节目绑定,此时parentType,elementType 字段必填
        //String xml2 = assembleXml.installADI(objectMap2, EnumUtil.ACTION.REJIST.getValue(), jsonMap, EnumUtil.ADI_TYPE.Mapping.getValue(),parentType,elementType);
        //System.out.println(xml2);

    }

}
