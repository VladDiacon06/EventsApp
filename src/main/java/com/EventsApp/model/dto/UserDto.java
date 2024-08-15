package com.EventsApp.model.dto;

import jakarta.persistence.Basic;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Date;


@Data
public class UserDto {

    @Size(min = 3, message = "Firstname must be at least 3 characters")
    @Size(max = 50, message = "Firstname must be no more than 50 characters")
    @Basic
    private String firstName;


    @Size(min = 3, message = "Lastname must be at least 3 characters")
    @Size(max = 50, message = "Lastname must be no more than 50 characters")
    private String lastName;


    @Size(min = 5, message = "Use at least 3 characters")
    @Size(max = 50, message = "Use no more than 50 characters")
    private String email;


    @Size(min = 6, message = "Password must be at least 6 characters")
    @Size(max = 15, message = "Password must be no more than 15 characters")
    private String password;


    @Size(min = 3, message = "Username must be at least 3 characters")
    @Size(max = 50, message = "Username must be no more than 50 characters")
    private String username;


    @Past(message = "Birthday date must be in the past")
    private Date birthday;


}

