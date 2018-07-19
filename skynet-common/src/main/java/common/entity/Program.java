/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: Program
 * Author:   MingRuiRen
 * Date:     2018/7/18 15:51
 * Description: program
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.entity;

/**
 * 〈一句话功能简述〉<br> 
 * 〈program〉
 *
 * @author MingRuiRen
 * @create 2018/7/18
 * @since 1.0.0
 */
public class Program {

    private  String name;
    private  String OrderNumber;
    private  String OriginalName;

    public Program(String name, String orderNumber, String originalName) {
        this.name = name;
        OrderNumber = orderNumber;
        OriginalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getOriginalName() {
        return OriginalName;
    }

    public void setOriginalName(String originalName) {
        OriginalName = originalName;
    }
}