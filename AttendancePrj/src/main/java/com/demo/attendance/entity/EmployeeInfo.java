package com.demo.attendance.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "employeeinfo")
public class EmployeeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	@NotEmpty(message = "Please Enter Name")
	private String name;
	
	@Column(nullable = false, unique = true)
	private String mail;

	@NotEmpty(message = "Please Enter Password")
	private String password;
	
	private String raw_password;

	@Enumerated(EnumType.STRING)
	private Group groupNo;

	public enum Group {
		グループ1, グループ２, グループ3, グループ4, グループ5;
	}
	
	private Position position;

	public enum Position {
		leader, employee;
	}

	@Enumerated(EnumType.STRING)
	private Role role;                

	public enum Role {
		Role_Admin, Role_Employee;
	}
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date hireDate;

	private String phoneNo;
	private String address;
	
//	@OneToMany(mappedBy = "employeeInfo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
//		      CascadeType.REFRESH })
	
	@OneToMany(mappedBy = "employeeInfo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AttendanceContactInfo> attendanceContactInfo;

	public EmployeeInfo() {
		super();
	}

	public EmployeeInfo(int employeeId, @NotEmpty(message = "Please Enter Name") String name, Group groupNo,
			Position position, Role role, String mail, @NotEmpty(message = "Please Enter Password") String password,
			String raw_password,Date hireDate, String phoneNo, String address) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.groupNo = groupNo;
		this.position = position;
		this.role = role;
		this.mail = mail;
		this.password = password;
		this.raw_password = raw_password;
		this.hireDate = hireDate;
		this.phoneNo = phoneNo;
		this.address = address;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<AttendanceContactInfo> getAttendanceContactInfo() {
		return attendanceContactInfo;
	}

	public void setAttendanceContactInfo(List<AttendanceContactInfo> attendanceContactInfo) {
		this.attendanceContactInfo = attendanceContactInfo;
	}	
}
