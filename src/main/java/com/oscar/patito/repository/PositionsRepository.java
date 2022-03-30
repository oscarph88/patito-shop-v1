package com.oscar.patito.repository;

import com.oscar.patito.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionsRepository extends JpaRepository<Position, Integer> {
}
