package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
public List<Employee> getAllEmployees(){
	return employeeRepository.findAll();
}
	//add employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	//get single employee by id
//	@GetMapping("/employees/{id}")
	@RequestMapping(
			  value = "/employees/{id}", 
			  produces = "application/json", 
			  method ={RequestMethod.GET})
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
	Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("This employee does not exist which id:"+id));
		return ResponseEntity.ok(employee);
	}
	//update employee
	
	@RequestMapping(
			  value = "/employees/{id}", 
			  produces = "application/json", 
			  method ={RequestMethod.PUT})
	
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("This employee does not exist which id:"+id));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());		//store the update employee to the data base
		Employee UpdatedEmployee= employeeRepository.save(employee);
		return ResponseEntity.ok(UpdatedEmployee);
	}
	
	//delete employee
	@DeleteMapping( "/employees/{id}")
	
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("this employee is not found"));
		
		employeeRepository.delete(employee);
		Map<String,Boolean> response =new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	}
