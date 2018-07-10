package com.ns.warlock.controller.admin;


import com.ns.warlock.dto.OrderDTO;
import com.ns.warlock.service.OrderItemService;
import com.ns.warlock.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

@Controller("orderController")
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

    @Resource(name = "orderServiceImpl")
    private OrderService orderService;

    @Resource(name = "orderItemServiceImpl")
    private OrderItemService orderItemService;

    @GetMapping(value = "/list")
    public String list (ModelMap modelMap) {
        modelMap.addAttribute("list", orderService.findAll());
        return "/admin/order/list";
    }

    /**
     * 编辑
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit (Long id, String memberId, ModelMap modelMap) {
        modelMap.addAttribute("order", orderService.find(id));
        modelMap.addAttribute("orderItems", orderItemService.findList(memberId, id));
        return "/admin/order/edit";
    }



    @PostMapping(value = "/update")
    public String update (OrderDTO orderDTO) {

        //修改订单状态
        OrderDTO tempOrder = orderService.find(orderDTO.getId());
        tempOrder.setShippingSn(orderDTO.getShippingSn());
        tempOrder.setShippingStatus(orderDTO.getShippingStatus());
        tempOrder.setUpdateDate(new Date());
        orderService.update(tempOrder);

        return "redirect:list.jhtml";
    }

    /**
     * 浏览
     * @param id
     * @param memberId
     * @param modelmap
     * @return
     */
    @GetMapping(value = "/view")
    public String view (Long id, String memberId, ModelMap modelmap) {
        modelmap.addAttribute("order", orderService.find(id));
        modelmap.addAttribute("orderItems", orderItemService.findList(memberId, id));
        return "/admin/order/view";
    }

//    @PostMapping(value = "/delete")
//    public @ResponseBody
//    Message delete(Long[] ids) {
//        Message message = null;
//        boolean checkPass = true;
//
//        if (checkPass) {
//            orderService.delete(ids);
//            message = new Message(Message.Type.success, "该数据已经成功删除");
//        }
//        return message;
//    }

}
