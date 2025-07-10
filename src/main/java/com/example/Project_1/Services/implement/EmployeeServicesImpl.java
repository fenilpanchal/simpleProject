package com.example.Project_1.Services.implement;

import com.example.Project_1.Mapper.EmployeeMapper;
import com.example.Project_1.Model.Employee;
import com.example.Project_1.Dto.EmployeeDto;
import com.example.Project_1.Model.EmployeePrincipal;
import com.example.Project_1.Model.Role;
import com.example.Project_1.Repository.EmployeeRepository;
import com.example.Project_1.Repository.RoleRepository;
import com.example.Project_1.Services.EmployeeServices;
import com.example.Project_1.Services.JWTServices;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServicesImpl implements EmployeeServices {

    @Autowired
    @Lazy
    private AuthenticationManager manager;

    @Autowired
    private EmployeeRepository employee2Repository;

    @Autowired
    private RoleRepository repository;

    @Autowired
    private JWTServices services;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServicesImpl(EmployeeMapper employeeMapper, RoleRepository repository, EmployeeRepository employee2Repository, AuthenticationManager manager) {
        this.employeeMapper = employeeMapper;
        this.employee2Repository = employee2Repository;
        this.manager = manager;
        this.repository = repository;
    }

    /// dto

    public List<EmployeeDto> getAll() {
        log.info(" getAll Employee ! ");
        return employee2Repository.findAll()
                .stream()
                .map(employeeMapper::dto)
                .collect(Collectors.toList());
    }

    public EmployeeDto getById(Integer id) {
        log.info(" getById Method !!  :{}", id);
        Employee employee = employee2Repository.findById(id).orElseThrow(
                () -> new RuntimeException (" Employee Id not Found : " + id));
        return employeeMapper.dto(employee);
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.info("Creating Employee with username : {}", employeeDto.getUsername());

        if (employee2Repository.findByUsername(employeeDto.getUsername()) != null) {
            throw new RuntimeException("Username already exists. Please choose a different username.");
        }
        if (!employeeDto.getEmail().toLowerCase().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            throw new RuntimeException("Email must be a valid Gmail address (e.g.,Employee@gmail.com)");
        }

        Role role = repository.findByName(employeeDto.getRole().toUpperCase())
                .orElseThrow(() -> new UsernameNotFoundException(" Role not Found : "));

        Employee employee = Employee.builder()
                .username(employeeDto.getUsername())
                .password(encoder.encode(employeeDto.getPassword()))
                .email(employeeDto.getEmail())
                .role(role)
                .build();

        return employeeMapper.dto(employee2Repository.save(employee));
    }
    public EmployeeDto updateEmployee(Integer id, EmployeeDto employeeDto, String role, Authentication authentication) throws AccessDeniedException {
        log.info("Update Employee with username : {}", employeeDto.getUsername());

        if (!employeeDto.getEmail().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            throw new RuntimeException("Email must be a valid Gmail address (e.g.,Employee@gmail.com)");
        }

        Employee employee = employee2Repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Id"));
        employee.setUsername(employeeDto.getUsername());
        employee.setPassword(encoder.encode(employeeDto.getPassword()));
        employee.setEmail(employeeDto.getEmail());

        Role role2 = repository.findByName(employeeDto.getRole().toUpperCase())
                .orElseThrow(() -> new RuntimeException(" Role not found: "));

        employee.setRole(role2);
        return employeeMapper.dto(employee2Repository.save(employee));
    }

    public void deleteEmployee(Integer id,String currentUserRole) {

        boolean employee = employee2Repository.existsById(id);

            if (!employee){
                throw new RuntimeException ("Employee with ID " + id + " not found");
            }

        employee2Repository.deleteById(id);
        log.warn(" Deleted successfully : {} ", id);
    }

    public List<EmployeeDto> getAllAdmin(){
        List<Employee> admins = employee2Repository.findByRoleName("ADMIN");
        return admins.stream()
                .map(employeeMapper::dto)
                .collect(Collectors.toList());
    }

/// dto

    public String login(EmployeeDto employee2) {
        log.info(" Login SuccessFully :{}", employee2.getUsername());

        // add proper validations /done
        if (employee2.getEmail()==null || employee2.getEmail().isEmpty()){
            throw new RuntimeException("Please enter your Email ID!");
        }
        if (!employee2.getEmail().matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            throw new RuntimeException("Email must be a valid Gmail address (e.g.,Employee@gmail.com)");
        }

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(employee2.getUsername(), employee2.getPassword()));
        if (authentication.isAuthenticated()){
            Employee employee3 = employee2Repository.findByUsername(employee2.getUsername());
            return services.generateToken(employee3.getUsername(),(employee3.getRole()));
        }
        return "Fail";
    }

    public String logoutEmployee (Integer id,String token){

        String actualToken = token.substring(7);
        EmployeeDto employee = getById(id);
        String usernameFromToken = services.First(actualToken);

        if (!employee.getUsername().equals(usernameFromToken)) {
            throw new RuntimeException("Invalid user. You can only logout with your own User-Id.");
        }
        return "Log-out Successfully ..";
    }

    ///Search api

    public List<Employee> searchName(String keyword){
        return employee2Repository.findByUsernameContaining(keyword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employee2Repository.findByUsername(username);

        if (employee == null) {
            throw new UsernameNotFoundException("Not Found");
        }
        return new EmployeePrincipal(employee);
    }
}
