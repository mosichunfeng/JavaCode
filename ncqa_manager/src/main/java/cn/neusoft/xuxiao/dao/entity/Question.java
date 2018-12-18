package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="question")
public class Question {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="question_index")
    private int question_index;

    @Column(name="select_type")
    private int select_type;

    @Column(name="content")
    private String content;

}
