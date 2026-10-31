package rw.ac.jerichoschool.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.jerichoschool.api.domain.Pupil;
import rw.ac.jerichoschool.api.repository.PupilRepository;

@Service
public class PupilService {

    @Autowired
    private PupilRepository pupilRepo;

    public String savePupil(Pupil pupil) {
        pupilRepo.save(pupil);
        return "Pupil saved successfully";
    }

    public List<Pupil> findAllPupils() {
        return pupilRepo.findAll();
    }

    public Optional<Pupil> findPupilById(UUID id) {
        return pupilRepo.findById(id);
    }

    public String updatePupil(UUID id, Pupil newData) {
        Optional<Pupil> existing = pupilRepo.findById(id);
        if (existing.isEmpty()) {
            return "The pupil with that id does not exist";
        }
        Pupil pupil = existing.get();
        pupil.setFirstName(newData.getFirstName());
        pupil.setLastName(newData.getLastName());
        pupil.setAdmissionNumber(newData.getAdmissionNumber());
        pupil.setGuardianPhone(newData.getGuardianPhone());
        pupil.setGradeLevel(newData.getGradeLevel());
        pupilRepo.save(pupil);
        return "Pupil updated successfully";
    }

    public String deletePupil(UUID id) {
        Optional<Pupil> existing = pupilRepo.findById(id);
        if (existing.isEmpty()) {
            return "The pupil with that id does not exist";
        }
        pupilRepo.delete(existing.get());
        return "Pupil deleted successfully";
    }
}
