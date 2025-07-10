//package com.example.Project_1.Repository;
//
//
//import com.example.Project_1.Model.Employee2;
//import com.example.Project_1.Model.Role;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//
//public class RepositoryTest {
//
//    @Autowired
//    private Employee2Repository employee2Repository ;
//
//    @Test
//    public void TestFindByUsername(){
//        Employee2 employee = new Employee2(null,"Rohan","Rohan@123","Rohan@gmail.com",Role.ADMIN);
//        employee2Repository.findByUsername(employee.getUsername());
//
//        Assertions.assertNotNull(employee);
//        Assertions.assertEquals("Rohan@gmail.com",employee.getEmail());
//        Assertions.assertEquals(Role.ADMIN,employee.getRole());
//        Assertions.assertEquals("Rohan@123",employee.getPassword());
//        System.out.println("FindByUsername :: " + employee.getUsername());
//    }
//
//    @Test
//    public void TestExistByRole(){
//
//        Employee2 employee = new Employee2(null,"Rohan","R@123","R@gmail.com",Role.ADMIN);
//        boolean employee2 = employee2Repository.existsByRole(Role.ADMIN);
//
//        Assertions.assertNotNull(employee);
//        Assertions.assertTrue(employee2);
//        System.out.println("Exist Rol is ==> " + employee.getRole());
//    }
//}