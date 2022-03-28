package com.EmployeeManagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class GoogleMapController {

    @Value("${server.url}")
    private String employeeURL;

    @GetMapping("/findLocation")
    public String greeting(Model model) {
        model.addAttribute("employeeURL", employeeURL);
        return "searchLocation";
    }

}
