package com.demo.attendance.service;

import java.util.Arrays;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.attendance.dto.EmployeeInfoDTO;
import com.demo.attendance.entity.EmployeeInfo;
import com.demo.attendance.repo.EmployeeInfoRepo;

@Service
public class EmployeeInfoServiceImp implements EmployeeInfoService {
	@Autowired
	private EmployeeInfoRepo repo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void register(EmployeeInfoDTO signUpObj) {
		EmployeeInfo emp = new EmployeeInfo();
		emp.setGroupNo(signUpObj.getGroupNo());
		emp.setPosition(signUpObj.getPosition());
		emp.setName(signUpObj.getName());
		emp.setPhoneNo(signUpObj.getPhoneNo());
		emp.setMail(signUpObj.getMail());
		emp.setPassword(passwordEncoder.encode(signUpObj.getRaw_password()));// for security encoded
		emp.setRaw_password(signUpObj.getRaw_password());// Raw
		if (signUpObj.isAdminCheck()) {
			emp.setRole(com.demo.attendance.entity.EmployeeInfo.Role.Role_Admin);
		} else {
			emp.setRole(com.demo.attendance.entity.EmployeeInfo.Role.Role_Employee);
		}
		emp.setHireDate(signUpObj.getHireDate());
		emp.setAddress(signUpObj.getAddress());
		repo.save(emp);
	}

	@Override
	public List<EmployeeInfo> getAllEmployee() {
		return repo.findAll();
	}

	public Page<EmployeeInfo> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.repo.findAll(pageable);
	}

	@Override
	public EmployeeInfo getEmployeeInfoById(Integer employeeId) {
		Optional<EmployeeInfo> optional = repo.findById(employeeId);
		EmployeeInfo employeeInfo = null;

		if (optional.isPresent()) {
			employeeInfo = optional.get();
		} else {
			throw new RuntimeException("このemployee_idのEmployeeInfoが見つかりません ::" + employeeId);
		}
		return employeeInfo;
	}

	@Override
	public void updateEmployee(EmployeeInfoDTO updateObj) {
		// TODO Auto-generated method stub
		Optional<EmployeeInfo> optional = repo.findById(updateObj.getEmployeeId());
		EmployeeInfo employeeInfo = null;
		if (optional.isPresent()) {
			employeeInfo = optional.get();
		} else {
			throw new RuntimeException("このemployee_idのEmployeeInfoが見つかりません ::" + updateObj.getEmployeeId());
		}
		employeeInfo.setGroupNo(updateObj.getGroupNo());
		employeeInfo.setPosition(updateObj.getPosition());
		employeeInfo.setName(updateObj.getName());
		employeeInfo.setPhoneNo(updateObj.getPhoneNo());
		employeeInfo.setMail(updateObj.getMail());
		employeeInfo.setRaw_password(updateObj.getRaw_password());
		employeeInfo.setPassword(passwordEncoder.encode(updateObj.getRaw_password()));
		if (updateObj.isAdminCheck()) {
			employeeInfo.setRole(com.demo.attendance.entity.EmployeeInfo.Role.Role_Admin);
		} else {
			employeeInfo.setRole(com.demo.attendance.entity.EmployeeInfo.Role.Role_Employee);
		}
		employeeInfo.setHireDate(updateObj.getHireDate());
		employeeInfo.setAddress(updateObj.getAddress());
		repo.save(employeeInfo);
	}

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		EmployeeInfo emp = repo.findByMail(mail);
		if (emp == null) {	
			throw new UsernameNotFoundException("ユーザー名またはパスワードが間違っています!");			
		}
		return new User(emp.getMail(), emp.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("ROLE_".concat(emp.getRole().toString()))));

	}

}
