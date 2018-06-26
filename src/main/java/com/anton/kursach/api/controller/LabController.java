package com.anton.kursach.api.controller;

import com.anton.kursach.api.model.dto.LabResponse;
import com.anton.kursach.api.model.request.LabInsertRequest;
import com.anton.kursach.model.Lab;
import com.anton.kursach.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LabController {

    private final LabService labService;

    @Autowired
    public LabController(LabService labService) {
        this.labService = labService;
    }

    @RequestMapping(value = "/lab", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LabResponse> saveLab(@RequestBody LabInsertRequest request) {
        return new ResponseEntity<>(labService.insertLab(request), HttpStatus.OK);
    }

    @RequestMapping(value = "/lab/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<LabResponse>> saveLabList(@RequestBody List<LabInsertRequest> requestList) {
        return new ResponseEntity<>(labService.insertListOfLab(requestList), HttpStatus.OK);
    }

    @RequestMapping(value = "/lab/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<LabResponse>> getLabBySubjectId(@PathVariable Long id) {
        return new ResponseEntity<>(labService.getLabsBySubjectId(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/lab", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LabResponse> updateLab(@RequestBody Lab lab) {
        return new ResponseEntity<>(labService.updateLab(lab), HttpStatus.OK);
    }

    @RequestMapping(value = "/lab/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteLabById(@PathVariable Long id) {
        labService.deleteLabById(id);
        return new ResponseEntity<>("Lab deleted", HttpStatus.OK);
    }

}
