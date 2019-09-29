package com.kietnguyen.karaokemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kietnguyen.karaokemanagement.model.Event;


@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

}
