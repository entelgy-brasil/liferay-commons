package br.com.entelgy.commons.liferay.dto;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.PortletRequest;
import java.util.Locale;

public class PortalDTO {

	private final long companyId;
	private final long groupId;
	private final User user;
	private final long scopeGroupId;
	private final String portalUrl;
	private final String contextPath;
	private final Locale locale;
	private final String languageId;

	private PortalDTO(PortletRequest request) {
		this(getThemeDisplay(request));
	}

	private PortalDTO(ThemeDisplay themeDisplay) {
		this.companyId = themeDisplay.getCompanyId();
		this.groupId = themeDisplay.getSiteGroupId();
		this.user = themeDisplay.getUser();
		this.scopeGroupId = themeDisplay.getScopeGroupId();
		this.portalUrl = themeDisplay.getPortalURL();
		this.contextPath = themeDisplay.getPathContext();
		this.locale = themeDisplay.getLocale();
		this.languageId = themeDisplay.getLanguageId();
	}

	public long getCompanyId() {
		return companyId;
	}

	public long getGroupId() {
		return groupId;
	}

	public User getUser() {
		return user;
	}

	public long getScopeGroupId() {
		return scopeGroupId;
	}

	public String getPortalUrl() {
		return portalUrl;
	}

	public String getContextPath() {
		return contextPath;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguageId() {
        return languageId;
    }

	public static PortalDTO getInstance(ThemeDisplay themeDisplay) {
		return new PortalDTO(themeDisplay);
	}

	public static PortalDTO getInstance(PortletRequest request) {
		return new PortalDTO(request);
	}

	public static ThemeDisplay getThemeDisplay(PortletRequest request) {
		return (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
	}

}
