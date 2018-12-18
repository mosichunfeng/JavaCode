package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("IQuestionDao")
public interface IQuestionDao extends JpaRepository<Question,Integer> {

}
