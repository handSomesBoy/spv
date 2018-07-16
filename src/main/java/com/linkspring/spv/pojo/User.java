package com.linkspring.spv.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.util.Date;
//不存在的属性不映射 例如枚举值name
@JsonIgnoreProperties
public class User implements Serializable {
	private static final long serialVersionUID = -3096736268081409238L;
	private Integer id;
	@NotNull(message = "用户名不能为空，请您先填写用户名")
	private String username;

	private String fullname;
	@NotNull(message = "手机号不能为空，请您先填写手机号")
	private String mobile;

	private String email;
	private String password;

	private Integer insertUid;

	private Date insertTime;

	private Date updateTime;

	private Boolean isDel;

	private Boolean isJob;

	private Integer version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getInsertUid() {
		return insertUid;
	}

	public void setInsertUid(Integer insertUid) {
		this.insertUid = insertUid;
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

	public Boolean getIsDel() {
		return isDel;
	}

	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}

	public Boolean getIsJob() {
		return isJob;
	}

	public void setIsJob(Boolean isJob) {
		this.isJob = isJob;
	}


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", fullname='" + fullname + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", insertUid=" + insertUid +
				", insertTime=" + insertTime +
				", updateTime=" + updateTime +
				", isDel=" + isDel +
				", isJob=" + isJob +
				", version=" + version +
				'}';
	}

}