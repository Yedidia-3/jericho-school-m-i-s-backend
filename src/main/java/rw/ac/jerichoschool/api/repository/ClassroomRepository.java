package rw.ac.jerichoschool.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {

    @Query("SELECT c FROM Classroom c WHERE NOT EXISTS (SELECT t FROM Teacher t WHERE t.classroom = c) ORDER BY c.roomNumber ASC")
    List<Classroom> findFreeClassrooms();
}
