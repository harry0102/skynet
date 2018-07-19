package common.util.xml.jaxbc2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hubert
 * @date 2018/6/8
 * @description FieldUtil
 */
public class FieldUtil {

    /**
     * 根据属性名获取属性值
     */
    private Object getFieldValueByName(String fieldName, Object obj) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(getter, new Class[]{});
            return method.invoke(obj, new Object[]{});
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param obj 通用对象
     * @return map
     */
    public Map<String, Object> getFiledsInfo(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Object> infoMap = new HashMap<>(30);
        for (Field field : fields) {
            infoMap.put(field.getName(), getFieldValueByName(field.getName(), obj));
        }
        /*for(int i=0;i<fields.length;i++){
            infoMap.put(fields[i].getName(),getFieldValueByName(fields[i].getName(), obj));
		}*/
        return infoMap;
    }

    /**
     * 将java对象转map
     *
     * @param obj 通用对象
     * @return map
     * @throws IllegalAccessException 非法类型
     */
    public Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 初始化map数据
     *
     * @param map_data_object 数据模型，key-value
     * @param map_template    模版对象
     * @param map_c2_data     c2数据,key-value
     */
    public void initDataMap(Map<String, Object> map_data_object, Map map_template, Map<String, String> map_c2_data) {

        for (Object mapData : map_template.entrySet()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) mapData;
            if (map_data_object.containsKey(entry.getKey())) {
                map_c2_data.put(entry.getValue(), String.valueOf(map_data_object.get(entry.getKey())));
            }
        }

    }
}
