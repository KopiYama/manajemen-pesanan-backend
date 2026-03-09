package com.restoran.notificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notification_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationLog {
    @Id
    private String id;
    private String orderId;
    private String message;
    private LocalDateTime createdAt;
}
