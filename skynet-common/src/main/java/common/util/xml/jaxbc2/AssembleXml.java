package common.util.xml.jaxbc2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.util.xml.c2.adi.ADI;
import common.util.xml.c2.adi.Mappings;
import common.util.xml.c2.adi.ObjectFactory;
import common.util.xml.c2.adi.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hubert
 * @date 2018/6/11
 * @description AssembleXml
 */
public class AssembleXml {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 仅发mapping关系时，如栏目与剧集或节目绑定,此时parentType,elementType 字段必填
     *
     * @param objectMap   数据对象，Series:""...，Program:""...，mapping,采用LinkedMap(有序)
     * @param action      rejsit,update,delete
     * @param jsonData    模版数据，json
     * @param adiType     adi包含元素分类Series_Picture,Program_Moive_Picture_Series等
     * @param elementType 元素类型
     * @param parentType  上级元素类型
     * @return String xml
     */
    public String installADI(Map<String, Object> objectMap, String action, String jsonData, String adiType, String parentType, String elementType) {
        String xmlContent = "";

        ADIUtil adiUtil = new ADIUtil();
        ObjectFactory objectFactory = new ObjectFactory();
        ADI adi = objectFactory.createADI();
        Objects objects = objectFactory.createObjects();
        Mappings mappings = objectFactory.createMappings();

        //剧头
        if (EnumUtil.ADI_TYPE.Series_Picture.getValue().equals(adiType)) {
            //组装方法拆解各独立函数方法中，提供封装度与单函数内代码量
            handleSeriesPicture(adiUtil, objectMap, action, jsonData, adi, objects, objectFactory, mappings);
            //连续剧子集
        } else if (EnumUtil.ADI_TYPE.Program_Moive_Picture_Series.getValue().equals(adiType)) {
            handleProgramMoivePictureSeries(adiUtil, objectMap, action, jsonData, adi, objects, objectFactory, mappings);
            //单集
        } else if (EnumUtil.ADI_TYPE.Program_Moive_Picture.getValue().equals(adiType)) {
            System.out.println("111");

            //栏目
        } else if (EnumUtil.ADI_TYPE.Category_Picture.getValue().equals(adiType)) {
            System.out.println("111");
            //仅mapping
        } else if (EnumUtil.ADI_TYPE.Mapping.getValue().equals(adiType)) {
            handleMapping(adiUtil, objectMap, parentType, elementType, action, adi, objectFactory, mappings);
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ADI.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            //是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //编码格式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            marshaller.marshal(adi, out);
            xmlContent = out.toString("utf-8");
            if (xmlContent.contains("xsi:")) {
                xmlContent = xmlContent.replaceAll("xsi:", "");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return xmlContent;
    }

    /**
     * 处理Series_Picture
     *
     * @param adiUtil       adi处理类
     * @param objectMap     map数据封装
     * @param action        类型
     * @param jsonData      模版数据
     * @param adi           adi节点
     * @param objects       objects节点
     * @param objectFactory 节点
     * @param mappings      mapping节点
     */
    private void handleSeriesPicture(ADIUtil adiUtil, Map<String, Object> objectMap, String action, String jsonData, ADI adi, Objects objects, ObjectFactory objectFactory, Mappings mappings) {
        JSONObject jsonObject = JSON.parseObject(jsonData);
        Map mapSeries = JSON.parseObject(jsonObject.get(EnumUtil.Content_Type.Series.getValue()).toString());
        Map mapPicture = JSON.parseObject(jsonObject.get(EnumUtil.Content_Type.Picture.getValue()).toString());
        String parentCode = "";
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            if (entry.getKey().contains(EnumUtil.Content_Type.Series.getValue())) {
                //获取剧头对象
                Object object = entry.getValue();
                Map<String, Object> map_data_object = new HashMap<>();
                try {
                    map_data_object = convertToMap(object);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
                //C2数据对象
                Map<String, String> map_c2_data = new LinkedHashMap<>(30);
                initMap(map_data_object, map_c2_data, mapSeries);

                if (map_data_object.get("code") != null) {
                    parentCode = String.valueOf(map_data_object.get("code"));
                } else {
                    return;
                }

                //组装series
                adiUtil.adiToXml(map_c2_data, action, EnumUtil.Content_Type.Series.getValue(), adi, objectFactory, objects);
            } else if (entry.getKey().contains(EnumUtil.Content_Type.Picture.getValue())) {
                //图片
                Object object = entry.getValue();
                Map<String, Object> map_data_object = null;
                try {
                    map_data_object = convertToMap(object);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }

                //C2数据对象
                Map<String, String> map_c2_data = new LinkedHashMap<>(30);
                initMap(map_data_object, map_c2_data, mapPicture);

                //组装Picture
                adiUtil.adiToXml(map_c2_data, action, EnumUtil.Content_Type.Picture.getValue(), adi, objectFactory, objects);

                Map<String, String> map_mapping_data = mappingInit(parentCode, action, EnumUtil.Content_Type.Series.getValue(), EnumUtil.Content_Type.Picture.getValue(), map_data_object);

                //组装mapping
                adiUtil.adiToXml(map_mapping_data, action, EnumUtil.ADI_TYPE.Mapping.getValue(), adi, objectFactory, mappings);
            }
        }
    }

    /**
     * 处理Program_Moive_Picture_Series
     *
     * @param adiUtil       adi处理类
     * @param objectMap     map数据封装
     * @param action        类型
     * @param jsonData      模版数据
     * @param adi           adi节点
     * @param objects       objects节点
     * @param objectFactory 节点
     * @param mappings      mapping节点
     */
    private void handleProgramMoivePictureSeries(ADIUtil adiUtil, Map<String, Object> objectMap, String action, String jsonData, ADI adi, Objects objects, ObjectFactory objectFactory, Mappings mappings) {
        //Series的code需要传入，mapping关系中需要
        JSONObject jsonObject = JSON.parseObject(jsonData);
        Map mapProgram = JSON.parseObject(jsonObject.get(EnumUtil.Content_Type.Program.getValue()).toString());
        Map mapMovie = JSON.parseObject(jsonObject.get(EnumUtil.Content_Type.Movie.getValue()).toString());
        Map mapPicture = JSON.parseObject(jsonObject.get(EnumUtil.Content_Type.Picture.getValue()).toString());
        String parentCode = "";
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            if (entry.getKey().contains(EnumUtil.Content_Type.Program.getValue())) {
                //获取子集
                Object object = entry.getValue();
                Map<String, Object> map_data_object = new HashMap<>();
                try {
                    map_data_object = convertToMap(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //C2数据对象
                Map<String, String> map_c2_data = new LinkedHashMap<>(30);
                initMap(map_data_object, map_c2_data, mapProgram);

                if (map_data_object.get("code") != null) {
                    parentCode = String.valueOf(map_data_object.get("code"));
                } else {
                    return;
                }

                //组装Program
                adiUtil.adiToXml(map_c2_data, action, EnumUtil.Content_Type.Program.getValue(), adi, objectFactory, objects);

                Map<String, String> map_mapping_data = mappingInit(parentCode, action, EnumUtil.Content_Type.Series.getValue(), EnumUtil.Content_Type.Program.getValue(), map_data_object);

                //组装Mapping
                adiUtil.adiToXml(map_mapping_data, action, EnumUtil.ADI_TYPE.Mapping.getValue(), adi, objectFactory, mappings);
            } else if (entry.getKey().contains(EnumUtil.Content_Type.Movie.getValue())) {
                //获取视频
                Object object = entry.getValue();
                Map<String, Object> map_data_object = null;
                try {
                    map_data_object = convertToMap(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //C2数据对象
                Map<String, String> map_c2_data = new LinkedHashMap<>(30);
                initMap(map_data_object, map_c2_data, mapMovie);

                //组装视频
                adiUtil.adiToXml(map_c2_data, action, EnumUtil.Content_Type.Movie.getValue(), adi, objectFactory, objects);
            } else if (entry.getKey().contains(EnumUtil.Content_Type.Picture.getValue())) {
                //图片
                Object object = entry.getValue();
                Map<String, Object> map_data_object = null;
                try {
                    map_data_object = convertToMap(object);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }

                //C2数据对象
                Map<String, String> map_c2_data = new LinkedHashMap<>(30);
                initMap(map_data_object, map_c2_data, mapPicture);

                //组装Picture
                adiUtil.adiToXml(map_c2_data, action, EnumUtil.Content_Type.Picture.getValue(), adi, objectFactory, objects);

                Map<String, String> map_mapping_data = mappingInit(parentCode, action, EnumUtil.Content_Type.Program.getValue(), EnumUtil.Content_Type.Picture.getValue(), map_data_object);

                //组装mapping
                adiUtil.adiToXml(map_mapping_data, action, EnumUtil.ADI_TYPE.Mapping.getValue(), adi, objectFactory, mappings);
            }
        }
    }


    /**
     * 将Object转换成Map
     *
     * @param object 数据对象
     * @return map
     * @throws IllegalAccessException IllegalAccessException
     */
    private Map<String, Object> convertToMap(Object object) throws IllegalAccessException {
        FieldUtil fieldUtil = new FieldUtil();
        return fieldUtil.objectToMap(object);
    }

    /**
     * @param map_data_object object 转map
     * @param map_c2_data     C2 map数据
     * @param map_template    模版
     */
    private void initMap(Map<String, Object> map_data_object, Map<String, String> map_c2_data, Map map_template) {
        FieldUtil fieldUtil = new FieldUtil();
        //模版，根据模版ID查询，转换为map
        //Map map_template = JSON.parseObject(jsonData);
        fieldUtil.initDataMap(map_data_object, map_template, map_c2_data);
    }

    /**
     * 设置mapping中的Property节点
     *
     * @param parentCode      父节点code
     * @param action          REJIST,UPDATE,DELETE
     * @param parentType      父节点类型
     * @param elementType     子节点类型
     * @param map_data_object 数据对象，series、program、movie、picture等
     * @return map_mapping_data
     */
    private Map<String, String> mappingInit(String parentCode, String action, String parentType, String elementType, Map<String, Object> map_data_object) {
        Map<String, String> map_mapping_data = new HashMap<>(10);
        map_mapping_data.put("ParentCode", parentCode);
        map_mapping_data.put("ParentType", parentType);
        map_mapping_data.put("ElementCode", String.valueOf(map_data_object.get("code")));
        map_mapping_data.put("ElementType", elementType);
        map_mapping_data.put("Action", action);

        if (String.valueOf(map_data_object.get("type")) != null) {
            map_mapping_data.put("Type", String.valueOf(map_data_object.get("type")));
        }
        map_mapping_data.put("Sequence", String.valueOf(map_data_object.get("sequence")));

        //当Mapping的ParentType为Package(SVOD)时, 标识SVOD节目的服务起始时间(YYYYMMDDHH24MiSS)
        if ("Package".equals(parentType)) {
            map_mapping_data.put("ValidStart", "");
            map_mapping_data.put("ValidStart", "");
        }
        return map_mapping_data;
    }

    private void handleMapping(ADIUtil adiUtil, Map<String, Object> objectMap, String parentType, String elementType, String action, ADI adi, ObjectFactory objectFactory, Mappings mappings) {

        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            Object object = entry.getValue();
            Map<String, Object> map_data_object = new HashMap<>();
            try {
                map_data_object = convertToMap(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            Map<String, String> map_mapping_data = new HashMap<>(10);

            map_mapping_data.put("ParentType", parentType);
            map_mapping_data.put("ElementType", elementType);
            map_mapping_data.put("Action", action);

            if (entry.getKey().contains("Parent")) {
                map_mapping_data.put("ParentCode", String.valueOf(map_data_object.get("code")));
                map_mapping_data.put("Sequence", String.valueOf(map_data_object.get("sequence")));
                map_mapping_data.put("ParentName", String.valueOf(map_data_object.get("name")));
                //continue;
            } else if (entry.getKey().contains("Parent")) {
                map_mapping_data.put("ElementCode", String.valueOf(map_data_object.get("code")));
                map_mapping_data.put("ElementName", String.valueOf(map_data_object.get("name")));
            }

            //组装mapping
            adiUtil.adiToXml(map_mapping_data, action, EnumUtil.ADI_TYPE.Mapping.getValue(), adi, objectFactory, mappings);

        }
    }
}
