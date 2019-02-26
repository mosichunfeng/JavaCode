package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.Register;
import lombok.Data;

import java.util.List;

@Data
public class FindRegisterListResponse extends BasePage{
    private List<Register> registerList;
}
