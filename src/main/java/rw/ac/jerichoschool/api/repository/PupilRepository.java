package rw.ac.jerichoschool.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rw.ac.jerichoschool.api.domain.Pupil;

@Repository
public interface PupilRepository extends JpaRepository<Pupil, UUID> {

    List<Pupil> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
