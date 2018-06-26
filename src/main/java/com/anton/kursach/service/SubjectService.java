package com.anton.kursach.service;

import com.anton.kursach.exception.BadRequestException;
import com.anton.kursach.model.Subject;
import com.anton.kursach.model.UserProfile;
import com.anton.kursach.repository.ISubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.anton.kursach.common.Messages.EMPTY_FIELD;
import static com.anton.kursach.common.Messages.NOT_FOUND_SUBJECT;

@Service
@Slf4j
public class SubjectService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SubjectService.class);

    private final ISubjectRepository subjectRepository;

    private final UserService userService;

    @Autowired
    public SubjectService(ISubjectRepository subjectRepository, UserService userService) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
    }

    public Subject saveSubject(Subject subject) {
        validateSubjectRequest(subject);
        log.trace("[SubjectService] [saveSubject] []. ", subject);

        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfile currentUserProfile = userService.findUserByLogin(user);

        subject.setUserByUserId(currentUserProfile);
        return subjectRepository.save(subject);
    }

    private void validateSubjectRequest(Subject subject) {
        if (subject.getName().isEmpty()) {
            throw (new BadRequestException(EMPTY_FIELD));
        }
    }

    public List<Subject> getSubjectByUser() {
        log.debug("[UserService] [getSubjectByUser].");

        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfile currentUserProfile = userService.findUserByLogin(login);

        return currentUserProfile.getSubjects();
    }

    public Subject getSubjectById(Long id) {
        log.debug("[UserService] [getSubjectById].", id);

        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfile currentUserProfile = userService.findUserByLogin(user);

        Subject subject = subjectRepository.findFirstByIdAndUserProfileId(id, currentUserProfile.getId());

        if (subject == null) {
            throw new BadRequestException(NOT_FOUND_SUBJECT + id);
        }
        return subject;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Subject updateSubject(Subject subject) {
        log.trace("[SubjectService] [updateSubject] [].", subject);

        if (!subjectRepository.existsById(subject.getId())) {
            throw new BadRequestException(NOT_FOUND_SUBJECT + subject.getId());
        }

        validateSubjectRequest(subject);
        return subjectRepository.save(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSubjectById(Long id) {
        log.trace("[SubjectService] [deleteSubjectById].", id);
        subjectRepository.deleteById(id);
    }

}
