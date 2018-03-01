package br.com.entelgy.commons.util;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

public class LiferayUtil {

    private static final Log LOG = LogFactoryUtil.getLog(LiferayUtil.class);

    private static final String LOGIN = "login";
    private static final String ENCRYPT = "encrypt";

    private static final String LOGIN_UTIL = "com.liferay.portlet.login.util.LoginUtil";
    private static final String PASSWORD_ENCRYPTOR_UTIL = "com.liferay.portal.security.pwd.PasswordEncryptorUtil";

    private static final String CAPTCHA_TEXT = "captchaText";
    private static final String REDIRECT_FORMAT = "%s%s%s";
    private static final String PAGE_FORMAT = "%s/%s";
    public static final String LOCALE_KEY = "org.apache.struts.action.LOCALE";

    private LiferayUtil() {
    }

    /**
     * Login in portal liferay
     */
    public static Boolean loginLiferay(final HttpServletRequest request, final HttpServletResponse response, final String login, final String password) {

        Boolean valid = Boolean.FALSE;

        try {

            ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

            Class<?> lClass = classLoader.loadClass(LOGIN_UTIL);
            Method method = lClass.getDeclaredMethod(LOGIN, HttpServletRequest.class, HttpServletResponse.class, String.class, String.class,
                Boolean.TYPE, String.class);
            method.invoke(null, request, response, login, password, Boolean.FALSE, CompanyConstants.AUTH_TYPE_EA);

            valid = Boolean.TRUE;

        } catch (Exception e) {
            LOG.info("M=loginLiferay , login invalid", e);
        }

        return valid;
    }

    /**
     * Encrypt Password liferay
     */
    public static String encryptPasswordLiferay(final String password, final String userPassword) {

        String encrypt = StringPool.BLANK;

        try {

            ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

            Class<?> lClass = classLoader.loadClass(PASSWORD_ENCRYPTOR_UTIL);
            Method method = lClass.getDeclaredMethod(ENCRYPT, String.class, String.class);

            encrypt = (String) method.invoke(null, password, userPassword);
        } catch (Exception e) {
            LOG.info("M=encryptPasswordLiferay , encrypt password", e);
        }

        return encrypt;
    }

    /**
     * Valid Captcha
     *
     * @throws CaptchaException
     */
    public static void validCaptcha(final ActionRequest request) throws CaptchaException {

        String captchaText = ParamUtil.getString(request, CAPTCHA_TEXT);
        String captcha = getCaptchaSession(request);

        if (!captchaText.equals(captcha)) {
            throw new CaptchaException();
        }

    }

    /**
     * Get Captcha in Session
     */
    public static String getCaptchaSession(final ActionRequest request) {
        PortletSession session = request.getPortletSession();

        Enumeration<String> names = session.getAttributeNames();

        while (names.hasMoreElements()) {

            String name = names.nextElement();

            if (name.contains("CAPTCHA_TEXT")) {
                return (String) session.getAttribute(name);
            }
        }

        return StringPool.BLANK;
    }


    /**
     * Redirect Public Home
     */
    public static String redirectHomePublic(final ThemeDisplay themeDisplay) {
        return String.format(REDIRECT_FORMAT, themeDisplay.getURLPortal(), themeDisplay.getPathFriendlyURLPublic(), themeDisplay.getSiteGroup()
            .getFriendlyURL());
    }

    /**
     * Redirect Private Home
     */
    public static String redirectHomePrivate(final ThemeDisplay themeDisplay) {
        return String.format(REDIRECT_FORMAT, themeDisplay.getURLPortal(), themeDisplay.getPathFriendlyURLPrivateGroup(), themeDisplay.getSiteGroup()
            .getFriendlyURL());
    }

    /**
     * Redirect Private Page
     */
    public static String redirectPagePrivate(final ThemeDisplay themeDisplay, final String page) {
        return String.format(PAGE_FORMAT, redirectHomePrivate(themeDisplay), page);
    }

    /**
     * Disable defaults messages
     */
    public static void disableSessionMessage(PortletRequest request) {
        SessionMessages.add(request, PortalUtil.getPortletId(request) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
        SessionMessages.add(request, PortalUtil.getPortletId(request) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * Theme Display
     */
    public static ThemeDisplay getThemeDisplay(PortletRequest portletRequest) {
        return (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
    }

    public static void setLanguageIdCookie(HttpServletRequest request, HttpServletResponse response, String languageId){
        HttpSession session = request.getSession();
        Locale locale = LocaleUtil.fromLanguageId(languageId);
        session.setAttribute(LOCALE_KEY, locale);
        LanguageUtil.getLanguage().updateCookie(request, response, locale);
    }

}
