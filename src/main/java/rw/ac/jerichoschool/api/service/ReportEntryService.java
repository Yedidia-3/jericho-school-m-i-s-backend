package rw.ac.jerichoschool.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.jerichoschool.api.domain.Assessment;
import rw.ac.jerichoschool.api.domain.AssessmentStatus;
import rw.ac.jerichoschool.api.domain.ReportEntry;
import rw.ac.jerichoschool.api.repository.AssessmentRepository;
import rw.ac.jerichoschool.api.repository.ReportEntryRepository;

@Service
public class ReportEntryService {

    @Autowired
    private ReportEntryRepository reportEntryRepo;

    @Autowired
    private AssessmentRepository assessmentRepo;

    public String saveReportEntry(UUID assessmentId, ReportEntry reportEntry) {
        Optional<Assessment> assessment = assessmentRepo.findById(assessmentId);
        if (assessment.isEmpty()) {
            return "The assessment with that id does not exist";
        }
        if (assessment.get().getStatus() != AssessmentStatus.GRADED) {
            return "Report entries are only for GRADED assessments";
        }
        reportEntry.setAssessment(assessment.get());
        reportEntry.setEnteredDate(LocalDate.now());
        reportEntryRepo.save(reportEntry);
        return "Report entry saved successfully";
    }

    public List<ReportEntry> findByPupil(String admissionNumber) {
        return reportEntryRepo.findByPupilAdmissionNumber(admissionNumber);
    }

    public String deleteReportEntry(UUID id) {
        Optional<ReportEntry> existing = reportEntryRepo.findById(id);
        if (existing.isEmpty()) {
            return "The report entry with that id does not exist";
        }
        reportEntryRepo.delete(existing.get());
        return "Report entry deleted successfully";
    }
}
