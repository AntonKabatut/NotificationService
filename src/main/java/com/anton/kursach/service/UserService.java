package com.anton.kursach.service;

import com.anton.kursach.exception.BadRequestException;
import com.anton.kursach.model.UserProfile;
import com.anton.kursach.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.anton.kursach.common.Messages.*;

@Service
@Slf4j
public class UserService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    private final IUserRepository iUserRepository;

    @Autowired
    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public void insertUser(UserProfile userProfile) {
        validateUserRequest(userProfile);
        log.trace("[UserService] [insertUser] []. ", userProfile);

        if (iUserRepository.existsByLogin(userProfile.getLogin())) {
            throw new BadRequestException(USER_ALREADY_EXISTS + userProfile.getLogin());
        }

        iUserRepository.save(userProfile);
    }

    public List<UserProfile> getAllUsers() {
        log.debug("[UserService] [getAllUsers].");
        return (List<UserProfile>) iUserRepository.findAll();
    }

    UserProfile findUserByLogin(String login) {
        log.debug("[UserService] [findUserByLogin] [].", login);
        UserProfile user = iUserRepository.findUserByLogin(login);

        if (user == null) {
            throw new BadRequestException(NOT_FOUND_USER + login);
        }

        return user;
    }

    private void validateUserRequest(UserProfile user) {
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            throw new BadRequestException(EMPTY_FIELD);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserProfile updateUser(UserProfile user) {

        if (!iUserRepository.existsById(user.getId())) {
            throw new BadRequestException(NOT_FOUND_USER_ID + user.getId());
        }

        validateUserRequest(user);
        log.trace("[UserService] [updateUser] [].", user);

        return iUserRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserById(Long id) {
        log.trace("[UserService] [deleteUserById].", id);
        iUserRepository.deleteById(id);
    }

    UserProfile getUserByLabId(Long labId) {
        log.debug("[UserService] [getUserByLabId] [].", labId);
        return iUserRepository.getUserProfileByLabId(labId);
    }

}
