package com.project.lessonlog.student.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    private String name;
    private String instrument;
    private String phone;
    private String memo;

    public Student(String name, String instrument, String phone, String memo) {
        this.name = name;
        this.instrument = instrument;
        this.phone = phone;
        this.memo = memo;
    }
}

