package common.util.c2xml.service;

import common.util.c2xml.adi.C2Mappings;
import common.util.c2xml.adi.C2Objects;
import common.util.c2xml.entity.C2Metadata;
import common.util.c2xml.entity.MappingBean;
import common.util.c2xml.entity.Program;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface IBeantoXmlService {


    C2Objects getObjects( Map<Object,Object> map);


    C2Mappings getMappings( Map<Object,Object> map);

    /**
     * @param obj
     * @param load
     * @return java.lang.String
     * @descrption 对象转xml
     * @author MingRuiRen
     * @date 2018/7/19 16:11
     */
    String beanToXml(Object obj, Class<?> load) throws JAXBException, UnsupportedEncodingException;

}
