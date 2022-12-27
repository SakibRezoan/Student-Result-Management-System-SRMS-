package com.reza.student_result.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reza.student_result.entities.Enclosure;
import com.reza.student_result.entities.Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.utils.FileUpload;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class StudentHelper extends FileUpload {

    @Resource
    private Environment env;

    public List<Enclosure> getStudentEnclosures(
            MultipartFile file,
            String request,
            Student student) throws JsonProcessingException {

        List<Enclosure> enclosures = new ArrayList<>();
        if (student.getEnclosures() != null) {
            if (!student.getEnclosures().isEmpty()) {
                List<Enclosure> lists = new ArrayList<>(student.getEnclosures());
                enclosures.addAll(lists);
            }
        }

        //Enclosure enclosure = new ObjectMapper().readValue(request, Enclosure.class);
        Enclosure enclosure = new Enclosure();
        enclosure.setName(request);

        enclosures.add(enclosure);

        if (nonNull(file)) {
            setEnclosureFile(enclosure, file);
        }
        return enclosures;
    }
    public void setEnclosureFile(
            Enclosure enclosure,
            MultipartFile file) {
        enclosure.setUrl(upload(file, env.getProperty("ftpFileUploadPath")));
    }

    public void setBaseData(Student student, RecordStatus status, Boolean forUpdate) {
        student.setRecordStatus(status);
    }

}
