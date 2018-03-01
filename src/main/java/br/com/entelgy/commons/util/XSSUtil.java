package br.com.entelgy.commons.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class XSSUtil {

	private static final List<Pattern> patterns;

	static {

		final Pattern[] defaultPatterns = new Pattern[] {

		        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),

		        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

		        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

		        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
		        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

		        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

		        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

		        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),

		        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),

		        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        
		        Pattern.compile("href[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        
		        Pattern.compile("<a>(.*?)</a>", Pattern.CASE_INSENSITIVE),
		        Pattern.compile("</a>", Pattern.CASE_INSENSITIVE),
		        Pattern.compile("<a(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        
		        Pattern.compile("<button>(.*?)</button>", Pattern.CASE_INSENSITIVE),
		        Pattern.compile("</button>", Pattern.CASE_INSENSITIVE),
		        Pattern.compile("<button(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        
		};

		patterns = Arrays.asList(defaultPatterns);

	}
	
    private XSSUtil() {
        // hiding implicit constructor
    }

	public static String stripXSS(String value) {

		if (value != null && !value.isEmpty()) {

			value = value.replaceAll("\0", "");

			value = value.replaceAll("", "");

			for (Pattern scriptPattern : patterns) {
				value = scriptPattern.matcher(value).replaceAll("");
			}
			
			if (value.isEmpty()) {
				value = "-";
			}

		}

		return value;
	}

}
