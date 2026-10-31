package rw.ac.jerichoschool.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}
