package com.soshal.service;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class IssueRequest {
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime dueDate;
    private List<String> tags;
}
