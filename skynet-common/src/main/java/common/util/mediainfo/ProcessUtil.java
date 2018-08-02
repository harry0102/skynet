package common.util.mediainfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ProcessUtil {
	public static void exec(String command, File outFile, File errorFile) throws IOException, InterruptedException {
		LOGGER.debug("run command:" + command);
		Process p = Runtime.getRuntime().exec(command);
		new StreamReader(p.getErrorStream(), errorFile);
		new StreamReader(p.getInputStream(), outFile);
		int ret = p.waitFor();
	}

	private static class StreamReader extends Thread {
		public StreamReader(InputStream inputStream, File outFile) throws IOException {
			this.inputStream = inputStream;
			process = new ProcessShower(outFile);
			super.start();
		}

		@Override
		public void run() {
			try {
				int c;
				while ((c = inputStream.read()) != -1) {
					process.show(c);
				}
			} catch (IOException e) {
				LOGGER.error("read data error", e);
			} finally {
				try {
					process.close();
				} catch (IOException e) {
					// IGNORE
				}
			}
		}

		private final InputStream inputStream;
		private ProcessShower process;
	}

	private static class ProcessShower {
		public ProcessShower(File outputFile) throws IOException {
			printStream = new FileWriter(outputFile);
		}

		public void show(int c) throws IOException {
			printStream.write(c);
		}

		public void close() throws IOException {
			printStream.close();
		}

		private Writer printStream;
	}

	private ProcessUtil() {
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessUtil.class);
}
