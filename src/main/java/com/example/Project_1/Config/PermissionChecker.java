package com.example.Project_1.Config;

import com.example.Project_1.Model.Employee;
import com.example.Project_1.Model.EmployeePrincipal;
import com.example.Project_1.Repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component("permissionChecker")
public class PermissionChecker {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean canUpdateUser(Authentication auth, Integer targetUserId) {
        if (auth == null || !(auth.getPrincipal() instanceof EmployeePrincipal principal)) return false;

        Employee currentUser = principal.getEmployee();

        if (getRoleLevel(currentUser.getRole().getName()) == 3) {return false;}

        Employee targetUser = employeeRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        int currentRoleLevel = getRoleLevel(currentUser.getRole().getName());
        int targetRoleLevel = getRoleLevel(targetUser.getRole().getName());

        return currentRoleLevel < targetRoleLevel || currentUser.getId().equals(targetUser.getId());
    }

    private int getRoleLevel(String role) {
        return switch (role.toUpperCase()) {
            case "SUPER_ADMIN" -> 1;
            case "ADMIN" -> 2;
            case "USER" -> 3;
            default -> Integer.MAX_VALUE;
        };
    }

    public boolean canDelete(Authentication auth, Integer targetUserId) {
        if (auth == null || !(auth.getPrincipal() instanceof EmployeePrincipal principal)) return false;

        Employee employee = principal.getEmployee();

       Employee getUser = employeeRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Employee id " + targetUserId + " is not Found " ));

       int currentLevel = getLevel(employee.getRole().getName());
        int targetLevel = getLevel(getUser.getRole().getName());

        return currentLevel < targetLevel || employee.getId().equals(getUser.getId());
    }

    private int getLevel(String role) {
        return switch (role.toUpperCase()) {
            case "SUPER_ADMIN" -> 1;
            case "ADMIN" -> 2;
            case "USER" -> 3;
            default -> Integer.MAX_VALUE;
        };
    }


    public boolean canAccessOnlyAdmin(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof EmployeePrincipal principal)) return false;

        Employee currentUser = principal.getEmployee();
        String roleName = currentUser.getRole().getName().toUpperCase();

        return roleName.equals("ADMIN");
    }

}
