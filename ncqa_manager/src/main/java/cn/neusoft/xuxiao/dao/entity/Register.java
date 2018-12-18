package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="register")
public class Register {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="student_id")
    private String studentId;

    @Column(name="student_name")
    private String student_name;

    @Column(name="start_time")
    private String start_time;

    @Column(name="image_url")
    private String image_url;

    @Column(name="address")
    private String address;

}
