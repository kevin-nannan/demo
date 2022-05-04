package com.example.jpa;

import com.example.jpa.transaction.Service;
import com.example.jpa.transaction.Student;
import com.example.jpa.transaction.repository.StudentRepository;
import com.example.jpa.transaction.repository.TeacherRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
class TransactionApplicationTests {
    @Resource
    private StudentRepository studentRepository;
    @Resource
    private TeacherRepository teacherRepository;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private Service service;





    @Test
    void contextLoads() {
        try {
            service.test();
        } catch (Exception e) {
            System.out.println(e.getCause() + "   " + e.getMessage());
        }
        try {
            service.self("self");
            Assert.assertEquals(studentRepository.findById(1L).get().getName(), "self");
        } catch (Exception e) {
            System.out.println(e.getCause() + "   " + e.getMessage());
        }
        try {
            service.transferSelfMethod();
        } catch (Exception e) {
            System.out.println(e.getCause() + "   " + e.getMessage());
        }
    }

}
