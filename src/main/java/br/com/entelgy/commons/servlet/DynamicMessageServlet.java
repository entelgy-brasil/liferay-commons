package br.com.entelgy.commons.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;

import br.com.entelgy.commons.util.JSONUtils;

public class DynamicMessageServlet extends HttpServlet {

    private static final String DDL_FIELD_MESSAGE = "Message";
    private static final String DDL_FIELD_ID = "ID";

    private static final Log LOGGER = LogFactoryUtil.getLog(DynamicMessageServlet.class);

    private static final long serialVersionUID = 2L;

    private static final String CONTENT_TYPE_JSON_UTF8 = "application/json; charset=utf-8";
    private static final String CHARSET_UTF8 = "utf-8";
    private static final String LANGUAGE_PARAM = "language";
    private static final String GROUP_ID_PARAM = "groupId";
    private static final String DDL_NAME_EN = "Messages";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        String language = req.getParameter(LANGUAGE_PARAM);
        
        Long groupId = 0L;
        
        try {
        	
        	groupId = Long.parseLong(req.getParameter(GROUP_ID_PARAM));
			
		} catch (NumberFormatException e) {
			;
		}
        

        if (StringUtils.isBlank(language) || groupId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {

            List<DDLRecordSet> dynamicLists = DDLRecordSetLocalServiceUtil.getRecordSets(groupId);
            DDLRecordSet dynamicList = findByNameEn(dynamicLists, DDL_NAME_EN);
            Map<String, String> messages = loadMessages(dynamicList, language);

            resp.setCharacterEncoding(CHARSET_UTF8);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType(CONTENT_TYPE_JSON_UTF8);

            resp.getWriter().append(JSONUtils.toJson(messages));

        } catch (Exception e) {
            LOGGER.error("Error loading dynamic data list messages", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private DDLRecordSet findByNameEn(List<DDLRecordSet> lists, String nameEn) {
        for (DDLRecordSet ddlRecordSet : lists) {
            if (StringUtils.equals(ddlRecordSet.getName(LocaleUtil.ENGLISH), nameEn)) {
                return ddlRecordSet;
            }
        }

        return null;
    }

    private Map<String, String> loadMessages(DDLRecordSet dynamicList, String language) throws SystemException, PortalException {
        Map<String, String> messages = new HashMap<>();

        if (dynamicList != null) {
            for (DDLRecord message : dynamicList.getRecords()) {
                Integer id = (Integer) message.getFieldValue(DDL_FIELD_ID);
                String messageValue = (String) message.getFieldValue(DDL_FIELD_MESSAGE, getLocale(language));

                messages.put(id.toString(), messageValue);
            }
        } else {
            LOGGER.warn("Dynamic data list named 'Messages' has not been found on this site");
        }

        return messages;
    }

    private Locale getLocale(String language) {
        return LocaleUtil.fromLanguageId(language);
    }
}
