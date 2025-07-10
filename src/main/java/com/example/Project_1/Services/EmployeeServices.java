package com.example.Project_1.Services;


import com.example.Project_1.Dto.EmployeeDto;
import com.example.Project_1.Model.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface EmployeeServices extends UserDetailsService {

    List<EmployeeDto> getAll();
    EmployeeDto getById(Integer id);
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee( Integer id,EmployeeDto employeeDto,String role ,Authentication authentication) throws AccessDeniedException;
    void deleteEmployee(Integer id ,String currentUserRole);

    List<EmployeeDto> getAllAdmin();

    String login(EmployeeDto employee2);
    List<Employee> searchName(String keyword);

}
