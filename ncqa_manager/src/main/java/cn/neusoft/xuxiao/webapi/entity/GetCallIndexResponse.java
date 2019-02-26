package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.Call;
import cn.neusoft.xuxiao.dao.entity.ClassInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetCallIndexResponse extends BasePage {
    private List<Call> callList;
    private List<ClassInfo> classList;
}
