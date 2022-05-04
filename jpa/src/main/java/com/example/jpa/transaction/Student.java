package com.example.jpa.transaction;

import lombok.*;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * @author zhangchengkai
 * @Description
 * @createTime 2022-05-04 13:30
 * @other
 */
@Entity
@Table(name = "student")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    //如果使用AUTO会create table hibernate_sequence (next_val bigint) engine=MyISAM
    //记录多张表的id  统一划分
    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "teacher_id")
    private Long teacherId;

}
