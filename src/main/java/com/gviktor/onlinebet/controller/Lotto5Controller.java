package com.gviktor.onlinebet.controller;

import com.gviktor.onlinebet.service.Lotto5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lotto5")
public class Lotto5Controller {
    private Lotto5Service service;

    @Autowired
    public Lotto5Controller(Lotto5Service service) {
        this.service = service;
    }
}
