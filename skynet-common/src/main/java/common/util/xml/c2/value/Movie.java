package common.util.xml.c2.value;

/**
 * @author hubert
 * @date 2018/5/31
 * @description Movie
 */
public class Movie {

    private Integer id;
    private String name;
    private String code;
    private String fileurl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
