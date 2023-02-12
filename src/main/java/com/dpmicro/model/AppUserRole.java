package com.dpmicro.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum AppUserRole implements GrantedAuthority {
    ROLE_DUMMY(0),
    NEW_USER(1);

    private static final long serialVersionUID = 1L;
    private final int id;
    private static final Map<Integer, AppUserRole> idMap = new HashMap<Integer, AppUserRole>();

    static {
        for (AppUserRole role : AppUserRole.values()) {
            idMap.put(role.getId(), role);
        }
    }

    AppUserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static AppUserRole valueById(int id) {
        return idMap.get(id);
    }

    public static List<AppUserRole> getRoles() {
        List<AppUserRole> roles = new ArrayList<AppUserRole>();
        roles.add(AppUserRole.valueById(ROLE_DUMMY.getId()));
        roles.add(AppUserRole.valueById(NEW_USER.getId()));
        return roles;
    }

    public String getAuthority() {
        return name();
    }

}
