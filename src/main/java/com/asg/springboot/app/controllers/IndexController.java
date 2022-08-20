package com.asg.springboot.app.controllers;

import com.asg.springboot.app.models.service.GeneradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // De momento esto se deja asi. No es escalable esta muy acomplado
    // En un futuro si hubiera otros generadores implementaría una interfaz generador y
    // aplicaría inyección de dependencias entre los distintos tipos de generadores.
    private GeneradorService generadorService  =new GeneradorService();

    @Value("${texto.com.asg.springboot.app.titulo}")
    private String titulo;

    @Value("${texto.com.asg.springboot.app.name}")
    private String nombreProyecto;

    @GetMapping({"/index", "/", "", "/home"})
    public String index(Model model) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("nombreProyecto", nombreProyecto);
        model.addAttribute("prueba", generadorService.mostrarSoluciones());
        return "index";
    }
}
