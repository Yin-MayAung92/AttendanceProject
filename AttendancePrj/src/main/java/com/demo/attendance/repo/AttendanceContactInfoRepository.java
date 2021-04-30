package com.demo.attendance.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.demo.attendance.entity.AttendanceContactInfo;

@Repository
public interface AttendanceContactInfoRepository extends JpaRepository<AttendanceContactInfo, Integer> {
	Page<AttendanceContactInfo> findAll(Pageable pageable);

	@Query(value = "SELECT * FROM attendancecontactinfo WHERE attendancecontactinfo.attendance_date = :attendanceDate", nativeQuery = true)
	List<AttendanceContactInfo> findByAttendanceDate(
			@Param("attendanceDate") @DateTimeFormat(pattern = "yyyy-mm-dd") Date attendanceDate);

	// Update Admin AttendanceINFO Confirm
	Optional<AttendanceContactInfo> findByAttendanceId(int attendanceId);

	@Query(value = "SELECT * FROM attendancecontactinfo WHERE attendancecontactinfo.attendance_date = :attendanceDate and attendancecontactinfo.confirm = :confirm", nativeQuery = true)
	Page<AttendanceContactInfo> findByDateConfirm(
			@Param("attendanceDate") @DateTimeFormat(pattern = "yyyy-mm-dd") Date attendance_date,
			@Param("confirm") String confirm, Pageable pageable);

	@Query(value = "SELECT * FROM attendancecontactinfo WHERE attendancecontactinfo.confirm = :confirm", nativeQuery = true)
	Page<AttendanceContactInfo> findByConfirm(@Param("confirm") String confirm, Pageable pageable);
	
	@Query(value = "SELECT * FROM attendancecontactinfo WHERE attendancecontactinfo.attendance_date = :attendanceDate", nativeQuery = true)
	Page<AttendanceContactInfo> findByDate(@Param("attendanceDate") @DateTimeFormat(pattern = "yyyy-mm-dd") Date attendance_date, Pageable pageable);

}
