package common.util.xml.jaxbc2;

/**
 * @author hubert
 * @date 2018/6/8
 * @description EnumUtil
 */
public class EnumUtil {

    public enum ACTION {

        REJIST("REJIST"),
        UPDATE("UPDATE"),
        DELETE("DELETE");

        String value;

        ACTION(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * 基础数据类型
     */
    public enum Content_Type {

        Series("Series"),
        Program("Program"),
        Movie("Movie"),
        Picture("Picture"),
        Category("Category");

        String value;

        Content_Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * xml报文包含类型,Y:必须项,N:选填项
     * 1. 剧头类型Serie_Picture,包含Series节点(Y),Picture节点(N),Mapping节点(Y/N)，当picture节点不是必须节点，若不包含时，则不能带有mapping节点
     * 2. 节目类型Program_Moive_Picture，包含Program节点(Y)，Movie节点(Y),Picture节点(Y),Papping节点(Y),Program与Movie的关联关系
     * 3. 子集类型Program_Moive_Picture_Series，包含Program节点(Y)，Movie节点(Y),Picture节点(Y),Series节点(Y),Mapping节点(Y),program与movie的关联关系,program与series的归属关系
     * 4  栏目分类Category_Picture,包含Category节点(Y),Picture节点(N),Mapping节点(Y/N)，当picture节点不是必须节点，若不包含时，则不能带有mapping节点
     * 5. 包-剧头信息Package_Series
     * 6. 包-单集Package_Program
     * 7. Mapping类型Mapping，mapping节点(Y),仅包含mapping，无其他节点信息，常用于节目绑定栏目，节目绑定产品包（前提是数据节点已下发）
     * 9. 其他类型待完善
     */
    public enum ADI_TYPE {

        Series_Picture("Series_Picture"),
        Program_Moive_Picture("Program_Moive_Picture"),
        Program_Moive_Picture_Series("Program_Moive_Picture_Series"),
        Category_Picture("Category_Picture"),
        Mapping("Mapping");

        String value;

        ADI_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


}
