package com.project.lessonlog.student.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Student {

    private final String name;
    private final String instrument;
    private final String phone;
    private final String memo;
    
    @Setter
    private Long id;

    public Student(String name, String instrument, String phone, String memo) {
        this.name = name;
        this.instrument = instrument;
        this.phone = phone;
        this.memo = memo;
    }
}

