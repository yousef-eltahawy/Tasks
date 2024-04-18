package com.google.tasks.Repositories;

import com.google.tasks.entities.Reminders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemindersRepository extends JpaRepository<Reminders,Long> {
}
