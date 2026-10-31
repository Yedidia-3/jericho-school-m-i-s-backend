package rw.ac.jerichoschool.api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.Assessment;
import rw.ac.jerichoschool.api.domain.AssessmentStatus;
import rw.ac.jerichoschool.api.domain.Teacher;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {

    List<Assessment> findByStatusOrderByAssessmentDateAsc(AssessmentStatus status);

    List<Assessment> findByAssessmentDateBetweenOrderByAssessmentDateAsc(LocalDate start, LocalDate end);

    boolean existsByTeacherAndAssessmentDateAndStatusNot(Teacher teacher, LocalDate assessmentDate,
            AssessmentStatus status);
}
