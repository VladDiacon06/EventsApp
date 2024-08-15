package com.EventsApp.model.dto;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.Date;

@Data
public class EventDto {

    private String title;

    @Size(max = 100, message = "Use no more than 100 characters")
    private String description;

    @Size(min = 5, message = "Use at least 3 characters")
    @Size(max = 50, message = "Use no more than 50 characters")
    private String location;

    @Size(min = 2, message = "Use at least 2 characters")
    @Size(max = 30, message = "Use no more than 30 characters")
    @Future(message = "date must be in the future")
    private Date date;

    @Size(min = 2, message = "Use at least 2 characters")
    @Size(max = 30, message = "Use no more than 30 characters")
    private Time time;

    private String category;

}
