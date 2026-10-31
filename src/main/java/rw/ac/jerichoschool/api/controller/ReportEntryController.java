package rw.ac.jerichoschool.api.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.ac.jerichoschool.api.domain.ReportEntry;
import rw.ac.jerichoschool.api.service.ReportEntryService;

@RestController
@RequestMapping(value = "/api/report-entries")
public class ReportEntryController {

    @Autowired
    private ReportEntryService reportEntryServ;

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestParam UUID assessmentId, @RequestBody ReportEntry reportEntry) {
        String msg = reportEntryServ.saveReportEntry(assessmentId, reportEntry);
        if (msg.equals("Report entry saved successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } else if (msg.equals("The assessment with that id does not exist")) {
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/pupil/{admissionNumber}")
    public ResponseEntity<?> findByPupil(@PathVariable String admissionNumber) {
        return new ResponseEntity<>(reportEntryServ.findByPupil(admissionNumber), HttpStatus.OK);
    }
}
