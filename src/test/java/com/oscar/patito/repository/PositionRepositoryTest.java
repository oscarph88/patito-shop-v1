package com.oscar.patito.repository;

import com.oscar.patito.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class PositionRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    PositionsRepository positionsRepository;

    @Test
    public void savePositionTest() {
        Position pos = new Position(1,"Uno", "dos");
        Position pos2 = positionsRepository.save(pos);
        assertThat(pos.getId(), is(equalTo(pos2.getId())));
    }
}
