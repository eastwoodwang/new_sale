package com.ns.warlock.controller.admin;

import com.ns.warlock.common.Message;
import com.ns.warlock.dto.GroupLevelDTO;
import com.ns.warlock.service.GroupLevelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller("memberGroupLevelController")
@RequestMapping("/admin/group_level")
public class GroupLevelController extends BaseController {


    @Resource(name = "groupLevelServiceImpl")
    private GroupLevelService groupLevelService;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("list", groupLevelService.findAll());
        return "/admin/group_level/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "/admin/group_level/add";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(GroupLevelDTO groupLevelDTO) {
        groupLevelService.insert(groupLevelDTO);
        return "redirect:list.jhtml";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Long id, ModelMap model) {
        model.addAttribute("groupLevelDTO", groupLevelService.find(id));
        return "/admin/group_level/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(GroupLevelDTO groupLevelDTO) {
        groupLevelService.update(groupLevelDTO);
        return "redirect:list.jhtml";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        Message message = null;
        boolean checkPass = true;

        if (checkPass) {
            groupLevelService.delete(ids);
            message = new Message(Message.Type.success, "数据已经成功删除");
        }
        return message;
    }
}
