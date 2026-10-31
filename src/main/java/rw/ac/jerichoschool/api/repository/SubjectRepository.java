package rw.ac.jerichoschool.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    @Query("SELECT s FROM Subject s WHERE s.teachers IS EMPTY")
    List<Subject> findUnusedSubjects();
}
