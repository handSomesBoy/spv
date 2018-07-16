package com.linkspring.spv.pojo;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
/*@Table(name = "permission")*/
public class Permission {
    private Integer id;

    private String name;

    private Integer pid;

    private Integer zindex;

    private Integer istype;

    private String descpt;

    private String code;

    private String icon;

    private String page;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    private Date insertTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getZindex() {
        return zindex;
    }

    public void setZindex(Integer zindex) {
        this.zindex = zindex;
    }

    public Integer getIstype() {
        return istype;
    }

    public void setIstype(Integer istype) {
        this.istype = istype;
    }

    public String getDescpt() {
        return descpt;
    }

    public void setDescpt(String descpt) {
        this.descpt = descpt == null ? null : descpt.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page == null ? null : page.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override public String toString() {
        return "Permission{" + "id=" + id + ", name='" + name + '\'' + ", pid="
                + pid + ", zindex=" + zindex + ", istype=" + istype
                + ", descpt='" + descpt + '\'' + ", code='" + code + '\''
                + ", icon='" + icon + '\'' + ", page='" + page + '\''
                + ", insertTime=" + insertTime + ", updateTime=" + updateTime
                + '}';
    }
    // 拥有的子菜单列表
    @Transient
    private List<Permission> children;
    public List<Permission> getChildren() {
        return children;
    }
    public void setChildren(List<Permission> children) {
        this.children = children;
    }
}