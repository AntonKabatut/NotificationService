package com.anton.kursach.api.controller;

import com.anton.kursach.model.Subject;
import com.anton.kursach.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Subject> saveSubject(@RequestBody Subject subject) {
        return new ResponseEntity<>(subjectService.saveSubject(subject), HttpStatus.OK);
    }

    @RequestMapping(value = "/subject", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Subject>> getSubject() {
        return new ResponseEntity<>(subjectService.getSubjectByUser(), HttpStatus.OK);
    }

    @RequestMapping(value = "/subject", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Subject> updateSubject(@RequestBody Subject subject) {
        return new ResponseEntity<>(subjectService.updateSubject(subject),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/subject/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubjectById(id);
        return new ResponseEntity<>("Subject deleted", HttpStatus.OK);
    }

    @RequestMapping(value = "/subject/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        return new ResponseEntity<>(subjectService.getSubjectById(id), HttpStatus.OK);
    }

}
