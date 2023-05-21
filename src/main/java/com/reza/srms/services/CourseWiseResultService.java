package com.reza.srms.services;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.reza.srms.dtos.CourseWiseResultImportDto;
import com.reza.srms.entities.Course;
import com.reza.srms.entities.CourseWiseResult;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.repositories.CourseWiseResultRepository;
import com.reza.srms.repositories.StudentRepository;
import com.reza.srms.utils.GpaAndGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        List<CourseWiseResult> courseWiseResultList = new ArrayList<>();

        for (CourseWiseResultImportDto dto : resultImportDtoList) {
            CourseWiseResult courseWiseResult = new CourseWiseResult();
            courseWiseResult.setCourse(course);

            Optional<Student> student = studentRepository.findByBatchAndRollAndSemesterStatus(batchNo, dto.getRoll(), semesterStatus);

            if (student.isEmpty())
                return "Student not found with this batch: " + batchNo + " roll: " + dto.getRoll() + " and semester: " + semesterStatus.getLabel();
            courseWiseResult.setStudent(student.get());

            if (dto.getTotalMarksInTheoryExam() < dto.getObtainedMarksInTheoryExam())
                return "Obtained marks in theory can't be more than total marks in theory";

            if (dto.getTotalMarksInLabExam() < dto.getObtainedMarksInTheoryExam())
                return "Obtained marks in lab can't be more than total marks in lab";

            courseWiseResult.setTotalMarksInTheoryExam(dto.getTotalMarksInTheoryExam());
            courseWiseResult.setTotalMarksInLabExam(dto.getTotalMarksInLabExam());
            courseWiseResult.setObtainedMarksInTheoryExam(dto.getObtainedMarksInTheoryExam());
            courseWiseResult.setObtainedMarksInLabExam(dto.getObtainedMarksInLabExam());
            GpaAndGrade gpaAndGrade = getGpaAndGrade(course, dto.getTotalMarksInTheoryExam(), dto.getTotalMarksInLabExam(), dto.getObtainedMarksInTheoryExam(), dto.getObtainedMarksInLabExam());
            courseWiseResult.setGpa(gpaAndGrade.getGpa());
            courseWiseResult.setGrade(gpaAndGrade.getGrade());

            courseWiseResultList.add(courseWiseResult);
        }
        courseWiseResultRepository.saveAll(courseWiseResultList);

        return SUCCESS;
    }

    private GpaAndGrade getGpaAndGrade(Course course, Integer totalMarksInTheoryExam, Integer totalMarksInLabExam, Float obtainedMarksInTheoryExam, Float obtainedMarksInLabExam) {

        float totalObtainedMarksIn100 = calculateMarksIn100(course, totalMarksInTheoryExam, totalMarksInLabExam, obtainedMarksInTheoryExam, obtainedMarksInLabExam);

        GpaAndGrade gpaAndGrade = new GpaAndGrade();

        if (totalObtainedMarksIn100 <= 100 && totalObtainedMarksIn100 >= 79.5){
            gpaAndGrade.setGpa(4.0f);
            gpaAndGrade.setGrade(A_PLUS.getLabel());
        }
        else if (totalObtainedMarksIn100 <= 79.4 && totalObtainedMarksIn100 >= 74.5) {
            gpaAndGrade.setGpa(3.75f);
            gpaAndGrade.setGrade(A.getLabel());
        }
        else if (totalObtainedMarksIn100 <= 74.4 && totalObtainedMarksIn100 >= 69.5) {
            gpaAndGrade.setGpa(3.50f);
            gpaAndGrade.setGrade(A_MINUS.getLabel());
        }
        else if (totalObtainedMarksIn100 <= 69.4 && totalObtainedMarksIn100 >= 64.5) {
            gpaAndGrade.setGpa(3.25f);
            gpaAndGrade.setGrade(B_PLUS.getLabel());
        }
        else if (totalObtainedMarksIn100 <= 64.4 && totalObtainedMarksIn100 >= 59.5) {
            gpaAndGrade.setGpa(3.0f);
            gpaAndGrade.setGrade(B.getLabel());
        }
        else if (totalObtainedMarksIn100 <= 59.4 && totalObtainedMarksIn100 >= 54.5)  {
            gpaAndGrade.setGpa(2.75f);
            gpaAndGrade.setGrade(B_MINUS.getLabel());
        }
        else if (totalObtainedMarksIn100 <= 54.4 && totalObtainedMarksIn100 >= 49.5) {
            gpaAndGrade.setGpa(2.50f);
            gpaAndGrade.setGrade(C_PLUS.getLabel());
        }
        else if (totalObtainedMarksIn100 <= 49.4 && totalObtainedMarksIn100 >= 44.5) {
            gpaAndGrade.setGpa(2.25f);
            gpaAndGrade.setGrade(C.getLabel());
        }
        else if (totalObtainedMarksIn100 <= 44.4 && totalObtainedMarksIn100 >= 39.5) {
            gpaAndGrade.setGpa(2.0f);
            gpaAndGrade.setGrade(D.getLabel());
        }
        else {
            gpaAndGrade.setGpa(0f);
            gpaAndGrade.setGrade(F.getLabel());
        }
        return gpaAndGrade;
    }

    private Float calculateMarksIn100(Course course, Integer totalMarksInTheoryExam, Integer totalMarksInLabExam, Float obtainedMarksInTheoryExam, Float obtainedMarksInLabExam) {
        return (((obtainedMarksInTheoryExam * 100 * course.getNoOfCreditsInTheory()) / totalMarksInTheoryExam) +
                                ((obtainedMarksInLabExam * 100 * course.getNoOfCreditsInLab()) / totalMarksInLabExam)) / 2.0f;
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
