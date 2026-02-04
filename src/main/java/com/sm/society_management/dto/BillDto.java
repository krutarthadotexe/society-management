package com.sm.society_management.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BillDto {
    private int userId;
    private String billType;
    private int amount;
    private LocalDate dueDate;
}
