package rw.ac.jerichoschool.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("SELECT DISTINCT t FROM Teacher t JOIN t.subjects s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Teacher> findBySubjectName(@Param("name") String name);
}
