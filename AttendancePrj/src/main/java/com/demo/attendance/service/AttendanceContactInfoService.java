package com.demo.attendance.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.demo.attendance.entity.AttendanceContactInfo;

public interface AttendanceContactInfoService {
	void saveAttendanceContentInfo(AttendanceContactInfo attendanceContactInfo);
	List<AttendanceContactInfo> getAllAttendanceContactInfo();
	Page<AttendanceContactInfo> findPaginated(int pageNo, int pageSize);
	AttendanceContactInfo getAttendanceInfo(Integer attendanceId);
	//Update AttendanceInfo Confirm 
	AttendanceContactInfo getAttendanceContactInfoById(Integer attendanceId);
	Page<AttendanceContactInfo> findByDateConfirmWithPaginated(Date attendance_date, String confirm, int pageNo,
			int pageSize);
	Page<AttendanceContactInfo> findByConfirmWithPaginated(String confirm, int pageNo, int pageSize);
	Page<AttendanceContactInfo> findByDateWithPaginated(Date attendance_date, int pageNo, int pageSize);
	
}
