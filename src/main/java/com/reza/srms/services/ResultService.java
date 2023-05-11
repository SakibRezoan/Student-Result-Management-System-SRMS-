package com.reza.srms.services;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.reza.srms.dtos.CourseWiseResultImportDto;
import com.reza.srms.entities.Course;
import com.reza.srms.entities.CourseWiseResult;
import com.reza.srms.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.reza.srms.constant.ResponseStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class ResultService {

    public String uploadResult(List<Student> studentList, Course course, MultipartFile resultFile) {
        List<CourseWiseResultImportDto> resultImportDtoList;
        try {
            resultImportDtoList = fetchResultItems(resultFile);
        } catch (IOException e) {
            return e.getMessage();
        }
        for (CourseWiseResultImportDto dto : resultImportDtoList) {
            CourseWiseResult courseWiseResult = new CourseWiseResult();
            courseWiseResult.setCourse(course);
            courseWiseResult.setStudent(studentList.stream().filter(student -> student.getRoll().equals(dto.getRoll())).findFirst().get());
            courseWiseResult.setObtainedMarks(dto.getObtainedMarks());
//            courseWiseResult.setGpa(calculateGpa(dto.getObtainedMarks()));
//            courseWiseResult.setGrade(calculateGrade(dto.getObtainedMarks()));
        }

        return SUCCESS;
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
