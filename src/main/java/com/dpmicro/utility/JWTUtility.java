package com.dpmicro.utility;

import java.util.HashMap;
import java.util.Map;

import com.dpmicro.model.AppUserRole;
import com.dpmicro.model.UserModel;

public class JWTUtility {

    public static Map<String, Object> setAuthDetails(Object appObject) {
        Map<String, Object> authMap = new HashMap<>();
        if (appObject instanceof UserModel) {
            UserModel userModel = (UserModel) appObject;
            authMap.put("name", userModel.getName());
            authMap.put("mobileNo", userModel.getMobileNo());
            authMap.put("email", userModel.getEmail());
            authMap.put("appUserRoleNames", AppUserRole.valueById(userModel.getUserType()));
        } else {
            Map map = (HashMap) appObject;
            authMap.put("name", map.get("name"));
            authMap.put("mobileNo", map.get("mobileNo"));
            authMap.put("email", map.get("email"));
            authMap.put("appUserRoleNames", map.get("appUserRoleNames"));
        }
        return authMap;
    }
}
