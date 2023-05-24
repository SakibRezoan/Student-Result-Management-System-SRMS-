package com.reza.srms.services;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.reza.srms.dtos.StudentDto;
import com.reza.srms.entities.SemesterWiseResult;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.Semester;
import com.reza.srms.repositories.SemesterWiseResultRepository;
import com.reza.srms.repositories.StudentRepository;
import com.reza.srms.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static com.reza.srms.constant.ResponseStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final SemesterWiseResultRepository semesterWiseResultRepository;

    @Transactional
    public Student save(StudentDto dto) {
        Student student = dto.toEntity();

        studentRepository.save(student);

        SemesterWiseResult semesterWiseResult = semesterWiseResultRepository.findByStudentIdAndSemester(student.getId(), student.getSemester().toString()).orElse(new SemesterWiseResult());

        semesterWiseResult.setStudent(student);
        semesterWiseResult.setSemester(student.getSemester());

        semesterWiseResultRepository.save(semesterWiseResult);

        return student;

    }

    public Optional<Student> findByRoll(Long roll) {
        return studentRepository.findByRoll(roll);
    }

    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email.trim());
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public Student update(StudentDto dto, Student student) {

        dto.update(student);

        return studentRepository.save(student);

    }

    public Student changeSemesterStatus(Student student, Semester semester) {

        student.setSemester(semester);

        SemesterWiseResult semesterWiseResult = semesterWiseResultRepository.findByStudentIdAndSemester(student.getId(), semester.toString()).orElse(new SemesterWiseResult());

        semesterWiseResult.setStudent(student);

        semesterWiseResult.setSemester(semester);

        semesterWiseResultRepository.save(semesterWiseResult);

        return studentRepository.save(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public Map<String, Object> getList(Semester semester, Long roll, String search, Integer page, Integer size) {
        ServiceHelper<Student> helper = new ServiceHelper<>(Student.class);
        return helper.getList(
                studentRepository.getList(semester.getLabel(), roll, search.trim(),
                        helper.getPageable("", page, size)),
                page,
                size
        );
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findByNewRollExceptExisting(Long newRoll, Long previousRoll) {
        return studentRepository.findByNewRollExceptExisting(newRoll, previousRoll);
    }

    public Optional<Student> findByNewEmailExceptExisting(String newEmail, String previousEmail) {
        return studentRepository.findByNewEmailExceptExisting(newEmail.trim().toLowerCase(), previousEmail);
    }

    public Optional<Student> findByNewMobileExceptExisting(String newMobile, String previousMobile) {
        return studentRepository.findByNewMobileExceptExisting(newMobile.trim(), previousMobile);
    }

    public Optional<Student> findByMobile(String mobile) {
        return studentRepository.findByMobile(mobile.trim());
    }

    @Transactional
    public String uploadStudents(Integer batch, MultipartFile studentFile) {
        List<StudentDto> studentDtoList;
        try {
            studentDtoList = fetchStudentItems(studentFile);
        } catch (IOException e) {
            return e.getMessage();
        }

        List<SemesterWiseResult> semesterWiseResultList = new ArrayList<>();

        List<Student> studentList = new ArrayList<>();

        for (StudentDto dto : studentDtoList) {

            Student student = dto.toEntity();

            studentList.add(student);

            SemesterWiseResult semesterWiseResult = new SemesterWiseResult();

            semesterWiseResult.setStudent(student);

            semesterWiseResult.setSemester(student.getSemester());

            semesterWiseResultList.add(semesterWiseResult);
        }

        semesterWiseResultRepository.saveAll(semesterWiseResultList);

        studentRepository.saveAll(studentList);

        return SUCCESS;
    }

    private List<StudentDto> fetchStudentItems(MultipartFile studentFile) throws IOException {
        String extension = Objects.requireNonNull(studentFile.getOriginalFilename()).substring(studentFile.getOriginalFilename().lastIndexOf(".") + 1);
        return Poiji.fromExcel(
                studentFile.getInputStream(),
                PoijiExcelType.valueOf(extension.toUpperCase()),
                StudentDto.class,
                PoijiOptions.PoijiOptionsBuilder.settings().preferNullOverDefault(true).build()
        ).parallelStream().toList();
    }
}
