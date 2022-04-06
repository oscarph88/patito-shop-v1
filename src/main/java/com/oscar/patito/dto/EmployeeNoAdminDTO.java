package com.oscar.patito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class EmployeeNoAdminDTO {
        private Integer id;
        private String corporateEmail;
        private String firstName;
        private String lastName;
        private String gender;
        private ContactDTO contact;
        private String position;
}
