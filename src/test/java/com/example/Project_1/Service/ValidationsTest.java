//package com.example.Project_1.Service;
//
//import com.example.Project_1.Model.Employee2;
//import com.example.Project_1.Model.Role;
//import com.example.Project_1.Repository.Employee2Repository;
//import com.example.Project_1.Services.implement.Employee2ServicesImpl;
//import com.example.Project_1.Services.JWTServices;
//import jakarta.transaction.Transactional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//@Transactional
//public class ValidationsTest {
//
//    @InjectMocks
//    private Employee2ServicesImpl employee2ServicesImpl;
//
//    @Mock
//    private Employee2Repository employee2Repository;
//
//    @Mock
//    private AuthenticationManager manager;
//
//    @Mock
//    private JWTServices services;
//
//
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
///// register
//    @Test
//    public void UsernameAlreadyExist() {
//        Employee2 employee1 = new Employee2();
//        employee1.setUsername("existingUser");
//        employee1.setEmail("E@gmail.com");
//
//        when(employee2Repository.findByUsername("existingUser")).thenReturn(new Employee2());
//        Authentication superAdmin = authentication("SUPER_ADMIN");
//
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.register(employee1, "USER",superAdmin)
//        );
//        assertEquals("Username already exists. Please choose a different username.", ex.getMessage());
//    }
//    @Test
//    public void emptyEmail(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("emptyEmail");
//        employee2.setEmail("");
//
//        when(employee2Repository.findByUsername("emptyEmail")).thenReturn(null);
//        Authentication adminAuth = authentication("ADMIN");
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.register(employee2, "USER", adminAuth)
//        );
//        assertEquals("Please enter your Email ID!", ex.getMessage());
//    }
//    @Test
//    public void invalidEmail(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("Email");
//        employee2.setEmail("notgmail@.com");
//        when(employee2Repository.findByUsername("Email")).thenReturn(null);
//
//        Authentication superAdminAuth = authentication("SUPER_ADMIN");
//
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.register(employee2, "USER", superAdminAuth)
//        );
//        assertEquals("Email must be a valid Gmail address (e.g., example@gmail.com)", ex.getMessage());
//    }
//
//    @Test
//    public void adminCanCreateUserOnly(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("Admin");
//        employee2.setEmail("A@gmail.com");
//
//        when(employee2Repository.findByUsername("Admin")).thenReturn(null);
//        Authentication adminAuth = authentication("ADMIN");
//
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.register(employee2,"MANAGER", adminAuth)
//        );
//        assertEquals("Admins can only create Users.", ex.getMessage());
//    }
//
//    @Test
//    public void superAdminCreateBoth(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("Super");
//        employee2.setEmail("S@gmail.com");
//
//        when(employee2Repository.findByUsername("Super")).thenReturn(null);
//        Authentication authentication = authentication("SUPER_ADMIN");
//        RuntimeException ex = assertThrows(RuntimeException.class, () ->
//                employee2ServicesImpl.register(employee2, "MANAGER", authentication)
//        );
//        assertEquals("Super-Admin can only create Admins or Users.", ex.getMessage());
//    }
//
//    @Test
//    public void superAdminCanCreateAdmin(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("Super_Admin");
//        employee2.setEmail("SA@gmail.com");
//
//        when(employee2Repository.findByUsername("Super_Admin")).thenReturn(null);
//        Authentication authentication = authentication("USER");
//
//        RuntimeException runtimeException= assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.register(employee2,"SUPER_ADMIN",authentication));
//        assertEquals("Only a Super-Admin can create another Admin.", runtimeException.getMessage());
//    }
//
//    @Test
//    public void adminAreNotCreateAdmin(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("AdminCreateAdmin");
//        employee2.setEmail("A@gmail.com");
//
//        when(employee2Repository.findByUsername("AdminCreateAdmin")).thenReturn(null);
//        Authentication authentication = authentication("ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.register(employee2,"ADMIN",authentication));
//        assertEquals("Admins are NOT allowed to create Admin accounts.", runtimeException.getMessage());
//    }
//
//    /// Update
//
//    @Test
//    public void adminCanNotUpdateAnotherAdmin(){
//
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("AnotherAdmin");
//        employee2.setEmail("Ano@gmail.com");
//
//
//        lenient().when(employee2Repository.findById(1)).thenReturn(Optional.of(new Employee2()));
//        Authentication authentication = authentication("ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.UpdateE(1,employee2,"ADMIN",authentication));
//        assertEquals("Admins are NOT allowed to Update Admin accounts.", runtimeException.getMessage());
//    }
//
//    @Test
//    public void superAdminCanUpdateAdmin(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("UpdateAdmin");
//        employee2.setEmail("U@gmail.com");
//        employee2.setPassword("password");
//
//        lenient().when(employee2Repository.findById(2)).thenReturn(Optional.of(new Employee2()));
//        Authentication authentication = authentication("ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.UpdateE(2,employee2,"SUPER_ADMIN",authentication));
//        assertTrue(runtimeException.getMessage().contains("Only a Super-Admin can Update another Admin"));
//    }
//
//    @Test
//    public void superAdminCanUpdateAll(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("UpdateAll");
//        employee2.setEmail("User@gmail.com");
//
//        lenient().when(employee2Repository.findById(3)).thenReturn(Optional.of(new Employee2()));
//        Authentication authentication = authentication("SUPER_ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.UpdateE(3,employee2,"MANAGER",authentication));
//        assertEquals("Super-Admin can only Update Admins or Users.", runtimeException.getMessage());
//    }
//
//    @Test
//    public void adminCanUpdateOnlyUsers(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("UpdateOnlyUser");
//        employee2.setEmail("Update@gmail.com");
//        employee2.setPassword("password1");
//
//        lenient().when(employee2Repository.findById(4)).thenReturn(Optional.of(new Employee2()));
//        Authentication authentication = authentication("ADMIN");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.UpdateE(4,employee2,"MANAGER",authentication));
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
//        assertDoesNotThrow(()-> employee2ServicesImpl.Delete(1,"SUPER_ADMIN"));
//        verify(employee2Repository).deleteById(1);
//
//
//        Employee2 employee3 = new Employee2();
//        employee3.setId(2);
//        employee3.setRole(Role.USER);
//
//        when(employee2Repository.findById(2)).thenReturn(Optional.of(employee3));
//        assertDoesNotThrow(()-> employee2ServicesImpl.Delete(2,"ADMIN"));
//        verify(employee2Repository).deleteById(2);
//
//
//        Employee2 employee4 = new Employee2();
//        employee4.setId(3);
//        employee4.setRole(Role.ADMIN);
//
//        when(employee2Repository.findById(3)).thenReturn(Optional.of(employee4));
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.Delete(3,"ADMIN"));
//        assertEquals("Access Denied: Only Super-Admin can delete Admins and Users.", runtimeException.getMessage());
//
//
//        Employee2 employee5 = new Employee2();
//        employee5.setId(4);
//        employee5.setRole(Role.USER);
//
//        when(employee2Repository.findById(4)).thenReturn(Optional.of(employee5));
//        RuntimeException runtimeException1 = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.Delete(4,"USER"));
//        assertEquals("Access Denied: Only Super-Admin can delete Admins and Users.", runtimeException1.getMessage());
//
//    }
//    @Test
//    public void loginMissingEmailTest(){
//        Employee2 employee2 = new Employee2();
//        employee2.setUsername("Employee");
//        employee2.setPassword("Employee");
//
//        RuntimeException runtimeException = assertThrows(RuntimeException.class,()->
//                employee2ServicesImpl.login(employee2));
//        assertEquals("Please enter your Email ID!", runtimeException.getMessage());
//    }
//    @Test
//    public void invalidEmailTest(){
//        Employee2 employee2 = new Employee2();
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
//        Employee2 employee2 =new Employee2();
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
//        Employee2 employee = new Employee2();
//        employee.setUsername("user1");
//        employee.setPassword("pass123");
//        employee.setEmail("user1@gmail.com");
//
//        Authentication auth = Mockito.mock(Authentication.class);
//        when(auth.isAuthenticated()).thenReturn(false);
//
//        when(manager.authenticate(any())).thenReturn(auth);
//
//        String response = employee2ServicesImpl.login(employee);
//        assertEquals("Fail", response);
//    }
//
//}
