package com.anton.kursach.repository;

import com.anton.kursach.model.Lab;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ILabRepository extends CrudRepository<Lab, Long> {

    @Query(value = "SELECT * FROM labs WHERE id = ?2 AND subject_id IN (SELECT id FROM subjects WHERE user_id = ?1) LIMIT 1", nativeQuery = true)
    Lab getFirstById(Long userId, Long labId);

    @Query(value = "SELECT * FROM labs WHERE day_of_notify=?1 AND need_notify='1' AND is_notified='0'", nativeQuery = true)
    List<Lab> getLabsByDaysAndNeedAndNotified(Long days);

}
