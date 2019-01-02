package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.RegisterInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetRegisterIndexResponse extends BasePage{
    private List<RegisterInfo> registerInfoList;
}

