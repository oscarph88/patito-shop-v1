package com.oscar.patito.repository;

import com.oscar.patito.model.PositionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionInfoRepository extends JpaRepository<PositionInfo, String> {
}
