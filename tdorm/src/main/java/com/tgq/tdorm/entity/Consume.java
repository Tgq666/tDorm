package com.tgq.tdorm.entity;

import java.util.Date;

/**
 * @Author tgq
 * @Date 2020/12/4 17:53
 */
public class Consume {

    /**
     * isSettled:是否已经被支付
     * payName:支付人
     * consumeName:消费人
     */
    private Integer id;
    private String payName;
    private String goodsName;
    private String consumeName;
    private Integer goodsPrice;
    private String note;
    private String settledName;
    private String dormNum;
    private Date createTime;
    private Date updateTime;
    private Date consumeTime;
    private Integer isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getConsumeName() {
        return consumeName;
    }

    public void setConsumeName(String consumeName) {
        this.consumeName = consumeName;
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSettledName() {
        return settledName;
    }

    public void setSettledName(String settledName) {
        this.settledName = settledName;
    }

    public String getDormNum() {
        return dormNum;
    }

    public void setDormNum(String dormNum) {
        this.dormNum = dormNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Date consumeTime) {
        this.consumeTime = consumeTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Consume{" +
                "id=" + id +
                ", payName='" + payName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", consumeName='" + consumeName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", note='" + note + '\'' +
                ", settledName='" + settledName + '\'' +
                ", dormNum='" + dormNum + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", consumeTime=" + consumeTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
