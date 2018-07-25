/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: Ftp
 * Author:   MingRuiRen
 * Date:     2018/7/24 14:42
 * Description: Ftp
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util;

/**
 * 〈一句话功能简述〉<br>
 * 〈Ftp〉
 *
 * @author MingRuiRen
 * @create 2018/7/24
 * @since 1.0.0
 */
public class Ftp {

    /**
     * ip地址
     */
    private String ip;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;



    public Ftp() {
    }



    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}