package com.dmc30.userapi.controller;

import com.dmc30.userapi.model.bean.UserDetails;
import com.dmc30.userapi.model.entity.Abonne;
import com.dmc30.userapi.model.entity.Employe;
import com.dmc30.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/check")
    public String status() {
        return "user-api's working!";
    }

    @PostMapping("/signin")
    public String createAbonne(@Valid @RequestBody Abonne abonne, @RequestParam int paysId) {
        userService.createAbonne(abonne, paysId);
        return "L'abonné "+abonne.getNumAbonne()+" "+abonne.getPrenom()+" "+abonne.getNom()+" a bien été enregistré.";
    }

    @PostMapping("/employe")
    public String createEmploye(@Valid @RequestBody Employe employe) {
        userService.createEmploye(employe);
        return "L'employe "+employe.getMatricule()+" "+employe.getPrenom()+" "+employe.getNom()+" a bien été enregistré.";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDetails userDetails) {
        String message = userService.authenticateUser(userDetails);
        return message;
    }

}
