package com.example.Project_1.Mapper;

import com.example.Project_1.Model.Role;
import com.example.Project_1.Repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperHelper {

    private RoleRepository repository ;

    public Role map(String roleName) {
        return repository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }
    public String map(Role role) {
        return role.getName();
    }

}
