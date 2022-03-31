package com.oscar.patito.repository;

import com.oscar.patito.model.PositionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionInfoRepository extends JpaRepository<PositionInfo, String> {
    @Query(name = "PositionInfo.findActivePositions")
    List<PositionInfo> findAllActive(@Param("active") Boolean s);
}
