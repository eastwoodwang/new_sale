package com.ns.warlock.controller.admin;

import com.ns.warlock.dto.AdminDTO;
import com.ns.warlock.dto.MemberDTO;
import com.ns.warlock.dto.MemberDepositDTO;
import com.ns.warlock.service.AdminService;
import com.ns.warlock.service.MemberDepositService;
import com.ns.warlock.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 会员充值
 */
@RequestMapping("/admin/member_deposit")
@Controller
public class MemberDepositController extends BaseController {

    @Resource(name = "memberDepositServiceImpl")
    private MemberDepositService memberDepositService;

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "adminServiceImpl")
    private AdminService adminService;

    /**
     * 列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("list", memberDepositService.list());
        return "/admin/member_deposit/list";
    }


    /**
     * 检查充值用户是否存在
     * @param memberPhone
     * @return
     */
    @RequestMapping(value = "/member_validate", method = RequestMethod.GET)
    public @ResponseBody boolean
    validate(String memberPhone) {
        MemberDTO memberDTO = memberService.checkMemberExist(memberPhone);
        if (memberDTO == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 新增加
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String id, ModelMap model) {
        model.addAttribute("memberId", id);
        return "/admin/member_deposit/add";
    }

    /**
     * 保存
     * @param memberDepositDTO
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(MemberDepositDTO memberDepositDTO) {
        MemberDTO memberDTO = memberService.find(memberDepositDTO.getMemberId());

        AdminDTO adminDTO = adminService.getCurrent();

        //memberDepositDTO.setMemberId(memberDTO.getId());
        memberDepositDTO.setOperator(adminDTO.getName());

        memberDepositService.add(memberDepositDTO);

        //充值成功将充值的数据提交到用户上
        BigDecimal balance = memberDepositDTO.getCredit().add(memberDTO.getBalance());
        memberDTO.setBalance(balance);
        memberService.update(memberDTO);

        return "redirect:/admin/member/list.jhtml";
    }
}
