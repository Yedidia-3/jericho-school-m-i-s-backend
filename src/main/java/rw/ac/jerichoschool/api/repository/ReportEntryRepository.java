package rw.ac.jerichoschool.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.Assessment;
import rw.ac.jerichoschool.api.domain.ReportEntry;

@Repository
public interface ReportEntryRepository extends JpaRepository<ReportEntry, UUID> {

    @Query("SELECT d FROM ReportEntry d WHERE d.assessment.pupil.admissionNumber = :admissionNumber ORDER BY d.enteredDate DESC")
    List<ReportEntry> findByPupilAdmissionNumber(@Param("admissionNumber") String admissionNumber);

    boolean existsByAssessment(Assessment assessment);
}
