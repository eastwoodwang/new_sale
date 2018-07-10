package com.ns.warlock.dto;

import java.math.BigDecimal;
import java.util.Date;

public class MemberDTO extends BaseDTO {

    private static final long serialVersionUID = 7161747694441410399L;

    /**
     * 微信的OPENID
     */
    private String openId;

    /**
     * 微信的昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 微信头像
     */
    private String headerUrl;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 上次访问时间
     */
    private Date lastLoginDate;

    /**
     * 当月个人累计
     */
    private BigDecimal ownerCoast;

    /**
     * 当月团队(叶子人员)累计(不包含个人)
     */
    private BigDecimal leafCoast;

    /**
     * 团队总销量
     */
    private BigDecimal groupCoast;

    /**
     * 会员等级
     */
    private String memberLevel;

    /**
     * 会员等级名称
     */
    private String memberLevelName;

    /**
     * 二维码
     */
    private String qrCodeUrl;

    /**
     * 所属团队ID
     */
    private long groupId;

    /**
     * 所属团队名称
     */
    private String groupName;

    /**
     * 推荐人ID
     */
    private long parentId;

    /**
     * 推荐人OPENID
     */
    private String parentOpenId;

    /**
     * 上级的树结构 如：1,2,3,4
     */
    private String parentTree;

    /**
     * 状态
     */
    private int status;

    /**
     * 用户来源渠道类型
     */
    private int channelType;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 层级
     */
    private int priority;

    /**
     * 返点率
     */
    private BigDecimal rate;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public BigDecimal getOwnerCoast() {
        return ownerCoast;
    }

    public void setOwnerCoast(BigDecimal ownerCoast) {
        this.ownerCoast = ownerCoast;
    }

    public BigDecimal getLeafCoast() {
        return leafCoast;
    }

    public void setLeafCoast(BigDecimal leafCoast) {
        this.leafCoast = leafCoast;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getParentOpenId() {
        return parentOpenId;
    }

    public void setParentOpenId(String parentOpenId) {
        this.parentOpenId = parentOpenId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getParentTree() {
        return parentTree;
    }

    public void setParentTree(String parentTree) {
        this.parentTree = parentTree;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getGroupCoast() {
        return groupCoast;
    }

    public void setGroupCoast(BigDecimal groupCoast) {
        this.groupCoast = groupCoast;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
