package com.web.personalstudy.repository;

import com.web.personalstudy.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findByIdOrElseThrow(Long sid);
}
