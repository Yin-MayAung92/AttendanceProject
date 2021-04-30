package com.demo.attendance.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.attendance.entity.EmployeeInfo;
import com.demo.attendance.entity.EmployeeInfo.Role;
import com.demo.attendance.repo.EmployeeInfoRepo;

@Controller
@RequestMapping
public class SignInController {
	
	@Autowired
	private EmployeeInfoRepo repo;

	@GetMapping("/index")
	public String home() {
		return "index";
	}

	@GetMapping("/home")
	String index(HttpServletRequest req, final RedirectAttributes redirectAttributes) {
		EmployeeInfo user = new EmployeeInfo();
		user = repo.findByMail(req.getRemoteUser());
		return user.getRole() == Role.Role_Admin ? "redirect:/adminHome" : "redirect:/employeeHome";

	}

}
