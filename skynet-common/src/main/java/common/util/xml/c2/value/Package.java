package common.util.xml.c2.value;

/**
 * @author hubert
 * @date 2018/6/11
 * @description Package
 */
public class Package {

    private Integer id;
    private String name;
    private String code;
    private Integer type;
    private String sortName;
    private String searchName;
    private String rentalPeriod;
    private String orderNumber;
    /*与mapping中对应字段名称不同*/
    private String licensingWindowStart;
    /*与mapping中对应字段名称不同*/
    private String licensingWindowEnd;
    private Integer price;
    private Integer status;
    private String description;


    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
}
