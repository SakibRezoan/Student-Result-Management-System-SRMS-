package com.reza.srms.services;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.reza.srms.dtos.CourseWiseResultImportDto;
import com.reza.srms.entities.Course;
import com.reza.srms.entities.CourseWiseResult;
import com.reza.srms.entities.Student;
import com.reza.srms.entities.StudentResult;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.repositories.CourseWiseResultRepository;
import com.reza.srms.repositories.StudentRepository;
import com.reza.srms.utils.GpaAndGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.reza.srms.constant.ResponseStatus.SUCCESS;
import static com.reza.srms.enums.Grade.*;

@Service
@RequiredArgsConstructor
public class CourseWiseResultService {

    private final CourseWiseResultRepository courseWiseResultRepository;
    private final StudentRepository studentRepository;

    public String uploadResult(Integer batchNo, SemesterStatus semesterStatus, Course course, MultipartFile resultFile) {
        List<CourseWiseResultImportDto> resultImportDtoList;
        try {
            resultImportDtoList = fetchResultItems(resultFile);
        } catch (IOException e) {
            return e.getMessage();
        }
        CourseWiseResult courseWiseResult = new CourseWiseResult();
        courseWiseResult.setCourse(course);
        courseWiseResult.setBatchNo(batchNo);
        courseWiseResult.setSemesterStatus(semesterStatus);
        courseWiseResult.setFileName(resultFile.getOriginalFilename());

        List<StudentResult> studentResultList = new ArrayList<>();

        for (CourseWiseResultImportDto dto : resultImportDtoList) {

            StudentResult studentResult = new StudentResult();

            Optional<Student> student = studentRepository.findByBatchAndRollAndSemesterStatus(batchNo, dto.getRoll(), semesterStatus);

            if (student.isEmpty())
                return "Student not found with this batch: " + batchNo + " roll: " + dto.getRoll() + " and semester: " + semesterStatus;

            studentResult.setStudent(student.get());

            if (dto.getTotalMarksInTheoryExam() < dto.getObtainedMarksInTheoryExam())
                return "Obtained marks in theory can't be more than total marks in theory for roll: " + dto.getRoll();

            if (dto.getTotalMarksInLabExam() < dto.getObtainedMarksInLabExam())
                return "Obtained marks in lab can't be more than total marks in lab for roll: " + dto.getRoll();

            studentResult.setTotalMarksInTheoryExam(dto.getTotalMarksInTheoryExam());
            studentResult.setTotalMarksInLabExam(dto.getTotalMarksInLabExam());
            studentResult.setObtainedMarksInTheoryExam(dto.getObtainedMarksInTheoryExam());
            studentResult.setObtainedMarksInLabExam(dto.getObtainedMarksInLabExam());

            float totalObtainedMarksIn100 = calculateMarksIn100(course, dto.getTotalMarksInTheoryExam(), dto.getTotalMarksInLabExam(), dto.getObtainedMarksInTheoryExam(), dto.getObtainedMarksInLabExam());

            studentResult.setObtainedMarksInScale100(totalObtainedMarksIn100);

            GpaAndGrade gpaAndGrade = getGpaAndGrade(totalObtainedMarksIn100);

            studentResult.setGpa(gpaAndGrade.getGpa());

            studentResult.setGrade(gpaAndGrade.getGrade());

            studentResult.setCourseWiseResult(courseWiseResult);

            studentResultList.add(studentResult);
        }
        courseWiseResult.addStudentResultList(studentResultList);

        courseWiseResultRepository.save(courseWiseResult);

        return SUCCESS;
    }

    private GpaAndGrade getGpaAndGrade(float totalObtainedMarksIn100) {

        GpaAndGrade gpaAndGrade = new GpaAndGrade();

        if (totalObtainedMarksIn100 <= 100 && totalObtainedMarksIn100 >= 79.5) {
            gpaAndGrade.setGpa(4.0f);
            gpaAndGrade.setGrade(A_PLUS.getLabel());
        } else if (totalObtainedMarksIn100 <= 79.4 && totalObtainedMarksIn100 >= 74.5) {
            gpaAndGrade.setGpa(3.75f);
            gpaAndGrade.setGrade(A.getLabel());
        } else if (totalObtainedMarksIn100 <= 74.4 && totalObtainedMarksIn100 >= 69.5) {
            gpaAndGrade.setGpa(3.50f);
            gpaAndGrade.setGrade(A_MINUS.getLabel());
        } else if (totalObtainedMarksIn100 <= 69.4 && totalObtainedMarksIn100 >= 64.5) {
            gpaAndGrade.setGpa(3.25f);
            gpaAndGrade.setGrade(B_PLUS.getLabel());
        } else if (totalObtainedMarksIn100 <= 64.4 && totalObtainedMarksIn100 >= 59.5) {
            gpaAndGrade.setGpa(3.0f);
            gpaAndGrade.setGrade(B.getLabel());
        } else if (totalObtainedMarksIn100 <= 59.4 && totalObtainedMarksIn100 >= 54.5) {
            gpaAndGrade.setGpa(2.75f);
            gpaAndGrade.setGrade(B_MINUS.getLabel());
        } else if (totalObtainedMarksIn100 <= 54.4 && totalObtainedMarksIn100 >= 49.5) {
            gpaAndGrade.setGpa(2.50f);
            gpaAndGrade.setGrade(C_PLUS.getLabel());
        } else if (totalObtainedMarksIn100 <= 49.4 && totalObtainedMarksIn100 >= 44.5) {
            gpaAndGrade.setGpa(2.25f);
            gpaAndGrade.setGrade(C.getLabel());
        } else if (totalObtainedMarksIn100 <= 44.4 && totalObtainedMarksIn100 >= 39.5) {
            gpaAndGrade.setGpa(2.0f);
            gpaAndGrade.setGrade(D.getLabel());
        } else {
            gpaAndGrade.setGpa(0f);
            gpaAndGrade.setGrade(F.getLabel());
        }
        return gpaAndGrade;
    }

    private Float calculateMarksIn100(Course course, Integer totalMarksInTheoryExam, Integer totalMarksInLabExam, Float obtainedMarksInTheoryExam, Float obtainedMarksInLabExam) {
        float creditedMarksInTheoryExam = creditedMarks(course.getNoOfCreditsInTheory(), (float)totalMarksInTheoryExam);
        float creditedMarksInLabExam = creditedMarks(course.getNoOfCreditsInLab(), (float)totalMarksInLabExam);
        float creditedObtainedMarksInTheoryExam = creditedMarks(course.getNoOfCreditsInTheory(), obtainedMarksInTheoryExam);
        float creditedObtainedMarksInLabExam = creditedMarks(course.getNoOfCreditsInLab(), obtainedMarksInLabExam);

        float totalCreditedMarksInExam = creditedMarksInTheoryExam + creditedMarksInLabExam;
        float totalCreditedObtainedMarksInExam = creditedObtainedMarksInTheoryExam + creditedObtainedMarksInLabExam;

        float totalObtainedMarksIn100 = (totalCreditedObtainedMarksInExam * 100) / totalCreditedMarksInExam;

        BigDecimal decimal = new BigDecimal(totalObtainedMarksIn100);

        return decimal.setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    private float creditedMarks(Integer noOfCreditsInTheory, float marks) {
        return noOfCreditsInTheory * marks;
    }

    private List<CourseWiseResultImportDto> fetchResultItems(MultipartFile resultFile) throws IOException {
        String extension = Objects.requireNonNull(resultFile.getOriginalFilename()).substring(resultFile.getOriginalFilename().lastIndexOf(".") + 1);
        return Poiji.fromExcel(
                resultFile.getInputStream(),
                PoijiExcelType.valueOf(extension.toUpperCase()),
                CourseWiseResultImportDto.class,
                PoijiOptions.PoijiOptionsBuilder.settings().preferNullOverDefault(true).build()
        ).parallelStream().toList();
    }
}
