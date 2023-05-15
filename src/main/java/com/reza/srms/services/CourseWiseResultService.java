package com.reza.srms.services;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.reza.srms.dtos.CourseWiseResultImportDto;
import com.reza.srms.entities.Course;
import com.reza.srms.entities.CourseWiseResult;
import com.reza.srms.entities.Student;
import com.reza.srms.enums.Grade;
import com.reza.srms.enums.SemesterStatus;
import com.reza.srms.repositories.CourseWiseResultRepository;
import com.reza.srms.repositories.StudentRepository;
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

            if (dto.getObtainedMarks() > 100 || dto.getObtainedMarks() < 0)
                return "Obtained marks can't be more than 100 or less than 0";

            courseWiseResult.setObtainedMarks(dto.getObtainedMarks());
            courseWiseResult.setGpa(calculateGpa(dto.getObtainedMarks()));
            courseWiseResult.setGrade(calculateGrade(dto.getObtainedMarks()).getLabel());

            courseWiseResultList.add(courseWiseResult);
        }
        courseWiseResultRepository.saveAll(courseWiseResultList);

        return SUCCESS;
    }

    private Float calculateGpa(Float obtainedMarks) {
        if (obtainedMarks <= 100 && obtainedMarks >= 79.5)
            return 4.0F;
        else if (obtainedMarks <= 79.4 && obtainedMarks >= 74.5)
            return 3.75F;
        else if (obtainedMarks <= 74.4 && obtainedMarks >= 69.5)
            return 3.5F;
        else if (obtainedMarks <= 69.4 && obtainedMarks >= 64.5)
            return 3.25F;
        else if (obtainedMarks <= 64.4 && obtainedMarks >= 59.5)
            return 3.0F;
        else if (obtainedMarks <= 59.4 && obtainedMarks >= 54.5)
            return 2.75F;
        else if (obtainedMarks <= 54.4 && obtainedMarks >= 49.5)
            return 2.5F;
        else if (obtainedMarks <= 49.4 && obtainedMarks >= 44.5)
            return 2.25F;
        else if (obtainedMarks <= 44.4 && obtainedMarks >= 39.5)
            return 2.0F;
        else
            return 0F;
    }

    private Grade calculateGrade(Float obtainedMarks) {
        if (obtainedMarks <= 100 && obtainedMarks >= 79.5)
            return A_PLUS;
        else if (obtainedMarks <= 79.4 && obtainedMarks >= 74.5)
            return A;
        else if (obtainedMarks <= 74.4 && obtainedMarks >= 69.5)
            return A_MINUS;
        else if (obtainedMarks <= 69.4 && obtainedMarks >= 64.5)
            return B_PLUS;
        else if (obtainedMarks <= 64.4 && obtainedMarks >= 59.5)
            return B;
        else if (obtainedMarks <= 59.4 && obtainedMarks >= 54.5)
            return B_MINUS;
        else if (obtainedMarks <= 54.4 && obtainedMarks >= 49.5)
            return C_PLUS;
        else if (obtainedMarks <= 49.4 && obtainedMarks >= 44.5)
            return C;
        else if (obtainedMarks <= 44.4 && obtainedMarks >= 39.5)
            return D;
        else
            return F;
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
