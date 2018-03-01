package br.com.entelgy.commons.util;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class MessageUtil {

	private static final String CHARSET_ISO88591 = "ISO-8859-1";
	private static final String CHARSET_UTF8 = "UTF-8";
	private static final String LANGUAGE_PROPERTIES = "content/Language";

	private MessageUtil(){
	}
	
	public static Map<String, String> loadMessagesBy(String languageId) throws IOException {
		
		Locale locale = LocaleUtil.fromLanguageId(languageId);
		
		ResourceBundle resourceBundle;
		if (locale.equals(LocaleUtil.UK) || locale.equals(LocaleUtil.US)) {
			resourceBundle = ResourceBundle.getBundle(LANGUAGE_PROPERTIES, Locale.ROOT);
		} else {
			resourceBundle = ResourceBundle.getBundle(LANGUAGE_PROPERTIES, locale);
		}
		
		Enumeration<String> keys = resourceBundle.getKeys();
		
		Map<String, String> messages = new HashMap<>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			messages.put(key, getStringUTF8(resourceBundle, key));
		}
		return messages;
	}

	private static String getStringUTF8(ResourceBundle resourceBundle, String key) throws UnsupportedEncodingException {
		return new String(resourceBundle.getString(key).getBytes(CHARSET_ISO88591), CHARSET_UTF8);
	}
}