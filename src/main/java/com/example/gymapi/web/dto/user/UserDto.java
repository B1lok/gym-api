package com.example.gymapi.web.dto.user;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String phoneNumber;
}
