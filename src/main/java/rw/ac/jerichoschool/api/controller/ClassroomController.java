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
import org.springframework.web.bind.annotation.RestController;

import rw.ac.jerichoschool.api.domain.Classroom;
import rw.ac.jerichoschool.api.service.ClassroomService;

@RestController
@RequestMapping(value = "/api/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomServ;

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestBody Classroom classroom) {
        String msg = classroomServ.saveClassroom(classroom);
        if (msg.equals("Classroom saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(classroomServ.findAllClassrooms(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Classroom> classroom = classroomServ.findClassroomById(id);
        if (classroom.isPresent()) {
            return new ResponseEntity<>(classroom.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The classroom with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Classroom classroom) {
        String msg = classroomServ.updateClassroom(id, classroom);
        if (msg.equals("Classroom updated successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String msg = classroomServ.deleteClassroom(id);
        if (msg.equals("Classroom deleted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }
}
