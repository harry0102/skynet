/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: MappingBean
 * Author:   MingRuiRen
 * Date:     2018/7/19 15:57
 * Description: MappingBean
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.c2xml.entity;

/**
 * 〈一句话功能简述〉<br> 
 * 〈MappingBean〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
public class MappingBean {

    String mapid;

    String action;

    String elementType;

    String elementID;

    String elementCode;

    String parentType;

    String parentID;

    String parentCode;

    String Type;

    String Sequence;

    public MappingBean(String mapid, String action, String elementType, String elementID, String elementCode, String parentType, String parentID, String parentCode, String type, String sequence) {
        this.mapid = mapid;
        this.action = action;
        this.elementType = elementType;
        this.elementID = elementID;
        this.elementCode = elementCode;
        this.parentType = parentType;
        this.parentID = parentID;
        this.parentCode = parentCode;
        Type = type;
        Sequence = sequence;
    }

    public String getMapid() {
        return mapid;
    }

    public void setMapid(String mapid) {
        this.mapid = mapid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementID() {
        return elementID;
    }

    public void setElementID(String elementID) {
        this.elementID = elementID;
    }

    public String getElementCode() {
        return elementCode;
    }

    public void setElementCode(String elementCode) {
        this.elementCode = elementCode;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String sequence) {
        Sequence = sequence;
    }
}