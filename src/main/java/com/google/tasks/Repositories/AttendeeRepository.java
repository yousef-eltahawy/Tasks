package com.google.tasks.Repositories;

import com.google.tasks.entities.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee,Long> {
}
