package com.google.tasks.Repositories;

import com.google.tasks.entities.EventReminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventReminderRepository extends JpaRepository<EventReminder,Long> {
}
