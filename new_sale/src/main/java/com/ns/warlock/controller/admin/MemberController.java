package com.ns.warlock.controller.admin;

import com.ns.warlock.common.CommonStatus;
import com.ns.warlock.common.Message;
import com.ns.warlock.dto.MemberDTO;
import com.ns.warlock.service.MemberLevelService;
import com.ns.warlock.service.MemberService;
import com.ns.warlock.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController {

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "memberLevelServiceImpl")
    private MemberLevelService memberLevelService;

    @Resource(name = "orderServiceImpl")
    private OrderService orderService;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/list")
    public String list(ModelMap model) {
        model.addAttribute("list", memberService.findAll());
        return "/admin/member/list";
    }

//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public String add() {
//        return "/admin/member/add";
//    }
//
//
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public String save(MemberDTO memberDTO) {
//        memberService.insert(memberDTO);
//        return "redirect:list.jhtml";
//    }

    @GetMapping(value = "/edit")
    public String edit(Long id, ModelMap model) {
        model.addAttribute("memberDTO", memberService.find(id));
        model.addAttribute("levelList", memberLevelService.findAll());
        model.addAttribute("statusList", CommonStatus.values());
        return "/admin/member/edit";
    }

    @PostMapping(value = "/update")
    public String update(MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:list.jhtml";
    }

    @PostMapping(value = "/delete")
    public @ResponseBody
    Message delete(Long[] ids) {
        Message message = null;
        boolean checkPass = true;

        if (checkPass) {
            memberService.delete(ids);
            message = new Message(Message.Type.success, "该用户已经成功删除");
        }
        return message;
    }

    /**
     * 获取会员订单列表
     * @param memberId
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/detail")
    public String detail(long memberId, ModelMap modelMap) {
        modelMap.addAttribute("list", orderService.findByMemberId(memberId));
        return "/admin/member/detail";
    }

}
