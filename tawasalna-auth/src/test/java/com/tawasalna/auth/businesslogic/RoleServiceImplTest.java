package com.tawasalna.auth.businesslogic;

import com.tawasalna.auth.businesslogic.role.RoleServiceImpl;
import com.tawasalna.auth.models.Role;
import com.tawasalna.auth.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @InjectMocks
    RoleServiceImpl roleService;
    @Mock
    RoleRepository roleRepository;

    @Test
    void addTest() {
        Role expectedrole = new Role(null, "ROLE_ADMIN");

        when(roleService.add("ROLE_ADMIN")).thenReturn(expectedrole);

        Role addRole = roleService.add("ROLE_ADMIN");

        assertEquals(addRole, expectedrole);
    }

    @Test
    void getAllRole() {
        // Given
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("1", "ROLE_SUPER_ADMIN"));
        roles.add(new Role("2", "ROLE_ADMIN"));
        Mockito.when(roleRepository.findAll()).thenReturn(roles);

        // When
        List<Role> foundRoles = roleService.getAllRole();

        // Then
        assertEquals(roles.size(), foundRoles.size());
        for (int i = 0; i < roles.size(); i++) {
            assertEquals(roles.get(i).getId(), foundRoles.get(i).getId());
            assertEquals(roles.get(i).getName(), foundRoles.get(i).getName());
        }
    }

    @Test
    void getRoleByName() {
        String roleName = "ROLE_ADMIN";
        Role expectedrole = new Role();

        expectedrole.setName(roleName);

        Mockito.when(roleRepository.findByName(roleName)).thenReturn(Optional.of(expectedrole));

        Optional<Role> addrole = roleService.getRoleByName(roleName);

        assertTrue(addrole.isPresent());
        assertEquals(expectedrole, addrole.get());
    }
}
