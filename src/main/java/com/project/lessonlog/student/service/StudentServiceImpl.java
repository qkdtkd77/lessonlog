package com.project.lessonlog.student.service;

import com.project.lessonlog.common.PageResponse;
import com.project.lessonlog.common.PaginationValidator;
import com.project.lessonlog.exception.StudentNotFoundException;
import com.project.lessonlog.student.domain.Student;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.mapper.StudentMapper;
import com.project.lessonlog.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public PageResponse<List<StudentDto>> getAllStudents(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        PaginationValidator.validate(pageNumber, pageSize, sortBy, sortOrder, List.of("id", "name"));
        Sort sort = Sort.by(
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                sortBy);

        Pageable pageDetails = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<Student> studentPage = studentRepository.findAll(pageDetails);
        List<Student> students = studentPage.getContent();


        return PageResponse.of(studentMapper.toStudentDtoList(students), studentPage);
    }

    @Override
    public StudentDto registerStudent(String name, String instrument, String phone, String memo) {
        return studentMapper.toStudentDto(studentRepository.save(new Student(name, instrument, phone, memo)));
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        studentRepository.delete(student);
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));
        return studentMapper.toStudentDto(student);
    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Student exists = studentRepository.findById(studentId).orElseThrow(
                () -> new StudentNotFoundException(studentId));

        exists.updateStudent(studentDto.getName(), studentDto.getInstrument(), studentDto.getPhone(), studentDto.getMemo());
        Student updated = studentRepository.save(exists);
        return studentMapper.toStudentDto(updated);
    }
}
