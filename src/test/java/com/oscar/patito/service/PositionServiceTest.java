package com.oscar.patito.service;

import com.oscar.patito.PatitoApplication;
import com.oscar.patito.dto.PositionDTO;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PatitoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PositionServiceTest {

    @MockBean
    private PositionService positionService;

    @BeforeAll
    void setMockOutput() {
        PositionDTO pos = new PositionDTO(1, "name", "description");
        List<PositionDTO> posList = new ArrayList<>();
        posList.add(pos);
        posList.add(getPosition());
        System.out.println("Loading data");
        System.out.println(positionService.savePositions(posList));
    }

    @Test
    public void listPositions() throws Exception{
        PositionDTO pos = getPosition();
        List<PositionDTO> positions = new ArrayList<>();
        positions.add(pos);
        List<PositionDTO> positions2 = positionService.listPositions();
        assertEquals(positions2.size(), 0);
    }


    private PositionDTO getPosition(){
        return new PositionDTO(2,"name", "desc");

    }
}
