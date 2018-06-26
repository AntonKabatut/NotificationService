package com.anton.kursach.repository;

import com.anton.kursach.model.Subject;
import org.springframework.data.repository.CrudRepository;

public interface ISubjectRepository extends CrudRepository<Subject, Long> {

    void deleteById(Long id);

    Subject findFirstByIdAndUserProfileId(Long id, Long userProfileId);

}
