package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class StudentRegisterDO {
    private Student student;
    private List<Register> registerList;
}
