package com.web.personalstudy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ScheduleAssignees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public ScheduleAssignees(Schedule schedule) {
        this.schedule = schedule;
    }
}
