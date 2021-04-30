package com.demo.attendance.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.attendance.dto.EmployeeInfoDTO;
import com.demo.attendance.entity.EmployeeInfo;

public interface EmployeeInfoService extends UserDetailsService{
	void register(EmployeeInfoDTO signUpObj);
	List<EmployeeInfo> getAllEmployee();
	Page<EmployeeInfo> findPaginated(int pageNo, int pageSize);	
	EmployeeInfo getEmployeeInfoById(Integer employeeId);
	void updateEmployee(EmployeeInfoDTO updateObj);
}
