package br.com.entelgy.commons.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {

	private static final Log LOG = LogFactoryUtil.getLog(FileReaderUtil.class);

	private FileReaderUtil() {

	}

	public static List<String> readerLines(String filePath) {

		List<String> results = new ArrayList<>();

		try {

			File file = ResourceUtils.getFile(FileReaderUtil.class.getResource(filePath).getPath());
			String currentLine;

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				
				while ((currentLine = br.readLine()) != null) {
					results.add(currentLine);
				}
				
			}
			

		}
		catch (IOException e) {
			LOG.error(String.format("Error while read file %s", filePath), e);
		}

		return results;
	}

	public static Path getResourceFilePath(String filePath) {

		return Paths.get(FileReaderUtil.class.getResource(filePath).getPath());
	}

}
