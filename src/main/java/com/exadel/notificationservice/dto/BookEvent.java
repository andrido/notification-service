package com.exadel.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEvent {
    private Long bookId;
    private String title;
    private String status;
    private String userName;
    private String userEmail;
}
