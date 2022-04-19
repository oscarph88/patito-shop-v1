package com.oscar.patito.service;

import com.oscar.patito.PatitoApplication;
import com.oscar.patito.dto.PositionDTO;
import com.oscar.patito.model.Position;
import com.oscar.patito.repository.PositionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = PatitoApplication.class)
public class PositionServiceTest2 {

    @Mock
    PositionsRepository positionsRepository;

    @InjectMocks
    PositionService positionService;

    @Test
    void positionCreationTest(){
        Position pos = new Position(1,"UnoXX", "dos1");
        Position pos2 = positionsRepository.save(pos);
        //System.out.println(pos2.getName());
        List<PositionDTO> positions= new ArrayList<>();
        PositionDTO posDto = new PositionDTO(2,"Uno", "dos2");
        PositionDTO posDto2 = new PositionDTO(3,"Uno", "dos3");
        positions.add(posDto);
        positions.add(posDto2);
        //Employee employee  = getEmployee();
        //when(positionsRepository.save(pos)).thenReturn(pos);
        positionService.savePositions(positions);
        List<PositionDTO> positions2 = positionService.listPositions();
        Optional<Position> pos3 = positionsRepository.findById(2);
        if(pos3.isPresent())
            System.out.println(pos3.get().getName());
        assertEquals(positions2.size(), 20);
        //employeeService.saveEmployee(employee);
    }
}
