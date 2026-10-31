package rw.ac.jerichoschool.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
}
