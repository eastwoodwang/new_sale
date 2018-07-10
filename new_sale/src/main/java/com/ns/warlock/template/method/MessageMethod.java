package com.ns.warlock.template.method;

import com.ns.warlock.util.SpringUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 模板方法 - 多语言
 *
 * @author wangxy
 * @version 1.5
 * @since 2017/2/7.
 */
@Component("messageMethod")
public class MessageMethod implements TemplateMethodModelEx {

    @SuppressWarnings("rawtypes")
    public Object exec(List list) throws TemplateModelException {
        if (list != null && !list.isEmpty() && list.get(0) != null && StringUtils.isNotEmpty(list.get(0).toString())) {
            String message = null;
            String code = list.get(0).toString();
            if (list.size() > 1) {
                Object[] args = list.subList(1, list.size()).toArray();
                message = SpringUtils.getMessage(code, args);
            } else {
                message = SpringUtils.getMessage(code);
            }
            return new SimpleScalar(message);
        }
        return null;
    }
}
