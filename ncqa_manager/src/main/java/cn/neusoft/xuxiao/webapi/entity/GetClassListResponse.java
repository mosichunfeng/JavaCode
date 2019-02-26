package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.ClassInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetClassListResponse  {
    private List<ClassInfo> classList;
}
