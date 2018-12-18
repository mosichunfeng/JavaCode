package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name="student")
public class Student implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="student_id")
    private String student_id;

    @Column(name="student_name")
    private String student_name;

    @Column(name="student_class")
    private String student_class;

    @Column(name="student_gender")
    private String student_gender;

    @Column(name="student_tel")
    private String student_tel;

    @Column(name="work_detail")
    private int work_detail;

}
