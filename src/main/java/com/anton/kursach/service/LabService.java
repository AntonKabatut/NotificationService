package com.anton.kursach.service;

import com.anton.kursach.api.model.dto.LabResponse;
import com.anton.kursach.api.model.request.LabInsertRequest;
import com.anton.kursach.exception.BadRequestException;
import com.anton.kursach.model.Lab;
import com.anton.kursach.model.Subject;
import com.anton.kursach.repository.ILabRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.anton.kursach.common.Messages.*;
import static com.anton.kursach.util.DateMapping.formatDateWithoutTime;

@Service
@Slf4j
public class LabService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LabService.class);

    private final ILabRepository iLabRepository;

    private final SubjectService subjectService;

    private final DozerBeanMapper mapper;

    @Autowired
    public LabService(ILabRepository iLabRepository, SubjectService subjectService, DozerBeanMapper mapper) {
        this.iLabRepository = iLabRepository;
        this.subjectService = subjectService;
        this.mapper = mapper;
    }

    public LabResponse insertLab(LabInsertRequest request) {
        log.trace("[LabService] [insertLab] []. ", request);
        Subject subject = subjectService.getSubjectById(request.getSubjId());

        if (subject == null) {
            throw new BadRequestException(NOT_FOUND_SUBJECT + request.getSubjId());
        }

        Lab lab = mapper.map(request, Lab.class);
        validateLabRequest(lab);
        lab.setSubject(subject);
        lab.setDays(formatDateWithoutTime(lab.getDays()));
        lab.setDeadline(formatDateWithoutTime(lab.getDeadline()));

        return mapper.map(iLabRepository.save(lab), LabResponse.class);
    }

    public List<LabResponse> insertListOfLab(List<LabInsertRequest> labsRequest) {
        log.trace("[LabService] [insertLab] []. ", labsRequest);
        List<Lab> labs = new ArrayList<>();
        List<LabResponse> labResponse = new ArrayList<>();
        Subject subject = subjectService.getSubjectById(labsRequest.get(0).getSubjId());

        if (subject == null) {
            throw new BadRequestException(NOT_FOUND_SUBJECT + labsRequest.get(0).getSubjId());
        }

        labsRequest.forEach((lab) -> labs.add(mapper.map(lab, Lab.class)));

        labs.forEach((lab) -> {
            validateLabRequest(lab);
            lab.setSubject(subject);
            lab.setDays(formatDateWithoutTime(lab.getDays()));
        });

        List<Lab> savedLabs = (List<Lab>) iLabRepository.saveAll(labs);
        savedLabs.forEach((lab) -> labResponse.add(mapper.map(lab, LabResponse.class)));

        return labResponse;
    }

    public List<LabResponse> getLabsBySubjectId(Long id) {
        log.debug("[LabService] [getLabBySubjectId].");
        Subject subject = subjectService.getSubjectById(id);

        if (subject == null) {
            throw new BadRequestException(NOT_FOUND_SUBJECT + id);
        }

        List<LabResponse> labResponse = new ArrayList<>();
        subject.getLabs().forEach((lab) -> labResponse.add(mapper.map(lab, LabResponse.class)));

        return labResponse;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LabResponse updateLab(Lab lab) {
        log.trace("[LabService] [updateLab] [].", lab);

        if (!iLabRepository.existsById(lab.getId())) {
            throw new BadRequestException(NOT_FOUND_LAB + lab.getId());
        }

        validateLabRequest(lab);
        return mapper.map(iLabRepository.save(lab), LabResponse.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteLabById(Long id) {
        log.trace("[LabService] [deleteLabById] [].", id);
        iLabRepository.deleteById(id);
    }

    private void validateLabRequest(Lab lab) {
        if (lab.getName().isEmpty()) {
            throw new BadRequestException(EMPTY_FIELD);
        }
    }

    List<Lab> getLabsNeededNotify(Long currentDate) {
        log.debug("[LabService] [getLabsNeededNotify].", currentDate);
        return iLabRepository.getLabsByDaysAndNeedAndNotified(currentDate);
    }

}
