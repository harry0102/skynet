package common.util.mediainfo;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 计算大文件MD5 
 *  David  2012-10-12
 */
public class GetBigFileMD5 {
	protected final static Log log = LogFactory.getLog(GetBigFileMD5.class);

   
    static MessageDigest MD5 = null;


    static {
        try {
        MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
          log.error(ne.getMessage(), ne);
        }
    }


    /**
     * 对一个文件获取md5值
     * @return md5串
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
        fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
            MD5.update(buffer, 0, length);
            }


            return new String(Hex.encodeHex(MD5.digest()));
        } catch (FileNotFoundException e) {
        	log.error(e.getMessage(), e);
            return null;
        } catch (IOException e) {
        	log.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                if (fileInputStream != null)
                fileInputStream.close();
            } catch (IOException e) {
            	log.error(e.getMessage(), e);
            }
        }
    }


    /**
     * 求一个字符串的md5值
     * @param target 字符串
     * @return md5 value
     */
    public static String MD5(String target) {
        return DigestUtils.md5Hex(target);
    }

    
	public static String getMD5new(String name) {
		String calcMd5 = "";
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(name));
			calcMd5 = DigestUtils.md5Hex(inputStream);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}finally {
			try {
				if (inputStream != null) {
					inputStream.close();// 关闭流
				}
			} catch (Exception ex) {
				log.error("getMD5new关闭流失败:"+ex.getMessage(), ex);
			}
		}
		return calcMd5;
	}
    

    public static void main(String[] args) throws IOException {
    	System.out.println(System.currentTimeMillis());
    	String path = "F:\\DOWNLOAD\\mm\\星际迷航1：星际旅行.720p.国英双语.BD中英双字.mkv";
    	System.out.println("MD5="+getMD5new(path));
    	System.out.println(System.currentTimeMillis());

    }
}