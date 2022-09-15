package com.asg.springboot.app.controllers;

import com.asg.springboot.app.models.service.GeneradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {

    // De momento esto se deja asi. No es escalable esta muy acomplado
    // En un futuro si hubiera otros generadores implementaría una interfaz generador y
    // aplicaría inyección de dependencias entre los distintos tipos de generadores.
    @Autowired
    GeneradorService generadorService;

    @Value("${texto.com.asg.springboot.app.titulo}")
    private String titulo;

    @Value("${texto.com.asg.springboot.app.name}")
    private String nombreProyecto;

    @GetMapping({"/index", "/", "", "/home"})
    public String index(Model model) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("nombreProyecto", nombreProyecto);
        if(generadorService.soluciones != null) {
            model.addAttribute("solucionesList", generadorService.soluciones);
        }
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/generateSolutions", method = RequestMethod.POST)
    public ResponseEntity<String> generateSolutions(Principal principal, Model model, @RequestParam("defaultBlocks") ArrayList<String> defaultBlocks,
                                            @RequestParam("solutionSize") int solutionSize, @RequestParam(value = "mandatoryBlocks", defaultValue = "") ArrayList<String> mandatoryBlocks) {
        generadorService.soluciones = new ArrayList<>();
        ArrayList<String[]> solucionesList = generadorService.mostrarSoluciones(defaultBlocks, solutionSize, mandatoryBlocks);
        generadorService.soluciones = solucionesList;
        return new ResponseEntity<>("Cargando solcuiones", HttpStatus.OK);
    }
}
