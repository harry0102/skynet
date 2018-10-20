/**
 * Copyright (C), 2015-2018, 杭州行云有限公司
 * FileName: FTPUtils
 * Author:   MingRuiRen
 * Date:     2018/7/24 14:26
 * Description: FTPUtils
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈FTPUtils〉
 *
 * @author MingRuiRen
 * @create 2018/7/24
 * @since 1.0.0
 */
public class FtpUtil {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    private static final String UTF_8 = "utf-8";
    private static final String ISO_8859_1 = "iso-8859-1";
    private static final int TRANSTIMEOUT = 5 * 60 * 60 * 1000;
    private static final int CONNECTTIMEOUT = 30 * 60 * 1000;


    private static FTPClient ftpClient;

    public FtpUtil(Ftp ftp) throws Exception {
        connectFtp(ftp);
    }

    public static void main(String[] args) throws Exception {


        String sourcePath = "D:\\迅雷下载\\视频文件\\";
        String remotePath = "/AAA杭州cp/scan/";

        Ftp ftp = new Ftp();
        ftp.setIp("192.168.4.53");
        ftp.setUserName("vstore");
        ftp.setPassWord("vspukka");

        FtpUtil ftpUtil = new FtpUtil(ftp);

        List<File> list = new ArrayList<>();
        List<File> files = getFiles(sourcePath, list);
        for (File f : files) {
            String path = f.getPath();
            String remoteFileName = f.getName();

            ftpUtil.upload(path, remotePath, remoteFileName);

        }
        closeFtp();

    }


    /**
     * @param ftp
     * @return boolean
     * @descrption 获取ftp连接
     * @author MingRuiRen
     * @date 2018/7/25 13:45
     */
    public static boolean connectFtp(Ftp ftp) throws Exception {
        ftpClient = new FTPClient();
        boolean flag = false;
        int reply;
        if (ftp.getPort() == null) {
            ftpClient.connect(ftp.getIp(), 21);
        } else {
            ftpClient.connect(ftp.getIp(), ftp.getPort());
        }
        ftpClient.setControlEncoding("utf-8");
        ftpClient.login(ftp.getUserName(), ftp.getPassWord());
        //设置传输超时时间为5H
        ftpClient.setDataTimeout(TRANSTIMEOUT);
        //连接超时为0.5H
        ftpClient.setConnectTimeout(CONNECTTIMEOUT);
        ftpClient.enterLocalActiveMode();//主动模式
//        ftpClient.enterLocalPassiveMode();//被动模式

        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            return flag;
        }
        flag = true;
        return flag;
    }


    /**
     * @param sourcePath
     * @param list
     * @return java.util.List<java.io.File>
     * @descrption 递归获取某路径下的所有文件，文件夹，并输出
     * @author MingRuiRen
     * @date 2018/7/25 13:46
     */
    public static List<File> getFiles(String sourcePath, List<File> list) {

        logger.info("FtpUtil.getFiles begin, sourcePath={}", sourcePath);

        File file = new File(sourcePath);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (File f : files) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (f.isDirectory()) {
                    getFiles(f.getPath(), list);
                } else {
                    list.add(f);
                }

            }
        } else {
            list.add(file);
        }
        return list;
    }


    /**
     * @param
     * @return void
     * @descrption 关闭ftp连接
     * @author MingRuiRen
     * @date 2018/7/25 13:46
     */
    public static void closeFtp() {

        try {
            ftpClient.logout();
        } catch (IOException e) {
            logger.error("FTP退出异常!", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    logger.error("FTP断开连接异常!", e);
                }
            }
        }


    }


    /**
     * 创建目录(有则切换目录，没有则创建目录)
     *
     * @param dir
     * @return
     */
    public static boolean createDir(String dir) {

        String d;
        try {

            //目录编码，解决中文路径问题
            d = new String(strUnicode(dir.toString(), UTF_8, ISO_8859_1));
            //尝试切入目录
            if (ftpClient.changeWorkingDirectory(d)) {
                return true;
            }
            String[] arr = dir.split("/");
            StringBuffer sbfDir = new StringBuffer();
            //循环生成子目录
            for (String s : arr) {
                sbfDir.append("/");
                sbfDir.append(s);
                //目录编码，解决中文路径问题
                d = new String(strUnicode(sbfDir.toString(), UTF_8, ISO_8859_1));
                //尝试切入目录
                if (ftpClient.changeWorkingDirectory(d)) {
                    continue;
                }
                if (!ftpClient.makeDirectory(d)) {
                    logger.info("[失败]ftp创建目录：" + sbfDir.toString());
                    return false;
                }
                logger.info("[成功]创建ftp目录：" + sbfDir.toString());
            }
            //将目录切换至指定路径
            return ftpClient.changeWorkingDirectory(d);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * @param localPath
     * @return long
     * @descrption 获取本地文件大小
     * @author MingRuiRen
     * @date 2018/7/25 13:49
     */
    public static long getFileSize(String localPath) {
        logger.info("FtpUtil.getFileSize, sourcePath={}", localPath);
        long localFileSize = 0L;
        File localFile = new File(localPath);
        if (localFile.exists() && localFile.isFile()) {
            localFileSize = localFile.length();
        }
        logger.info("FtpUtil.getFileSize, sourcePath={}, localFileSize={}", localPath, localFileSize);
        return localFileSize;
    }


    /**
     * @param remoteDir
     * @param remoteFileName
     * @return long
     * @descrption 获取原创FTP文件大小
     * @author MingRuiRen
     * @date 2018/7/25 13:49
     */
    public static long getFtpFileSize(String remoteDir, String remoteFileName) throws IOException {

        logger.info("FtpUtil.getFtpFileSize, remoteDir={},remoteFileName={}", remoteDir, remoteFileName);
        long ftpFileSize = 0L;
        //创建远程目录，若存在则跳过

        createDir(remoteDir);
        FTPFile[] ftpFiles = ftpClient.listFiles(strUnicode(remoteFileName, UTF_8, ISO_8859_1));
        if (null != ftpFiles && ftpFiles.length > 0) {
            ftpFileSize = ftpFiles[0].getSize();
        }
        logger.info("FtpUtil.getFtpFileSize, remoteDir={},remoteFileName={},ftpFileSize={}", new Object[]{remoteDir, remoteFileName, ftpFileSize});
        return ftpFileSize;
    }


    /**
     * @param str
     * @param src
     * @param dest
     * @return java.lang.String
     * @descrption 字符串编码
     * @author MingRuiRen
     * @date 2018/7/25 13:50
     */
    public static String strUnicode(String str, String src, String dest) throws UnsupportedEncodingException {
        return new String(str.getBytes(src), dest);
    }

    /**
     * @param localFile
     * @param remotePath
     * @param remoteFileName
     * @return boolean
     * @descrption 本地文件上传到指定ftp目录
     * <p>
     * commons-net的FTPClient，在使用public InputStream retrieveFileStream(String remote)
     * 方法时需要特别注意，在调用这个接口后，一定要手动close掉返回的InputStream，然后再调用completePendingCommand方法，若不是按照这个顺序，则不对，伪代码：
     * InputStream is = ftpClient.retrieveFileStream(remote);
     * is.close();
     * ftpClient.completePendingCommand();
     * 参考链接 https://blog.csdn.net/wangjian5748/article/details/3404619
     * @author MingRuiRen
     * @date 2018/7/25 13:50
     */
    public boolean upload(String localFile, String remotePath, String remoteFileName) throws Exception {

        boolean success = true;
        RandomAccessFile raf = null;
        OutputStream ftpOut = null;
        try {

            //本地文件长度(字节)
            long localFileSize = getFileSize(localFile);

            if (!remotePath.startsWith("/")) {
                remotePath = "/" + remotePath;
            }
            if (!remotePath.endsWith("/")) {
                remotePath = remotePath + "/";
            }

            //远程文件大小(字节)
            long remoteSize = getFtpFileSize(remotePath, remoteFileName);
            if (remoteSize == localFileSize) {
                return true;
            }

            Long buffer = 1024 * 10L;
            ftpClient.setBufferSize(buffer.intValue());
            String remote = remotePath + remoteFileName;

            if (remoteSize > localFileSize) {
                boolean flag = ftpClient.deleteFile(strUnicode(remote, UTF_8, ISO_8859_1));
                if (flag) {
                    remoteSize = 0L;
                }
            }

            raf = new RandomAccessFile(localFile, "r");

            //打印上传进度
            long process = 0L;
            long localreadbytes = remoteSize;
            long step = localFileSize / 100;
            ftpOut = ftpClient.appendFileStream(strUnicode(remote, UTF_8, ISO_8859_1));
            if (remoteSize > 0) {
                ftpClient.setRestartOffset(remoteSize);
                process = remoteSize / step;
                raf.seek(remoteSize);
                localreadbytes = remoteSize;
            }

            int readByte = 0;
            byte[] b = new byte[buffer.intValue()];
            while ((readByte = raf.read(b)) != -1) {

                //写入目标FTP文件
                ftpOut.write(b, 0, readByte);

                localreadbytes += readByte;
                if (localreadbytes / step != process) {
                    process = localreadbytes / step;
                    logger.info("上传进度:" + remote + "==" + process + "%");
                }

            }


        } catch (Exception e) {
            success = false;
            logger.error("上传文件到FTP异常！", e);
        } finally {
            try {

                if (null != raf) {
                    raf.close();
                }
                if (null != ftpOut) {
                    ftpOut.close();
                    ftpClient.completePendingCommand();
                }

            } catch (Exception e) {
                logger.error("关闭流异常:" + e.getMessage(), e);
            }
        }
        return success;
    }


}