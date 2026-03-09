package com.restoran.notificationservice.repository;

import com.restoran.notificationservice.entity.NotificationLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationLog, String> {
}
