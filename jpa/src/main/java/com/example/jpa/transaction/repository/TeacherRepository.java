package com.example.jpa.transaction.repository;

import com.example.jpa.transaction.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
