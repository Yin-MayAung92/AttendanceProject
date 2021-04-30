package com.demo.attendance.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.attendance.entity.AttendanceContactInfo;
import com.demo.attendance.repo.AttendanceContactInfoRepository;

@Service
public class AttendanceContactInfoServiceImpl implements AttendanceContactInfoService {

	@Autowired
	private AttendanceContactInfoRepository attendanceRepo;

	public List<AttendanceContactInfo> getAllAttendanceContactInfo() {
		return attendanceRepo.findAll();
	}

	@Override
	public void saveAttendanceContentInfo(AttendanceContactInfo attendanceContactInfo) {

		System.out.println("ServiceImpl >>>>>> " + attendanceContactInfo.getAttendanceDate() + ","
				+ attendanceContactInfo.getAttendanceType() + "," + attendanceContactInfo.getType() + ","
				+ attendanceContactInfo.getOnSiteContact() + "," + attendanceContactInfo.getReason() + ","
				+ "employeeID..."+attendanceContactInfo.getEmployeeInfo().getEmployeeId());

		AttendanceContactInfo attendanceInfo = new AttendanceContactInfo();
		attendanceInfo.setAttendanceId(attendanceContactInfo.getAttendanceId());
		attendanceInfo.setAttendanceDate(attendanceContactInfo.getAttendanceDate());
		attendanceInfo.setAttendanceType(attendanceContactInfo.getAttendanceType());
		attendanceInfo.setType(attendanceContactInfo.getType());
		attendanceInfo.setOnSiteContact(attendanceContactInfo.getOnSiteContact());
		attendanceInfo.setConfirm(attendanceContactInfo.getConfirm());
		attendanceInfo.setReason(attendanceContactInfo.getReason());
		attendanceInfo.setEmployeeInfo(attendanceContactInfo.getEmployeeInfo());
		this.attendanceRepo.save(attendanceInfo);
	}

	@Override
	public Page<AttendanceContactInfo> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.attendanceRepo.findAll(pageable);
	}

	public AttendanceContactInfo getAttendanceInfo(Integer attendanceId) {
		Optional<AttendanceContactInfo> optional = attendanceRepo.findById(attendanceId);
		AttendanceContactInfo attendanceContactInfo = null;

		if (optional.isPresent()) {
			attendanceContactInfo = optional.get();
		} else {
			throw new RuntimeException("このattendance_idの勤怠連絡情報が見つかりません ::" + attendanceId);
		}
		return attendanceContactInfo;
	}

	// Update AttendanceContactInfo AT ADMINCONTALLER
	public AttendanceContactInfo getAttendanceContactInfoById(Integer attendanceId) {
		Optional<AttendanceContactInfo> optional = attendanceRepo.findByAttendanceId(attendanceId);
		AttendanceContactInfo attendanceContactInfo = null;

		if (optional.isPresent()) {
			attendanceContactInfo = optional.get();
		} else {
			throw new RuntimeException("このattendance_idの勤怠連絡情報が見つかりません ::" + attendanceId);
		}
		return attendanceContactInfo;
	}
	
	@Override
	public Page<AttendanceContactInfo> findByDateConfirmWithPaginated(Date attendance_date, String confirm, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.attendanceRepo.findByDateConfirm( attendance_date, confirm, pageable);
	}

	@Override
	public Page<AttendanceContactInfo> findByConfirmWithPaginated(String confirm, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.attendanceRepo.findByConfirm(confirm, pageable);
	}

	@Override
	public Page<AttendanceContactInfo> findByDateWithPaginated(Date attendance_date, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.attendanceRepo.findByDate(attendance_date, pageable);
	}

}
