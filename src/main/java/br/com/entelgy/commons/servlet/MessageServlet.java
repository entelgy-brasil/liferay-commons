package br.com.entelgy.commons.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import br.com.entelgy.commons.util.JSONUtils;
import br.com.entelgy.commons.util.MessageUtil;

public class MessageServlet extends HttpServlet {
	private static final Log LOGGER = LogFactoryUtil.getLog(MessageServlet.class);
	
	private static final long serialVersionUID = 1L;
	
	private static final String CONTENT_TYPE_JSON_UTF8 = "application/json; charset=utf-8";
	private static final String CHARSET_UTF8 = "utf-8";
	private static final String LANGUAGE_PARAM = "language";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		
		String language = req.getParameter(LANGUAGE_PARAM);
		
		if (StringUtils.isBlank(language)) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		try {
			
			Map<String, String> messages = MessageUtil.loadMessagesBy(language);
			
			resp.setCharacterEncoding(CHARSET_UTF8);
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType(CONTENT_TYPE_JSON_UTF8);

			resp.getWriter().append(JSONUtils.toJson(messages));
			
		} catch (IOException e) {
			LOGGER.error(e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
