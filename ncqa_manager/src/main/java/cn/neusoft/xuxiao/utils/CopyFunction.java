package cn.neusoft.xuxiao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.beanutils.BeanUtils;

public class CopyFunction {

    private static Logger logger = LoggerFactory.getLogger(CopyFunction.class);

    /**
     * @deprecated method dbObject2Bean is deprecat
     */
    public static <V> V dbObject2Bean(Object obj, V bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (bean == null) {
            return null;
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            Object object = BeanUtils.getProperty(obj, varName);
            if (object != null) {
                BeanUtils.setProperty(bean, varName, object);
            }
        }
        return bean;
    }

    /**
     * @param source
     * @param target
     * @Title: copyProperties
     * @Description:封装apache的common方法，支持集合copy,默认将null转为默认值
     */
    @Deprecated
    public static <V> void copyProperties(Object source, V target) {
        //参数检查
        if (null == source || null == target) {
            logger.info("please check the args！");
            throw new RuntimeException("java.lang.NullException!");
        }

        //属性copy
        try {
            BeanUtils.copyProperties(target, source);
        } catch (Exception e) {
            logger.info("property convert error！");
            throw new RuntimeException(e);
        }
    }

    /**
     * 支持简单对象的属性copy，拦截过滤空串和null值
     */
    public static <V> V copyPropertys(Object source, V target) {

        //参数检查
        if (null == source || null == target) {
            logger.info("please check the args！");
            throw new RuntimeException("java.lang.NullException!");
        }

        //属性值copy
        for (Class<?> clazz = target.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Class<? extends Field> fieldCls = field.getClass();
                try {
                    String filedVal = BeanUtils.getProperty(source, fieldName);    //取出待copy的值
                    setProperty(fieldCls, target, fieldName, filedVal);
                } catch (NoSuchMethodException e) {
                    //logger.warn("property convert error,because noSuchMethod!");
                } catch (Exception e) {
                    logger.info("property convert error！class:" + target.getClass() + "--> field:" + fieldName);
                    throw new RuntimeException(e);
                }
            }
        }
        return target;
    }

    /**
     * @param @param  clazz
     * @param @param  obj
     * @param @param  field
     * @param @param  val
     * @param @throws Exception
     * @return void
     * @throws
     * @Title: setProperty
     * @Description:属性转换设置
     */
    @SuppressWarnings({"rawtypes"})
    public static void setProperty(Class clazz, Object obj, String field, String val) throws Exception {
        //空值时不设置
        if (StringUtil.isEmpty(field) || StringUtil.isEmpty(val)) {
            return;
        }

        //char
        if (clazz.equals(Character.class) || clazz.equals(char.class)) {
            char ch = val.trim().charAt(0);    //默认取第一个字符
            BeanUtils.setProperty(obj, field, new Character(ch));
        } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            BeanUtils.setProperty(obj, field, Boolean.valueOf(val));
        } else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            BeanUtils.setProperty(obj, field, new Byte(val));
        } else if (clazz.equals(Short.class) || clazz.equals(short.class)) {
            BeanUtils.setProperty(obj, field, new Short(val));
        } else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            BeanUtils.setProperty(obj, field, new Integer(val));
        } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            BeanUtils.setProperty(obj, field, new Long(val));
        } else if (clazz.equals(BigDecimal.class)) {
            BeanUtils.setProperty(obj, field, new BigDecimal(val));
        } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            BeanUtils.setProperty(obj, field, new Double(val));
        } else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            BeanUtils.setProperty(obj, field, new Float(val));
        } else if (clazz.equals(Date.class)) {
            Date date = TimeTool.StrToDate(val);
            BeanUtils.setProperty(obj, field, date);
        } else if (clazz.equals(Object[].class)) {
            //Object[] objs = getObjs(val);
            //BeanUtils.setProperty(obj , field, objs);
        } else if (clazz.equals(Class[].class)) {
            //Class[] clas = getClas(val);
            //BeanUtils.setProperty(obj , field, clas);
        } else {
            BeanUtils.setProperty(obj, field, val);
        }
    }
}
