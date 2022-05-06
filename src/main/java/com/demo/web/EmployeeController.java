package com.demo.web;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.exception.RecordNotFoundException;
import com.demo.model.EmployeeEntity;
import com.demo.model.Greeting;
import com.demo.model.UserEntity;
import com.demo.service.EmployeeService;

@Controller
@RequestMapping(value = { "employees", "/", "welcome" })
public class EmployeeController {
	@Autowired
	EmployeeService service;

	@GetMapping
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees() {
		List<EmployeeEntity> list = service.getAllEmployees();

		return new ResponseEntity<List<EmployeeEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		EmployeeEntity entity = service.getEmployeeById(id);

		return new ResponseEntity<EmployeeEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<EmployeeEntity> createOrUpdateEmployee(EmployeeEntity employee)
			throws RecordNotFoundException {
		EmployeeEntity updated = service.createOrUpdateEmployee(employee);
		return new ResponseEntity<EmployeeEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteEmployeeById(id);
		return HttpStatus.FORBIDDEN;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("employeeent", new UserEntity());
		return "login";
	}

	@PostMapping("/login")
	public String greetingSubmit(@ModelAttribute UserEntity employee, Model model) throws RecordNotFoundException {

		List<UserEntity> empList = service.getEmployee(employee.getFirstName(), employee.getPassword());
        if(CollectionUtils.isNotEmpty(empList)) {
			model.addAttribute("greeting", empList.get(0));
			return "result2";
		} else
			return "fail";
	}

	@GetMapping("/greeting")
	public String greetingForm(Model model) {
		model.addAttribute("greeting", new Greeting());
		return "greeting";
	}

	@PostMapping("/greeting")
	public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
		model.addAttribute("greeting", greeting);
		return "result";
	}

}