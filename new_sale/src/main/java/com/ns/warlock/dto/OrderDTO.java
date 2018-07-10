package com.ns.warlock.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public class OrderDTO extends BaseDTO {

    private static final long serialVersionUID = -5952939190509366746L;

    /**
     * 订单状态
     */
    public enum OrderStatus {

        /** 未确认 */
        unconfirmed,

        /** 已确认 */
        confirmed,

        /** 已完成:已经支付 */
        completed,

        /** 已取消 */
        cancelled
    }

    /**
     * 配送状态
     */
    public enum ShippingStatus {

        /** 未发货 */
        unshipped,

        /** 已发货 */
        shipped,

        /** 已退货 */
        returned
    }


    /** 订单编号 */
    private String orderSn;

    /** 订单状态 */
    private int orderStatus;

    /** 快递ID */
    private long shippingId;

    /** 快递单号 */
    private String shippingSn;

    /** 快递状态 */
    private int shippingStatus;

    /** 会员ID */
    private String memberId;

    /** 会员名 */
    private String memberName;

    /** 地址ID */
    private String addressId;

    /** 附言 */
    private String memo;

    /** 支付时间 */
    private Date payDate;

    /** 订单总金额 */
    private BigDecimal totalAmount;

    /** 订单子项 */
    private List<OrderItemDTO> orderItemDTOList;


    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public long getShippingId() {
        return shippingId;
    }

    public void setShippingId(long shippingId) {
        this.shippingId = shippingId;
    }

    public String getShippingSn() {
        return shippingSn;
    }

    public void setShippingSn(String shippingSn) {
        this.shippingSn = shippingSn;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public void setOrderItemDTOList(List<OrderItemDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
    }
}
