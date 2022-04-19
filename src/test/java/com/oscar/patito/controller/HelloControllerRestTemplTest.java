package com.oscar.patito.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerRestTemplTest {

    @LocalServerPort
    private int port;

    private String url;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        //url = String.format("http://localhost:%d/", port);
        url= "http://localhost:8080/api/patito/data/greeting";
    }

    @Test
    public void greetingShouldReturnDefaultMessage() {
        System.out.println("URL "+url);
        assertThat(this.restTemplate.getForObject(url, String.class)).contains("Hello World!");
    }
}