//package com.example.Project_1.Service;
//
//import com.example.Project_1.Dto.EmployeeDto;
//import com.example.Project_1.Mapper.EmployeeMapper;
//import com.example.Project_1.Model.Employee2;
//import com.example.Project_1.Model.Role;
//import com.example.Project_1.Repository.Employee2Repository;
//import com.example.Project_1.Services.JWTServices;
//import com.example.Project_1.Services.implement.Employee2ServicesImpl;
//import jakarta.transaction.Transactional;
//
//import org.hibernate.sql.Update;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@Service
//@ExtendWith(MockitoExtension.class)
//@Transactional //rolling back
//
//    public class ServiceTest {
//
//    @InjectMocks
//    private Employee2ServicesImpl employee2ServicesImpl;
//
//    @Mock
//    private Employee2Repository employee2Repository;
//
//    @Mock
//    private EmployeeMapper mapper;
//
//    @Mock
//    private AuthenticationManager manager;
//
//    @Mock
//    private JWTServices services;
//
//    @Mock
//    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//    @Test
//    void getAllDto() {
//
//        Employee2 emp1 = new Employee2(1, "Abhi","A@123","A@gmail.com",Role.ADMIN);
//        Employee2 emp2 = new Employee2(2, "Ravi","R@123","R@gmail.com",Role.USER);
//
//        EmployeeDto dto1 = new EmployeeDto(1, "Abhi","A@123","A@gmail.com",Role.ADMIN);
//        EmployeeDto dto2 = new EmployeeDto(2, "Ravi","R@123","R@gmail.com",Role.USER);
//
//        List<Employee2> entityList = List.of(emp1, emp2);
//        List<EmployeeDto> x = List.of(dto1, dto2);
//
//        when(employee2Repository.findAll()).thenReturn(entityList);
//        when(mapper.dto(emp1)).thenReturn(dto1);
//        when(mapper.dto(emp2)).thenReturn(dto2);
//
//        List<EmployeeDto> result = employee2ServicesImpl.getAll();
//
//        assertEquals(2, result.size());
//        assertEquals("Abhi", result.get(0).getUsername());
//        assertEquals("Ravi", result.get(1).getUsername());
//    }
//
//    @Test
//    public void getEmployeeById(){
//        Employee2 employee = new Employee2(1, "Fenil", "F@123","F@gmail.com",Role.ADMIN);
//        EmployeeDto emp = new EmployeeDto(1, "Fenil", "F@123","F@gmail.com",Role.ADMIN);
//
//        when(employee2Repository.findById(1)).thenReturn(Optional.of(employee));
//        when(mapper.dto(employee)).thenReturn(emp);
//
//        EmployeeDto employeeDto = employee2ServicesImpl.getById(1);
//
//        assertNotNull(employeeDto);
//        assertEquals(emp,employeeDto);
//        assertEquals("Fenil",employeeDto.getUsername());
//
//    }
//
//    @Test
//    public void CreateTest() {
//        EmployeeDto employee2 = new EmployeeDto(20, "Vicky", "Vic@123", "Vic@gmail.com", Role.ADMIN);
//        Employee2 savedEmployee = new Employee2(26, "Vicky",encoder.encode( "Vic@123"), "Vic@gmail.com", Role.ADMIN);
//        Authentication authentication = new Authentication() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return Collections.singleton(() -> "SUPER_ADMIN");
//            }
//
//            @Override
//            public Object getCredentials() {
//                return null;
//            }
//
//            @Override
//            public Object getDetails() {
//                return null;
//            }
//
//            @Override
//            public Object getPrincipal() {
//                return null;
//            }
//
//            @Override
//            public boolean isAuthenticated() {
//                return true;
//            }
//
//            @Override
//            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//            }
//
//            @Override
//            public String getName() {
//                return "superadmin";
//            }
//        };
//
//        when(employee2Repository.save(any(Employee2.class))).thenReturn(savedEmployee);
//        EmployeeDto employee3 = employee2ServicesImpl.createEmployee(employee2,"ADMIN",authentication);
//
//        assertNotNull(employee3.getId());
//        assertEquals("Vicky", employee3.getUsername());
//        assertTrue(encoder.matches("Vic@123", employee3.getPassword()));
//        assertEquals("Vicky", employee3.getUsername());
//        System.out.println(" Create new user ==> "+ employee3.getUsername());
//    }
//
// ///////////////////   Update
//
//
//    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    @Test
//    public void DeleteTest(){
//        Employee2 employee2 = new Employee2(25,"Vicky","Vis@123","Vis@gmail.com",Role.ADMIN);
//
//        when(employee2Repository.findById(employee2.getId())).thenReturn(Optional.of(employee2));
//
//        employee2ServicesImpl.deleteEmployee(employee2.getId(),"SUPER_ADMIN");
//        assertNotNull(employee2);
//
//        assertEquals("Vicky", employee2.getUsername());
//    }
//
//    @Test
//    public void search(){
//        String keyword = "ab";
//        List<Employee2> expectedList = List.of(
//                new Employee2(1,"Abhi","A@123","A@gmail.com", Role.USER),
//                new Employee2(2, "Abhay","Ab@123","Ab@gmail.com",Role.USER)
//        );
//
//        when(employee2Repository.findByUsernameContaining(keyword))
//                .thenReturn(expectedList);
//        List<Employee2> result = employee2ServicesImpl.searchName(keyword);
//        assertEquals(2, result.size());
//        assertEquals("Abhi", result.get(0).getUsername());
//        assertEquals("Abhay", result.get(1).getUsername());
//    }
//
//    /// ///////
//    private Authentication authentication(String role) {
//        return new Authentication() {
//
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return Collections.singleton(() ->role);
//            }
//            @Override
//            public Object getCredentials() {
//                return null;
//            }
//            @Override
//            public Object getDetails() {
//                return null;
//            }
//            @Override
//            public Object getPrincipal() {
//                return null;
//            }
//            @Override
//            public boolean isAuthenticated() {
//                return true;
//            }
//            @Override
//            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
//
//            @Override
//            public String getName() {
//                return "superadmin";
//            }
//        };
//    }
//    /// register
//    @Test
//    public void UsernameAlreadyExist() {
//        EmployeeDto employee1 = new EmployeeDto();
//        employee1.setUsername("existingUser");
//        employee1.setEmail("E@gmail.com");
//
//        when(employee2Repository.findByUsername("existingUser")).thenReturn(new Employee2());
//        Authentication superAdmin = authentication("SUPER_ADMIN");
//
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.createEmployee(employee1, "USER",superAdmin)
//        );
//        assertEquals("Username already exists. Please choose a different username.", ex.getMessage());
//    }
//    @Test
//    public void emptyEmail(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("emptyEmail");
//        employee2.setEmail("");
//
//        when(employee2Repository.findByUsername("emptyEmail")).thenReturn(null);
//        Authentication adminAuth = authentication("ADMIN");
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.createEmployee(employee2, "USER", adminAuth)
//        );
//        assertEquals("Please enter your Email ID!", ex.getMessage());
//    }
//    @Test
//    public void invalidEmail(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("Email");
//        employee2.setEmail("notgmail@.com");
//        when(employee2Repository.findByUsername("Email")).thenReturn(null);
//
//        Authentication superAdminAuth = authentication("SUPER_ADMIN");
//
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.createEmployee(employee2, "USER", superAdminAuth)
//        );
//        assertEquals("Email must be a valid Gmail address (e.g., example@gmail.com)", ex.getMessage());
//    }
//
//    @Test
//    public void adminCanCreateUserOnly(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("Admin");
//        employee2.setEmail("A@gmail.com");
//
//        when(employee2Repository.findByUsername("Admin")).thenReturn(null);
//        Authentication adminAuth = authentication("ADMIN");
//
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.createEmployee(employee2,"MANAGER", adminAuth)
//        );
//        assertEquals("Admins can only create Users.", ex.getMessage());
//    }
//
//    @Test
//    public void superAdminCreateBoth(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("Super");
//        employee2.setEmail("S@gmail.com");
//
//        when(employee2Repository.findByUsername("Super")).thenReturn(null);
//        Authentication authentication = authentication("SUPER_ADMIN");
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.createEmployee(employee2, "MANAGER", authentication)
//        );
//        assertEquals("Super-Admin can only create Admins or Users.", ex.getMessage());
//    }
//
//    @Test
//    public void superAdminCanCreateAdmin(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("Super_Admin");
//        employee2.setEmail("SA@gmail.com");
//
//        when(employee2Repository.findByUsername("Super_Admin")).thenReturn(null);
//        Authentication authentication = authentication("USER");
//
//        RuntimeException runtimeException= assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.createEmployee(employee2,"SUPER_ADMIN",authentication));
//        assertEquals("Only a Super-Admin can create another Admin.", runtimeException.getMessage());
//    }
//
//    @Test
//    public void adminAreNotCreateAdmin(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("AdminCreateAdmin");
//        employee2.setEmail("A@gmail.com");
//
//        when(employee2Repository.findByUsername("AdminCreateAdmin")).thenReturn(null);
//        Authentication authentication = authentication("ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.createEmployee(employee2,"ADMIN",authentication));
//        assertEquals("Admins are NOT allowed to create Admin accounts.", runtimeException.getMessage());
//    }
//
//    /// Update
//
//    @Test
//    public void adminCanNotUpdateAnotherAdmin(){
//
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("AnotherAdmin");
//        employee2.setEmail("Ano@gmail.com");
//
//        lenient().when(employee2Repository.findById(1)).thenReturn(Optional.of(new Employee2()));
//        Authentication authentication = authentication("ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.updateEmployee(1,employee2,"ADMIN",authentication));
//        assertEquals("Admins are NOT allowed to Update Admin accounts.", runtimeException.getMessage());
//    }
//
//    @Test
//    public void superAdminCanUpdateAdmin(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("UpdateAdmin");
//        employee2.setEmail("U@gmail.com");
//        employee2.setPassword("password");
//
//        lenient().when(employee2Repository.findById(2)).thenReturn(Optional.of(new Employee2()));
//        Authentication authentication = authentication("ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.updateEmployee(2,employee2,"SUPER_ADMIN",authentication));
//        assertTrue(runtimeException.getMessage().contains("Only a Super-Admin can Update another Admin"));
//    }
//
//    @Test
//    public void superAdminCanUpdateAll(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("UpdateAll");
//        employee2.setEmail("User@gmail.com");
//
//        lenient().when(employee2Repository.findById(3)).thenReturn(Optional.of(new Employee2()));
//        Authentication authentication = authentication("SUPER_ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.updateEmployee(3,employee2,"MANAGER",authentication));
//        assertEquals("Super-Admin can only Update Admins or Users.", runtimeException.getMessage());
//    }
//
//    @Test
//    public void adminCanUpdateOnlyUsers(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("UpdateOnlyUser");
//        employee2.setEmail("Update@gmail.com");
//        employee2.setPassword("password1");
//
//        lenient().when(employee2Repository.findById(4)).thenReturn(Optional.of(new Employee2()));
//        Authentication authentication = authentication("ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.updateEmployee(4,employee2,"MANAGER",authentication));
//        assertEquals("Admins can only Update Users.", runtimeException.getMessage());
//    }
//
//    /// Delete
//
//    @Test
//    public void deleteValidation(){
//        Employee2 employee2 = new Employee2();
//        employee2.setId(1);
//        employee2.setRole(Role.ADMIN);
//
//        when(employee2Repository.findById(1)).thenReturn(Optional.of(employee2));
//        assertDoesNotThrow(()-> employee2ServicesImpl.deleteEmployee(1,"SUPER_ADMIN"));
//        verify(employee2Repository).deleteById(1);
//
//        Employee2 employee3 = new Employee2();
//        employee3.setId(2);
//        employee3.setRole(Role.USER);
//
//        when(employee2Repository.findById(2)).thenReturn(Optional.of(employee3));
//        assertDoesNotThrow(()-> employee2ServicesImpl.deleteEmployee(2,"ADMIN"));
//        verify(employee2Repository).deleteById(2);
//
//        Employee2 employee4 = new Employee2();
//        employee4.setId(3);
//        employee4.setRole(Role.ADMIN);
//
//        when(employee2Repository.findById(3)).thenReturn(Optional.of(employee4));
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.deleteEmployee(3,"ADMIN"));
//        assertEquals("Access Denied: Only Super-Admin can delete Admins and Users.", runtimeException.getMessage());
//
//        Employee2 employee5 = new Employee2();
//        employee5.setId(4);
//        employee5.setRole(Role.USER);
//
//        when(employee2Repository.findById(4)).thenReturn(Optional.of(employee5));
//        RuntimeException runtimeException1 = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.deleteEmployee(4,"USER"));
//        assertEquals("Access Denied: Only Super-Admin can delete Admins and Users.", runtimeException1.getMessage());
//
//    }
//    @Test
//    public void loginMissingEmailTest(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("Employee");
//        employee2.setPassword("Employee");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.login(employee2));
//        assertEquals("Please enter your Email ID!", runtimeException.getMessage());
//    }
//    @Test
//    public void invalidEmailTest(){
//        EmployeeDto employee2 = new EmployeeDto();
//        employee2.setUsername("Invalid");
//        employee2.setPassword("Invalid");
//        employee2.setEmail("Invalid@1234gmAiL.Com");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.login(employee2));
//        assertEquals("Email must be a valid Gmail address (e.g., example@gmail.com)", runtimeException.getMessage());
//    }
//
//    @Test
//    public void loginSuccessfullyTest(){
//        EmployeeDto employee2 =new EmployeeDto();
//        employee2.setUsername("Employee");
//        employee2.setPassword("Employee@123");
//        employee2.setEmail("Employee@gmail.com");
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.isAuthenticated()).thenReturn(true);
//        when(manager.authenticate(any())).thenReturn(authentication);
//
//        Employee2 employee3 = new Employee2();
//        employee3.setUsername("Employee");
//        employee3.setRole(Role.SUPER_ADMIN);
//
//        when(employee2Repository.findByUsername("Employee")).thenReturn(employee3);
//        when(services.generateToken("Employee",Role.SUPER_ADMIN)).thenReturn("jwt-token-xyz");
//
//        String token = employee2ServicesImpl.login(employee2);
//        assertEquals("jwt-token-xyz",token);
//    }
//
//    @Test
//    public void loginFailTest(){
//        EmployeeDto employee = new EmployeeDto();
//        employee.setUsername("user1");
//        employee.setPassword("pass123");
//        employee.setEmail("user1@gmail.com");
//
//        Authentication auth = Mockito.mock(Authentication.class);
//        when(auth.isAuthenticated()).thenReturn(false);
//
//        when(manager.authenticate(any())).thenReturn(auth);
//        String response = employee2ServicesImpl.login(employee);
//        assertEquals("Fail", response);
//    }
//}