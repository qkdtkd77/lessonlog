package com.project.lessonlog.student.repository;

import com.project.lessonlog.student.domain.Student;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Student> studentMap = new HashMap<>();

    @Override
    public Student save(Student student) {
        Long id = idGenerator.incrementAndGet();
        student.setId(id);
        studentMap.put(id, student);
        return student;
    }

    @Override
    public List<Student> findAll() {
        return studentMap.values().stream().toList();
    }

    @Override
    public boolean delete(Long id) {
        Student removeStudent = studentMap.remove(id);
        return removeStudent != null;
    }
}
