package org.flowable.ui.common.security;

import java.util.ArrayList;
import java.util.List;

import org.flowable.idm.api.User;
import org.flowable.ui.common.model.RemoteUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Person;



public class SecurityUtils {

    private static User assumeUser;

    private SecurityUtils() {
    }


    public static String getCurrentUserId() {
        User user = getCurrentUserObject();
        if (user != null) {
            return user.getId();
        }
        return null;
    }


    public static User getCurrentUserObject() {
        if (assumeUser != null) {
            return assumeUser;
        }
        Person person = CurrentUserHelper.currentUser();
        String name = person.getName();
        RemoteUser user = new RemoteUser();
        user.setEmail("admin@flowable.com");
        user.setPassword("123456");
        user.setId(name);
        user.setEmail("");
        user.setDisplayName(name);
        user.setFullName(name);
        List<String> pris = new ArrayList<>();
        pris.add(DefaultPrivileges.ACCESS_MODELER);
        pris.add(DefaultPrivileges.ACCESS_IDM);
        pris.add(DefaultPrivileges.ACCESS_ADMIN);
        pris.add(DefaultPrivileges.ACCESS_TASK);
        pris.add(DefaultPrivileges.ACCESS_REST_API);
        user.setPrivileges(pris);
        return user;
    }

    public static FlowableAppUser getCurrentFlowableAppUser() {
        FlowableAppUser user = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null && securityContext.getAuthentication() != null) {
            Object principal = securityContext.getAuthentication().getPrincipal();
            if (principal instanceof FlowableAppUser) {
                user = (FlowableAppUser) principal;
            }
        }
        return user;
    }

    public static boolean currentUserHasCapability(String capability) {
        FlowableAppUser user = getCurrentFlowableAppUser();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            if (capability.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public static void assumeUser(User user) {
        assumeUser = user;
    }

    public static void clearAssumeUser() {
        assumeUser = null;
    }
}
