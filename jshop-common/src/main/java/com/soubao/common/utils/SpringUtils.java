package com.soubao.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Spring容器
 */
@Service
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }
    }

    /**
     * 获取容器
     *
     * @return 容器
     */
    public static ApplicationContext getContext() {
        return context;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getContext().getBean(name, clazz);
    }
}