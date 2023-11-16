package com.example.gymapi.web.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gym/admin")
public class AdminController {



}
