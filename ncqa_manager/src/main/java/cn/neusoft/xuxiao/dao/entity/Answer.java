package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="answer")
public class Answer {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="answer_index")
    private String answer_index;

    @Column(name="answer_content")
    private String answer_content;

}
