package rw.ac.jerichoschool.api.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.jerichoschool.api.domain.AssessmentStatus;
import rw.ac.jerichoschool.api.repository.AssessmentRepository;
import rw.ac.jerichoschool.api.repository.ClassroomRepository;
import rw.ac.jerichoschool.api.repository.PupilRepository;
import rw.ac.jerichoschool.api.repository.ReportEntryRepository;
import rw.ac.jerichoschool.api.repository.TeacherRepository;

@Service
public class DashboardService {

    @Autowired
    private PupilRepository pupilRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private ClassroomRepository classroomRepo;

    @Autowired
    private AssessmentRepository assessmentRepo;

    @Autowired
    private ReportEntryRepository reportEntryRepo;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("pupils", pupilRepo.count());
        summary.put("teachers", teacherRepo.count());
        summary.put("freeClassrooms", (long) classroomRepo.findFreeClassrooms().size());
        summary.put("assessmentsToday", assessmentRepo.countByAssessmentDate(LocalDate.now()));
        summary.put("planned", assessmentRepo.countByStatus(AssessmentStatus.PLANNED));
        summary.put("reportEntries", reportEntryRepo.count());
        return summary;
    }
}
