package com.tawasalna.auth.controller;

import com.tawasalna.auth.businesslogic.role.IRoleService;
import com.tawasalna.auth.models.Role;
import com.tawasalna.auth.payload.request.RoleDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Role Controller", description = "Controller with CRUD operations about managing roles.")
@CrossOrigin("*")
public class RoleController {

    private final IRoleService iRoleService;

    @PostMapping("/add")
    public ResponseEntity<Role> addRole(@RequestBody RoleDTO roleDTO) {
        return new ResponseEntity<>(
                iRoleService.add(roleDTO.getName()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Role>> findAll() {
        return new ResponseEntity<>(iRoleService.getAllRole(), HttpStatus.OK);
    }
}
