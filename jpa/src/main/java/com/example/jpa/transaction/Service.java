package com.example.jpa.transaction;

import com.example.jpa.transaction.annocationSelf.CkTransactional;
import com.example.jpa.transaction.repository.StudentRepository;
import com.example.jpa.transaction.repository.TeacherRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author zhangchengkai
 * @Description
 * @createTime 2022-05-04 13:37
 * @other
 */
@Component
public class Service {
    @Resource
    private StudentRepository studentRepository;
    @Resource
    private TeacherRepository teacherRepository;
    @Resource
    private TransactionTemplate transactionTemplate;

    //回滚失败
    @Transactional(rollbackFor = Exception.class)
    public void test() {
        studentRepository.save(Student.builder().id(1L).name("new").age(18).build());
        int i = 9 / 0;
        studentRepository.deleteById(3L);
        studentRepository.save(Student.builder().id(2L).name("student3").age(18).build());
    }
    //自己实现的transactional
    @CkTransactional
    public void self(String name){
        studentRepository.save(Student.builder().id(1L).name(name).age(18).build());
        int i = 9 / 0;
        studentRepository.deleteById(3L);
        studentRepository.save(Student.builder().id(2L).name("student3").age(18).build());

    }
    public void transactionTemplateDemo() {
        transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                studentRepository.save(Student.builder().id(1L).name("transactionTemplateDemo").age(18).build());
                studentRepository.deleteById(3L);
                return Boolean.TRUE;
            }
        });
    }

    //调用的方法的注解会失效
    //自己的ckTransactional生效
    public void transferSelfMethod(){
        studentRepository.save(Student.builder().id(4L).build());
        test();
        self("transferSelfMethod");
        transactionTemplateDemo();
    }
}
