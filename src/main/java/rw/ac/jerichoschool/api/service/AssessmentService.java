package rw.ac.jerichoschool.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.jerichoschool.api.domain.Assessment;
import rw.ac.jerichoschool.api.domain.AssessmentStatus;
import rw.ac.jerichoschool.api.domain.Pupil;
import rw.ac.jerichoschool.api.domain.Teacher;
import rw.ac.jerichoschool.api.repository.AssessmentRepository;
import rw.ac.jerichoschool.api.repository.PupilRepository;
import rw.ac.jerichoschool.api.repository.ReportEntryRepository;
import rw.ac.jerichoschool.api.repository.TeacherRepository;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepo;

    @Autowired
    private ReportEntryRepository reportEntryRepo;

    @Autowired
    private PupilRepository pupilRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    public String saveAssessment(Assessment assessment) {
        if (assessment.getAssessmentDate() != null
                && assessment.getAssessmentDate().isBefore(LocalDate.now())) {
            return "Assessment date cannot be in the past";
        }
        Assessment toSave = new Assessment();
        String error = copyAssessment(toSave, assessment);
        if (error != null) {
            return error;
        }
        boolean alreadyBooked = assessmentRepo.existsByTeacherAndAssessmentDateAndStatusNot(
                toSave.getTeacher(), toSave.getAssessmentDate(), AssessmentStatus.ABSENT);
        if (alreadyBooked) {
            return "Teacher already has an assessment on that date";
        }
        toSave.setStatus(AssessmentStatus.PLANNED);
        assessmentRepo.save(toSave);
        return "Assessment saved successfully";
    }

    public List<Assessment> findAllAssessments() {
        return assessmentRepo.findAll();
    }

    public Optional<Assessment> findAssessmentById(UUID id) {
        return assessmentRepo.findById(id);
    }

    public List<Assessment> findByStatus(AssessmentStatus status) {
        return assessmentRepo.findByStatusOrderByAssessmentDateAsc(status);
    }

    public List<Assessment> findBetween(LocalDate start, LocalDate end) {
        return assessmentRepo.findByAssessmentDateBetweenOrderByAssessmentDateAsc(start, end);
    }

    public List<Object[]> countByStatus() {
        return assessmentRepo.countByStatus();
    }

    @Transactional
    public String cancelDay(UUID teacherId, LocalDate date) {
        int cancelled = assessmentRepo.cancelByTeacherAndDate(
                teacherId, date, AssessmentStatus.ABSENT, AssessmentStatus.GRADED);
        return cancelled + " assessments cancelled";
    }

    public String updateAssessment(UUID id, Assessment newData) {
        Optional<Assessment> existing = assessmentRepo.findById(id);
        if (existing.isEmpty()) {
            return "The assessment with that id does not exist";
        }
        String error = copyAssessment(existing.get(), newData);
        if (error != null) {
            return error;
        }
        assessmentRepo.save(existing.get());
        return "Assessment updated successfully";
    }

    public String deleteAssessment(UUID id) {
        Optional<Assessment> existing = assessmentRepo.findById(id);
        if (existing.isEmpty()) {
            return "The assessment with that id does not exist";
        }
        if (reportEntryRepo.existsByAssessment(existing.get())) {
            return "That assessment has report entries, delete them first";
        }
        assessmentRepo.delete(existing.get());
        return "Assessment deleted successfully";
    }

    public String changeStatus(UUID id, AssessmentStatus newStatus) {
        Optional<Assessment> existing = assessmentRepo.findById(id);
        if (existing.isEmpty()) {
            return "The assessment with that id does not exist";
        }
        Assessment assessment = existing.get();
        AssessmentStatus current = assessment.getStatus();

        boolean allowed = false;
        if (current == AssessmentStatus.PLANNED) {
            allowed = newStatus == AssessmentStatus.SAT || newStatus == AssessmentStatus.ABSENT;
        } else if (current == AssessmentStatus.SAT) {
            allowed = newStatus == AssessmentStatus.GRADED || newStatus == AssessmentStatus.ABSENT;
        }

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        assessment.setStatus(newStatus);
        assessmentRepo.save(assessment);
        return "Assessment status updated successfully";
    }

    private String copyAssessment(Assessment target, Assessment newData) {
        if (newData.getPupil() == null || newData.getPupil().getId() == null) {
            return "The pupil with that id does not exist";
        }
        Optional<Pupil> pupil = pupilRepo.findById(newData.getPupil().getId());
        if (pupil.isEmpty()) {
            return "The pupil with that id does not exist";
        }

        if (newData.getTeacher() == null || newData.getTeacher().getId() == null) {
            return "The teacher with that id does not exist";
        }
        Optional<Teacher> teacher = teacherRepo.findById(newData.getTeacher().getId());
        if (teacher.isEmpty()) {
            return "The teacher with that id does not exist";
        }

        target.setAssessmentDate(newData.getAssessmentDate());
        target.setTerm(newData.getTerm());
        target.setMaxMarks(newData.getMaxMarks());
        target.setPupil(pupil.get());
        target.setTeacher(teacher.get());
        return null;
    }
}
