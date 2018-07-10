package com.ns.warlock.controller.admin;


import com.ns.warlock.Message;
import com.ns.warlock.common.DateEditor;
import com.ns.warlock.directive.FlashMessageDirective;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Date;

public class BaseController {

    /** 错误视图 */
    protected static final String ERROR_VIEW = "/admin/common/error";

    /** 成功消息 */
    protected static final Message SUCCESS_MESSAGE = Message.success("admin.message.success");

    //@Resource(name = "validator")
    //private Validator validator;

    /**
     * 数据绑定
     *
     * @param binder
     *            WebDataBinder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new DateEditor(true));
    }

    /**
     * 添加瞬时消息
     *
     * @param redirectAttributes
     *            RedirectAttributes
     * @param message
     *            消息
     */
    protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
        if (redirectAttributes != null && message != null) {
            redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
        }
    }

}
