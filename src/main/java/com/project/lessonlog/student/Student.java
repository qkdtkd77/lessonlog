package com.project.lessonlog.student;

import com.project.lessonlog.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30)
    private String instrument;

    @Column(nullable = false, length = 13)
    private String phone;

    @Column(length = 500)
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime deletedAt;

    public Student(User user, String name, String instrument, String phone, String memo) {
        this.user = user;
        this.name = name;
        this.instrument = instrument;
        this.phone = phone;
        this.memo = memo;
    }

    public void updateStudent(String name, String instrument, String phone, String memo) {
        this.name = name;
        this.instrument = instrument;
        this.phone = phone;
        this.memo = memo;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}

