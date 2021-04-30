package com.demo.attendance.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "attendancecontactinfo")
public class AttendanceContactInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int attendanceId;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column(name = "attendanceDate")
	private Date attendanceDate;
	
	@Enumerated(EnumType.STRING)
	private AType attendanceType;	
	public enum AType{
		有給連絡, 欠勤連絡, 休連絡, 休日出勤, シフト出勤, 遅刻, 早退, 現場出勤, テレワーク;
	}
	
	@Enumerated(EnumType.STRING)
	private Type type;
	public enum Type{
		全休, 午前半休, 午後半休, 全日出勤, 午前出勤, 午後出勤, 離席;
	}
	
	@Enumerated(EnumType.STRING)
	private Contact onSiteContact;
	
	public enum Contact{
		未済, 済み;
	}
	
	@Enumerated(EnumType.STRING)
	private Confirm confirm;
	public enum Confirm{
		未済, 済み;
	}
	
	@Column(name="reason")
	private String reason;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeId", referencedColumnName = "employeeId")
	private EmployeeInfo employeeInfo;

	public AttendanceContactInfo() {
		super();
	}	

	public AttendanceContactInfo(Date attendanceDate, AType attendanceType, Type type, Contact onSiteContact,
			Confirm confirm, String reason, EmployeeInfo employeeInfo) {
		super();
		this.attendanceDate = attendanceDate;
		this.attendanceType = attendanceType;
		this.type = type;
		this.onSiteContact = onSiteContact;
		this.confirm = confirm;
		this.reason = reason;
		this.employeeInfo = employeeInfo;
	}



	public int getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}

	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public AType getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(AType attendanceType) {
		this.attendanceType = attendanceType;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Contact getOnSiteContact() {
		return onSiteContact;
	}

	public void setOnSiteContact(Contact onSiteContact) {
		this.onSiteContact = onSiteContact;
	}

	public Confirm getConfirm() {
		return confirm;
	}

	public void setConfirm(Confirm confirm) {
		this.confirm = confirm;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(EmployeeInfo employeeInfo) {
		this.employeeInfo = employeeInfo;
	}
	
}
