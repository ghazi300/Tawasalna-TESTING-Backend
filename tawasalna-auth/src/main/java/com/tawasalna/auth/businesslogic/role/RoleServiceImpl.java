package com.tawasalna.auth.businesslogic.role;

import com.tawasalna.auth.exceptions.InvalidRoleException;
import com.tawasalna.auth.models.Role;
import com.tawasalna.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role add(String roleName) {
        if (roleRepository.existsByName(roleName))
            throw new InvalidRoleException(roleName, "Role not found");

        return roleRepository.save(new Role(null, roleName));
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
