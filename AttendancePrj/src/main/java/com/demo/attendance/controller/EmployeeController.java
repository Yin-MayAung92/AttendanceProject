package com.demo.attendance.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.attendance.entity.AttendanceContactInfo;
import com.demo.attendance.entity.EmployeeInfo;
import com.demo.attendance.repo.EmployeeInfoRepo;
import com.demo.attendance.service.AttendanceContactInfoService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeInfoRepo repo;
	
	@Autowired
	private AttendanceContactInfoService attendanceService;
	
	@GetMapping("/employeeHome")
	public String getIndexPage(@ModelAttribute("dataForNextPage") EmployeeInfo user, Model model,@DateTimeFormat(pattern = "yyyy-mm-dd") Date attendanceDate) {
		model.addAttribute("LoginUser", user);
		model.addAttribute("loggedinuser", user.getName());	
		return findAttPaginated(1, model, attendanceDate);
	}       
	
	//Show AttendanceInfo Form
	@GetMapping("/attendance-register")
	public String addAttendanceform( HttpServletRequest req, @ModelAttribute ("employeeInfo") EmployeeInfo emp, Model model) {
		EmployeeInfo empInfo = new EmployeeInfo();
		empInfo = repo.findByMail(req.getRemoteUser());
		//create model attribute to bind form data
		AttendanceContactInfo attendanceContactInfo = new AttendanceContactInfo();
		attendanceContactInfo.setEmployeeInfo(empInfo);
		model.addAttribute("attendanceContactInfo", attendanceContactInfo); 
		model.addAttribute("loginUser", empInfo);
		return "/employee/attendance-register"; 
	}
	
	@PostMapping("/saveAttendanceContactInfo")
	public String saveAttendanceContactInfo(@ModelAttribute("attendanceContactInfo") AttendanceContactInfo attendanceContactInfo, Model model) {
		//Save attendanceContactInfo To DataBase
		attendanceService.saveAttendanceContentInfo(attendanceContactInfo);
		return "redirect:/employeeHome";
	}	
	
	//pagination For AttendanceContactList
	@GetMapping("/page/{pageNo}")
	public String findAttPaginated(@PathVariable(value = "pageNo")int pageNo, Model model, Date attendanceDate) {
		System.out.println("EmpController_findAttPaginated");
		int pageSize = 5;
		Page<AttendanceContactInfo> attendPage = null;
		
		if(attendanceDate != null) {
			attendPage = attendanceService.findByDateWithPaginated(attendanceDate,pageNo, pageSize);
		}else {
			attendPage = attendanceService.findPaginated(pageNo, pageSize);
		}
		PageWrapper<AttendanceContactInfo> page = new PageWrapper<AttendanceContactInfo>(attendPage,
				"/page/" + pageNo);
		model.addAttribute("page", page);		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());	
		model.addAttribute("listAttendanceContactInfo", page.getContent());
			
		return "/employee/employeeHome";
	}
	
	@GetMapping("/updateAttendance/{attendanceId}")
	public String updateAttendanceInfo(@PathVariable(value = "attendanceId") Integer attendanceId, Model model,@DateTimeFormat(pattern = "yyyy-mm-dd") Date attendanceDate) {
		//set AttendanceContactInfo as a model attribute		
		model.addAttribute("attendanceContactInfo", attendanceService.getAttendanceInfo(attendanceId));
		return "/employee/update-attendance";
	}		
	
}
