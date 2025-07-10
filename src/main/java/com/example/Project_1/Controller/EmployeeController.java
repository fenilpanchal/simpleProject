package com.example.Project_1.Controller;

import com.example.Project_1.Dto.EmployeeDto;
import com.example.Project_1.Model.Employee;
import com.example.Project_1.Services.implement.EmployeeServicesImpl;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/Employee")
public class EmployeeController {

    @Autowired
    public EmployeeServicesImpl employeeServicesImpl;

///Dto
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        return ResponseEntity.ok(employeeServicesImpl.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@Valid @PathVariable Integer id){
        return ResponseEntity.ok(employeeServicesImpl.getById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        log.info("Register API called by : {}", employeeDto.getUsername());
        EmployeeDto e = employeeServicesImpl.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(e); // add proper responseCode / done
    }

    @PreAuthorize("@permissionChecker.canUpdateUser(authentication, #id)")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Integer id,
            @Valid @RequestBody EmployeeDto employeeDto,
            @RequestParam(required = false, defaultValue = "USER") String role,
            Authentication authentication) throws AccessDeniedException {

        log.info("Update API called by : {}", authentication.getName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeServicesImpl.updateEmployee(id,employeeDto, role, authentication));
    }

    @PreAuthorize("@permissionChecker.canDelete(authentication, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id,String token) {

        employeeServicesImpl.deleteEmployee(id, token);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@permissionChecker.canAccessOnlyAdmin(authentication)")
    @GetMapping("/admin")
    public ResponseEntity<List<EmployeeDto>> onlyAdminCanUse(){
        return ResponseEntity.ok(employeeServicesImpl.getAllAdmin());
    }
    /// Search
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployee(@RequestParam String keyword) {
            List<Employee> name = employeeServicesImpl.searchName(keyword);
            return ResponseEntity.ok(name);
    }
/// login , logout
    @PostMapping("/login") // modify method name // done
    public ResponseEntity<String> loginEmployee ( @RequestBody EmployeeDto employee2 ){
         return ResponseEntity.ok(employeeServicesImpl.login(employee2));
    }

    @PostMapping("/logout/{id}")// move logic to service layer // done
    public ResponseEntity<String> logoutEmployee( @PathVariable Integer id,@RequestHeader( value = "Authorization") String token){
        try{
        String response = employeeServicesImpl.logoutEmployee(id,token);
        return ResponseEntity.ok(response);
        }catch (RuntimeException er){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er.getMessage());
        }
    }
}