package com.spring.www.service;

import com.spring.www.domain.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRole(long id);
    List<Role> getRoles();
    void createRole(Role role);
    void deleteRole(long id);
}
