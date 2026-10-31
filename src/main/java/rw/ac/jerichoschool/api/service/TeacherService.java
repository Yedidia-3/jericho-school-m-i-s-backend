package rw.ac.jerichoschool.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.jerichoschool.api.domain.Classroom;
import rw.ac.jerichoschool.api.domain.Subject;
import rw.ac.jerichoschool.api.domain.Teacher;
import rw.ac.jerichoschool.api.repository.ClassroomRepository;
import rw.ac.jerichoschool.api.repository.SubjectRepository;
import rw.ac.jerichoschool.api.repository.TeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private ClassroomRepository classroomRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    public String saveTeacher(Teacher teacher) {
        Teacher toSave = new Teacher();
        String error = copyTeacher(toSave, teacher);
        if (error != null) {
            return error;
        }
        teacherRepo.save(toSave);
        return "Teacher saved successfully";
    }

    public List<Teacher> findAllTeachers() {
        return teacherRepo.findAll();
    }

    public Optional<Teacher> findTeacherById(UUID id) {
        return teacherRepo.findById(id);
    }

    public List<Teacher> findBySubjectName(String name) {
        return teacherRepo.findBySubjectName(name);
    }

    public String updateTeacher(UUID id, Teacher newData) {
        Optional<Teacher> existing = teacherRepo.findById(id);
        if (existing.isEmpty()) {
            return "The teacher with that id does not exist";
        }
        String error = copyTeacher(existing.get(), newData);
        if (error != null) {
            return error;
        }
        teacherRepo.save(existing.get());
        return "Teacher updated successfully";
    }

    public String deleteTeacher(UUID id) {
        Optional<Teacher> existing = teacherRepo.findById(id);
        if (existing.isEmpty()) {
            return "The teacher with that id does not exist";
        }
        teacherRepo.delete(existing.get());
        return "Teacher deleted successfully";
    }

    public String addSubject(UUID teacherId, UUID subjectId) {
        Optional<Teacher> teacher = teacherRepo.findById(teacherId);
        if (teacher.isEmpty()) {
            return "The teacher with that id does not exist";
        }
        Optional<Subject> subject = subjectRepo.findById(subjectId);
        if (subject.isEmpty()) {
            return "That subject does not exist";
        }
        if (teacher.get().getSubjects().contains(subject.get())) {
            return "The teacher already has that subject";
        }
        teacher.get().getSubjects().add(subject.get());
        teacherRepo.save(teacher.get());
        return "Subject added to the teacher";
    }

    public String changeClassroom(UUID teacherId, int roomNumber) {
        Optional<Teacher> teacher = teacherRepo.findById(teacherId);
        if (teacher.isEmpty()) {
            return "The teacher with that id does not exist";
        }
        Classroom classroom = classroomRepo.findByRoomNumber(roomNumber);
        if (classroom == null) {
            return "The classroom with that roomNumber does not exist";
        }
        if (teacherRepo.existsByClassroom(classroom)) {
            return "That classroom is taken by another teacher";
        }
        teacher.get().setClassroom(classroom);
        teacherRepo.save(teacher.get());
        return "Classroom changed successfully";
    }

    private String copyTeacher(Teacher target, Teacher newData) {
        if (newData.getClassroom() != null && newData.getClassroom().getId() != null) {
            Optional<Classroom> classroom = classroomRepo.findById(newData.getClassroom().getId());
            if (classroom.isEmpty()) {
                return "The classroom with that id does not exist";
            }
            target.setClassroom(classroom.get());
        } else {
            target.setClassroom(null);
        }

        List<Subject> subjects = new ArrayList<>();
        if (newData.getSubjects() != null) {
            for (Subject subject : newData.getSubjects()) {
                if (subject.getId() == null) {
                    return "The subject with that id does not exist";
                }
                Optional<Subject> found = subjectRepo.findById(subject.getId());
                if (found.isEmpty()) {
                    return "The subject with that id does not exist";
                }
                subjects.add(found.get());
            }
        }

        target.setFirstName(newData.getFirstName());
        target.setLastName(newData.getLastName());
        target.setEmail(newData.getEmail());
        target.setSubjects(subjects);
        return null;
    }
}
