/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: JAXBBeantoXMlImpl
 * Author:   MingRuiRen
 * Date:     2018/7/19 11:41
 * Description: JAXBxmlImpl
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.c2xml.service.impl;

import common.util.c2xml.adi.*;
import common.util.c2xml.entity.C2Metadata;
import common.util.c2xml.entity.MappingBean;
import common.util.c2xml.service.IBeantoXmlService;
import common.util.c2xml.util.FieldUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈JAXBxmlImpl〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
public class JaxbServiceImpl implements IBeantoXmlService {


    @Override
    public C2Objects getObjects(Map<Object, Object> map) {

        C2Objects c2Objects = new C2Objects();

        List<C2Object> list = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            C2Metadata c2Metadata = (C2Metadata) entry.getKey();
            Map<String, Object> templateMap = (Map<String, Object>) entry.getValue();
            list.addAll(getC2ObjectsList(c2Metadata, templateMap));

        }
        c2Objects.setObjects(list);

        return c2Objects;
    }

    public List<C2Object> getC2ObjectsList(C2Metadata c2Metadata, Map<String, Object> templateMap) {

        List<C2Object> C2Objects = new ArrayList<>();

        String code = FieldUtil.getFieldValueByFieldName("code", c2Metadata.getData()).toString();
        String id = FieldUtil.getFieldValueByFieldName("id", c2Metadata.getData()).toString();

        C2Object c2Object = new C2Object();
        c2Object.setAction(c2Metadata.getAction());
        c2Object.setElementType(c2Metadata.getElementType());
        c2Object.setCode(code);
        c2Object.setId(id);
        c2Object.setPropertys(getPropertys(c2Metadata.getData(), templateMap));

        C2Objects.add(c2Object);
        return C2Objects;
    }

    /**
     * @param
     * @return java.util.List<common.util.c2xml.entity.C2Property>
     * @descrption 返回属性列表
     * @author MingRuiRen
     * @date 2018/7/19 13:24
     */
    private List<C2Property> getPropertys(Object object, Map<String, Object> templateMap) {

        List<C2Property> C2Propertys = new ArrayList<>();

        for (Map.Entry<String, Object> entry : templateMap.entrySet()) {

            String key = entry.getKey();
            String valueKey = (String) entry.getValue();

            String name = key;
            String content = FieldUtil.getFieldValueByFieldName(valueKey, object).toString();

            C2Property property = new C2Property();
            property.setName(name);
            property.setContent(content);
            C2Propertys.add(property);
        }

        return C2Propertys;
    }

    /**
     * @param
     * @return common.util.c2xml.entity.C2Mappings
     * @descrption 返回Mappings对象
     * @author MingRuiRen
     * @date 2018/7/19 13:49
     */
    @Override
    public C2Mappings getMappings(Map<Object, Object> map) {

        C2Mappings mappings = new C2Mappings();

        List<C2Mapping> list = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            MappingBean mappingBean = (MappingBean) entry.getKey();
            Map<String, Object> templateMap = (Map<String, Object>) entry.getValue();
            list.addAll(getC2MappingsList(mappingBean, templateMap));
        }
        mappings.setMapping(list);

        return mappings;
    }

    private List<C2Mapping> getC2MappingsList(MappingBean mappingBean, Map<String, Object> mappingTemplateMap) {

        List<C2Mapping> c2Mappings = new ArrayList<>();
        List<C2Property> C2Propertys = new ArrayList<>();
        //属性
        C2Mapping mapping = new C2Mapping();
        mapping.setAction(mappingBean.getAction());
        mapping.setId(mappingBean.getMapid());
        mapping.setElementType(mappingBean.getElementType());
        mapping.setElementID(mappingBean.getElementID());
        mapping.setParentType(mappingBean.getParentType());
        mapping.setParentID(mappingBean.getParentID());
        mapping.setParentCode(mappingBean.getParentCode());
        mapping.setElementCode(mappingBean.getElementCode());
        //属性节点
        mapping.setPropertys(getPropertys(mappingBean, mappingTemplateMap));

        c2Mappings.add(mapping);
        return c2Mappings;
    }


    @Override
    public String beanToXml(Object obj, Class<?> load) throws JAXBException, UnsupportedEncodingException {

        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        marshaller.marshal(obj, out);
        String xmlContent = out.toString("utf-8");
        return xmlContent;
    }


}