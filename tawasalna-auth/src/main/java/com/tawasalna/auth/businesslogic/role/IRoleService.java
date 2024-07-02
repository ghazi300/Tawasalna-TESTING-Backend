package com.tawasalna.auth.businesslogic.role;

import com.tawasalna.auth.models.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Role add(String roleName);

    List<Role> getAllRole();

    Optional<Role> getRoleByName(String name);
}
