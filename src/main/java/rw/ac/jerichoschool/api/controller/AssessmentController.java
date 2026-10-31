package rw.ac.jerichoschool.api.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.ac.jerichoschool.api.domain.Assessment;
import rw.ac.jerichoschool.api.domain.AssessmentStatus;
import rw.ac.jerichoschool.api.service.AssessmentService;

@RestController
@RequestMapping(value = "/api/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentServ;

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestBody Assessment assessment) {
        String msg = assessmentServ.saveAssessment(assessment);
        if (msg.equals("Assessment saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } else if (msg.equals("Teacher already has an assessment on that date")) {
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        } else if (msg.equals("Assessment date cannot be in the past")) {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(assessmentServ.findAllAssessments(), HttpStatus.OK);
    }

    @GetMapping(value = "/by-status")
    public ResponseEntity<?> findByStatus(@RequestParam AssessmentStatus status) {
        return new ResponseEntity<>(assessmentServ.findByStatus(status), HttpStatus.OK);
    }

    @GetMapping(value = "/between")
    public ResponseEntity<?> findBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(assessmentServ.findBetween(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Assessment> assessment = assessmentServ.findAssessmentById(id);
        if (assessment.isPresent()) {
            return new ResponseEntity<>(assessment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The assessment with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Assessment assessment) {
        String msg = assessmentServ.updateAssessment(id, assessment);
        if (msg.equals("Assessment updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = assessmentServ.deleteAssessment(id);
        if (msg.equals("Assessment deleted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam AssessmentStatus status) {
        String msg = assessmentServ.changeStatus(id, status);
        if (msg.equals("Assessment status updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else if (msg.equals("The assessment with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }
}
