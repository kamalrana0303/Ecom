package com.generic.ecom.service;

import com.generic.ecom.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {
    public void createPrimaryRoles();

    @Transactional
    void createPrimaryUser();

    void saveRoles(String rolename);

    boolean isRoleExist(String rolename);

    List<Role> getAllRoles();
}
