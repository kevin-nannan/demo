package com.example.jpa.transaction.repository;

import com.example.jpa.transaction.Student;
import com.example.jpa.transaction.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangchengkai
 * @Description
 * @createTime 2022-05-04 13:38
 * @other
 */
@Repository
public
interface StudentRepository extends JpaRepository<Student, Long> {

}

