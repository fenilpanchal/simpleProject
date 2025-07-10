//package com.example.Project_1.Controller;
//
//import com.example.Project_1.Dto.EmployeeDto;
//import com.example.Project_1.Model.Employee2;
//import com.example.Project_1.Model.Role;
//import com.example.Project_1.Services.implement.Employee2ServicesImpl;
//import com.example.Project_1.Services.JWTServices;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//
//import org.mockito.ArgumentMatchers;
//
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser(username = "superadmin", authorities = {"SUPER_ADMIN"})
//@Import(ControllerTest.EmployeeControllerTestConfig.class)
//@ActiveProfiles("test")
//public class ControllerTest {
//
//    @MockitoBean
//    private Employee2ServicesImpl employee2ServicesImpl;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private JWTServices jwtServices;
//
//    @TestConfiguration
//    static class EmployeeControllerTestConfig {
//        @Bean
//        public Employee2ServicesImpl employee2ServicesImpl(){
//            return Mockito.mock(Employee2ServicesImpl.class);
//        }
//    }
//
//    @Test
//    public void GetAll()throws Exception{
//        List<EmployeeDto> employee2 = List.of(
//                new EmployeeDto(1,"Ayush","AY@123","AY@gmail.com",Role.ADMIN),
//                new EmployeeDto(2,"Fenil","F@123","F@gmail.com",Role.USER)
//        );
//        when(employee2ServicesImpl.getAll()).thenReturn(employee2);
//        mockMvc.perform(MockMvcRequestBuilders.get("/Employee"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].username").value("Ayush"))
//                .andExpect(jsonPath("$[1].username").value("Fenil"));
//    }
//
//    @Test
//    public void TestGetByIdEmployee()throws Exception{
//        EmployeeDto employee2 = new EmployeeDto(2,"Mihir","Mi@123","Mi@gmile.com",Role.USER);
//
//        when(employee2ServicesImpl.getById(employee2.getId())).thenReturn(employee2);
//
//        mockMvc.perform(get("/Employee/{id}",employee2.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(employee2.getId()))
//                .andExpect(jsonPath("$.username").value("Mihir"))
//                .andExpect(jsonPath("$.role").value("USER"));
//
//        System.out.println("Fetch Employees = " + employee2);
//        verify(employee2ServicesImpl).getById(employee2.getId());
//    }
//
//    @Test
//     public void TestCreateEmployee()throws Exception {
//
//        EmployeeDto employee2 = new EmployeeDto(null, "Abhi", "A@123", "A@gmail.com", Role.ADMIN);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("role","ADMIN");
//
//        when(employee2ServicesImpl.createEmployee(
//                ArgumentMatchers.any(EmployeeDto.class),
//                ArgumentMatchers.eq("ADMIN"),
//                ArgumentMatchers.any(Authentication.class)
//        )).thenReturn(employee2);
//       mockMvc.perform(MockMvcRequestBuilders.post("/Employee/register")
//                .params(params)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                        {
//                              "username": "Abhi",
//                              "password": "A@123",
//                              "email": "A@gmail.com"
//                          }
//                        """))
//                .andExpect(status().isOk())
//                .andExpect( jsonPath("$.username").value("Abhi"));
//        System.out.println("UserName = " + employee2.getUsername());
//        System.out.println("Password = " + employee2.getPassword());
//        System.out.println("Email = " + employee2.getEmail());
//        System.out.println("Role = " + employee2.getRole());
//        verify(employee2ServicesImpl).createEmployee(any(EmployeeDto.class),eq("ADMIN"),any(Authentication.class));
//    }
//
//    @Test
//    public void TestUpdateByEmployee() throws Exception {
//        EmployeeDto employee2 = new EmployeeDto(2,"Varun","Va@123","Va@gmail.com",Role.USER);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("role","USER");
//
//        when(employee2ServicesImpl.updateEmployee(eq(employee2.getId()),any(EmployeeDto.class),eq("USER"),any(Authentication.class)))
//                .thenReturn(employee2);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/Employee/{id}",employee2.getId())
//                        .params(params)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                        {
//                                "username": "Varun",
//                                "password": "Va@123",
//                                "email": "Va@gmail.com",
//                                "role": "USER"
//                          }
//                        """))
//                .andExpect(status().isOk())
//                .andExpect( jsonPath("$.id").value(employee2.getId()))
//                .andExpect( jsonPath("$.username").value("Varun"))
//                .andExpect( jsonPath("$.role").value("USER"));
//        System.out.println("Update Name  = " + employee2);
//        verify(employee2ServicesImpl).updateEmployee(eq(employee2.getId()),any(EmployeeDto.class),eq("USER"),any(Authentication.class));
//    }
//
//   @Test
//    public void Super_AdminCanDelete() throws Exception {
//       String token = "Bearer validToken";
//       when(jwtServices.extraRole(anyString())).thenReturn(List.of("SUPER_ADMIN"));
//
//      mockMvc.perform(MockMvcRequestBuilders.delete("/Employee/{id}", 1)
//                      .header("Authorization", token))
//      .andExpect(status().isNoContent());
//       verify(employee2ServicesImpl).deleteEmployee(1, "SUPER_ADMIN");
//   }
//
//   @Test
//   public void Admin_CanDelete() throws Exception {
//
//       String token = "Bearer validToken";
//       when(jwtServices.extraRole(anyString())).thenReturn(List.of("ADMIN"));
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/Employee/{id}", 1)
//               .header("Authorization", token))
//        .andExpect(status().isNoContent());
//
//       verify(employee2ServicesImpl).deleteEmployee(1, "ADMIN");
//   }
//
//    @Test
//    public void Unauthorized() throws Exception {
//        String token = "InvalidToken";
//        mockMvc.perform(MockMvcRequestBuilders.delete("/Employee/{id}", 1)
//                        .header("Authorization", token))
//                .andExpect(status().isUnauthorized())
//                .andExpect(content().string("Unauthorized: Please log in."));
//    }
//
//    @Test
//    public void ForbiddenForInvalidRole() throws Exception {
//        String token = "Bearer dummy.jwt.token";
//        String tokenValue = "dummy.jwt.token";
//        List<String> roles = List.of("USER");
//
//        when(jwtServices.extraRole(tokenValue)).thenReturn(roles);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/Employee/1")
//                        .header("Authorization", token))
//                .andExpect(status().isForbidden())
//                .andExpect(content().string("Access Denied: Only Super-Admin can delete Admins, and Admins can delete only Users."));
//    }
//
//    @Test
//    public void Search()throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.get("/Employee/search")
//                        .param("keyword", "a"))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void searchValid() throws Exception {
//        String keyword = "ab";
//        List<Employee2> mockList = List.of(new Employee2(1,"Abhi","A@123","A@gmail.com", Role.USER),
//                new Employee2(2, "Abhay","Ab@123","Ab@gmail.com",Role.USER));
//
//        when(employee2ServicesImpl.searchName(keyword)).thenReturn(mockList);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/Employee/search")
//                        .param("keyword", keyword))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].username").value("Abhi"))
//                .andExpect(jsonPath("$[1].username").value("Abhay"));
//    }
//
//
//    @Test
//    public void testLogin() throws Exception {
//        Employee2 emp = new Employee2(1, "TestUser", "Pass@123", "test@gmail.com", Role.USER);
//        String token = "fake-jwt-token";
//
//        when(employee2ServicesImpl.login(any())).thenReturn(token);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/Employee/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(emp)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(token));
//    }
//
//    @Test
//    public void LogoutSuccessfully() throws Exception {
//        int userId = 1;
//        String token = "Bearer dummy.jwt.token";
//        String username = "testuser";
//
//        EmployeeDto employeeWrongUser = new EmployeeDto();
//        employeeWrongUser.setId(userId);
//        employeeWrongUser.setUsername(username);
//
//        when(employee2ServicesImpl.getById(userId)).thenReturn(employeeWrongUser);
//        when(jwtServices.First("dummy.jwt.token")).thenReturn(username);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/Employee/logout/{id}", userId)
//                        .header("Authorization", token))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Log-out SuccessFully .. "));
//
//    }
//
//    @Test
//    public void LogoutUserNotFound() throws Exception{
//        int userId = 1;
//        String token = "Bearer dummy.jwt.token";
//
//        when(employee2ServicesImpl.getById(userId)).thenReturn(null);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/Employee/logout/{id}", userId)
//                        .header("Authorization", token))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("User not found"));
//    }
//
//    @Test
//    public void LogoutInvalidUser() throws Exception {
//        int userId = 1;
//        String token = "Bearer dummy.jwt.token";
//
//        EmployeeDto employee = new EmployeeDto();
//        employee.setId(userId);
//        employee.setUsername("correctUser");
//
//        when(employee2ServicesImpl.getById(userId)).thenReturn(employee);
//        when(jwtServices.First("dummy.jwt.token")).thenReturn("wrongUser");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/Employee/logout/{id}", userId)
//                        .header("Authorization", token))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Invalid user. You can only logout with your own User-Id."));
//    }
//
//}