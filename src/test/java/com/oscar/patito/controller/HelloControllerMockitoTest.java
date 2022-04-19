package com.oscar.patito.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.oscar.patito.api.DataApi;
import com.oscar.patito.dto.PositionDTO;
import com.oscar.patito.service.PositionService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HelloControllerMockitoTest {

    @Mock
    private PositionService helloService;

    @InjectMocks
    private DataApi helloController;



    @BeforeEach
    void setMockOutput() {
        when(helloService.getWelcomeMessage()).thenReturn("Hello Mockito Test");
        PositionDTO pos= new PositionDTO(1, "name", "description");
        List<PositionDTO> posList = new ArrayList<>();
        posList.add(pos);
        System.out.println("Loading data");
        System.out.println(helloController.loadPositions(posList));
    }

    @Test
    public void shouldReturnDefaultMessage() {
        String response = helloController.greeting();
        assertThat(response).isEqualTo("Hello Mockito Test");
    }

    @Test
    public void shouldReturnDefaultMessage2() {
        List<PositionDTO> pos = helloController.listPositions();
        assertThat(pos).isNotEmpty();
    }
}