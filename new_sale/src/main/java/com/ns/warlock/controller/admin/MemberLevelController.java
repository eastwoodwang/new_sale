package com.ns.warlock.controller.admin;

import com.ns.warlock.common.Message;
import com.ns.warlock.dto.MemberLevelDTO;
import com.ns.warlock.service.MemberLevelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RequestMapping("/admin/member_level")
@Controller("memberLevelController")
public class MemberLevelController extends BaseController {

    @Resource(name = "memberLevelServiceImpl")
    private MemberLevelService memberLevelService;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("list", memberLevelService.findAll());
        return "/admin/member_level/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "/admin/member_level/add";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(MemberLevelDTO memberLevelDTO) {
        memberLevelService.insert(memberLevelDTO);
        return "redirect:list.jhtml";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        model.addAttribute("memberLevelDTO", memberLevelService.find(id));
        return "/admin/member_level/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(MemberLevelDTO memberLevelDTO) {
        memberLevelService.update(memberLevelDTO);
        return "redirect:list.jhtml";
    }

    /**
     * 删除时检查有没有用户已经是要删除的会员
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        Message message = null;
        boolean checkPass = memberLevelService.checkMemberInLevel(ids);

        if (checkPass) {
            memberLevelService.delete(ids);
            message = new Message(Message.Type.success, "该用户已经成功删除");
        } else {
            message = new Message(Message.Type.error, "无法删除,会员列表下有会员");
        }
        return message;
    }

    /**
     * 验证填写的数据是否已经在某些范围内
     * @param range
     * @return
     */
    @RequestMapping(value = "/range_validate", method = RequestMethod.GET)
    public @ResponseBody
    boolean rangeValidate(BigDecimal range) {
        return memberLevelService.validateRange(range);
    }

}
