package rw.ac.jerichoschool.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.jerichoschool.api.domain.Classroom;
import rw.ac.jerichoschool.api.repository.ClassroomRepository;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepo;

    public String saveClassroom(Classroom classroom) {
        classroomRepo.save(classroom);
        return "Classroom saved successfully";
    }

    public List<Classroom> findAllClassrooms() {
        return classroomRepo.findAll();
    }

    public Optional<Classroom> findClassroomById(UUID id) {
        return classroomRepo.findById(id);
    }

    public String updateClassroom(UUID id, Classroom newData) {
        Optional<Classroom> existing = classroomRepo.findById(id);
        if (existing.isEmpty()) {
            return "The classroom with that id does not exist";
        }
        Classroom classroom = existing.get();
        classroom.setRoomNumber(newData.getRoomNumber());
        classroom.setName(newData.getName());
        classroomRepo.save(classroom);
        return "Classroom updated successfully";
    }

    public String deleteClassroom(UUID id) {
        Optional<Classroom> existing = classroomRepo.findById(id);
        if (existing.isEmpty()) {
            return "The classroom with that id does not exist";
        }
        classroomRepo.delete(existing.get());
        return "Classroom deleted successfully";
    }
}
