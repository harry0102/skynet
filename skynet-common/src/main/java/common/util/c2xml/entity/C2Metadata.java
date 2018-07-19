/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: C2Metadata
 * Author:   MingRuiRen
 * Date:     2018/7/19 16:16
 * Description: C2Metadata
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.c2xml.entity;

import org.apache.poi.ss.formula.functions.T;

/**
 * 〈一句话功能简述〉<br>
 * 〈C2Metadata〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
public class C2Metadata<T> {


    String ElementType;
    String Action;
    T data;

    public C2Metadata() {
    }

    public C2Metadata(String elementType, String action, T data) {
        ElementType = elementType;
        Action = action;
        this.data = data;
    }

    public String getElementType() {
        return ElementType;
    }

    public void setElementType(String elementType) {
        ElementType = elementType;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}