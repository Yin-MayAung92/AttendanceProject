package com.demo.attendance.controller;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.attendance.dto.EmployeeInfoDTO;
import com.demo.attendance.entity.AttendanceContactInfo;
import com.demo.attendance.entity.EmployeeInfo;
import com.demo.attendance.repo.AttendanceContactInfoRepository;
import com.demo.attendance.service.AttendanceContactInfoService;
import com.demo.attendance.service.EmployeeInfoService;

@Controller
public class AdminController {

	@Autowired
	private EmployeeInfoService empinfoService;

	@Autowired
	private AttendanceContactInfoService attendanceService;

	@Autowired
	private AttendanceContactInfoRepository repo;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@GetMapping("/adminHome")
	String getIndexPage(Model model) {
		return findAttendancePaginated(1, new AttendanceContactInfo(),model);
	}

	@GetMapping("/attendPaginated/{pageNo}")
	private String findAttendancePaginated(@PathVariable(value = "pageNo") int pageNo, @ModelAttribute("searchOptions") AttendanceContactInfo searchOptions, Model model) {
		// TODO Auto-generated method stub
		int pageSize = 5;
		Page<AttendanceContactInfo> attendancePage = null;
		if(searchOptions.getAttendanceDate() == null && searchOptions.getConfirm() == null) {
			attendancePage = attendanceService.findPaginated(pageNo, pageSize);
			model.addAttribute("searchOptions", new AttendanceContactInfo());
		}else {
			if(searchOptions.getAttendanceDate() != null && searchOptions.getConfirm() != null) {
				attendancePage = attendanceService.findByDateConfirmWithPaginated(searchOptions.getAttendanceDate(), searchOptions.getConfirm().toString(), pageNo,
						pageSize);
			}else if(searchOptions.getAttendanceDate() == null && searchOptions.getConfirm() != null) {
				attendancePage = attendanceService.findByConfirmWithPaginated(searchOptions.getConfirm().toString(), pageNo, pageSize);
			}else if(searchOptions.getAttendanceDate() != null && searchOptions.getConfirm() == null) {
				attendancePage = attendanceService.findByDateWithPaginated(searchOptions.getAttendanceDate(), pageNo, pageSize);
			}
			model.addAttribute("searchOptions", searchOptions);
		}	
		PageWrapper<AttendanceContactInfo> page = new PageWrapper<AttendanceContactInfo>(attendancePage,
				"/attendPaginated/" + pageNo);
		model.addAttribute("page", page);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", attendancePage.getTotalPages());
		model.addAttribute("totalItems", attendancePage.getTotalElements());
		model.addAttribute("listAttendanceContactInfo", page.getContent());			
		model.addAttribute("activeLink", "Attendance");
		return "/admin/attendance-lists";
	}
	
	// display list of employee
	@GetMapping("/empLists-form")
	public String getEmployeePage(Model model) {
		return findPaginated(1, model);
	}

	@GetMapping("/paginated/{pageNo}")
	private String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
		// TODO Auto-generated method stub
		int pageSize = 5;

		Page<EmployeeInfo> empPage = empinfoService.findPaginated(pageNo, pageSize);
		PageWrapper<EmployeeInfo> page = new PageWrapper<EmployeeInfo>(empPage, "/paginated/" + pageNo);

		model.addAttribute("page", page);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", empPage.getTotalPages());
		model.addAttribute("totalItems", empPage.getTotalElements());
		model.addAttribute("listEmployee", page.getContent());
		model.addAttribute("activeLink", "EmployeeLists");
		return "/admin/employee_lists";
	}

	@GetMapping("/register-form")
	public String showRegisterForm(Model model) {
		EmployeeInfoDTO empdto = new EmployeeInfoDTO();
		String rawpassword = generateRandomPassword(8);
		empdto.setRaw_password(rawpassword);
		model.addAttribute("signUpObj", empdto);
		model.addAttribute("activeLink", "EmployeeLists");
		return "/admin/employee-register";
	}

	@PostMapping("/register")
	public String registerEmployee(@ModelAttribute("signUpObj") EmployeeInfoDTO signUpObj, Model model) {
		// save employee to database
		empinfoService.register(signUpObj);
		return "redirect:/empLists-form";
	}

	private String generateRandomPassword(int len) {
		// TODO Auto-generated method stub
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi" + "jklmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

	@GetMapping("/update-form/{employeeId}")
	public String updateEmployeeInfo(@PathVariable(value = "employeeId") Integer employeeId, Model model) {
		// get Employee from the service
		EmployeeInfo update_emp = empinfoService.getEmployeeInfoById(employeeId);
		EmployeeInfoDTO empdto = new EmployeeInfoDTO();
		empdto.setEmployeeId(update_emp.getEmployeeId());
		empdto.setName(update_emp.getName());
		empdto.setMail(update_emp.getMail());
		if (encoder.matches(update_emp.getRaw_password(), update_emp.getPassword())) {
			empdto.setRaw_password(update_emp.getRaw_password());
		}
		empdto.setPhoneNo(update_emp.getPhoneNo());
		empdto.setGroupNo(update_emp.getGroupNo());
		empdto.setPosition(update_emp.getPosition());
		empdto.setHireDate(update_emp.getHireDate());
		empdto.setAddress(update_emp.getAddress());
		String role = update_emp.getRole().toString();
		if (role.equals("Role_Admin")) {
			empdto.setAdminCheck(true);
		} else {
			empdto.setAdminCheck(false);
		}
		model.addAttribute("updateObj", empdto);// set Employee as a model attribute to pre-pupulate the form data
		model.addAttribute("activeLink", "EmployeeLists");
		return "/admin/employee-update";
	}

	@PostMapping("/updatesave")
	public String updateEmployee(@ModelAttribute("updateObj") EmployeeInfoDTO updateObj, Model model) {
		// update into database
		empinfoService.updateEmployee(updateObj);
		return "redirect:/empLists-form";
	}

	@GetMapping("/search")
	String getAttendanceByOptions(@ModelAttribute("searchOptions") AttendanceContactInfo searchOptions, Model model) {
		return findAttendancePaginated(1, searchOptions, model);
	}

	@GetMapping("/editmodal")
	public String editmodal(@RequestParam("attendanceId") String attendanceId, Model model) {
		int id = Integer.parseInt(attendanceId);
		AttendanceContactInfo update_Obj = attendanceService.getAttendanceContactInfoById(id);
		if (update_Obj != null && update_Obj.getConfirm().toString() == "未済") {
			model.addAttribute("attendanceObj", update_Obj);
			return "/admin/shouninModal";
		} else {
			model.addAttribute("attendanceObj", update_Obj);
			return "/admin/detailsModal";
		}
	}

	@PostMapping("/updateShounin")
	public String updateShounin(@RequestParam String action,
			@ModelAttribute("attendanceObj") AttendanceContactInfo attendanceObj, Model model) {
		// update into database
		Optional<AttendanceContactInfo> optional = repo.findById(attendanceObj.getAttendanceId());
		AttendanceContactInfo attendanceContactInfo = null;

		if (optional.isPresent()) {
			attendanceContactInfo = optional.get();
		}
		if (action.equals("shounin")) {
			attendanceContactInfo.setConfirm(AttendanceContactInfo.Confirm.済み);
			attendanceService.saveAttendanceContentInfo(attendanceContactInfo);
		} else if (action.equals("hinin")) {
			attendanceContactInfo.setConfirm(AttendanceContactInfo.Confirm.未済);
			attendanceService.saveAttendanceContentInfo(attendanceContactInfo);
		}
		return "redirect:/adminHome";
	}
}
