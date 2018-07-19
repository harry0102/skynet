package common.util.xml.c2.value;

/**
 * @author hubert
 * @date 2018/5/31
 * @description Series
 */
public class Series {

    private Integer id;
    private String name;
    private String code;
    private String description;
    private String director;
    private String releaseyear;
    private String actorDisplay;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseyear() {
        return releaseyear;
    }

    public void setReleaseyear(String releaseyear) {
        this.releaseyear = releaseyear;
    }

    public String getActorDisplay() {
        return actorDisplay;
    }

    public void setActorDisplay(String actorDisplay) {
        this.actorDisplay = actorDisplay;
    }
}
