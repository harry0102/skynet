/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: MediaInfo
 * Author:   MingRuiRen
 * Date:     2018/7/26 9:51
 * Description: MediaInfo
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util.mediainfo;

/**
 * 〈一句话功能简述〉<br> 
 * 〈MediaInfo〉
 *
 * @author MingRuiRen
 * @create 2018/7/26
 * @since 1.0.0
 */
public class MediaInfo {

    /**
     * 码率
     */
    private Integer bitRate;
    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 时长
     */
    private Integer duration;

    /**
     * 分辨率宽
     */
    private Integer width;

    /**
     * 帧率
     */
    private String framerate;

    /**
     * 分辨率高
     */
    private Integer height;

    public Integer getBitRate() {
        return bitRate;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFramerate() {
        return framerate;
    }

    public void setFramerate(String framerate) {
        this.framerate = framerate;
    }
}