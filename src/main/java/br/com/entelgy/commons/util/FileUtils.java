package br.com.entelgy.commons.util;

import com.liferay.portal.kernel.util.FileUtil;

import java.util.HashSet;

public class FileUtils {

	private static final HashSet<String> WHITELIST_EXTENSIONS = new HashSet<>();

	static {
		WHITELIST_EXTENSIONS.add("doc");
		WHITELIST_EXTENSIONS.add("docx");
		WHITELIST_EXTENSIONS.add("pdf");
		WHITELIST_EXTENSIONS.add("png");
		WHITELIST_EXTENSIONS.add("jpeg");
		WHITELIST_EXTENSIONS.add("jpg");
		WHITELIST_EXTENSIONS.add("gif");
		WHITELIST_EXTENSIONS.add("xls");
		WHITELIST_EXTENSIONS.add("xlsx");
	}

	private FileUtils() {
	}

	public static boolean isValidExtension(String extension) {
		return WHITELIST_EXTENSIONS.contains(extension);
	}

	public static String getExtension(String filename) {
		return FileUtil.getExtension(filename);
	}

}
