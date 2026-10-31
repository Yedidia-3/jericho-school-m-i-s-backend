package rw.ac.jerichoschool.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.ReportEntry;

@Repository
public interface ReportEntryRepository extends JpaRepository<ReportEntry, UUID> {
}
