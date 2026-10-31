package rw.ac.jerichoschool.api.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.ac.jerichoschool.api.domain.Pupil;
import rw.ac.jerichoschool.api.service.PupilService;

@RestController
@RequestMapping(value = "/api/pupils")
public class PupilController {

    @Autowired
    private PupilService pupilServ;

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestBody Pupil pupil) {
        String msg = pupilServ.savePupil(pupil);
        if (msg.equals("Pupil saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(pupilServ.findAllPupils(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-last-name")
    public ResponseEntity<?> findByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(pupilServ.findByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Pupil> pupil = pupilServ.findPupilById(id);
        if (pupil.isPresent()) {
            return new ResponseEntity<>(pupil.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The pupil with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Pupil pupil) {
        String msg = pupilServ.updatePupil(id, pupil);
        if (msg.equals("Pupil updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = pupilServ.deletePupil(id);
        if (msg.equals("Pupil deleted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }
}
