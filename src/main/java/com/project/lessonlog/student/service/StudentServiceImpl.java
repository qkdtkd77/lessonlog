package com.project.lessonlog.student.service;

import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.lesson.repository.LessonNoteRepository;
import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.student.dto.StudentResponse;
import com.project.lessonlog.student.mapper.StudentMapper;
import com.project.lessonlog.student.repository.StudentRepository;
import com.project.lessonlog.util.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final List<String> SORTABLE_FIELDS = List.of("id", "name");
    private final StudentRepository studentRepository;
    private final LessonNoteRepository lessonNoteRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponse getAllStudents(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = PageableFactory.create(pageNumber, pageSize, sortBy, sortOrder, SORTABLE_FIELDS);
        Page<Student> studentPage = studentRepository.findAll(pageDetails);
        List<Student> students = studentPage.getContent();

        return new StudentResponse(studentMapper.toStudentDtoList(students),
                studentPage.getNumber() + 1,
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isLast());
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));
        return studentMapper.toStudentDto(student);
    }

    @Override
    @Transactional
    public StudentDto registerStudent(StudentRequest studentRequest) {
        Student student = studentMapper.toEntity(studentRequest);
        Student saved = studentRepository.save(student);
        return studentMapper.toStudentDto(saved);
    }

    @Override
    @Transactional
    public StudentDto updateStudent(Long studentId, StudentRequest studentRequest) {
        Student exists = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));

        exists.updateStudent(studentRequest.getName(),
                studentRequest.getInstrument(),
                studentRequest.getPhone(),
                studentRequest.getMemo());

        return studentMapper.toStudentDto(exists);
    }

    @Override
    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        lessonNoteRepository.deleteByStudent_Id(studentId);
        studentRepository.delete(student);
    }
}
