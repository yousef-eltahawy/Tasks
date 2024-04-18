package com.google.tasks.Repositories;

import com.google.tasks.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event,String> {
    Optional<Event> findBySummary(String s);

    Page<Event> findAll(Pageable pageable);
    List<Event> findAllByIsWaiting(boolean isWaiting);

}
