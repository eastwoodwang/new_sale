package com.ns.warlock.directive;

import com.ns.warlock.util.FreemarkerUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


import java.io.IOException;

public abstract class BaseDirective implements TemplateDirectiveModel {


    /**
     * 设置局部变量
     *
     * @param name
     *            名称
     * @param value
     *            变量值
     * @param env
     *            Environment
     * @param body
     *            TemplateDirectiveBody
     */
    protected void setLocalVariable(String name, Object value, Environment env, TemplateDirectiveBody body) throws TemplateException, IOException {
        TemplateModel sourceVariable = FreemarkerUtils.getVariable(name, env);
        FreemarkerUtils.setVariable(name, value, env);
        body.render(env.getOut());
        FreemarkerUtils.setVariable(name, sourceVariable, env);
    }

}
