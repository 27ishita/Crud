package com.employee.management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
	}
	
	//create employee
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/employees")
	public Employee getEmployee(@RequestBody Employee employee)
	{
		return  employeeRepository.save(employee);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeebyID(@PathVariable long id)
	{
		Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found Exception"+id));
		
		return ResponseEntity.ok(employee);
	}
	//update employee rest api
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails)
	{
		Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found Exception"+id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee = employeeRepository.save(employee);
		
		return ResponseEntity.ok(updateEmployee);

	}
	
	//delete an employee
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String , Boolean>> deleteEmployee(@PathVariable Long id){
		
		Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found Exception"+id));
		employeeRepository.delete(employee);
		Map<String,Boolean>response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}

}
