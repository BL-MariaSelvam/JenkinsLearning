package com.demo.jenkinslearning.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("Hello")
    public String message(){
        return "Hello Freestyle Job";
    }

    @GetMapping("showmsg")
    public String show(){
        return "Hello Jenkins";
    }

    @GetMapping("display")
    public String disp(){
        return "I completed my Freestyle project ";
    }
}
