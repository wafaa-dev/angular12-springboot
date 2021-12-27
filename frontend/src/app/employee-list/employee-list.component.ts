import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees!: Employee[];
  // employees: Employee[] = [];
  constructor(private employeeService:EmployeeService,
    private router:Router) { }

  ngOnInit(): void {
    // test data 
  //   this.employees=[{
  //     "id":1,
  //     "firstName":"wafa",
  //     "lastName":"sakouhi",
  //     "emailId":"wafa@mail.com"
  //   },{
  //         "id":2,
  //     "firstName":"nour",
  //     "lastName":"mnas",
  //     "emailId":"nour@mail.com"
  //   }]
    this.getEmployees();
  }
private getEmployees(){
  this.employeeService.getEmployeesList().subscribe(data => {
    // console.log(data,"all data ");
    this.employees = data;
  });
}
updateEmployee(id:number){
  this.router.navigate(['update-employee',id]);
}
deleteEmployee(id:number){
this.employeeService.deleteEmployee(id).subscribe(data =>{
  console.log(data)
  this.getEmployees();
})
}
employeeDetails(id:number){
  this.router.navigate(['employee-details',id]);

}
}

