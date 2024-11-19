package edu.ijse.datadish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class EmployeeDto {
    private String employeeID;
    private String employeeName;
    private String employeeContact;
    private String hireDate;
    private String userName;
    private String employeeStatus;
    private String address;
    private String email;
    private String role;
    private String password;
}
