/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: C2Objects
 * Author:   MingRuiRen
 * Date:     2018/7/19 10:29
 * Description: C2Objects
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.c2xml.adi;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈C2Objects〉
 *
 * @author MingRuiRen
 * @create 2018/7/19
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "Objects"
})
@XmlRootElement(name = "Objects")
public class C2Objects {

    @XmlElement(name = "Object", required = true)
    protected List<C2Object> Objects;

    public List<C2Object> getObjects() {
        return Objects;
    }

    public void setObjects(List<C2Object> objects) {
        Objects = objects;
    }
}