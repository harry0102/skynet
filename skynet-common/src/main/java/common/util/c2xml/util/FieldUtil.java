/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: FieldUtil
 * Author:   MingRuiRen
 * Date:     2018/7/19 14:04
 * Description: FieldUtil
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.c2xml.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 〈一句话功能简述〉<br>
 * 〈FieldUtil〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
public class FieldUtil {

    private static final Logger logger = LoggerFactory.getLogger(FieldUtil.class);

    /**
     * 根据属性名获取属性值
     * 1.考虑安全访问范围内的属性，没有权限访问到的属性不读取
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static Object getFieldValueByFieldName(String fieldName, Object object) {
        logger.info(object.toString());
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            logger.error("该对象没有对应的属性字段!  fieldName={}",fieldName);
            return "";
        }
    }


    /**
     * 根据属性名获取属性元素，包括各种安全范围和所有父类
     * 3.考虑父类继承过来的属性，包括四类访问权限，private，protect，default，public
     *
     * @param fieldName
     * @param object
     * @return
     */
    private Field getFieldByClasss(String fieldName, Object object) {
        Field field = null;
        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                // 这里甚么都不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会进入

            }
        }
        return field;

    }


}