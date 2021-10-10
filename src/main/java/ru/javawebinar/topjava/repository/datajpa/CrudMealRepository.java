package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal findByIdAndUser_id(int id, int userId);

    @Modifying
    int deleteByIdAndUser_id(int id, int userId);

    List<Meal> findAllByUser_idOrderByDateTimeDesc(int userId);

    @Modifying
    @Query("SELECT m FROM Meal m \n" +
            "WHERE m.user.id=:userId AND m.dateTime >= :start AND m.dateTime < :end ORDER BY m.dateTime DESC")
    List<Meal> getBetweenDates(@Param("start") LocalDateTime startDateTime,
                               @Param("end") LocalDateTime endDateTime,
                               @Param("userId") int userId);


}
