package com.oscar.patito.repository;

import com.oscar.patito.model.PositionInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionInfoHistoryRepository extends JpaRepository<PositionInfoHistory, String> {
    @Query(name = "History.findCurrentHistory")
    PositionInfoHistory findCurrentHistory(@Param("current") Boolean c, @Param("email") String e);
    @Query(name = "History.active.findCurrentHistory")
    List<PositionInfoHistory> findActiveCurrentHistory(@Param("email") String e);
}
