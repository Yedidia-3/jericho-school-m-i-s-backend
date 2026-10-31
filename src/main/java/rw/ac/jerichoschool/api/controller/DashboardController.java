package rw.ac.jerichoschool.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.ac.jerichoschool.api.service.DashboardService;

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardServ;

    @GetMapping(value = "/summary")
    public ResponseEntity<?> getSummary() {
        return new ResponseEntity<>(dashboardServ.getSummary(), HttpStatus.OK);
    }
}
