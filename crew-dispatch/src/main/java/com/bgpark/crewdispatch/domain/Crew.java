package com.bgpark.crewdispatch.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "crew")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobile", unique = true)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CrewStatus status;

    @Column(name = "last_rest_time")
    private LocalDateTime lastRestTime;

    @Column(name = "monthly_work_hours")
    private Integer monthlyWorkHours;

    @OneToMany(mappedBy = "crew")
    private List<Assignment> assignments = new ArrayList<>();
}
