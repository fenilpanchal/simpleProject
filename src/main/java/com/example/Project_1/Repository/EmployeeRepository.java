package com.example.Project_1.Repository;

import com.example.Project_1.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByUsername (String username);
    List<Employee> findByUsernameContaining(String keyword);
    List<Employee> findByRoleName (String roleName);
}
