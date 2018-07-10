package com.ns.warlock.util;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public final class FreemarkerUtils {

    /**
     * 不可实例化
     */
    private FreemarkerUtils() {
    }


    /**
     * 解析字符串模板
     *
     * @param template
     *            字符串模板
     * @param model
     *            数据
     * @return 解析后内容
     */
    public static String process(String template, Map<String, ?> model) throws IOException, TemplateException {
        Configuration configuration = null;
        ApplicationContext applicationContext = SpringUtils.getApplicationContext();
        if (applicationContext != null) {
            FreeMarkerConfigurer freeMarkerConfigurer = SpringUtils.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
            if (freeMarkerConfigurer != null) {
                configuration = freeMarkerConfigurer.getConfiguration();
            }
        }
        return process(template, model, configuration);
    }



    /**
     * 解析字符串模板
     *
     * @param template
     *            字符串模板
     * @param model
     *            数据
     * @param configuration
     *            配置
     * @return 解析后内容
     */
    public static String process(String template, Map<String, ?> model, Configuration configuration) throws IOException, TemplateException {
        if (template == null) {
            return null;
        }
        if (configuration == null) {
            configuration = new Configuration();
        }
        StringWriter out = new StringWriter();
        new Template("template", new StringReader(template), configuration).process(model, out);
        return out.toString();
    }

    /**
     * 获取变量
     *
     * @param name
     *            名称
     * @param env
     *            Environment
     * @return 变量
     */
    public static TemplateModel getVariable(String name, Environment env) throws TemplateModelException {
        Assert.hasText(name);
        Assert.notNull(env);
        return env.getVariable(name);
    }

    /**
     * 设置变量
     *
     * @param name
     *            名称
     * @param value
     *            变量值
     * @param env
     *            Environment
     */
    public static void setVariable(String name, Object value, Environment env) throws TemplateException {
        Assert.hasText(name);
        Assert.notNull(env);
        if (value instanceof TemplateModel) {
            env.setVariable(name, (TemplateModel) value);
        } else {
            env.setVariable(name, ObjectWrapper.BEANS_WRAPPER.wrap(value));
        }
    }
}
