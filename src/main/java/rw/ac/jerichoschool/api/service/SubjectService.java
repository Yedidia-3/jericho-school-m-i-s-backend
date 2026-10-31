package rw.ac.jerichoschool.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.jerichoschool.api.domain.Subject;
import rw.ac.jerichoschool.api.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepo;

    public String saveSubject(Subject subject) {
        subjectRepo.save(subject);
        return "Subject saved successfully";
    }

    public List<Subject> findAllSubjects() {
        return subjectRepo.findAll();
    }

    public Optional<Subject> findSubjectById(UUID id) {
        return subjectRepo.findById(id);
    }

    public String updateSubject(UUID id, Subject newData) {
        Optional<Subject> existing = subjectRepo.findById(id);
        if (existing.isEmpty()) {
            return "The subject with that id does not exist";
        }
        Subject subject = existing.get();
        subject.setName(newData.getName());
        subjectRepo.save(subject);
        return "Subject updated successfully";
    }

    public String deleteSubject(UUID id) {
        Optional<Subject> existing = subjectRepo.findById(id);
        if (existing.isEmpty()) {
            return "The subject with that id does not exist";
        }
        subjectRepo.delete(existing.get());
        return "Subject deleted successfully";
    }
}
