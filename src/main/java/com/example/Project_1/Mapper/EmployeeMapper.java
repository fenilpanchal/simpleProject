package com.example.Project_1.Mapper;

import com.example.Project_1.Dto.EmployeeDto;
import com.example.Project_1.Model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {RoleMapperHelper.class})
public interface EmployeeMapper {

    EmployeeDto dto (Employee employee);

    Employee entity (EmployeeDto employeeDto);
}
