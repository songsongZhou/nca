package com.tobi.nca.views.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("nca")
    public String nca(){

        return "nca/index";
    }
}
