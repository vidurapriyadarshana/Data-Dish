package edu.ijse.datadish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class LogInDto {

    private String userName;
    private String password;
    private String email;
    private String role;
}
