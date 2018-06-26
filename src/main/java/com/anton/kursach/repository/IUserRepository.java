package com.anton.kursach.repository;

import com.anton.kursach.model.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<UserProfile, Long> {

    UserProfile findUserByLogin(String login);

    void deleteById(Long id);

    @Query(value = "SELECT * FROM users WHERE id IN (SELECT user_id FROM subjects WHERE id IN (SELECT subject_id FROM labs WHERE id=?1))",
            nativeQuery = true)
    UserProfile getUserProfileByLabId(Long labId);

    boolean existsByLogin(String login);

}
