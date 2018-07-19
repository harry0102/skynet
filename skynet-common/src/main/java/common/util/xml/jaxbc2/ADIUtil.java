package common.util.xml.jaxbc2;

import common.util.xml.c2.adi.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.Object;
import java.util.Map;

/**
 * @author hubert
 * @date 2018/6/8
 * @description ADIUtil
 */
public class ADIUtil {

	/**
	 *
	 * @param map 数据对象，Series,Program,Movie,Picture
	 * @param action 类型
	 * @param type 数据类型
	 * @param adi xml根节点
	 * @param objectFactory 初始化对象
	 */
	public void adiToXml(Map<String,String> map, String action, String type, ADI adi, ObjectFactory objectFactory, Object object) {
		if (EnumUtil.ADI_TYPE.Mapping.getValue().equals(type)) {
			objectMappingToXml((Mappings)object,objectFactory,map,map.get("ParentType"),map.get("ElementType"),action);
			adi.setMappings((Mappings)object);
		} else {
			objectToXml((Objects)object,objectFactory,map,type,action);
			adi.setObjects((Objects)object);
		}
	}

	/**
	 * @param objects 节点对象
	 * @param factory 初始化对象
	 * @param map 对应的内容，series,program,movie,picture,category,cast,castmap，对象
	 * @param type 对应类型，series,program,movie,picture,category,cast,castmap，字符串
	 * @param action reject,update,delete
	 */
	private void objectToXml(Objects objects , ObjectFactory factory, Map<String,String> map, String type, String action){
		common.util.xml.c2.adi.Object object_map = factory.createObject();
		try {
			String code = map.get("Code");
			if (code == null || "".equals(code)) {
				code = map.get("code");
			}
			//内容类型，默认属性填充值
			object_map.setElementType(type);
			object_map.setID(code);
			object_map.setCode(code);
			object_map.setAction(action);

			//动态属性填充值
			entryMapInit(map,factory,object_map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		objects.getObject().add(object_map);
	}

	/**
	 * 多个mapping,在上一层做控制，该方法一次只处理一个
	 * map存放mapping的property
	 */
	private void objectMappingToXml(Mappings mappings ,ObjectFactory factory ,Map<String, String> map,String elementType,String parentType,String action) {
		Mapping mapping = factory.createMapping();
		mapping.setAction(action);
		mapping.setParentType(parentType);
		mapping.setParentCode(map.get("ParentCode"));
		mapping.setParentID(map.get("ParentCode"));
		mapping.setElementType(elementType);
		mapping.setElementCode(map.get("ElementCode"));
		mapping.setElementID(map.get("ElementCode"));

		entryMapInit(map,factory,mapping);

		mappings.getMapping().add(mapping);
	}

	/**
	 * 初始化Property属性
	 * @param map 数据对象
	 * @param factory 初始化
	 * @param obj 通用对象
	 */
	private void entryMapInit(Map<String, String> map, ObjectFactory factory, Object obj) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (StringUtils.equalsIgnoreCase(entry.getKey(),"code")) {
				//code不写在Property属性里
				continue;
			}

			if (StringUtils.equalsIgnoreCase(entry.getKey(),"elementtype")) {
				//elementtype不写在Property属性里
				continue;
			}

			if (StringUtils.equalsIgnoreCase(entry.getKey(),"action")) {
				//action不写在Property属性里
				continue;
			}

			if (StringUtils.equalsIgnoreCase(entry.getKey(),"parentcode")) {
				//parentcode不写在Property属性里
				continue;
			}

			if (StringUtils.equalsIgnoreCase(entry.getKey(),"elementcode")) {
				//elementcode不写在Property属性里
				continue;
			}

			if (StringUtils.equalsIgnoreCase(entry.getKey(),"parenttype")) {
				//parenttype不写在Property属性里
				continue;
			}

			Property property_map = factory.createProperty();
			property_map.setName( entry.getKey());
			property_map.setContent(entry.getValue());
			if (obj instanceof common.util.xml.c2.adi.Object) {
				((common.util.xml.c2.adi.Object) obj).getProperty().add(property_map);
			} else if (obj instanceof Mapping) {
				((Mapping) obj).getProperty().add(property_map);
			}
		}
	}
}
