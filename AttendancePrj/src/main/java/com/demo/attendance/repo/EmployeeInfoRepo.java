package com.demo.attendance.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.attendance.entity.EmployeeInfo;

public interface EmployeeInfoRepo extends JpaRepository<EmployeeInfo, Integer> {
	EmployeeInfo findByMail(String mail);
	Page<EmployeeInfo> findAll(Pageable pageable);
}
