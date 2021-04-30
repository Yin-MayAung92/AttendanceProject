package com.demo.attendance.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.demo.attendance.entity.EmployeeInfo.Group;
import com.demo.attendance.entity.EmployeeInfo.Position;

public class EmployeeInfoDTO {
	private int employeeId;
	private Group groupNo;
	private Position position;
	private String name;
	private String phoneNo;
	private String mail;
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date hireDate;
	private String password;
	private String raw_password;
	private boolean adminCheck;
	private String address;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Group getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Group groupNo) {
		this.groupNo = groupNo;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRaw_password() {
		return raw_password;
	}

	public void setRaw_password(String raw_password) {
		this.raw_password = raw_password;
	}

	public boolean isAdminCheck() {
		return adminCheck;
	}

	public void setAdminCheck(boolean adminCheck) {
		this.adminCheck = adminCheck;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
