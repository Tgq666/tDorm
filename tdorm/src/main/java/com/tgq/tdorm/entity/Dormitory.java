package com.tgq.tdorm.entity;

import java.util.Date;
import java.util.List;

/**
 * @Author tgq
 * @Date 2020/12/3 15:31
 */
public class Dormitory {
    /**
     * users：一对多，一个寝室有多名室员
     */
    private Integer id;
    private String dormName;
    private String dormNum;
    private String dormAdmin;
    private String dormDesc;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;

    private List<User> users;

    public String getDormNum() {
        return dormNum;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setDormNum(String dormNum) {
        this.dormNum = dormNum;
    }

    public String getDormDesc() {
        return dormDesc;
    }

    public void setDormDesc(String dormDesc) {
        this.dormDesc = dormDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDormName() {
        return dormName;
    }

    public void setDormName(String dormName) {
        this.dormName = dormName;
    }

    public String getDormAdmin() {
        return dormAdmin;
    }

    public void setDormAdmin(String dormAdmin) {
        this.dormAdmin = dormAdmin;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
