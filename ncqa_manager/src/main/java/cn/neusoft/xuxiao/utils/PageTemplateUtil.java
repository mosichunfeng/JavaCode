package cn.neusoft.xuxiao.utils;

import cn.neusoft.xuxiao.webapi.entity.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PageTemplateUtil {

    private static Logger logger = LoggerFactory.getLogger(PageTemplateUtil.class);

    public static <T extends BasePage> BasePage setBasePage(T qryCriteria, int count) {

        //方法参数检查
        if (null == qryCriteria || !(qryCriteria instanceof BasePage)) {
            logger.error("please check the method args！");
            throw new RuntimeException("please check the method args！");
        }

        //设置分页相关的参数
        BasePage baseForm = (BasePage) qryCriteria;
        int limit = (baseForm.getPageSize() == 0 ? count : baseForm.getPageSize());    //每页记录总数
        if (limit > 0 && count > 0) {
            int pages = (count % limit == 0) ? (count / limit) : (count / limit + 1);    //总页数
            int rowSrt = 0;    //开始行号
            int rowEnd = 0;    //结束行号
            if (baseForm.getPageNo() == -1 || baseForm.getPageNo() == pages) {//query last page
                rowSrt = (pages - 1) * limit;
                rowEnd = rowSrt + (count % limit == 0 ? limit : (count % limit)) - 1;
            } else {
                rowSrt = (baseForm.getPageNo() - 1) * limit;
                rowEnd = rowSrt + limit - 1;
            }
            baseForm.setRowSrt(rowSrt);
            baseForm.setRowEnd(rowEnd);
            baseForm.setPages(pages);
            baseForm.setCounts(count);
        } else {
            baseForm.setRowSrt(0);
            baseForm.setRowEnd(count - 1);
            baseForm.setPages(1);
            baseForm.setCounts(count);
        }
        return CopyFunction.copyPropertys(baseForm, new BasePage());
    }
}