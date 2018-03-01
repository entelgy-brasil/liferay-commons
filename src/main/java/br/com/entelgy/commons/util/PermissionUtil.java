package br.com.entelgy.commons.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

public class PermissionUtil {

    private static final String PERMISSION_INVALID = "Permission invalid";
    private static final String PERMISSION_CHECKER_INVALID = "Permission checker user invalid";

    private static final Log LOG = LogFactoryUtil.getLog(PermissionUtil.class);

    private PermissionUtil() {
    }

    public static void setPermission(long companyId, Class<?> clazz, String resourceID, String roleName, String[] permissions) {

        try {

            resourcePermissions(companyId, clazz, resourceID, roleName, permissions);

        } catch (Exception e) {
            LOG.info(PERMISSION_INVALID, e);

        }
    }

    public static void setUserPermission(long companyId, long userId, Class<?> clazz, String resourceID, String roleName,
            String[] permissions) {

        try {

            permissionCheckerUser(userId);

            resourcePermissions(companyId, clazz, resourceID, roleName, permissions);

        } catch (Exception e) {
            LOG.info(PERMISSION_INVALID, e);
        }
    }

	private static void permissionCheckerUser(long userId) {

		try {

			User user = UserLocalServiceUtil.getUser(userId);

			PrincipalThreadLocal.setName(user.getUserId());

			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

		} catch (Exception e) {
			LOG.info(PERMISSION_CHECKER_INVALID, e);
		}
	}

    private static void resourcePermissions(long companyId, Class<?> clazz, String resourceID, String roleName,
            String[] permissions) throws PortalException, SystemException {

        long roleId = RoleLocalServiceUtil.getRole(companyId, roleName).getRoleId();

        ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, clazz.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
                resourceID, roleId, permissions);
    }

}
