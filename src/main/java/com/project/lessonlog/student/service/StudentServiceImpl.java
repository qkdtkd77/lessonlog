package com.project.lessonlog.student.service;

import com.project.lessonlog.common.dto.PageResponse;
import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.lesson.repository.LessonNoteRepository;
import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.mapper.StudentMapper;
import com.project.lessonlog.student.repository.StudentRepository;
import com.project.lessonlog.util.PageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.lessonlog.student.config.AppConstant.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private static final List<String> SORTABLE_FIELDS = List.of("id", "name");
    private final StudentRepository studentRepository;
    private final LessonNoteRepository lessonNoteRepository;
    private final StudentMapper studentMapper;

    @Override
    public PageResponse<List<StudentDto>> getAllStudents(Integer pageNumber) {
        Pageable pageDetails = PageableFactory.create(pageNumber, DEFAULT_PAGE_SIZE, SORT_STUDENT_BY, SORT_STUDENT_ASC, SORTABLE_FIELDS);
        Page<Student> studentPage = studentRepository.findAll(pageDetails);
        List<Student> students = studentPage.getContent();

        return PageResponse.of(studentMapper.toStudentDtoList(students), studentPage);
    }

    @Override
    public StudentDto registerStudent(String name, String instrument, String phone, String memo) {
        return studentMapper.toStudentDto(studentRepository.save(new Student(name, instrument, phone, memo)));
    }

    @Override
    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        lessonNoteRepository.deleteByStudent_Id(studentId);
        studentRepository.delete(student);
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));
        return studentMapper.toStudentDto(student);
    }

    @Override
    @Transactional
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Student exists = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));

        exists.updateStudent(studentDto.getName(), studentDto.getInstrument(), studentDto.getPhone(), studentDto.getMemo());
        return studentMapper.toStudentDto(exists);
    }
}
