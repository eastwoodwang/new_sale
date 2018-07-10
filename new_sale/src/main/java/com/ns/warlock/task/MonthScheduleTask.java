package com.ns.warlock.task;

import com.ns.warlock.dto.GroupDTO;
import com.ns.warlock.dto.MemberDTO;
import com.ns.warlock.dto.SaleStatisDTO;
import com.ns.warlock.service.GroupService;
import com.ns.warlock.service.MemberService;
import com.ns.warlock.service.SaleStatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 每月初执行的任务
 * 执行时间参照：https://blog.csdn.net/aspnet2002web/article/details/52810569
 */
public class MonthScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(MonthScheduleTask.class);

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "saleStatisServiceImpl")
    private SaleStatisService saleStatisService;

    @Resource(name = "groupServiceImpl")
    private GroupService groupService;

    /**
     * 执行的方法
     * 事物的形式来处理 防止计算出现错误
     */
    @Transactional
    public void execute() {
        logger.debug("Start to execute");
        //获取所有用户
        List<MemberDTO> memberDTOList = memberService.findAllStatis();

        //获取当前月
        LocalDate localDate = LocalDate.now();
        String currentMonth = localDate.getYear() + "-" + localDate.getMonthValue();

        //获取并填充SaleStatisDTO
        memberDTOList.stream().map(memberDTO -> {

            SaleStatisDTO saleStatisDTO = new SaleStatisDTO();

            saleStatisDTO.setMemberId(memberDTO.getId());
            saleStatisDTO.setParentMemberId(memberDTO.getParentId());
            saleStatisDTO.setMonth(currentMonth);
            saleStatisDTO.setGroupId(memberDTO.getGroupId());
            saleStatisDTO.setPersonSale(memberDTO.getOwnerCoast());
            saleStatisDTO.setLeafSale(memberDTO.getLeafCoast());
            saleStatisDTO.setRate(memberDTO.getRate());
            saleStatisDTO.setGroupSale(memberDTO.getGroupCoast());
            BigDecimal tempCountBigDecimal = memberDTO.getOwnerCoast().add(memberDTO.getLeafCoast()); //获取叶子结点和自己总和
            saleStatisDTO.setSaleGain(memberDTO.getRate().multiply(tempCountBigDecimal));
            saleStatisDTO.setCreateDate(new Date());
            return saleStatisDTO;
        }).forEach(saleStatisDTO -> saleStatisService.create(saleStatisDTO));



        //获取分组用户 并按照顶位用户当月获取的返点
        //获取当前月用户顶级用户 和 分组
        List<SaleStatisDTO> saleStatisDTOList = saleStatisService.topMemberList(currentMonth);
        saleStatisDTOList.stream().map(saleStatisDTO -> {
            //获取属于同一个组 且 排除顶级用户后 下面的用户提成加和
            BigDecimal leafSale = saleStatisService.sumLeafList(saleStatisDTO.getGroupId(), saleStatisDTO.getMemberId(), currentMonth);
            saleStatisDTO.setLeafSaleGain(leafSale);
            //顶级用户获取总数并减去子节点分配 = 顶级用户自己所得
            BigDecimal finalGain = saleStatisDTO.getSaleGain().subtract(leafSale);
            saleStatisDTO.setSaleGain(finalGain);

            return saleStatisDTO;
        }).forEach(saleStatisDTO -> saleStatisService.update(saleStatisDTO));

        //最后清理ns_member设置为0
        //清理ns_member_group设置为0
        memberService.monthClear();
        groupService.monthGroupClear();
    }
}
