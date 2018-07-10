package com.ns.warlock.controller.admin;

import com.ns.warlock.common.Message;
import com.ns.warlock.dto.GroupDTO;
import com.ns.warlock.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller("groupController")
@RequestMapping("/admin/group")
public class GroupController extends BaseController {

    @Resource(name = "groupServiceImpl")
    private GroupService groupService;


    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("list", groupService.findAll());
        return "/admin/group/list";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        model.addAttribute("grouprDTO", groupService.find(id));
        return "/admin/group/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(GroupDTO groupDTO) {
        groupService.update(groupDTO);
        return "redirect:list.jhtml";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody Message delete(Long[] ids) {
        Message message = null;
        boolean checkPass = true;

        if (checkPass) {
            groupService.delete(ids);
            message = new Message(Message.Type.success, "该项已经成功删除");
        }
        return message;
    }

}
