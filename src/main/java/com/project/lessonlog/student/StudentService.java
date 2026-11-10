package com.project.lessonlog.student;

import com.project.lessonlog.exception.custom.StudentNotFoundException;
import com.project.lessonlog.exception.custom.UserNotFoundException;
import com.project.lessonlog.lesson.LessonNoteRepository;
import com.project.lessonlog.student.dto.StudentDto;
import com.project.lessonlog.student.dto.StudentRequest;
import com.project.lessonlog.student.dto.StudentResponse;
import com.project.lessonlog.user.User;
import com.project.lessonlog.user.UserRepository;
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
public class StudentService {

    private static final List<String> SORTABLE_FIELDS = List.of("id", "name");
    private final StudentRepository studentRepository;
    private final LessonNoteRepository lessonNoteRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;

    public StudentResponse getAllStudents(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = PageableFactory.createWithTieBreaker(
                pageNumber, pageSize, sortBy, sortOrder, SORTABLE_FIELDS, "id");
        Page<Student> studentPage = studentRepository.findByUser_Id(userId, pageDetails);
        List<Student> students = studentPage.getContent();

        return new StudentResponse(studentMapper.toStudentDtoList(students),
                studentPage.getNumber() + 1,
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isLast());
    }

    public StudentDto getStudentById(Long userId, Long studentId) {
        Student student = studentRepository.findByIdAndUserId(studentId, userId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        return studentMapper.toStudentDto(student);
    }

    @Transactional
    public StudentDto registerStudent(Long userId, StudentRequest studentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Student student = studentMapper.createEntityFromRequest(user, studentRequest);
        Student saved = studentRepository.save(student);
        return studentMapper.toStudentDto(saved);
    }

    @Transactional
    public StudentDto updateStudent(Long userId, Long studentId, StudentRequest studentRequest) {
        Student student = studentRepository.findByIdAndUserId(studentId, userId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        student.updateStudent(studentRequest.getName(),
                studentRequest.getInstrument(),
                studentRequest.getPhone(),
                studentRequest.getMemo());

        return studentMapper.toStudentDto(student);
    }

    @Transactional
    public void deleteStudent(Long userId, Long studentId) {
        Student student = studentRepository.findByIdAndUserId(studentId, userId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        lessonNoteRepository.deleteByStudentId(studentId);
        studentRepository.delete(student);
    }
}
