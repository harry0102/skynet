package common.util.mediainfo;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class MediaUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(MediaUtil.class);

	// private final static String MEDIAINFO_CMD =
	// MediaUtil.class.getResource("/media/MediaInfo.exe").toExternalForm().substring("file:".length());
	final static String DEFAULT_MEDIA_INFO = "0|0|0|0|0|0|0";

	public static void main(String[] args) {
		String media = getMediaInfoStr("d:/test/6M.ts");
		System.err.println(media);
		String os = System.getProperties().getProperty("os.name");
		System.out.println(os);
	}

	public static String getMediaInfoStr(String localFilePath) {
		String os = System.getProperties().getProperty("os.name");
		File out = null, err = null;
		try {
			out = File.createTempFile("mediainfo", "out");
			err = File.createTempFile("mediainfo", "err");
			// localFilePath = "E:\\opt\\nas4\\test\\22.ts";
			String line = "";
			String filename = MediaUtil.class.getResource("/media/mediainfo.template").toExternalForm();
			File file = new File(filename.substring("file:".length()));
			BufferedReader reader = null;
			try {
				LOGGER.info("以行为单位读取文件内容，一次读一整行：");
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				String MEDIAINFO_CMD = null;
				if (os.startsWith("win") || os.startsWith("Win")) {// windows
					MEDIAINFO_CMD = MediaUtil.class.getResource("/media/MediaInfo.exe").toExternalForm()
							.substring("file:".length());
				} else {// Linux
					MEDIAINFO_CMD ="mediainfo";
				}
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					// 显示行号
					LOGGER.info("line ==========" + line + ": " + tempString);
					LOGGER.info("localFilePath =========" + localFilePath);
					LOGGER.info(
							"ProcessUtil =========" + MEDIAINFO_CMD + " --Inform=" + tempString + " " + localFilePath);
					ProcessUtil.exec(MEDIAINFO_CMD + " --Inform=" + tempString + " " + localFilePath, out, err);
					line += FileUtils.readFileToString(out, Charsets.UTF_8.name());
					line = line.replaceAll("\r\n", "");
					line = line.replaceAll("\n", "");
				}
				reader.close();
			} catch (IOException e) {
				LOGGER.error("获取mediainfo异常："+localFilePath+e.getMessage(),e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("out put:" + line);
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("err put:" + FileUtils.readFileToString(err, Charsets.UTF_8.name()));
			}

			return Strings.isNullOrEmpty(line == null ? null : line.trim()) ? DEFAULT_MEDIA_INFO : line;
		} catch (Exception e) {
			LOGGER.error("read medainfo error", e);
		} finally {
			if (out != null) {
				try {
					FileUtils.forceDelete(out);
				} catch (IOException e) {
					LOGGER.error("delete out file error", e);
				}
			}

			if (err != null) {
				try {
					FileUtils.forceDelete(err);
				} catch (IOException e) {
					LOGGER.error("delete err file error", e);
				}
			}
		}

		return "";
	}

	public static MediaInfo getMediaInfo(String localPath){
		MediaInfo mediaInfo=new MediaInfo();
		String mediainfo = MediaUtil.getMediaInfoStr(localPath);
		try {
			String mediainfoarray[] = mediainfo.split("\\|");
			if (mediainfoarray != null) {
				if (mediainfoarray[0] != null && !"".equals(mediainfoarray[0])) {
					BigDecimal bd1 = new BigDecimal(mediainfoarray[0]);
					int rate = bd1.divide(new BigDecimal(1000), 0, BigDecimal.ROUND_HALF_EVEN).intValue();
					mediaInfo.setBitRate(rate);
				}
				if (mediainfoarray[1] != null && !"".equals(mediainfoarray[1])) {
					mediaInfo.setFileSize(mediainfoarray[1]);
				}
				if (mediainfoarray[2] != null && !"".equals(mediainfoarray[2])) {
					Integer value = ParseDate.parseSecond(mediainfoarray[2]);
					mediaInfo.setDuration(value);
				}
				if (mediainfoarray[3] != null && !"".equals(mediainfoarray[3])) {
					mediaInfo.setWidth(Integer.parseInt(mediainfoarray[3]));
				}
				if (mediainfoarray[4] != null && !"".equals(mediainfoarray[4])) {
					mediaInfo.setHeight(Integer.parseInt(mediainfoarray[4]));
				}
				if (mediainfoarray[5] != null && !"".equals(mediainfoarray[5])) {
					mediaInfo.setFramerate(mediainfoarray[5]);
				}
			}
		}catch (Exception e){
			LOGGER.error("获取mediainfo信息异常："+localPath);
		}
		return mediaInfo;
	}

}
